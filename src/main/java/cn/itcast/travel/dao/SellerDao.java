package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

/**
 * @author zhou
 * @create 2020/4/7
 */
public interface SellerDao {
    Seller queryBySid(int sid);
}
