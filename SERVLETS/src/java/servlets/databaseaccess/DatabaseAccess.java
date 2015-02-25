/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.databaseaccess;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

/**
 *
 * @author domarkwa
 */
public class DatabaseAccess extends HttpServlet {

    String URL = "jdbc:mysql://localhost/test";
    String USER = "root";
    String PASS = "root";
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Statement stmt = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            //register JDBC driver - register library in project properties!!
            Class.forName("com.mysql.jdbc.Driver");            
            //Driver myDriver = new com.mysql.jdbc.Driver();
            //DriverManager.registerDriver(myDriver);
            
            /*Properties info = new Properties( );
            info.put( "user", "root" );
            info.put( "password", "root" );
            Connection conn = DriverManager.getConnection(URL, info);*/
            
            //open a connection
            conn = DriverManager.getConnection(URL, USER, PASS);
            
            //create select query
            stmt = conn.createStatement();
            String sql;      
            sql = "SELECT id, first, last, age FROM employees";
            
            //execute query            
            ResultSet rs = stmt.executeQuery(sql);
            
            // Set response content type
            response.setContentType("text/html");
            
            try(PrintWriter out = response.getWriter()){
                //extract data from resultset
                while(rs.next()){
                    //Retrieve by column name
                    int id  = rs.getInt("id");
                    int age = rs.getInt("age");
                    String first = rs.getString("first");
                    String last = rs.getString("last");

                    //Display values
                    out.println("ID: " + id + "<br>");
                    out.println(", Age: " + age + "<br>");
                    out.println(", First: " + first + "<br>");
                    out.println(", Last: " + last + "<br>");
                }
            }
            
            //create update query
            sql = "UPDATE employees SET age = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);   
            pstmt.setInt(1, 50);
            pstmt.setInt(2, 103);
            pstmt.executeUpdate();
            
            // Clean-up environment
            rs.close();
            stmt.close();
            pstmt.close();
            conn.close();
         
        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        finally{ //finally block used to close resources
            
            try{
               if(stmt!=null)
                  stmt.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
            
            try{
               if(conn!=null)
               conn.close();
            }catch(SQLException e){
                e.printStackTrace();
            }//end finally try
        } //end try
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
