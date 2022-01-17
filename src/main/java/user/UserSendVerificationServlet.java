package user;

import com.alibaba.fastjson.JSON;
import model.Message;
import model.SentSimpleMail;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

@WebServlet(name = "UserSendVerificationServlet", urlPatterns = "/userSendVer")
public class UserSendVerificationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userMail = request.getParameter("userMail");
        String verCode = String.valueOf(new Random().nextInt(100000));
        try {
            new SentSimpleMail().sentSimpleMail("找回密码", verCode, userMail);
            //编码设置
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "text/html;charset=utf-8");

            Message message = new Message();
            message.setCode(0);
            message.setMsg("发送成功");
            message.setCount(1);
            message.setData(verCode); //

            String content = JSON.toJSONString(message);

            PrintWriter out = response.getWriter();
            out.write(content);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
