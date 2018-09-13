package Database_Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class JDBC_test {

	public static void main(String[] args){
		// TODO Auto-generated method stub
	
		try(
				Connection conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/", "myuser", "Mu1891!");
				Statement stmt = conn.createStatement();
				){
				Scanner scan1 = new Scanner(System.in);
				String dbName = scan1.nextLine();
							
				String sqlUpdate = "create database if not exists " + dbName ;
				System.out.println(sqlUpdate);
				int Result = stmt.executeUpdate(sqlUpdate);
			
			
				}
		catch(SQLException se) {
			se.printStackTrace();
		}
		
	}

}
