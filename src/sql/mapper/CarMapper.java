package sql.mapper;

import sql.entity.Car;
import ui.Result;

import java.sql.ResultSet;

public class CarMapper {

    public static Car CarMapper(ResultSet rs){
        Car car = null;
        try {
            car = new Car();
            car.setId(rs.getInt("id"));
            car.setLicense(rs.getString("license"));
            car.setBrand(rs.getString("brand"));
            car.setModel(rs.getString("model"));
            car.setSubModel(rs.getString("sub_model"));
            car.setYear(rs.getString("year"));
            car.setGear(rs.getString("gear"));
            car.setColor(rs.getString("color"));
            car.setPrice(rs.getInt("price"));
            if (rs.getString("gas").equalsIgnoreCase("0")){
                car.setGas("no");
            }else {
                car.setGas("yes");
            }
            car.setImage(rs.getBytes("image"));
            car.setStatus(rs.getString("status"));
            car.setReservedDate(rs.getDate("reserved_date"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return car;
    }
}
