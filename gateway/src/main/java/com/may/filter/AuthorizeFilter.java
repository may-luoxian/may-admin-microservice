package com.may.filter;

import com.may.client.AuthClient;
import com.may.utils.AutowiredBeanUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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

        //获取的关键看这里，在用的时候在获取bean
        AuthClient authClient = AutowiredBeanUtil.getBean(AuthClient.class);
        //异步调用feign服务接口
        Mono<String> sysLogListWithPage = authClient.validateResources();
        return sysLogListWithPage.doOnNext(e -> {
            System.out.println("feignClient请求结果是:" + e);
        }).then(chain.filter(exchange));
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
