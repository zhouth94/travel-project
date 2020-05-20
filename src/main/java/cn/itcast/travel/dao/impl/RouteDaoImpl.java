package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhou
 * @create 2020/4/5
 */
public class RouteDaoImpl implements RouteDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int queryTotalCount(int cid, String rname) {
        String sql = "select count(*) from tab_route where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if (cid > 0) {
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if(rname != null && rname.length() > 0) {
            sb.append(" and rname like ? "); //模糊查询
            params.add("%"+rname+"%");
        }
        sql = sb.toString();
        System.out.println(sql);
        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    @Override
    public List<Route> queryByPage(int cid, int start, int pageSize, String rname) {
        String sql = "select * from tab_route where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if (cid > 0) {
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if(rname != null && rname.length() > 0) {
            sb.append(" and rname LIKE ? ");
            params.add("%"+rname+"%");
        }
        sb.append(" limit ?,? ");
        sql = sb.toString();
        params.add(start);
        params.add(pageSize);
        List<Route> list = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
        return list;
    }

    @Override
    public Route queryByRid(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
    }
}
