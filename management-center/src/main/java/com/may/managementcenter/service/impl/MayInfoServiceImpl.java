package com.may.managementcenter.service.impl;

import com.may.managementcenter.service.MayInfoService;
import com.may.managementcenter.utils.IpUtil;
import com.may.utils.feignapi.RedisClient;
import com.may.utils.model.dto.RedisSetDTO;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;

import static com.may.utils.constant.CommonConstant.UNKNOWN;
import static com.may.utils.constant.RedisConstant.*;

@Service
public class MayInfoServiceImpl implements MayInfoService {

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private HttpServletRequest request;
    @Override
    public void report() {
        String ipAddress = IpUtil.getIpAddress(request);
        UserAgent userAgent = IpUtil.getUserAgent(request);
        Browser browser = userAgent.getBrowser();
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        String uuid = ipAddress + browser.getName() + operatingSystem.getName();
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());

        RedisSetDTO redisSetDTO = new RedisSetDTO();
        redisSetDTO.setKey(UNIQUE_VISITOR);
        redisSetDTO.setValue(md5);
        if (!redisClient.sIsMember(redisSetDTO)) {
            String ipSource = IpUtil.getIpSource(ipAddress);
            if (StringUtils.isNotBlank(ipSource)) {
                String ipProvince = IpUtil.getIpProvince(ipSource);
                redisClient.hIncr(VISITOR_AREA, ipProvince, 1L);
            } else {
                redisClient.hIncr(VISITOR_AREA, UNKNOWN, 1L);
            }
            redisClient.incr(BLOG_VIEWS_COUNT, 1);

            RedisSetDTO sAdd = new RedisSetDTO();
            sAdd.setKey(UNIQUE_VISITOR);
            sAdd.setValue(md5);
            redisClient.sAdd(sAdd);
        }
    }
}
