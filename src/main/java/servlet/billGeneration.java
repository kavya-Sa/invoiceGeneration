package servlet;
import console_application.purchase;
import com.google.gson.JsonParser;
import console_application.DAO;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Set;

import com.google.gson.JsonObject;

@WebServlet("/billing")
public class billGeneration extends HttpServlet {
    @Override
    protected  void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            boolean flag=true;
            Connection con= DAO.getConnection();
            BufferedReader buffer=req.getReader();
            JsonObject jObj= JsonParser.parseReader(buffer).getAsJsonObject();
            int user_id = jObj.get("userId").getAsInt();
            JsonObject jsonObject=jObj.get("products").getAsJsonObject();
            Set<String> products_id = jsonObject.keySet();
            HashMap<Integer,Integer> products = new HashMap<Integer,Integer>();
            PrintWriter out= resp.getWriter();
            int invoice_id=0;
            purchase a = new purchase();

int total_amount=0;
            for(String product : products_id) {
                products.put(Integer.parseInt(product), jsonObject.get(product).getAsInt());
if(flag){
    invoice_id = a.id_generation(user_id);
    flag=false;
}
    String sql = "SELECT amount FROM public.products WHERE id=?";
                PreparedStatement prep = con.prepareStatement(sql);
                prep.setInt(1, Integer.parseInt(product));
                ResultSet rs = prep.executeQuery();
                int amount = 0;
                int price;
                if (rs.next()) {
                    price = rs.getInt("amount");
                    amount = price * jsonObject.get(product).getAsInt();
                   out.println("amount " + amount);
                }
                total_amount = total_amount + amount;
                String sqlquery = "INSERT INTO invoice_items(invoice_id,product_id,quantity,amount) VALUES (?,?,?,?)";
                PreparedStatement prepare = con.prepareStatement(sqlquery);


                prepare.setInt(1, invoice_id);
                prepare.setInt(2, Integer.parseInt(product));
                prepare.setInt(3, jsonObject.get(product).getAsInt());
                prepare.setInt(4, amount);
                prepare.executeUpdate();
                rs.close();
            }
            String s="UPDATE invoice SET amount=? WHERE id=?";
            PreparedStatement prep=con.prepareStatement(s);
            prep.setInt(1,total_amount);
            prep.setInt(2,invoice_id);
            prep.executeUpdate();

con.close();

        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
}
