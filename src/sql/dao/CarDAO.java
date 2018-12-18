package sql.dao;

//import org.commons.lang3.StringUtils;
import sql.entity.Car;
import sql.entity.Customer;
import sql.mapper.CarMapper;
import sql.mapper.CustomerMapper;
import utils.MyStringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.lang3.StringUtils;

public class CarDAO {

    public Car findCar(int id) {
        Car car = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select * from car where id = " + id;

            rs = stmt.executeQuery(sqlCmd);

            if (rs.next()) {
                car = CarMapper.CarMapper(rs);
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
        return car;
    }

    public Car findCarById(int id) {

        Car car = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select * from car where id = " + id;

            rs = stmt.executeQuery(sqlCmd);

            if (rs.next()) {
                car = CarMapper.CarMapper(rs);
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
        return car;
    }

    public ResultSet findCarResultSet(String selectedField, String brand, String model, String subModel, String year, int ps, int pd, String gear, String color, String gas) {

        Car car = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select " + selectedField + " from car where status = 'AVAILABLE' and id = id ";

            if (!brand.equals("---"))
                sqlCmd += "and brand = " + MyStringUtils.QuotedStr(brand);

            if (!model.equals("---"))
                sqlCmd += "and model = " + MyStringUtils.QuotedStr(model);

            if (!subModel.equals("---"))
                sqlCmd += "and sub_model = " + MyStringUtils.QuotedStr(subModel);

            if (!year.equals("---"))
                sqlCmd += "and year = " + MyStringUtils.QuotedStr(year);

            if (!color.equals("---"))
                sqlCmd += "and color = " + MyStringUtils.QuotedStr(color);

            if (!gear.equals("---"))
                sqlCmd += "and gear = " + MyStringUtils.QuotedStr(gear);

            if (StringUtils.isNotBlank(gas))
                sqlCmd += "and gas = " + gas;

            if (ps>=0&&pd>=0) {
                sqlCmd += "and price between " +ps+" and "+pd;
            }

            rs = stmt.executeQuery(sqlCmd);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet findCarResultSet2(String selectedField, String brand, String model, String subModel, String year,String gear, String color, String gas, String status) {

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select " + selectedField + " from car where id = id ";

            if (!brand.equals("---"))
                sqlCmd += "and brand = " + MyStringUtils.QuotedStr(brand);

            if (!model.equals("---"))
                sqlCmd += "and model = " + MyStringUtils.QuotedStr(model);

            if (!subModel.equals("---"))
                sqlCmd += "and sub_model = " + MyStringUtils.QuotedStr(subModel);

            if (!year.equals("---"))
                sqlCmd += "and year = " + MyStringUtils.QuotedStr(year);

            if (!color.equals("---"))
                sqlCmd += "and color = " + MyStringUtils.QuotedStr(color);

            if (!gear.equals("---"))
                sqlCmd += "and gear = " + MyStringUtils.QuotedStr(gear);

            if (!gas.equals("---"))
                sqlCmd += "and gas = " + MyStringUtils.QuotedStr(gas);

            if (!status.equals("---"))
                sqlCmd += "and status = " + MyStringUtils.QuotedStr(status);

            rs = stmt.executeQuery(sqlCmd);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getCarTotal(String select) {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select "+select+" from car";
            rs = stmt.executeQuery(sqlCmd);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void deleteCar(int id) {
        Statement stmt = null;
        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Delete from car where id = "+id;
            stmt.executeUpdate(sqlCmd);
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int countRowInital(String field) {
        int ret = 0;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select count(distinct "+field+") from car\n";

            rs = stmt.executeQuery(sqlCmd);

            while (rs.next()) {
                ret = rs.getInt(1);
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
        return ret;
    }

    public String[] listBand() {
        String[] ret = new String[0];
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select distinct brand from car order by 1";

            ret = new String[countRowInital("brand") + 1];
            ret[0] = "---";

            int i = 1;
            rs = stmt.executeQuery(sqlCmd);
            while (rs.next()) {
                ret[i] = rs.getString("brand");
                i++;
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
        return ret;
    }

    public int countRow(String field, String check, String value) {
        int ret = 0;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select count(distinct " + field + ") from car where " + check + " = " + MyStringUtils.QuotedStr(value) + "\n";

            rs = stmt.executeQuery(sqlCmd);

            while (rs.next()) {
                ret = rs.getInt(1);
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
        return ret;
    }

    public String[] listModel(String brand) {
        String[] retModel = new String[0];
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select distinct model from car where brand = " + MyStringUtils.QuotedStr(brand) + "order by 1";

            retModel = new String[countRow("model", "brand", brand) + 1];
            retModel[0] = "---";
            int i = 1;
            rs = stmt.executeQuery(sqlCmd);
            while (rs.next()) {
                retModel[i] = rs.getString("model");
                i++;
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
        return retModel;
    }


    public String[] listSub(String model) {
        String[] ret = new String[0];
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select distinct sub_model from car where model = " + MyStringUtils.QuotedStr(model) + "order by 1";

            ret = new String[countRow("sub_model", "model", model) + 1];
            ret[0] = "---";
            int i = 1;
            rs = stmt.executeQuery(sqlCmd);
            while (rs.next()) {
                ret[i] = rs.getString("sub_model");
                i++;
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
        return ret;
    }

    public String[] listYear(String subModel) {
        String[] ret = new String[0];
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select distinct year from car where sub_model = " + MyStringUtils.QuotedStr(subModel) + "order by 1";

            ret = new String[countRow("year", "sub_model", subModel) + 1];
            ret[0] = "---";
            int i = 1;
            rs = stmt.executeQuery(sqlCmd);
            while (rs.next()) {
                ret[i] = rs.getString("year");
                i++;
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
        return ret;
    }

    public String[] listGear(String subModel) {
        String[] ret = new String[0];
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select distinct gear from car where sub_model = " + MyStringUtils.QuotedStr(subModel) + "order by 1";

            ret = new String[countRow("gear", "sub_model", subModel) + 1];
            ret[0] = "---";
            int i = 1;
            rs = stmt.executeQuery(sqlCmd);
            while (rs.next()) {
                ret[i] = rs.getString("gear");
                i++;
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
        return ret;
    }

    public String[] listColor(String year, String subModel) {
        String[] ret = new String[0];
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select distinct color from car where year = " + MyStringUtils.QuotedStr(year) + "and sub_model = " + MyStringUtils.QuotedStr(subModel) + "order by 1";

            ret = new String[countColor(year, subModel) + 1];
            ret[0] = "---";
            int i = 1;
            rs = stmt.executeQuery(sqlCmd);
            while (rs.next()) {
                ret[i] = rs.getString("color");
                i++;
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
        return ret;
    }

    public String[] listColorByModel(String model) {
        String[] ret = new String[0];
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select distinct color from car where model = " + MyStringUtils.QuotedStr(model) + "order by 1";

            ret = new String[countRow("color", "model", model) + 1];
            ret[0] = "---";
            int i = 1;
            rs = stmt.executeQuery(sqlCmd);
            while (rs.next()) {
                ret[i] = rs.getString("color");
                i++;
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
        return ret;
    }

    public int countColor(String year, String subModel) {
        int ret = 0;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select count(distinct color) from car where year = " + MyStringUtils.QuotedStr(year) + "and sub_model = " + MyStringUtils.QuotedStr(subModel) + "\n";

            rs = stmt.executeQuery(sqlCmd);

            while (rs.next()) {
                ret = rs.getInt(1);
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
        return ret;
    }

    public String[] listModelInitial() {
        String[] ret = new String[0];
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select distinct model from car order by 1";

            ret = new String[countRowInital("model")+1];
            ret[0] = "---";
            int i = 1;
            rs = stmt.executeQuery(sqlCmd);
            while (rs.next()) {
                ret[i] = rs.getString("model");
                i++;
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
        return ret;
    }

    public String[] listBrandInitial() {
        String[] ret = new String[0];
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select distinct brand from car order by 1";

            ret = new String[countRowInital("model")+1];
            ret[0] = "---";
            int i = 1;
            rs = stmt.executeQuery(sqlCmd);
            while (rs.next()) {
                ret[i] = rs.getString("brand");
                i++;
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
        return ret;
    }

    public String[] listSubInitial() {
        String[] ret = new String[0];
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select distinct sub_model from car order by 1";

            ret = new String[countRowInital("sub_model")+1];
            ret[0] = "---";
            int i = 1;
            rs = stmt.executeQuery(sqlCmd);
            while (rs.next()) {
                ret[i] = rs.getString("sub_model");
                i++;
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
        return ret;
    }

    public String[] listYearInitial() {
        String[] ret = new String[0];
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select distinct year from car order by 1";

            ret = new String[countRowInital("year")+1];
            ret[0] = "---";
            int i = 1;
            rs = stmt.executeQuery(sqlCmd);
            while (rs.next()) {
                ret[i] = rs.getString("year");
                i++;
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
        return ret;
    }


    public String[] listColorInitial() {
        String[] ret = new String[0];
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select distinct color from car order by 1";

            ret = new String[countRowInital("color")+1];
            ret[0] = "---";
            int i = 1;
            rs = stmt.executeQuery(sqlCmd);
            while (rs.next()) {
                ret[i] = rs.getString("color");
                i++;
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
        return ret;
    }

    public String[] listGearInitial() {
        String[] ret = new String[0];
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select distinct gear from car order by 1";

            ret = new String[countRowInital("gear")+1];
            ret[0] = "---";
            int i = 1;
            rs = stmt.executeQuery(sqlCmd);
            while (rs.next()) {
                ret[i] = rs.getString("gear");
                i++;
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
        return ret;
    }


    public static void main(String[] args) {

        CarDAO carDAO = new CarDAO();

//        System.out.println(carDAO.count());

        String[] list = carDAO.listBand();
        for (int i = 0; i < list.length; i++) {
            System.out.println(i + " : " + list[i]);
        }
    }

    public void bookCar(int carId, int custId) {
        String sqlCmd = "update car set reserved_date = getdate()+3, reserved_by = ? ,status = 'RESERVED' where id = ? ";
        PreparedStatement pstmt = null;
        try {
            pstmt = SqlConnection.getConnection().prepareStatement(sqlCmd);
            pstmt.setString(1, String.valueOf(custId));
            pstmt.setString(2, String.valueOf(carId));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createCar(Car car) {
        String sqlCmd = "insert into car (license, brand, model, sub_model, year, price, gear, gas, color, status, image) values(?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = SqlConnection.getConnection().prepareStatement(sqlCmd);
            pstmt.setString(1, car.getLicense());
            pstmt.setString(2, car.getBrand());
            pstmt.setString(3, car.getModel());
            pstmt.setString(4, car.getSubModel());
            pstmt.setString(5, car.getYear());
            pstmt.setInt(6, car.getPrice());
            pstmt.setString(7, car.getGear());
            pstmt.setString(8, car.getGas());
            pstmt.setString(9, car.getColor());
            pstmt.setString(10, "AVAILABLE");
            pstmt.setBytes(11, car.getImage());
            pstmt.executeUpdate();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editCar(int carId, Car car) {
        try {
            String sqlCmd = "Update car set license = ?,brand = ?,model = ?,sub_model = ?,year = ?,price = ?,gear = ?,gas = ?,color = ?,status = ?,image = ? where id = "+carId;
            PreparedStatement ps = SqlConnection.getConnection().prepareStatement(sqlCmd);
            ps.setString(1, car.getLicense());
            ps.setString(2, car.getBrand());
            ps.setString(3, car.getModel());
            ps.setString(4, car.getSubModel());
            ps.setString(5, car.getYear());
            ps.setInt(6, car.getPrice());
            ps.setString(7, car.getGear());
            ps.setString(8, car.getGas());
            ps.setString(9, car.getColor());
            ps.setString(10, car.getStatus());
            ps.setBytes(11, car.getImage());
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Car findCarByReserv(int custId) {
        Car car = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = SqlConnection.getConnection().createStatement();
            String sqlCmd = "Select * from car where reserved_by = " + custId;

            rs = stmt.executeQuery(sqlCmd);

            if (rs.next()) {
                car = CarMapper.CarMapper(rs);
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
        return car;
    }

    public void cancelReserv(int custId) {
        try {
            String sqlCmd = "Update car set status = ?,reserved_by = ?,reserved_date = ? where reserved_by = "+custId;
            PreparedStatement ps = SqlConnection.getConnection().prepareStatement(sqlCmd);
            ps.setString(1, "AVAILABLE");
            ps.setString(2, null);
            ps.setDate(3, null);
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


