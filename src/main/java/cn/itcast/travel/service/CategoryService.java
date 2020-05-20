package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * @author zhou
 * @create 2020/3/18
 */
public interface CategoryService {
    public List<Category> findAll();
}
