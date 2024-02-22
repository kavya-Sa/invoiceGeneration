package servlet;

import console_application.DAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
@WebServlet("/login")
public class user_details_webpage  extends HttpServlet {


protected  void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    int id=Integer.parseInt(request.getParameter("id"));

    String sql = "SELECT * FROM public.customers WHERE id=?";
PrintWriter out=response.getWriter();
    Connection c= null;
    try {
        c = DAO.getConnection();
    } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
    }
    try {
        PreparedStatement preparedStatement = c.prepareStatement(sql);

        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();


        if (resultSet.next()) {
            out.println("User Details:");
            out.println("ID: " + resultSet.getInt("id"));
            out.println("Name: " + resultSet.getString("name") );
            out.println("Address: " + resultSet.getString("address"));
            out.println("Phone_no: " +  resultSet.getBigDecimal("phone_no"));
            out.println(" ");
        } else {
            out.println("No customer found with ID: " + id);
        }
        resultSet.close();
        Statement stmt= c.createStatement();
    String q="SELECT * FROM public.products";

    ResultSet rd=stmt.executeQuery(q);
        out.printf("%-20s,%-10s,%-10s%n","product_id","product","price");
        out.println(" ");
    while (rd.next()) {
        int productId = rd.getInt("id");
        String name = rd.getString("name");
        int price = rd.getInt("amount");

        out.printf("%-20d,%-10s,%-10d%n",productId ,name ,price);
    }
        rd.close();
        preparedStatement.close();
        stmt.close();
        c.close();
} catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }
}
