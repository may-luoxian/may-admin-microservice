package com.may.managementcenter.controller;

import com.may.managementcenter.model.dto.TopAndFeaturedArticlesDTO;
import com.may.managementcenter.service.ArticleService;
import com.may.utils.model.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "文章模块")
@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @ApiOperation(value = "获取指定和推荐文章")
    @GetMapping("/articles/topAndFeatured")
    public ResultVO<TopAndFeaturedArticlesDTO> listTopAndFeaturedArticles() {
        return ResultVO.ok(articleService.listTopAndFeaturedArticles());
    }
}
