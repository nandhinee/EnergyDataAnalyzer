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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Murugappan
 */
public class DeleteProfile extends HttpServlet {

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
            out.println("<title>Servlet DeleteProfile</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeleteProfile at " + request.getContextPath() + "</h1>");
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
        //processRequest(request, response);
            PrintWriter print1 = response.getWriter();
            String deluid=request.getParameter("UIDtodelete");
            //send e-mail delete profile notification to the user
            
//                    HttpSession session = request.getSession(true);
//        String userMailIdtoDelete = (String) session.getAttribute("currentUserMailID");
//        System.out.println("\t\t From SessionUser Reg:"+userMailIdtoDelete);
          //print1.print("Test Delete"+uid);
            try{
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con = DriverManager.getConnection("jdbc:odbc:server");
            System.out.println("\n Step 1 - get email id from DB");
            
           
            //amber's code to send mail to user
            
            Statement stcheck=con.createStatement();   
           String getmailid="select [Fname],[MailID] from [UserReg] where [UID]='"+deluid+"'"  ; 
           ResultSet rsmail= stcheck.executeQuery(getmailid);
           String mailidfromDB=null;
           String userfnamefromDB=null;
           
                while(rsmail.next())
                {
                    userfnamefromDB=rsmail.getString("Fname");
                     mailidfromDB = rsmail.getString("MailID");
                }
            
            
             System.out.println("\n \t\temail id from DB"+mailidfromDB+userfnamefromDB);              // String content="Hi "+userfnamefromDB+","+"<br> <br><b>Sorry to see you go !!!<br><font color='orangered'>This is an e-mail confirmation to inform you that your Energy Data Analyzer profile has been deleted</font></b>. <br><br><i>You will no longer receive any e-mail communication from us</i>.<br><br>Thanks,<br>Energy Data Team";
       String content="<font color='#1e6dac'><b>Hi "+userfnamefromDB+","+"<br><br>Your Energy Data Analyzer profile has been deleted and you will no longer recive any mail from us.</font></b><br><br>Thanks,<br>Energy Data Team";
                
                        Mailing mail=new Mailing();        //System.out.println("Mail Check:");
        System.out.println(mail.MailingHTML(mailidfromDB, "Energy Data Analyzer Profile Deleted",content));
            
             
             //ashley's code
             Statement Delete=con.createStatement();
            String delfromUserReg=" delete from [UserReg] where UID='"+deluid+"'";
            int reuser= Delete.executeUpdate(delfromUserReg);
          
             String delfromProfile=" delete from [UserProfile] where UID='"+deluid+"'";
            int reprofile= Delete.executeUpdate(delfromProfile);
            
             String delfromActivity=" delete from [UserActivity] where UID='"+deluid+"'";
            int reActivity= Delete.executeUpdate(delfromActivity);
            
            System.out.println("From User Reg:"+reuser);
            System.out.println("From User Reg:"+reprofile);
            System.out.println("From User Reg:"+reActivity);
            response.sendRedirect(response.encodeRedirectURL("http://localhost:8084/Project/"));
             Delete.close();  //close all connections
         //  stcheck.close();
           con.close();
               }

               catch (Exception e)
               {
                   print1.print(e);
               }
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
