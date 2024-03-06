package servlet;

import console_application.DAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

//@WebServlet("/rough")
public class SQLInjection extends HttpServlet {
   protected void doGet(HttpServletRequest req, HttpServletResponse res){
//        try {
//            Connection con= DAO.getConnection();
//            PrintWriter out=res.getWriter();
//            String userId=req.getParameter("userId");
//            String sql="SELECT * FROM public.invoice WHERE  customer_id="+userId;
//            //out.println(sql);
//            Statement prepare=con.createStatement();
//
//            ResultSet rs=prepare.executeQuery(sql);
//            out.printf("%-20s %-20s","invoice_id","amount");
//            out.println(" ");
//            while(rs.next()){
//                int invoiceId=rs.getInt("id");
//                int amount=rs.getInt("amount");
//
//      out.printf("%-20s %-20s",invoiceId,amount);
//                out.println(" ");
//
//            }
//            rs.close();
//            prepare.close();
//            con.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
