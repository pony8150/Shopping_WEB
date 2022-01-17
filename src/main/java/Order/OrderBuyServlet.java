package Order;

import com.alibaba.fastjson.JSON;
import model.DataBase;
import model.Message;
import model.T_Shop_Order;

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

@WebServlet(name = "OrderBuyServlet", urlPatterns = "/orderBuy")
public class OrderBuyServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //update, set the status = 1
        response.setCharacterEncoding("UTF-8");
        //通知浏览器使用utf-8解码
        response.setHeader("Content-type", "text/html;charset=utf-8");
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        int num = Integer.parseInt(request.getParameter("num"));
        String productId = request.getParameter("productId");


        String sql="update T_Shop_Order set status = 1 where orderId = ?";

        DataBase dataBase = new DataBase(sql, DataBase.PREPARED_STATEMENT);
        PreparedStatement preStatement = dataBase.getPreStatement();

        int count = 0;
        try {
            preStatement.setInt(1,orderId);

            count = preStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }//返回值表示增删改几条数据
        //处理结果
        if(count>0){
            System.out.println("购买成功!");
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
        if(count > 0) {
            message.setCode(0);
            message.setMsg("购买成功!");
            System.out.println("购买成功!");
            //对库存和已经买数进行更新
            String sql_1 = "update T_Shop_ProductInfo set Stock -= ? , Selled += ? where ProductId = ?";

            DataBase dataBase_1 = new DataBase(sql_1, DataBase.PREPARED_STATEMENT);
            PreparedStatement preStatement_1 = dataBase_1.getPreStatement();

            int count_1 = 0;
            try {
                preStatement_1.setInt(1,num);
                preStatement_1.setInt(2,num);
                preStatement_1.setString(3,productId);

                count_1 = preStatement_1.executeUpdate();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }//返回值表示增删改几条数据
            //处理结果
            if(count_1>0){
                System.out.println("修改库存和已买数成功!");
            }
            //关闭
            try {
                dataBase_1.closePreStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                dataBase_1.closeConnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else
        {
            message.setCode(1);
            message.setMsg("购买失败!");
            System.out.println("购买失败");
        }
        message.setCount(count);

        String  content=  JSON.toJSONString(message);
        PrintWriter out = response.getWriter();
        out.write(content);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
