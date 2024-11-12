package com.may.managementcenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.may.managementcenter.entity.Article;
import com.may.managementcenter.model.dto.ArticleCardDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleMapper extends BaseMapper<Article> {
    List<ArticleCardDTO> listTopAndFeaturedArticles();
}
