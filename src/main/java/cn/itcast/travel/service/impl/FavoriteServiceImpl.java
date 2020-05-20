package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

/**
 * @author zhou
 * @create 2020/4/7
 */
public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao fd = new FavoriteDaoImpl();
    @Override
    public boolean isFavorite(String rid, int uid) {
        //在service层转换
        Favorite f = fd.queryByRidAndUid(Integer.parseInt(rid), uid);
        return f != null;
    }

    @Override
    public int addFavorite(String ridStr, int uid) {
        return fd.insert(Integer.parseInt(ridStr), uid);
    }
}
