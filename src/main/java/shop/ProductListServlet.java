package shop;

import com.alibaba.fastjson.JSON;
import model.DataBase;
import model.Message;
import model.T_Shop_ProductInfo;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

@WebServlet(name = "ProductListServlet", urlPatterns = "/productList")
public class ProductListServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        ArrayList<Object> list = new ArrayList<Object>();
//        //建立连接
//        Connection connection = null;
//        String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";//SQL数据库引擎
////        String dbURL="jdbc:sqlserver://47.98.59.211:1433;DatabaseName=192";//数据源  ！！！注意若出现加载或者连接数据库失败一般是这里出现问题
////        String Name="192";
////        String Pwd="192";
//        String dbURL="jdbc:sqlserver://127.0.0.1:1433;DatabaseName=192";//数据源  ！！！注意若出现加载或者连接数据库失败一般是这里出现问题
//        String Name="sa";
//        String Pwd="8150";
//
//
//        try {
//            Class.forName(driverName);
//            connection = DriverManager.getConnection(dbURL, Name, Pwd);
//            System.out.println("数据库连接成功!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("数据库连接失败");
//        }
//
//        //建立通讯声明
//        Statement statement = null;
//        try {
//            statement = connection.createStatement();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        String sql = "select * from  T_Shop_ProductInfo";
        DataBase dataBase = new DataBase(sql, DataBase.NORMAL_STATEMENT);
        ResultSet resultSet = null;
        try {
            resultSet = dataBase.executeSQL();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
//            resultSet = statement.executeQuery(sql);
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
            e.printStackTrace();
        }
        //关闭连接
        try {
            dataBase.closeStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            dataBase.closeConnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //编码设置
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "text/html;charset=utf-8");

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
