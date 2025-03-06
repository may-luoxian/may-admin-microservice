package com.syk.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syk.oj.entity.OjTag;
import com.syk.oj.mapper.OjTagMapper;
import com.syk.oj.service.OjTagService;
import org.springframework.stereotype.Service;

@Service
public class OjTagServiceImpl extends ServiceImpl<OjTagMapper, OjTag> implements OjTagService {
}
