/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Pinkyyy
 */
public class HelloWorld extends HttpServlet {

    private String message;
    
    public void init()throws ServletException {
        message = "Hello World!";
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //set response content type
        response.setContentType("text/html");
        
        try(PrintWriter out = response.getWriter()){
            out.println("<h1>" + message + "</h1>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
    
    public void destroy(){
        
    }
}
