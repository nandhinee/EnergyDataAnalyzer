/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Murugappan
 */
public class LoginServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
      //  processRequest(request, response);
        String user=request.getParameter("eUserID");
        String pass=request.getParameter("epassword");
        PrintWriter print1 = response.getWriter();
        print1.print("user name value:\t"+user+"\tpassword:"+pass);
        
           try
        {
 
           Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
           Connection con=DriverManager.getConnection("jdbc:odbc:DBCON");
           Statement stcheck=con.createStatement();  
          // Statement stinsert=con.createStatement(); 
           
           String checklogin="select [UID],[Pass] from [UserReg] where [UID]='"+user+"' and [Pass]='"+pass+"'";  //ogin Check performed 
           String check=null;
           ResultSet rs= stcheck.executeQuery(checklogin);
           
                while(rs.next())
                {
                        {
                            check = rs.getString("UID");
                           // print1.print("\nUser Name "+UIDcheck+" Already Exists..Please Choose a different User ID!! ");
                        }  
                }
          
                if(check==null)
                {
                      //print1.print("\nInvalid!!!");
                    //String insertstring="Insert into UserReg values('"+user+"','"+ulname+"','"+uid+"','"+pass+"','"+umailid+"','"+usecurityq+"','"+usecurityans+"')";
                  //  stinsert.executeUpdate(insertstring);
                   String contextPath= "http://localhost:8084/Project/LoginFailed.html?user="+user;
                   response.sendRedirect(response.encodeRedirectURL(contextPath));
                    
                }
                else
                {
                    //  print1.print("\nValid User and Password!!!");
                    
                    String contextPath= "http://localhost:8084/Project/home.html?user="+user;
                    response.sendRedirect(response.encodeRedirectURL(contextPath));
                }
          
          // 
          // 
           
           rs.close();  //close all connections
           //stcheck.close();
           stcheck.close();
           con.close();
           
           
           
        }
         catch(Exception e)
         {
             System.out.println(e);
             print1.print(e);
             print1.print("Exception");
         }
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
       // processRequest(request, response);
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
