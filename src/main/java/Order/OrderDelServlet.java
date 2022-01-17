package Order;

import com.alibaba.fastjson.JSON;
import model.DataBase;
import model.Message;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "OrderDelServlet", urlPatterns = "/orderDel")
public class OrderDelServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer orderId = Integer.parseInt(request.getParameter("orderId"));

        String sql="delete from [dbo].[T_Shop_Order] where orderId = ?";

        DataBase dataBase = new DataBase(sql, DataBase.PREPARED_STATEMENT);
        PreparedStatement preStatement = dataBase.getPreStatement();

        int count_1 = 0;
        try {
            preStatement.setInt(1,orderId);
            count_1 = preStatement.executeUpdate();
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

        Message message=new Message();
        if(count_1 == 1) {
            message.setCode(0);
            message.setMsg("删除成功");
            System.out.println("删除数据成功");
        }else
        {
            message.setCode(1);
            message.setMsg("删除失败");
            System.out.println("删除数据失败");
        }
        message.setCount(count_1);

        String  content=  JSON.toJSONString(message);
        PrintWriter out = response.getWriter();
        out.write(content);


    }
}
