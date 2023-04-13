package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExecutor {

    public static void main(String[] args) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport", "postgres", "password");

        try {
            Connection connection = dcm.getConnection();
            OrderDAO orderDAO = new OrderDAO(connection);
//            Order order = orderDAO.findById(1000);
            CustomerDAO customerDAO = new CustomerDAO(connection);
//            Customer customer = customerDAO.findById(1000);

//            Customer dbCustomer = customerDAO.create(customer);
//            dbCustomer = customerDAO.findById(dbCustomer.getId());
//            dbCustomer = customerDAO.update(dbCustomer);
//            customerDAO.delete(dbCustomer.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
