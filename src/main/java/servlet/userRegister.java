package servlet;

import console_application.DAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.*;

@WebServlet("/new_user")
public class userRegister extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        PrintWriter out=res.getWriter();
String name=req.getParameter("name");
String address=req.getParameter("address");
        String phone_no=req.getParameter("phone_no");
        BigDecimal ph=new BigDecimal(phone_no);
        try {
            Connection con= DAO.getConnection();

            String sq="SELECT * FROM customers WHERE name=? AND (address=? AND phone_no=?)";
            PreparedStatement stmt = con.prepareStatement(sq);
stmt.setString(1,name);
stmt.setString(2,address);
stmt.setBigDecimal(3,ph);
ResultSet rs= stmt.executeQuery();
if(rs.next()) {
    out.println("user already exists");
}else{String sql="INSERT INTO customers(name,address,phone_no) VALUES(?,?,?)";
    PreparedStatement prep=con.prepareStatement(sql);

    prep.setString(1,name);
    prep.setString(2,address);
    prep.setBigDecimal(3,ph);
    prep.executeUpdate();
    out.println("successfully registered");
    prep.close();
}
stmt.close();
rs.close();
con.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
