package com.may.managementcenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.may.managementcenter.entity.Article;
import com.may.managementcenter.model.dto.TopAndFeaturedArticlesDTO;

public interface ArticleService extends IService<Article> {
    TopAndFeaturedArticlesDTO listTopAndFeaturedArticles();
}
