package DataLayer.DAO;

import BussinessLayer.Response;
import BussinessLayer.ResponseT;
import BussinessLayer.Supplier.PeriodicOrder;
import DataLayer.DTO.PeriodicOrderDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PeriodicOrderDAO extends DAO{

    public Response insert(Integer orderID, LocalDate supplyDate, Integer intervals, Integer productID, Integer quantity) {

        String order = "INSERT INTO PeriodicOrder(orderID, supplyDate, intervals, productID, quantity) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConn().value;
             PreparedStatement pstmt = conn.prepareStatement(order);) {

            // inserting to employee table
            pstmt.setInt(1, orderID);
            pstmt.setDate(2, Date.valueOf(supplyDate));
            pstmt.setInt(3,intervals);
            pstmt.setInt(4, productID);
            pstmt.setInt(5, quantity);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }


    public Response insert(PeriodicOrderDTO periodic){
        return insert(periodic.getProductID(), periodic.getSupplyDate(), periodic.getIntervals(),
                periodic.getProductID(), periodic.getQuantity());
    }

    public Response delete(Integer periodicID) {
        String SQL = "DELETE FROM PeriodicOrder WHERE orderID = ?";
        try {
            ResponseT<Connection> r = getConn();
            if(!r.ErrorOccured()) {
                PreparedStatement ps = r.value.prepareStatement(SQL);
                ps.setInt(1, periodicID);

                if(!ps.execute()) {
                    return new Response("cannot delete periodic order num: "+periodicID+" in db");
                }
            }
        }catch (SQLException e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response updateInterval(Integer periodicID, Integer interval) {
        String SQL = "UPDATE PeriodicOrder SET interval = ? WHERE periodicID = ?";
        try {
            ResponseT<Connection> r = getConn();
            if(!r.ErrorOccured()) {
                PreparedStatement ps = r.value.prepareStatement(SQL);
                ps.setInt(1, interval);
                ps.setInt(2, periodicID);

                if(!ps.execute()) {
                    return new Response("cannot update interval of periodic order num "+periodicID+ " in db");
                }
            }
        }catch (SQLException e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response updateQuantity(Integer periodicID, Integer productID, Integer quantity) {
        String SQL = "UPDATE PeriodicOrder SET quantity = ? WHERE periodicID = ? AND productID = ?";
        try {
            ResponseT<Connection> r = getConn();
            if(!r.ErrorOccured()) {
                PreparedStatement ps = r.value.prepareStatement(SQL);
                ps.setInt(1, quantity);
                ps.setInt(2, periodicID);
                ps.setInt(3, productID);

                if(!ps.execute()) {
                    return new Response("cannot update quantity of productID: "+productID+
                            " in periodic order num "+periodicID+ " in db");
                }
            }
        }catch (SQLException e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response deleteProduct(Integer periodicID, Integer productID) {
        String SQL = "DELETE FROM PeriodicOrder WHERE orderID = ? AND productID = ?";
        try {
            ResponseT<Connection> r = getConn();
            if(!r.ErrorOccured()) {
                PreparedStatement ps = r.value.prepareStatement(SQL);
                ps.setInt(1, periodicID);
                ps.setInt(2, productID);

                if(!ps.execute()) {
                    return new Response("cannot add product: "+productID+ "to periodic order num: "+periodicID+" in db");
                }
            }
        }catch (SQLException e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public ResponseT<List<PeriodicOrderDTO>> read() {
        String SQL = "SELECT * FROM PeriodicOrder";
        List<PeriodicOrderDTO> poList = new LinkedList<>();
        try {
            ResponseT<Connection> r = getConn();
            if(!r.ErrorOccured()) {
                PreparedStatement ps = r.value.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    poList.add(new PeriodicOrderDTO(rs.getInt("orderId"), rs.getDate("supplyDate").toLocalDate(),
                            rs.getInt("intervals"), rs.getInt("productID"),
                            rs.getInt("quantity")));
                }
            }
        }catch (Exception e) {
            return new ResponseT("cannot read sale");
        }
        return new ResponseT<>(poList);
    }


    public ResponseT<HashMap<Integer, Integer>> getProductsFromPeriod(int periodID) {
        String SQL = "SELECT productID, quantity FROM PeriodicOrder WHERE orderID = ?";
        HashMap<Integer,Integer> prodQua = new HashMap<>();
        try {
            ResponseT<Connection> r = getConn();
            if(!r.ErrorOccured()) {
                PreparedStatement ps = r.value.prepareStatement(SQL);
                ps.setInt(1, periodID);
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    prodQua.put(rs.getInt("productID"), rs.getInt("quantity"));
                }
            }
        }catch (Exception e) {
            return new ResponseT("cannot read products from order");
        }
        return new ResponseT<>(prodQua);
    }
    //add product TODO: maybe delete this function
//    public Response addProduct(PeriodicOrder period, Integer productID, Integer quantity) {
//        String supplier = "INSERT INTO PeriodicOrder (orderID, supplyDate, intervals, productID, quantity) VALUES (?, ?, ?, ?, ?)";
//
//        try (Connection conn = getConn().value;
//             PreparedStatement pstmt = conn.prepareStatement(supplier);) {
//
//            // inserting to employee table
//            pstmt.setInt(1, period.getpOrderID());
//            pstmt.setDate(2, Date.valueOf(period.getDateOfSupply()));
//            pstmt.setInt(3,period.getInterval());
//            pstmt.setInt(4, productID);
//            pstmt.setInt(5, quantity);
//
//            pstmt.executeUpdate();
//
//        } catch (SQLException e) {
//            return new Response(e.getMessage());
//        }
//        return new Response();
//    }
}