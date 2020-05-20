package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author zhou
 * @create 2020/3/17
 */
public class CategoryDaoImpl implements CategoryDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());


    @Override
    public List<Category> queryAll() {
        String sql = "SELECT * FROM tab_category";
        List<Category> list = template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
        if(list.size() > 0) {
            return list;
        }
        return null;
    }
}
