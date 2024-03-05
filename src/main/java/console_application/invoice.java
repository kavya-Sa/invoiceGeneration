package console_application;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class invoice {
boolean flag=true;

        public  void invoicing(int user_id) throws ClassNotFoundException, SQLException {

Scanner sc=new Scanner(System.in);
            // System.out.println("enter invoice id:");
                int n = sc.nextInt();
                Connection con = DAO.getConnection();
con.setAutoCommit(false);

            String sq="SELECT id FROM invoice WHERE customer_id=?";
            PreparedStatement prepared=con.prepareStatement(sq);
            prepared.setInt(1,user_id);
            ResultSet rs= prepared.executeQuery();
            while(rs.next()) {
                int id = rs.getInt(1);

                if (id == n) {
flag=false;
                    String sql = "SELECT customer_id,amount FROM invoice WHERE id=?";
                    PreparedStatement prepare = con.prepareStatement(sql);
                    prepare.setInt(1, n);
                    int customer_id = 0;
                    int total_amount = 0;
                    try (ResultSet rstd = prepare.executeQuery()) {
                        if (rstd.next()) {
                            customer_id = rstd.getInt(1);
                            total_amount = rstd.getInt(2);
                        }
                    }
                    //System.out.println("total amount:" + total_amount);
//======================================================================================================
                    String s = "SELECT name,address,phone_no FROM customers WHERE id=?";
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.setInt(1, customer_id);
                    ResultSet rsd = prep.executeQuery();

                    if (rsd.next()) {
                        String name = rsd.getString(1);
                        String address = rsd.getString(2);
                        BigDecimal phone_no = rsd.getBigDecimal(3);
                        System.out.println(name + address + phone_no);
                    }

//=======================================================================================================

                    String q = "SELECT it.product_id,p.name,it.quantity,it.amount FROM invoice_items AS it JOIN products as p ON it.product_id=p.id WHERE it.invoice_id=?";
                    PreparedStatement prest = con.prepareStatement(q);
                    prest.setInt(1, n);
                    ResultSet rst = prest.executeQuery();
                   // System.out.printf("%-20s %-20s %-10s %-10s%n", "product_id", "product_name", "quantity", "amount");
                    while (rst.next()) {
                        int product_id = rst.getInt(1);
                        String pro_name = rst.getString(2);
                        int quantity = rst.getInt(3);
                        int amount = rst.getInt(4);
                        //System.out.printf("%-20d %-20s %-10d %-10d%n", product_id, pro_name, quantity, amount);
                        con.commit();
                    }


                }
           }
//   if(!rs.next() && flag==true){
//          // System.out.println("please check your invoice id or user id");
//       }
      }
        }

