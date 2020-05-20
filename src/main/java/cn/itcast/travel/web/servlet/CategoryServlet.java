package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author zhou
 * @create 2020/3/18
 */
@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    private CategoryService cs = new CategoryServiceImpl();

    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        ArrayList<Category> list = (ArrayList<Category>) cs.findAll();

        //序列化json
        try {
            baseWriteValue(list, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
