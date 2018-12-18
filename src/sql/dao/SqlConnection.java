package sql.dao;

import utils.MyStringUtils;

import java.sql.*;

public class SqlConnection {


    public static Connection getConnection(){
        Connection conn = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
//            String url = "jdbc:jtds:sqlserver://localhost:1433/tentrod";
//            String user = "sa";
//            String pwd = "P@ssw0rd";

            TentRodProperties tprop = new TentRodProperties();
            String url = tprop.getDBConnection();
            String user = tprop.getDBUser();
            String pwd =  tprop.getDBPassword();

            conn = DriverManager.getConnection(url, user, pwd);
        } catch (ClassNotFoundException e) {
            System.out.println(""+e);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    public boolean checkLogin(String user, char[] password) {
        boolean result = false;
        try {
            Statement stmt = getConnection().createStatement();
            String sqlCmd = "Select * from users where username = '" + user +"'"+" and password = '"+String.valueOf(password)+"'";

            ResultSet rs = stmt.executeQuery(sqlCmd);
            if (rs.next()) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Integer checkLoginId(String user, String password) {
        Integer result = null;
        try {
            Statement stmt = getConnection().createStatement();
            String sqlCmd = String.format("select * from users where username = %s and password = %s"
                            , MyStringUtils.QuotedStr(user)
                            , MyStringUtils.QuotedStr(String.valueOf(password)));
            ResultSet rs = stmt.executeQuery(sqlCmd);
            if (rs.next()) {
                result = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String checkLoginRole(String user, String password) {
        String role = null;
        try {
            Statement stmt = getConnection().createStatement();
            String sqlCmd = String.format("select * from users where username = %s and password = %s"
                    , MyStringUtils.QuotedStr(user)
                    , MyStringUtils.QuotedStr(String.valueOf(password)));

            ResultSet rs = stmt.executeQuery(sqlCmd);
            if (rs.next()) {
                role = rs.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }
}
