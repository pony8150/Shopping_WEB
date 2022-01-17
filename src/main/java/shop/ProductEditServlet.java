package shop;

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

@WebServlet(name = "ProductEditServlet", urlPatterns = "/productEdit")
public class ProductEditServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ProductName=request.getParameter("ProductName");
        String Size=request.getParameter("Size");
        String Description=request.getParameter("Description");
        Float Price=Float.parseFloat(request.getParameter("Price"));
        Integer Stock=Integer.parseInt(request.getParameter("Stock"));
        Integer Selled=Integer.parseInt(request.getParameter("Selled"));
        Integer ProductId=Integer.parseInt(request.getParameter("ProductId"));


//        String sql="insert into  [dbo].[T_Shop_ProductInfo] (ProductName,[Size],Price,Stock,Selled)  values (?,?,?,?,?) ";
        String sql ="update [dbo].[T_Shop_ProductInfo] set ProductName=?, Size=?, Price=?, Description=?, Stock=?, Selled=? where ProductId =?";

        DataBase dataBase = new DataBase(sql, DataBase.PREPARED_STATEMENT);
        PreparedStatement preStatement = dataBase.getPreStatement();
        int count_1 = 0;
        try {
            preStatement.setString(1,ProductName);
            preStatement.setString(2,Size);
            preStatement.setFloat(3,Price);
            preStatement.setString(4,Description);
            preStatement.setInt(5,Stock);
            preStatement.setInt(6,Selled);
            preStatement.setInt(7,ProductId);
            count_1 = preStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }//返回值表示增删改几条数据
        //处理结果
        if(count_1>0){
            System.out.println("修改成功!");
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


        response.setCharacterEncoding("UTF-8");
        //通知浏览器使用utf-8解码
        response.setHeader("Content-type", "text/html;charset=utf-8");

        Message message=new Message();
        if(count_1>0) {
            message.setCode(0);
            message.setMsg("修改成功");
            System.out.println("修改数据成功");
        }else
        {
            message.setCode(1);
            message.setMsg("修改失败");
            System.out.println("修改数据失败");
        }
        message.setCount(count_1);

        String  content=  JSON.toJSONString(message);
        PrintWriter out = response.getWriter();
        out.write(content);

    }
}
