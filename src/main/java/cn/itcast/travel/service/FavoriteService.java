package cn.itcast.travel.service;

/**
 * @author zhou
 * @create 2020/4/7
 */
public interface FavoriteService {
    boolean isFavorite(String rid, int uid);

    int addFavorite(String ridStr, int uid);
}
