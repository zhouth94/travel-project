package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

/**
 * @author zhou
 * @create 2020/4/7
 */
public interface FavoriteDao {
    Favorite queryByRidAndUid(int rid, int uid);

    int queryCountByRid(int rid);

    int insert(int rid, int uid);
}
