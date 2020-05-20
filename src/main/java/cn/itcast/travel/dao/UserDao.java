package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

/**
 * @author zhou
 * @create 2020/3/13
 */
public interface UserDao {
    User findUserByUsername(String username);

    int insert(User user);

    User queryUserByCode(String code);

    int setStatus(User user);

    User queryUserByUsernameAndPassword(String username, String password);
}
