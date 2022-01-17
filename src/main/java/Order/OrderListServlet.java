package Order;

import com.alibaba.fastjson.JSON;
import model.DataBase;
import model.Message;
import model.T_Shop_Order;
import model.T_Shop_ProductInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "OrderListServlet", urlPatterns = "/orderList")
public class OrderListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Object> list = new ArrayList<Object>();
        String sql = "select * from  T_Shop_Order";
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
                T_Shop_Order order = new T_Shop_Order();

                order.setOrderId(resultSet.getInt("orderId"));
                order.setUserId(resultSet.getInt("userId"));
                order.setProductId(resultSet.getInt("productId"));
                order.setProductName(resultSet.getString("productName"));
                order.setNum(resultSet.getInt("num"));
                order.setStatus(resultSet.getInt("status"));

                list.add(order);
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
