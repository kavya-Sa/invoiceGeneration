package servlet;

import console_application.DAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
@WebServlet("/invoice")
public class invoiceGeneration extends HttpServlet {
   boolean flag=true;

   protected  void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException {
     int invoice_id = Integer.parseInt(req.getParameter("invoice_id"));
     int user_id = Integer.parseInt(req.getParameter("user_id"));
    res.setContentType("text");
     PrintWriter out=res.getWriter();

     try {
        Connection con = DAO.getConnection();

        PreparedStatement prepared = con.prepareStatement("SELECT id FROM invoice WHERE customer_id=?");

        prepared.setInt(1, user_id);

        ResultSet rs = prepared.executeQuery();
        if(rs.next()) {

           int id = rs.getInt(1);

           if (id == invoice_id) {

              flag = false;

              PreparedStatement prepare = con.prepareStatement("SELECT customer_id,amount FROM invoice WHERE id=?");
              prepare.setInt(1, invoice_id);
              int customer_id = 0;
              int total_amount = 0;
              ResultSet rstd = prepare.executeQuery();
                 if (rstd.next()) {

                    customer_id = rstd.getInt(1);
                    total_amount = rstd.getInt(2);
                 }
               rstd.close();
                 prepare.close();
                 prepared.close();


//======================================================================================================

              PreparedStatement prep = con.prepareStatement("SELECT name,address,phone_no FROM customers WHERE id=?");
              prep.setInt(1, customer_id);
              ResultSet rsd = prep.executeQuery();

              if (rsd.next()) {
                 String name = rsd.getString(1);
                 String address = rsd.getString(2);
                 BigDecimal phone_no = rsd.getBigDecimal(3);
                 out.println("name: "+name);
                 out.println("address: "+address);
                 out.println("phone_no: "+phone_no);

              }
               rsd.close();
              prep.close();


//=======================================================================================================


              PreparedStatement prest = con.prepareStatement("SELECT it.product_id,p.name,it.quantity,it.amount FROM invoice_items AS it JOIN products as p ON it.product_id=p.id WHERE it.invoice_id=?");
              prest.setInt(1, invoice_id);
              ResultSet rst = prest.executeQuery();
              //out.printf("%-20s %-20s %-10s %-10s%n", "product_id", "product_name", "quantity", "amount");
              while (rst.next()) {
                 int product_id = rst.getInt(1);
                 String pro_name = rst.getString(2);
                 int quantity = rst.getInt(3);
                 int amount = rst.getInt(4);
                out.printf("%-20d %-20s %-10d %-10d%n", product_id, pro_name, quantity, amount);

              }  rst.close();prest.close();

              out.println("total amount:" + total_amount);

           }
        }
        if (!rs.next() && flag){
           out.println("please check your invoice id and user id");
        }
rs.close();
        prepared.close();
con.close();
     }catch(Exception e){

        //e.printStackTrace();
     }
   }
}



