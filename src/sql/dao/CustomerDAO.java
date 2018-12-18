package sql.dao;

import sql.entity.Customer;
import sql.mapper.CustomerMapper;
import utils.MyStringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDAO {

    public Customer findCustomer(Integer id) {
        Customer customer = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select * from users where id = " + id;
            rs = stmt.executeQuery(sqlCmd);

            if (rs.next()) {
                customer = CustomerMapper.CustomerMapper(rs);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
            }
        }
        return customer;
    }

    public Integer createCust(Customer customer) {
        Integer ret = null;
        String sqlCmd = "insert into users (title, name, surname, age, email, tel, username, password, image, role) values(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = SqlConnection.getConnection().prepareStatement(sqlCmd);
            pstmt.setString(1,customer.getTitle());
            pstmt.setString(2,customer.getName());
            pstmt.setString(3,customer.getSurname());
            pstmt.setString(4,customer.getAge());
            pstmt.setString(5,customer.getEmail());
            pstmt.setString(6,customer.getTel());
            pstmt.setString(7,customer.getUsername());
            pstmt.setString(8,customer.getPassword());
            pstmt.setBytes(9,customer.getImage());
            pstmt.setString(10,customer.getRole());
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public String getCustName(String username) {
        String name = null;
        try {
            Statement stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select * from users where username = " + MyStringUtils.QuotedStr(username);
            ResultSet rs = stmt.executeQuery(sqlCmd);
            rs.next();
            name = rs.getString(2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public Customer findCustomerByUsername(String username) {
        Customer customer = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select * from users where username = " + MyStringUtils.QuotedStr(username);
            rs = stmt.executeQuery(sqlCmd);

            if (rs.next()) {
                customer = CustomerMapper.CustomerMapper(rs);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
            }
        }
        return customer;
    }


    public ResultSet findTotal(String field) {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select "+field+" from users where id = id";
            rs = stmt.executeQuery(sqlCmd);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void deleteCust(int custId) {
        Statement stmt = null;
        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Delete from users where id = "+custId;
            stmt.executeUpdate(sqlCmd);
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editCust(int custId, Customer customer) {
        try {
            String sqlCmd = "Update users set title = ?,name = ?,surname = ?,age = ?,email = ?,tel = ?,username = ?,password = ?,image = ? where id = "+custId;
            PreparedStatement ps = SqlConnection.getConnection().prepareStatement(sqlCmd);
            ps.setString(1, customer.getTitle());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getSurname());
            ps.setString(4, customer.getAge());
            ps.setString(5, customer.getEmail());
            ps.setString(6, customer.getTel());
            ps.setString(7, customer.getUsername());
            ps.setString(8, customer.getPassword());
            ps.setBytes(9, customer.getImage());

            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}