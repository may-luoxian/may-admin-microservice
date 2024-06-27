package com.may.gateway.filter;

import com.may.gateway.client.AuthClient;
import com.may.gateway.constant.WhiteListConstant;
import com.may.gateway.utils.AutowiredBeanUtil;
import com.may.utils.model.dto.ValidResourceDTO;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;


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

        return validateResources.doOnNext(e -> {
            System.out.println("feignClient请求结果是:" + e);
        }).then(chain.filter(exchange));

    }

    @Override
    public int getOrder() {
        return 1;
    }
}
