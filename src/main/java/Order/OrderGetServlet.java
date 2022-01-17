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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "OrderGetServlet", urlPatterns = "/orderGet")
public class OrderGetServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Object> list = new ArrayList<Object>();

        if(request.getSession().getAttribute("userId") == null){
            //用户尚未登录
            Message message=new Message();
            message.setCode(1);
            message.setMsg("请先登录，再打开购物车");
            System.out.println("无法打开购物车");
            message.setCount(0);

            String  content=  JSON.toJSONString(message);
            PrintWriter out = response.getWriter();
            out.write(content);

            return ;
        }
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        String sql="select * from [dbo].[T_Shop_Order] where userId = ? and status=0";
        DataBase dataBase = new DataBase(sql, DataBase.PREPARED_STATEMENT);

        PreparedStatement preStatement = dataBase.getPreStatement();

        int count_1 = 0;
        try {
            preStatement.setInt(1,userId);
            //            count_1 = preStatement.executeUpdate();
            ResultSet resultSet = preStatement.executeQuery();
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
