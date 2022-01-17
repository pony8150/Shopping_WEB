package shop;

import com.alibaba.fastjson.JSON;
import model.DataBase;
import model.Message;
import model.T_Shop_ProductInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "ProductGetServlet", urlPatterns = "/productGet")
public class ProductGetServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Object> list = new ArrayList<Object>();
        int productId = Integer.parseInt(request.getParameter("productId"));
        String sql="select * from [dbo].[T_Shop_ProductInfo] where ProductId = ?";
        //建立连接
        DataBase dataBase = new DataBase(sql, DataBase.PREPARED_STATEMENT);
        //建立通讯声明
        PreparedStatement preStatement = dataBase.getPreStatement();
        //执行语句
        int count_1 = 0;
        try {
            preStatement.setInt(1,productId);
            //            count_1 = preStatement.executeUpdate();
            ResultSet resultSet = preStatement.executeQuery();
            while(resultSet.next()){
                T_Shop_ProductInfo shop = new T_Shop_ProductInfo();

                shop.setProductId(resultSet.getInt("ProductId"));
                shop.setProductName(resultSet.getString("ProductName"));
                shop.setSize(resultSet.getString("Size"));
                shop.setPrice(resultSet.getFloat("Price"));
                shop.setPicPath(resultSet.getString("PicPath"));
                shop.setDescription(resultSet.getString("Description"));
                shop.setStock(resultSet.getInt("Stock"));
                shop.setSelled(resultSet.getInt("Selled"));

                list.add(shop);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }//返回值表示增删改几条数据
        //关闭连接
        try {
            dataBase.closePreStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            dataBase.closeConnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.setCharacterEncoding("UTF-8");
        //通知浏览器使用utf-8解码
        response.setHeader("Content-type", "text/html;charset=utf-8");
        //处理结果
        Message message = new Message();
        message.setCode(0);
        message.setMsg("读取成功");
        message.setCount(list.size());
        message.setData(list); //??

        String content = JSON.toJSONString(message);

        PrintWriter out = response.getWriter();
        out.write(content);

    }
}
