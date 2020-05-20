package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

/**
 * @author zhou
 * @create 2020/4/5
 */
public class RouteServiceImpl implements RouteService {
    private RouteDao rd = new RouteDaoImpl();
    private RouteImgDao ridao = new RouteImgDaoImpl();
    private SellerDao sd = new SellerDaoImpl();
    private FavoriteDao fd = new FavoriteDaoImpl();
    @Override
    public PageBean<Route> findByPage(int cid, int currentPage, int pageSize, String rname) {
        PageBean<Route> pg = new PageBean<Route>();
        pg.setCurrentPage(currentPage);
        pg.setPageSize(pageSize);
        int totalCount = rd.queryTotalCount(cid, rname);
        pg.setTotalCount(totalCount);
        int start = (currentPage - 1) * pageSize;
        List<Route> list = rd.queryByPage(cid, start, pageSize, rname);
        pg.setList(list);
        int totalPage = 0;
        totalPage = (totalCount % pageSize == 0)? (totalCount / pageSize) : (totalCount / pageSize + 1);
        pg.setTotalPage(totalPage);
        return pg;
    }

    @Override
    public Route findOne(int rid) {
        //根据rid 查询route对象
        Route route = rd.queryByRid(rid);
        //根据rid查询图片集合
        List<RouteImg> imgList = ridao.queryByRid(rid);
        //将集合设置到route对象
        route.setRouteImgList(imgList);
        //根据route对象的sid查询商家
        Seller seller = sd.queryBySid(route.getSid());
        route.setSeller(seller);
        int count = fd.queryCountByRid(rid);
        route.setCount(count);
        return route;
    }
}
