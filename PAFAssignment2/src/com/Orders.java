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
				String order_id = Integer.toString(rs.getInt("order_id"));
				String customerID = rs.getString("customerID");
				String customerName = rs.getString("customerName");
				String productID = rs.getString("productID");
				String date = rs.getString("date");
			
				
				// Add into the html table
				//output += "<tr>"
				//+ "<td><input id='hidOrdIDUpdate'" + "name='hidOrdIDUpdate'" + "type='hidden' value='"
				//+ OrdID + "'>" + OrdID + "</td>";
				output += "<tr><td>" + customerID + "</td>";
				output += "<td>" + customerName + "</td>";
				output += "<td>" + productID + "</td>";
				output += "<td>" + date + "</td>";
				
				
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update'"
						+ "class='btnUpdate btn btn-secondary' data-order_id='" + order_id + "'></td>\"></td>" 
						+ "<td><input name='btnRemove'"
						+ "type='button' value='Remove'" + "class='btnRemove btn btn-danger'" + "data-OrdID='" + order_id
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
	
public String insertOrder(String customerID,String customerName,String productID,String date) {
	
	String output = "";
		
		try{
			  
			Connection con  = connect();
			if (con == null) {
				
				return "Error while connecting to the database for inserting.";
			}
			 
					
			String query = " insert into orders ('order_id','customerID','customerName','productID','date') values (?, ?, ?, ?, ?)";
			PreparedStatement pstmnt = con.prepareStatement(query);
			
					pstmnt.setInt(1, 0);
			        pstmnt.setString(2,customerID);
			        pstmnt.setString(3, customerName);
					pstmnt.setString(4, productID);
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

public String updateOrder(String order_id,String customerID,String customerName,String productID,String date)  {
	String output = "";
	
	try{
		
		Connection con  = connect();
		if (con == null) {
			
			return "Error while connecting to the database for updating.";
		}
		
		
		
			String query = "UPDATE orders SET customerID=?, customerName=?, productID=?, date=? WHERE order_id=?";

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
public String deleteOrder(String order_id) {
	
	String output = "";
	
	try{
		
		Connection con  = connect();
		if (con == null) {
			
			return "Error while connecting to the database for inserting.";
		}
		
		
			 String query = "delete from orders where order_id=?";
				
			 PreparedStatement pstmnt = con.prepareStatement(query);
			 
				pstmnt.setInt(1, Integer.parseInt(order_id));
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
