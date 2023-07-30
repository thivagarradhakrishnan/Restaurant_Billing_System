package View;
import java.sql.*;
import java.util.*;

import com.mysql.cj.protocol.Resultset;

import Controller.userc;
import Model.UserDB;
import Model.connection;
import View.CafeManagement;
public class User extends UserDB{
	static Connection con = connection.getConnection();
	CafeManagement rs=new CafeManagement();
	userc ur=new userc();
	public  void login() throws Exception {
		Scanner input=new Scanner(System.in);
	    System.out.print("Username: ");
	    ur.setusername(input.nextLine());
	    System.out.print("Password: ");
	    ur.setpassword(input.nextLine());
	    if(ur.checklogin().next()) {
	        System.out.println("Login successful!");
			ResultSet cresult=getcid(ur.getusername(),ur.getpassword());
	        while(cresult.next()) { 
	            collectcid=cresult.getInt(1);
	        }
	        billing();
	    } else {
	        System.out.println("Invalid username or password.");
	        
	    }
	    System.out.println("Enter 1 to login");
	    System.out.println("Enter 2 to signup");
	    System.out.println("Enter 3 to Exit");
	    ur.setchoice(input.nextInt());
	    if(ur.getchoice()==1)
	        login();
	    else if(ur.getchoice()==2)
	    	signup();
	    else {
	    	return;
	    }
	    
	}	

	public static int collectcid=0;
	public  void billing() throws Exception{
        Scanner sc = new Scanner(System.in);
        int count=0,deletecount=0;
        while (true) {
            System.out.println("Welcome to Cafe");
            System.out.println("1. Order item");
            System.out.println("2. delete Order item");
            System.out.println("3. View Order");
            System.out.println("4. log out");
            ur.setchoice(sc.nextInt());
            switch ( ur.getchoice()) {
                case 1:
                     ResultSet resultSet = vieworders();
                     rs.printResultset(resultSet);
                     System.out.println("Enter item number to Order :");
                     ur.setitemno(sc.nextInt());
                     sc.nextLine();
                     System.out.println("Enter quantity of item to Order :");
                     ur.setQuantity(sc.nextFloat());
                     ResultSet resultSt =selectorders(ur.getitemno());
                     float f=0;
                     while (resultSt.next()){
                      f=resultSt.getFloat(1);
                     }
                     float sum=f *ur.getQuantity();
                     int rslt=insertorders(ur.getitemno(),collectcid,sum,ur.getQuantity());
                     if(rslt >0) {
                    	 System.out.println("successfully item add ");
                    	 count++;
                     }else {
                    	 System.out.println("No Items Purchased yet");
                     }
                    break;
                case 2:
                	ResultSet tresult=selectDeleteOrders(count);
                	ur.orderdel(tresult);
                    System.out.print("Enter item_id to delete your order:");
                    ur.setitemid(sc.nextInt());
            		int result=deleteorders(collectcid,ur.getitemid());
                    if (result >0) {
                    	System.out.println("Item deleted successfully.");
                    	deletecount++;
                    }else {
                    	System.out.println("Item not deleted Successfully.");
                    }
                case 3:
                	ResultSet dresult=confirmorders(count,deletecount);
                    rs.printResultset(dresult);
                	break;
                case 4:
                    System.out.println("Thank you for Ordering Cafe .");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            System.out.println();
        }
	}
        


		public void  signup() throws Exception {
        	Scanner input=new Scanner(System.in);
            System.out.print("Choose a username: ");
            ur.setusername(input.nextLine());
            ResultSet result = checkname(ur.getusername());
            if (ur.checkname().next()){
                System.out.println("Username already exists, please choose another.");
                signup();
                return;
            }
            System.out.print("Choose a password: ");
            ur.setpassword(input.nextLine());
            System.out.print("Enter your Email_id: ");
            ur.setemail(input.nextLine());
            System.out.print("Enter your DATE OF BIRTH(YYYY-MM-DD): ");
            ur.setdob(input.nextLine());
            if (ur.insertcustomer() > 0) {
                System.out.println("User created successfully!");
                System.out.println();
                System.out.println("***LOG_IN**");
                login();
            } else {
                System.out.println("Failed to create user.");
            }
        }

	}
	

