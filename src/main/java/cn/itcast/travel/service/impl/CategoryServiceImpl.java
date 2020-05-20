package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author zhou
 * @create 2020/3/18
 */
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao cd = new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {
        //从redis中查询
        //1.获取jedis客户端
        Jedis jedis = JedisUtil.getJedis();
        //2.使用sortedset排序查询
        //需要查询sortedset中的分数cid和值cname
//        Set<String> set = jedis.zrange("category", 0, -1);
        Set<Tuple> set = jedis.zrangeWithScores("category", 0, -1);

        List<Category> cateList = null;
        if(set == null || set.size() == 0) {
            System.out.println("从数据库查询到...");
            //若为空，则需要从数据库中查询，再将结果存入redis
            cateList = cd.queryAll();
            //再将集合数据存入到redis ：zadd key score value
            for (int i = 0; i < cateList.size(); i++) {
                jedis.zadd("category", cateList.get(i).getCid(), cateList.get(i).getCname());
            }
        }else {
            System.out.println("从redis查询到...");
            //若set中查询到数据，则返回set
            cateList = new ArrayList<Category>();
            for (Tuple tuple : set) {
                Category c = new Category();
                c.setCid((int)tuple.getScore());
                c.setCname(tuple.getElement());
                cateList.add(c);
            }
        }
        return cateList;
    }
}
