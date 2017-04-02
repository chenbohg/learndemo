package com.taotao.manage.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.taotao.manage.mapper.ContentMapper;
import com.taotao.manage.pojo.Content;

@Service
public class ContentService extends BaseService<Content>{
    
    @Autowired
    private ContentMapper contentMapper;

    /**
     * 内容列表
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    public PageInfo<Content> queryContentByPage(Long categoryId, Integer page, Integer rows) {
        Content content = new Content();
        content.setCategoryId(categoryId);
        PageInfo<Content> queryPageListByWhere = this.queryPageListByWhere(content, page, rows);
        return queryPageListByWhere;
    }

    /**
     * 新增
     * @param content
     */
    public void saveContent(Content content) {
        content.setId(null);
        content.setCreated(new Date());
        content.setUpdated(content.getCreated());
        this.saveSelective(content);
    }

}
