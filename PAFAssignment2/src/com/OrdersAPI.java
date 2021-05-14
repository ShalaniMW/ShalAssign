package com;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Servlet implementation class OrdersAPI
 */
@WebServlet("/OrdersAPI")
public class OrdersAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Orders OrdObj = new Orders();
	
    public OrdersAPI() {
        super();

    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String output = OrdObj.insertOrder( request.getParameter("customerID"),
				 request.getParameter("customerName"),
				 request.getParameter("productID"),
				 request.getParameter("date"));
				 
		
		 
		 response.getWriter().write(output);
	}

	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
			Map paras = getParasMap(request);
		
		
		
		 String output = OrdObj.updateOrder( paras.get("hidOrdIDSave").toString(),
				 paras.get("customerID").toString(),
				 paras.get("customerName").toString(),
				 paras.get("productID").toString(),
				 paras.get("date").toString());
				
		 
		
		 
		response.getWriter().write(output);
	}

	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map paras = getParasMap(request);
		 
		 String output = OrdObj.deleteOrder(paras.get("order_id").toString());
		 response.getWriter().write(output);
	}
	
	private static Map getParasMap(HttpServletRequest request)
    {
     Map<String, String> map = new HashMap<String, String>();
    try {
     Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
     String queryString = scanner.hasNext() ?
     scanner.useDelimiter("\\A").next() : "";
     scanner.close();
     String[] params = queryString.split("&");
     for (String param : params)
     { 
    	 String[] p = param.split("=");
    	 map.put(p[0], p[1]);
     }
      }catch (Exception e)
    	 {
    	 }
    	return map;  
    }

}
