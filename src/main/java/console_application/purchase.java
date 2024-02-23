package console_application;

import java.sql.*;
import java.util.Scanner;
public class purchase {
static boolean flag =true;




    public  void purchasing(int user_id) throws SQLException, ClassNotFoundException {
       validationCheck(user_id);

       purchase_items(user_id);
    }

    public int  id_generation(int user_id) throws ClassNotFoundException, SQLException {
Connection con= DAO.getConnection();
        String sqlq="INSERT INTO invoice(customer_id) VALUES (?)";
        PreparedStatement prepare=con.prepareStatement(sqlq,Statement.RETURN_GENERATED_KEYS);
        prepare.setInt(1,user_id);
        prepare.executeUpdate();
        ResultSet generatedKeys = prepare.getGeneratedKeys();
        generatedKeys.next();
        return generatedKeys.getInt(1);
    }
    public void validationCheck(int user_id) throws ClassNotFoundException, SQLException {

       String sql="SELECT * FROM public.customers WHERE id=?";
         Connection con = DAO.getConnection();
       PreparedStatement prepare=con.prepareStatement(sql);
       prepare.setInt(1,user_id);
       ResultSet rs=prepare.executeQuery();
       if(rs.next()) {
           System.out.println(rs.getString(2));
           System.out.println(" ");
       }
       else{
           System.out.println("invalid id");
       }
        prepare.close();
        con.close();
//========================================================================================================

    }
    public void purchase_items(int user_id) throws ClassNotFoundException, SQLException {
int invoice_id = 0;
        int total_amount=0;

        do{
            System.out.println(" ");
            System.out.println("1.purchasing ");
        System.out.println("2.exit  ");
        Scanner sc=new Scanner(System.in);
        System.out.println("enter");
        int b=sc.nextInt();
            Connection   con = DAO.getConnection();
        if (b==1){
            System.out.println("enter product_id");
            int product_id=sc.nextInt();
            System.out.println("enter quantity");

            int quantity=sc.nextInt();
            if(product_id==(int)product_id &&  quantity==(int)quantity && flag==true){
invoice_id=id_generation(user_id);
               flag = false;
            }
                String sql="SELECT amount FROM public.products WHERE id=?";
                PreparedStatement prep=con.prepareStatement(sql);
                prep.setInt(1,product_id);
                ResultSet rs=prep.executeQuery();
                int amount = 0;
            int price;
                if(rs.next()){
                price=rs.getInt("amount");
                amount=price*quantity;
                System.out.println("amount "+amount);}
    total_amount=total_amount+amount;
            String sqlquery="INSERT INTO invoice_items(invoice_id,product_id,quantity,amount) VALUES (?,?,?,?)";
            PreparedStatement prepare=con.prepareStatement(sqlquery);
                prepare.setInt(1,invoice_id);
                prepare.setInt(2,product_id);
                prepare.setInt(3,quantity);
                prepare.setInt(4,amount);
                prepare.executeUpdate();
            prep.close();
            prepare.close();
            con.close();
        }else{

            String s="UPDATE invoice SET amount=? WHERE id=?";
            PreparedStatement prep=con.prepareStatement(s);
            prep.setInt(1,total_amount);
            prep.setInt(2,invoice_id);
          prep.executeUpdate();
System.out.println("your invoice id is "+invoice_id);
            System.out.println("Thank you");
            break;
        }}while(true);
        }
    }



