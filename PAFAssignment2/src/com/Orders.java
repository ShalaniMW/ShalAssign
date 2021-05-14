package com;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.TimeZone;

public class Orders {
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paf", "root", "shl199809");
			
			System.out.println("connected successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}      
		return con;
	}
	
	public String ReadOrders() {
		
		String output = "";
		
		try{
			Connection con  = connect();
				if (con == null) {

					return "Error while connecting to the database for reading.";
				}
			
				  output = "<table border='1'><tr><th>Order ID</th>"+
					 		"<th>Customer ID</th> "+
					 		"<th>Customer Name</th> "+
					 		"<th>Product ID</th>"
					 		+"<th>Date</th>"+ "<th>Update</th><th>Remove</th></tr>";
				
			
			String readQuery = "select * from orders";
			
			 PreparedStatement pstmt = con.prepareStatement(readQuery);
			 		 		 
				
			 ResultSet rs = pstmt.executeQuery(readQuery); 
			
			 
			while(rs.next()) {
				String OrdID = Integer.toString(rs.getInt("order_id"));
				String cid = rs.getString("customerID");
				String cname = rs.getString("customerName");
				String pid = rs.getString("productID");
				String date = rs.getString("date");
			
				
				// Add into the html table
				//output += "<tr>"
				//+ "<td><input id='hidOrdIDUpdate'" + "name='hidOrdIDUpdate'" + "type='hidden' value='"
				//+ OrdID + "'>" + OrdID + "</td>";
				output += "<tr><td>" + cid + "</td>";
				output += "<td>" + cname + "</td>";
				output += "<td>" + pid + "</td>";
				output += "<td>" + date + "</td>";
				
				
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update'"
						+ "class='btnUpdate btn btn-secondary' data-OrdID='" + OrdID + "'></td>\"></td>" 
						+ "<td><input name='btnRemove'"
						+ "type='button' value='Remove'" + "class='btnRemove btn btn-danger'" + "data-OrdID='" + OrdID
						+ "'>" + "</td></tr>";
			}
		
		   output += "</table>";
			  
		}
		catch(SQLException e){
			
			output = "Error while reading the orders.";
			System.err.println(e.getMessage());
		
		}
		return output;
	}
	
public String insertOrder(String cid,String cname,String pid,String date) {
	
	String output = "";
		
		try{
			  
			Connection con  = connect();
			if (con == null) {
				
				return "Error while connecting to the database for inserting.";
			}
			 
					
			String query = " insert into orders ('order_id','customer_id','customerName','productID','date') values (?, ?, ?, ?, ?)";
			PreparedStatement pstmnt = con.prepareStatement(query);
			
					pstmnt.setInt(1, 0);
			        pstmnt.setString(2,cid);
			        pstmnt.setString(3, cname);
					pstmnt.setString(4, pid);
					pstmnt.setString(5, date);
					
					pstmnt.execute();
			con.close();
		
			String newOrders = ReadOrders();
			output = "{\"status\":\"success\", \"data\": \"" + newOrders + "\"}";
					
			
		}
		catch(SQLException e){
			output = "{\"status\":\"error\", \"data\":" + " \"Error while inserting the Order.\"}";
			
		}
		return output;
	}

public String updateOrder(String OrdID,String cid,String cname,String pid,String date)  {
	String output = "";
	
	try{
		
		Connection con  = connect();
		if (con == null) {
			
			return "Error while connecting to the database for updating.";
		}
		
		
		
			String query = "UPDATE orders SET cid=?, cname=?, pid=?, date=? WHERE order_id=?";

		PreparedStatement pstmnt = con.prepareStatement(query);
		
         pstmnt.execute();
         con.close();
			String newOrders = ReadOrders();
			
			output = "{\"status\":\"success\", \"data\": \"" + newOrders + "\"}";
		
		
	}
	catch(SQLException e){
		output = "{\"status\":\"error\", \"data\":" + "\"Error while updating the Order.\"}";
		
	}
	return output;
	
}
public String deleteOrder(String OrdID) {
	
	String output = "";
	
	try{
		
		Connection con  = connect();
		if (con == null) {
			
			return "Error while connecting to the database for inserting.";
		}
		
		
			 String query = "delete from orders where order_id=?";
				
			 PreparedStatement pstmnt = con.prepareStatement(query);
			 
				pstmnt.setInt(1, Integer.parseInt(OrdID));
				pstmnt.execute();
				
				con.close();
				String newOrders = ReadOrders();
				output = "{\"status\":\"success\", \"data\": \"" + newOrders + "\"}";
				
			
	
	}catch(SQLException e){
		
		output = "{\"status\":\"error\", \"data\":" + "\"Error while deleting the Order.\"}";
		System.err.println(e.getMessage());
	}
	return output;
} 

}
