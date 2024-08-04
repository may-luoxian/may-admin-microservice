package com.may.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.may.gateway.client.AuthClient;
import com.may.gateway.constant.WhiteListConstant;
import com.may.gateway.utils.AutowiredBeanUtil;
import com.may.utils.model.dto.ValidResourceDTO;
import com.may.utils.model.vo.ResultVO;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * 鉴权过滤器
 * 1.拦截所有接收到的请求
 * 2.通过open-feign调用用户中心进行鉴权
 * 3.根据用户中心返回值分别处理
 */
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //TODO:Auth模块未完成，暂时注掉身份认证

        // 获取身份验证所需参数
        String method = exchange.getRequest().getMethod().toString();
        String url = exchange.getRequest().getURI().toString();
        URL urlObject;
        try {
            urlObject = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        String path = urlObject.getPath();

        // 判断接口是否存在于白名单，若在直接放行，不进行身份校验
        String[] whiteList = WhiteListConstant.getWhiteList();
        boolean contains = Arrays.asList(whiteList).contains(path);
        if (contains) {
            return chain.filter(exchange);
        }

        //异步调用feign服务接口
        AuthClient authClient = AutowiredBeanUtil.getBean(AuthClient.class);

        ValidResourceDTO validResourceDTO = new ValidResourceDTO();
        validResourceDTO.setUrl(path);
        validResourceDTO.setMethod(method);
        Mono<String> validateResources = authClient.validateResources(validResourceDTO);

        return validateResources.flatMap(e -> {
            System.out.println("feignClient请求结果是:" + e);
            JSONObject jsonObject = JSON.parseObject(e);
            String code = jsonObject.getString("code");
            String message = jsonObject.getString("message");
            Boolean flag = jsonObject.getBoolean("flag");
            if (code == "20000") {
                return chain.filter(exchange);
            } else {
                return Mono.defer(() -> {
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("code", code);
                    resultMap.put("message", message);
                    resultMap.put("flag", flag);
                    byte[] bytes;
                    try {
                        bytes = new ObjectMapper().writeValueAsBytes(resultMap);
                    } catch (JsonProcessingException ex) {
                        throw new RuntimeException(ex);
                    }

                    ServerHttpResponse response = exchange.getResponse();
                    response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_UTF8.toString());
                    DataBuffer buffer = response.bufferFactory().wrap(bytes);
                    return response.writeWith(Flux.just(buffer));
                });

//                TODO:此处记录ServerHttpResponseDecorator重写
//                ServerHttpResponse response = exchange.getResponse();
//
//                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {
//                    @Override
//                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
//                        if (body instanceof Flux) {
//                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
//                            return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
//                                DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
//                                DataBuffer join = dataBufferFactory.join(dataBuffers);
//                                byte[] content = new byte[join.readableByteCount()];
//                                join.read(content);
//                                DataBufferUtils.release(join);
//                                String responseData = new String(content, StandardCharsets.UTF_8);
//                                responseData = responseData.replaceAll(":null", ":\"\"");
//                                byte[] uppedContent = responseData.getBytes(StandardCharsets.UTF_8);
//
////                                byte[] content = new byte[dataBuffer.readableByteCount()];
////                                dataBuffer.read(content);
////                                //释放掉内存
////                                DataBufferUtils.release(dataBuffer);
////                                String string = new String(content, StandardCharsets.UTF_8);
////                                //修改response之后的字符串
////                                String lastStr = JSON.parseObject(string, String.class);
////                                byte[] bytes = lastStr.getBytes();
//                                return dataBufferFactory.wrap(uppedContent);
//                            }));
//                        } else {
//                            // if body is not a flux. never got there.
//                            return super.writeWith(body);
//                        }
//
//                    }
//                };
//
//                byte[] bytes = e.getBytes();
//                DataBuffer wrap = response.bufferFactory().wrap(bytes);
//                Mono<Void> voidMono = decoratedResponse.writeWith(Flux.just(wrap));
//                return voidMono;
            }
        });
    }

    @Override
    public int getOrder() {
        return 1;
    }

    static class Result implements Serializable {
        private Boolean flag;
        private Integer code;
        private String message;

        public Result() {
        }

        public Result(Boolean flag, Integer code, String message) {
            this.flag = flag;
            this.code = code;
            this.message = message;
        }

        public Boolean getFlag() {
            return flag;
        }

        public void setFlag(Boolean flag) {
            this.flag = flag;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public byte[] toByteArray() {
            ByteBuf buf = Unpooled.buffer(32);
            buf.writeBoolean(getFlag());
            buf.writeInt(getCode());
            buf.writeBytes(getMessage().getBytes());
            return buf.array();
        }
    }
}
