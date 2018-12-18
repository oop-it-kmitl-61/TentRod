package sql.mapper;

import sql.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper {

    public static Customer CustomerMapper(ResultSet rs){
        Customer customer = null;
        try {
            customer = new Customer();
            customer.setTitle(rs.getString("title"));
            customer.setId(rs.getInt("id"));
            customer.setName(rs.getString("name"));
            customer.setSurname(rs.getString("surname"));
            customer.setAge(rs.getString("age"));
            customer.setEmail(rs.getString("email"));
            customer.setTel(rs.getString("tel"));
            customer.setUsername(rs.getString("username"));
            customer.setPassword(rs.getString("password"));
            customer.setImage(rs.getBytes("image"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }


}
