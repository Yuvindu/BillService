package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;



//data base connection

public class Bill {
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ceb_api", "root", "root");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	//insert

	public String insertBill(String id, String uid, String amount, String paid, String created_at) { 
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for inserting."; } 
	 
			// create a prepared statement 
			String query = " INSERT into bills (`id`,`uid`,`amount`,`paid`,`created_at`)"
					+ " values (?, ?, ?, ?, ?)"; 
	 
	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setString(1, id);
			preparedStmt.setString(2, uid);
			preparedStmt.setString(3, amount);
			preparedStmt.setString(4, paid);
			preparedStmt.setString(5, created_at);
	 
			
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	   
			String newBill = readBill(); 
			output =  "{\"status\":\"success\", \"data\": \"" + newBill + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while inserting the Bill.\"}";  
			System.err.println(e.getMessage());   
		} 
		
	  return output;  
	    } 
	}
	
	//read
	
	public String readBill()  
	{   
		String output = ""; 
	
		try   
		{    
			Connection con = connect(); 
		
			if (con == null)    
			{
				return "Error while connecting to the database for reading."; 
			} 
	 
			// Prepare the html table to be displayed    
			output = "<table border='1'><tr>"
					+ "<th> ID</th>"
					+ "<th> UID</th>"
					+ "<th> Amount</th>" 
					+ "<th> Paid</th>"
					+ "<th>CreatedAt</th></tr>";
			
			String query = "select * from Bill"; 
			Statement stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery(query); 
	 
			// iterate through the rows in the result set    
			while (rs.next())    
			{     
				String id = rs.getString("id");
				String uid = rs.getString("uid"); 
				String amount = rs.getString("amount"); 
				String paid = rs.getString("paid");  
				String created_at = rs.getString("created_at"); 
		 
			
			
	 
				// Add into the html table 
				
				output += "<tr><td><input id=\'hididUpdate\' name=\'hididUpdate\' type=\'hidden\' value=\'" + id + "'>"
				            + uid + "</td>"; 
				output += "<td>" + amount + "</td>";
				output += "<td>" + paid + "</td>";
				output += "<td>" + created_at + "</td>";
				
			  
 
			// buttons     
			output +="<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"       
					+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-iid='" + uid + "'>" + "</td></tr>"; 
		 		 
			}
			con.close(); 
	 
			// Complete the html table    
			output += "</table>";   
		}   
		catch (Exception e)   
		{    
			output = "Error while reading the Bill.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}
	
	//update
	
	public String updateBill(String id, String uid, String amount, String paid, String created_at) { 
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for updating."; } 
	 
			// create a prepared statement    
			String query = " UPDATE bills SET uid = ? , amount = ?, paid = ? , created_at = ? WHERE id = ? ";
	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setString(1, uid);
			preparedStmt.setString(2, amount);
			preparedStmt.setString(3, paid);
			preparedStmt.setString(4, created_at);
			preparedStmt.setString(5, id);
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	 
			String newBill = readBill();    
			output = "{\"status\":\"success\", \"data\": \"" + newBill + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while updating the bill.\"}";   
			System.err.println(e.getMessage());   
		} 
	 
	  return output;  
	    } 
	}
	
	//delete
	
	public String deleteBill(String uid)   
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{
				return "Error while connecting to the database for deleting."; 
				
			} 
	 
			// create a prepared statement    
			String query = "DELETE from bills WHERE id=?";  
	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setInt(1, Integer.parseInt(uid)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	 
			String newBill = readBill();  
			    
			output = "{\"status\":\"success\", \"data\": \"" +  newBill + "\"}";    
		}   
		catch (Exception e)   
		{    
			output = "Error while deleting the Bill.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}
	
}
