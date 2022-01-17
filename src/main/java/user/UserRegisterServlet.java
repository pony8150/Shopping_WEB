package user;

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

@WebServlet(name = "UserRegisterServlet", urlPatterns = "/userRegister")
public class UserRegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String userPwd = request.getParameter("userPwd");
        String userMail = request.getParameter("userMail");

//        String sql="insert into  [dbo].[T_Shop_ProductInfo] (ProductName,[Size],Price,Stock,Selled)  values (?,?,?,?,?) ";
        String sql = "insert into T_Shop_User (userName, userPwd, userMail) values (?, ?, ?)";
        DataBase dataBase = new DataBase(sql, DataBase.PREPARED_STATEMENT);
        PreparedStatement preStatement = dataBase.getPreStatement();

        int count_1 = 0;
        try {
            preStatement.setString(1,userName);
            preStatement.setString(2,userPwd);
            preStatement.setString(3,userMail);

            count_1 = preStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }//返回值表示增删改几条数据
        //处理结果
        if(count_1>0){
            System.out.println("插入成功!");
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
        if(count_1 > 0) {
            message.setCode(0);
            message.setMsg("添加用户成功");
            System.out.println("添加数据成功");
        }else
        {
            message.setCode(1);
            message.setMsg("添加用户失败");
            System.out.println("添加数据失败");
        }
        message.setCount(count_1);


        String  content=  JSON.toJSONString(message);
        PrintWriter out = response.getWriter();
        out.write(content);
    }
}
