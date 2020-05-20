package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

/**
 * @author zhou
 * @create 2020/3/13
 */
public class UserServiceImpl implements UserService {
    UserDao ud = new UserDaoImpl();

    @Override
    public boolean regist(User user) {
        User u = ud.findUserByUsername(user.getUsername());
        if(u != null){ //用户已存在
            return false;
        }
        //为注册用户设置信息
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");

        //注册的方法
        int res = ud.insert(user);
        if(res > 0){
            //注册成功，发送激活邮件
            MailUtils.sendMail(user.getEmail(), "<a href='https://localhost:8080/travel/activeServlet?code="+ user.getCode() +"'>点击激活【旅游网】</a>", "激活邮件");
            return true;
        }
        return false;
    }

    @Override
    public boolean active(String code) {
        if (code != null){
            User user = ud.queryUserByCode(code);
            if(user != null){
                //为user设置status
                int flag = ud.setStatus(user);
                if(flag > 0){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public User login(User u) {
        User user = null;
        if(u != null){
            user = ud.queryUserByUsernameAndPassword(u.getUsername(), u.getPassword());
        }
        return user;
    }


}
