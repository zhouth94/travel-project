package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

/**
 * @author zhou
 * @create 2020/4/7
 */
public interface RouteImgDao {
    List<RouteImg> queryByRid(int rid);
}
