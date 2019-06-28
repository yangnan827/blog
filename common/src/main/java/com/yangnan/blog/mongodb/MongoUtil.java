/*
package com.yangnan.blog.mongodb;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@SuppressWarnings("ALL")
@Slf4j
@Component
public class MongoUtil implements InitializingBean {

    private static final int DEFAULT_SKIP = 0;
    private static final int DEFAULT_LIMIT = 200;

    */
/**
     * spring mongodb　集成操作类
     *//*

    @Autowired
    protected MongoTemplate mongoTemplate;

    */
/**
     * 通过条件查询实体(集合)
     *
     * @param query
     *//*

    public <T> List<T> find(Query query, Class<T> clazz) {
        return mongoTemplate.find(query, clazz);
    }

    public <T> List<T> find(Query query, String collectionName, Class<T> clazz) {
        return mongoTemplate.find(query, clazz, collectionName);
    }

    */
/**
     * 通过一定的条件查询一个实体
     *
     * @param query
     * @return
     *//*

    public <T> T findOne(Query query, Class<T> clazz) {
        return mongoTemplate.findOne(query, clazz);
    }

    public <T> T findOne(Query query, String collectionName, Class<T> clazz) {
        return mongoTemplate.findOne(query, clazz, collectionName);
    }

    */
/**
     * 通过条件查询更新数据
     *
     * @param query
     * @param update
     * @return
     *//*

    public void update(Query query, Update update) {
        mongoTemplate.findAndModify(query, update, this.getEntityClass());
    }

    public void update(Query query, Update update, String collectionName) {
        mongoTemplate.findAndModify(query, update, this.getEntityClass(), collectionName);
    }

    public void removeById(String id, String collectionName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, collectionName);
    }

    */
/**
     * 保存一个对象到mongodb
     *
     * @param entity
     * @return
     *//*

    public <T> T save(T entity) {
        mongoTemplate.insert(entity);
        return entity;
    }

    public <T> T save(T entity, String collectionName) {
        mongoTemplate.insert(entity, collectionName);
        return entity;
    }

    */
/**
     * 通过ID获取记录
     *
     * @param id
     * @return
     *//*

    public <T> T findById(String id, Class<T> clazz) {
        return mongoTemplate.findById(id, clazz);
    }

    */
/**
     * 通过ID获取记录,并且指定了集合名(表的意思)
     *
     * @param id
     * @param collectionName 集合名
     * @return
     *//*

    public <T> T findById(String id, String collectionName, Class<T> clazz) {
        return mongoTemplate.findById(id, clazz, collectionName);
    }

    */
/**
     * 分页查询
     *
     * @param page
     * @param query
     * @return
     *//*

    public <T> Page<T> findPage(Page<T> page, Query query, Class<T> clazz) {
        long count = this.count(query);
        page.setTotal(count);
        int pageNumber = page.getPageNumber();
        int pageSize = page.getPageSize();
        query.skip((pageNumber - 1) * pageSize).limit(pageSize);
        List<T> rows = this.find(query, clazz);
        page.setRows(rows);
        return page;
    }

    public <T> Page<T> findPage(Page<T> page, Query query, String collectionName, Class<T> clazz) {
        page.getClass().getGenericSuperclass();
        long count = this.count(query, collectionName);
        page.setTotal(count);
        int pageNumber = page.getPageNumber();
        int pageSize = page.getPageSize();
        query.skip((pageNumber - 1) * pageSize).limit(pageSize);
        List<T> rows = this.find(query, collectionName, clazz);
        page.setRows(rows);
        return page;
    }

    */
/**
     * 求数据总和
     *
     * @param query
     * @return
     *//*

    public long count(Query query) {
        return mongoTemplate.count(query, this.getEntityClass());
    }

    public long count(Query query, String collectionName) {
        return mongoTemplate.count(query, this.getEntityClass(), collectionName);
    }


    */
/**
     * 获取需要操作的实体类class
     *
     * @return
     *//*

    private Class getEntityClass() {
        Type superclass = this.getClass().getGenericSuperclass();
        Type[] actualTypeArguments = ((ParameterizedType) superclass).getActualTypeArguments();
        return (Class) actualTypeArguments[0];
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        assert null != mongoTemplate;
    }

    @Data
    public static class Page<T> {

        private Integer pageNumber;

        private Integer pageSize;

        private Long total;

        private List<T> rows;

    }
}
*/
