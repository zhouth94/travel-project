package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author zhou
 * @create 2020/3/16
 */
@WebServlet(value = "/user/*")

@SuppressWarnings("Duplicates")
public class UserServlet extends BaseServlet {
    private UserService us = new UserServiceImpl();

    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //首先判断验证码是否正确
        String check = request.getParameter("check");
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        //移除验证码的缓存
        request.getSession().removeAttribute("CHECKCODE_SERVER");

        if(!checkcode_server.equalsIgnoreCase(check) || null==check){ //验证码不匹配
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");

            String json = baseWriteValueAsString(info);
            //将json数据写回客户端
            response.setContentType("application/json;charset=utf-8");
            response.getOutputStream().write(json.getBytes());
//            baseWwriteValue(info, response);
            return;
        }

        Map<String, String[]> map = request.getParameterMap();
        User user = new User();

        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        boolean res = us.regist(user);

        ResultInfo info = new ResultInfo();
        if(res){ //注册成功
            info.setFlag(true);
        }else {
            info.setFlag(false);
            info.setErrorMsg("用户名已存在，注册失败");
        }

        //将info对象序列化为json
        String json = baseWriteValueAsString(info);

        //将json数据写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getOutputStream().write(json.getBytes());
    }

    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        String msg = null;
        if(null != code){
            //调用service进行邮箱激活操作
            boolean res = us.active(code);
            if(res) { //激活成功
                msg = "激活成功，请<a href='login.html'>登录</a>";
            }else {
                msg = "激活失败，请联系管理员";
            }
        }
        response.getWriter().write(msg);
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");

        Map<String, String[]> map = request.getParameterMap();
        User u = new User();
        try {
            BeanUtils.populate(u, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        User loginUser = us.login(u);

        ResultInfo info = new ResultInfo();
        if(loginUser == null) {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        if(loginUser != null && !"Y".equals(loginUser.getStatus())) {
            info.setFlag(false);
            info.setErrorMsg("您未激活，请激活后再登录");
        }
        if(loginUser != null && "Y".equals(loginUser.getStatus())) {
            info.setFlag(true);
            request.getSession().setAttribute("loginUser", loginUser);
        }

        /*ObjectMapper mapper = new ObjectMapper();

        //设置响应数据的格式数据 --> 重要 ，否则前台登录页面不会跳转
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getWriter(), info);*/
        baseWriteValue(info, response);
    }

    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User loginUser = (User) request.getSession().getAttribute("loginUser");

        ObjectMapper mapper = new ObjectMapper();

       /* //设置响应格式
        response.setContentType("application/json;charset=utf-8");
        String json = mapper.writeValueAsString(loginUser);

        response.getOutputStream().write(json.getBytes());*/
       baseWriteValue(loginUser, response);
    }

    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

}
