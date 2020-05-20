package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * @author zhou
 * @create 2020/4/5
 */
public interface RouteDao {
    public int queryTotalCount(int cid, String rname);
    public List<Route> queryByPage(int cid, int start, int pageSize, String rname);
    Route queryByRid(int id);
}
