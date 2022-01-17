package model;

import java.sql.*;
//        String dbURL="jdbc:sqlserver://47.98.59.211:1433;DatabaseName=192";//数据源  ！！！注意若出现加载或者连接数据库失败一般是这里出现问题
//        String Name="192";
//        String Pwd="192";

public class DataBase {
    public static int NORMAL_STATEMENT = 1;
    public static int PREPARED_STATEMENT = 2;

    private Connection connection;
    private String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";//SQL数据库引擎
    private String dbURL = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=192";//数据源  ！！！注意若出现加载或者连接数据库失败一般是这里出现问题
    private String Name = "sa";
    private String Pwd = "8150";
    private Statement statement;
    private String exeSQL;
    private PreparedStatement preStatement;

    public DataBase(String exeSQL, int typeOfStatement) {
        this.exeSQL = exeSQL;
        //建立连接
        connection = null;
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(dbURL, Name, Pwd);
            System.out.println("数据库连接成功!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("数据库连接失败");
        }
        //建立通讯声明
        if (typeOfStatement == NORMAL_STATEMENT) {
            statement = null;
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (typeOfStatement == PREPARED_STATEMENT) {
            //建立通讯声明
            preStatement = null;
            try {
                // stmt = connection.createStatement();
//                String exeSQL="delete from [dbo].[T_Shop_ProductInfo] where ProductId = ?";
                preStatement = connection.prepareStatement(exeSQL);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public PreparedStatement getPreStatement() {
        return preStatement;
    }

    public ResultSet executeSQL() throws SQLException {
        //执行语句
        return statement.executeQuery(exeSQL);
    }

    public void closeStatement() throws SQLException {
        //关闭连接
        statement.close();
    }

    public void closePreStatement() throws SQLException {
        //关闭连接
        preStatement.close();
    }


    public void closeConnect() throws SQLException {
        //关闭连接
        connection.close();
    }
}
