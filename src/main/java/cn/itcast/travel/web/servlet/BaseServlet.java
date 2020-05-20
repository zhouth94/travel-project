package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author zhou
 * @create 2020/3/16
 */
//@WebServlet(value = "/BaseServlet")
public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getContextPath()); //获取虚拟路径
        //获取路径
        String uri = req.getRequestURI(); // /travel
//        System.out.println("uri路径:"  + uri); //  /travel/user/login
        //通过uri获取执行的方法名
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);
//        System.out.println("methodName:" + methodName);

        //this -> 谁调用我，我代表谁
//        System.out.println(this); //cn.itcast.travel.web.servlet.UserServlet@18f7b20e

        //获取UserServlet的class字节码对象
        Class<? extends BaseServlet> clz = this.getClass();
        try {
            //获取受保护的方法
            Method method = clz.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
//            System.out.println(method);
            method.setAccessible(true);

            //执行方法
            method.invoke(this, req, resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    /**
     * 序列化json，并写回客户端浏览器
     * @param obj
     * @param response
     * @throws IOException
     */
    public void baseWriteValue(Object obj, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        //设置响应数据的格式数据 --> 重要 ，否则前台登录页面不会跳转
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), obj);
    }

    /**
     * 返回json字符串
     * @param obj
     * @return
     */
    public String baseWriteValueAsString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(obj);
        return json;
    }

}
