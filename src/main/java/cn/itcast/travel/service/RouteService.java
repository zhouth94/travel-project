package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

/**
 * @author zhou
 * @create 2020/4/5
 */
public interface RouteService {
    PageBean<Route> findByPage(int cid, int currentPage, int pageSize, String rname);

    Route findOne(int rid);
}
