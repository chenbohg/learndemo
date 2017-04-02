package com.taotao.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.manage.mapper.ContentCategoryMapper;
import com.taotao.manage.pojo.ContentCategory;

@Service
public class ContentCategoryService extends BaseService<ContentCategory>{
    
    @Autowired
    private ContentCategoryMapper contentCategoryMapper;

    public List<ContentCategory> queryContentCategoryList(Long parentId) {
        ContentCategory contentCategory = new ContentCategory();
        contentCategory.setParentId(parentId);
        return this.contentCategoryMapper.select(contentCategory);
    }

    /**
     * 新增分类
     * @param parentId
     * @param name
     * @return
     */
    public ContentCategory saveContentCategory(Long parentId, String name) {
        ContentCategory contentCategory = new ContentCategory();
        contentCategory.setName(name);
        contentCategory.setId(null);
        contentCategory.setIsParent(false);
        contentCategory.setParentId(parentId);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(contentCategory.getCreated());
        contentCategory.setSortOrder(1);
        contentCategory.setStatus(1);
        this.contentCategoryMapper.insertSelective(contentCategory);
        
        //判断父类
        ContentCategory parent = this.contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parent.getIsParent()) {
            parent.setIsParent(true);
            parent.setUpdated(new Date());
            this.contentCategoryMapper.updateByPrimaryKeySelective(parent);
        }
        return contentCategory;
    }

    /**
     * 更新分类
     * @param contentCategory
     */
    public void updateContentCategory(ContentCategory contentCategory) {
        contentCategory.setUpdated(new Date());
        this.contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
    }

}
