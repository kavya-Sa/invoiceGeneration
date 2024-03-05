package servlet;

import console_application.DAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@WebServlet("/invoices")
public class invoicesOfUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection con= DAO.getConnection();
PrintWriter out=resp.getWriter();
        int userId=Integer.parseInt(req.getParameter("userId"));
String sql="SELECT * FROM public.invoice WHERE  customer_id=?";
        PreparedStatement prepare=con.prepareStatement(sql);
        prepare.setInt(1,userId);
        ResultSet rs=prepare.executeQuery();
            out.printf("%-20s %-20s","invoice_id","amount");
            out.println(" ");
        while(rs.next()){
            int invoiceId=rs.getInt("id");
            int amount=rs.getInt("amount");

            out.printf("%-20s %-20s",invoiceId,amount);
            out.println(" ");

        }rs.close();
        con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
