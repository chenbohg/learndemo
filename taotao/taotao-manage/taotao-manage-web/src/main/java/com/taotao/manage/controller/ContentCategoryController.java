package com.taotao.manage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.manage.pojo.ContentCategory;
import com.taotao.manage.service.ContentCategoryService;

@RequestMapping("content/category")
@Controller
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService categoryService;

    /**
     * 显示内容分类树
     * 
     * @param parentId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ContentCategory>> queryContentCategoryList(
            @RequestParam(value = "id", defaultValue = "0") Long parentId) {
        try {
            List<ContentCategory> list = this.categoryService.queryContentCategoryList(parentId);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 新增分类
     */
    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ContentCategory> saveContentCategory(@RequestParam("parentId") Long parentId,
            @RequestParam("name") String name) {
        try {
            ContentCategory contentCategory = this.categoryService.saveContentCategory(parentId,name);
            return ResponseEntity.status(HttpStatus.CREATED).body(contentCategory);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    /**
     * 重命名
     */
    @RequestMapping(method=RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Void> updateContentCategory(ContentCategory contentCategory) {
        try {
            this.categoryService.updateContentCategory(contentCategory);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    
    /**
     * 删除
     */
    @RequestMapping(method=RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteContentCategory(ContentCategory contentCategory){
        try {
            //递归所有的子节点
            List<Object> deleteIds = new ArrayList<Object>();
            deleteIds.add(contentCategory.getId());
            
            findAllSubNode(deleteIds,contentCategory.getId());
            
            //执行批量删除
            this.categoryService.deleteByIds(ContentCategory.class, deleteIds);
            
            //更父节点为isparent
            //判断是否是有子类目
            ContentCategory param  = new ContentCategory();
            param.setParentId(contentCategory.getParentId());
            
            List<ContentCategory> contentCategories = this.categoryService.queryListByWhere(param);
            if (contentCategories.isEmpty()) {
                ContentCategory parent = new ContentCategory();
                parent.setId(contentCategory.getParentId());
                parent.setIsParent(false);
                parent.setUpdated(new Date());
                this.categoryService.updateSelective(parent);
            }
            
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    /**
     * 递归所有子节点
     * @param deleteIds
     * @param id
     */
    private void findAllSubNode(List<Object> deleteIds, Long parentId) {
        ContentCategory param  = new ContentCategory();
        param.setParentId(parentId);
        
        List<ContentCategory> contentCategories = this.categoryService.queryListByWhere(param);
        
        for (ContentCategory contentCategory : contentCategories) {
            deleteIds.add(contentCategory.getId());
            
            //开始递归
            if (contentCategory.getIsParent()) {
                findAllSubNode(deleteIds, contentCategory.getId());
            }
        }
        
    }
    
    
}
