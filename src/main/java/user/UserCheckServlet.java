package user;

import com.alibaba.fastjson.JSON;
import model.DataBase;
import model.Message;
import model.T_Shop_ProductInfo;
import model.T_Shop_User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "UserCheckServlet", urlPatterns = "/userCheck")
public class UserCheckServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<T_Shop_User> list = new ArrayList<T_Shop_User>();
        String userName = request.getParameter("userName");
        String userPwd = request.getParameter("userPwd");

//        String sql = "insert into T_Shop_User (userName, userPwd) values (?, ?)";
        String sql = "select * from T_Shop_User where userName= ? and userPwd=?";
        DataBase dataBase = new DataBase(sql, DataBase.PREPARED_STATEMENT);
        PreparedStatement preStatement = dataBase.getPreStatement();
        try {
            preStatement.setString(1, userName);
            preStatement.setString(2, userPwd);
            ResultSet resultSet = preStatement.executeQuery();
            while (resultSet.next()) {
                T_Shop_User user = new T_Shop_User();

                user.setUserId(resultSet.getInt("userId"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserName(resultSet.getString("userPwd"));
                user.setStatus(resultSet.getInt("status"));

                list.add(user);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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

        Message message = new Message();
        if (list.size() > 0) {
            message.setCode(0);
            message.setMsg("登录成功!!");
            System.out.println("登录成功");
            HttpSession session = request.getSession();
            session.setAttribute("userId", list.get(0).getUserId());
        } else {
            message.setCode(1);
            message.setMsg("登录失败");
            System.out.println("登录失败");
        }
        message.setCount(list.size());


        String content = JSON.toJSONString(message);
        PrintWriter out = response.getWriter();
        out.write(content);
    }
}
