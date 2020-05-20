package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhou
 * @create 2020/4/5
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService rs = new RouteServiceImpl();
    private FavoriteService fs = new FavoriteServiceImpl();
    public void findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //接收参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        //接收路线名称 rname
        request.setCharacterEncoding("utf-8");
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("ISO-8859-1"),"utf-8");
        System.out.println("rname:" + rname);

        int currentPage = 0;
        if(currentPageStr != null && currentPageStr.length() >0) {
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage = 1;
        }
        int pageSize = 0;
        if(pageSizeStr != null && pageSizeStr.length() >0) {
            pageSize = Integer.parseInt(pageSizeStr);
        }else {
            pageSize = 5;
        }
        int cid = 0;
        if(cidStr != null && cidStr.length() >0 && !"null".equals(cidStr)) { //cid : "null"
            cid = Integer.parseInt(cidStr);
        }
        PageBean<Route> pageBean = rs.findByPage(cid, currentPage, pageSize, rname);

        //将pageBean对象序列化为json
        ObjectMapper mapper = new ObjectMapper();
        //设置响应数据的格式数据 --> 重要 ，否则前台登录页面不会跳转
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getWriter(), pageBean);

    }

    /**
     * 根据rid查询一条旅游线路的信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ridStr = request.getParameter("rid");
        int rid = 0;
        if(ridStr != null && ridStr.length() > 0) {
            rid = Integer.parseInt(ridStr);
        }
        Route route = rs.findOne(rid);
        ///转为json写回客户端
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), route);
    }

    /**
     * 判断当前登录用户是否收藏该指定线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ridStr = request.getParameter("rid");
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        int uid;
        if (loginUser == null) { //未登录
            uid = 0;
        }else {
            uid = loginUser.getUid();
        }
        boolean flag = fs.isFavorite(ridStr, uid);
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), flag);
    }

    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ridStr = request.getParameter("rid");
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        int uid;
        if (loginUser == null) { //未登录
            return;
        }else {
            uid = loginUser.getUid();
        }
        fs.addFavorite(ridStr, uid);
    }
}
