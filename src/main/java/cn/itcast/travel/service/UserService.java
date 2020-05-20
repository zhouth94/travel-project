package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

/**
 * @author zhou
 * @create 2020/3/13
 */
public interface UserService {
    boolean regist(User user);

    boolean active(String code);

    User login(User u);
}
