package Order;

import com.alibaba.fastjson.JSON;
import model.DataBase;
import model.Message;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "OrderAddServlet", urlPatterns = "/orderAdd")
public class OrderAddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        //通知浏览器使用utf-8解码
        response.setHeader("Content-type", "text/html;charset=utf-8");

        Integer productId=Integer.parseInt(request.getParameter("productId"));
        String productName=request.getParameter("productName");
        Integer num=Integer.parseInt(request.getParameter("num"));
        Integer status=Integer.parseInt(request.getParameter("status"));

        if(request.getSession().getAttribute("userId") == null){
            //用户尚未登录
            Message message=new Message();
            message.setCode(1);
            message.setMsg("添加订单失败");
            System.out.println("添加订单失败");
            message.setCount(0);

            String  content=  JSON.toJSONString(message);
            PrintWriter out = response.getWriter();
            out.write(content);

            return ;
        }
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        String sql="insert into  [dbo].[T_Shop_Order](userId, productId, productName, num, status) values (?, ?, ?, ?, ?)";
        DataBase dataBase = new DataBase(sql, DataBase.PREPARED_STATEMENT);
        PreparedStatement preStatement = dataBase.getPreStatement();

        int count_1 = 0;
        try {
            preStatement.setInt(1,userId);
            preStatement.setInt(2,productId);
            preStatement.setString(3,productName);
            preStatement.setInt(4,num);
            preStatement.setInt(5,status);

            count_1 = preStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }//返回值表示增删改几条数据
        //处理结果
        if(count_1>0){
            System.out.println("插入订单成功!");
        }

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


        Message message=new Message();
        if(count_1>0) {
            message.setCode(0);
            message.setMsg("添加订单成功");
            System.out.println("添加订单成功");
        }else
        {
            message.setCode(1);
            message.setMsg("添加订单失败");
            System.out.println("添加订单失败");
        }
        message.setCount(count_1);

        String  content=  JSON.toJSONString(message);
        PrintWriter out = response.getWriter();
        out.write(content);
    }
}
