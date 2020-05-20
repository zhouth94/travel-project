package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * @author zhou
 * @create 2020/3/17
 */
public interface CategoryDao {
    public List<Category> queryAll();
}
