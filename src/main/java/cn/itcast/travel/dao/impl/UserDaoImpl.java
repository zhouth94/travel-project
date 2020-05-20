package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author zhou
 * @create 2020/3/13
 */
public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findUserByUsername(String username) {
        User user = null;
        try{
            String sql = "select * from tab_user where username = ?";
            /**
             * queryForObject 方法，没有查询到会抛异常，而不会返回null,所以需要异常处理
             */
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        }catch(Exception e){

        }
        return user;
    }

    @Override
    public int insert(User user) {
        String sql = "insert into tab_user(username, password, name, birthday, sex, telephone, email, status, code) " +
                " values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return template.update(sql, user.getUsername(), user.getPassword(), user.getName(),
                user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(), user.getStatus(), user.getCode());
    }

    @Override
    public User queryUserByCode(String code) {
        User user = null;
        try {
            String sql = "select * from tab_user where code = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int setStatus(User user) {
        String sql = "update tab_user set status = 'Y' where uid = ?";
        int res = template.update(sql, user.getUid());
        return res;
    }

    @Override
    public User queryUserByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            String sql = "select * from tab_user where username = ? and password = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }
}
