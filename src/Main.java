import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception{


        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","<username>","<password>");
        if(con.isClosed()){
            System.out.println("Unable to connect with the db");
        }else{
            Scanner sc = new Scanner(System.in);
            for(int i=0;true;i++) {
                System.out.println("----------------------------");
                System.out.println("| Enter 1 for add the user |");
                System.out.println("| Enter 5 for exit .       |");
                System.out.println("----------------------------");
                System.out.println("Press the key :");
                int k = sc.nextInt();
                if (k == 5) {
                    break;
                }
                else if (k == 1) {
                    System.out.println("Enter the name :");
                    String name = sc.next();
                    sc.nextLine();
                    System.out.println("Enter the password :");
                    String pass = sc.next();
                    String query = "insert into info (username,password) values (?,?)";
                    PreparedStatement stmt = con.prepareStatement(query);
                    stmt.setString(1, name);
                    stmt.setString(2, pass);
                    Mail mail = new Mail();
                    int c_code = mail.send(name);
                    if(c_code==-1){
                        System.out.println();
                        System.out.println("<------------------------------>");
                        System.out.println("Unable to send mail");
                        System.out.println("<------------------------------>");
                    }else {
                        long old_time = System.currentTimeMillis();
//                        System.out.println();
                        System.out.println("Enter the OTP");
                        int code = sc.nextInt();
                        if (code != -1) {
                            long current_time = System.currentTimeMillis();
//                            System.out.println(current_time-old_time);
                            if (current_time - old_time <= 60000L) {

                                if (code == c_code) {
                                    System.out.println();System.out.println("<----------------------------------------------->");
                                    System.out.println("Correct Code");
                                    stmt.execute();
                                    System.out.println("User Added Successfully");
                                    System.out.println("<----------------------------------------------->");
                                } else {
                                    System.out.println();
                                    System.out.println("<----------------------------------------------->");
                                    System.out.println("Invalid OTP");
                                    System.out.println("<----------------------------------------------->");
                                }
                            } else {
                                System.out.println();
                                System.out.println("<----------------------------------------------->");
                                System.out.println("OTP Expired");
                                System.out.println("<----------------------------------------------->");
                            }
                            System.out.println();
                            System.out.println();
                        }
                    }
                }
            }
        }
    }
}