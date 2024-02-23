package console_application;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void  main(String [] args) throws SQLException, ClassNotFoundException {
System.out.println("1.purchasing  and invoice generation");
System.out.println("2.invoice generation");
System.out.println("3.exit ");
Scanner sc=new Scanner(System.in);
System.out.println("Enter the service you want:");
int n=sc.nextInt();
purchase pa=new purchase();
invoice i=new invoice();
        System.out.println(" ");
        System.out.println("enter a valid user_id");
        int user_id=sc.nextInt();
switch(n){
    case 1:

     pa.purchasing(user_id);
     i.invoicing(user_id);

     break;
    case 2:
        i.invoicing(user_id);
        break;
    case 3:
        System.out.println("Thank you😇");
        break;
    default:
        System.out.print("invalid entry !!😑 please try again");
}
    }
}
