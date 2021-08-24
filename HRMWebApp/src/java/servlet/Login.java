/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;


import SessionBeans.LoginFacadeRemote;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.Entity;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import util.ENCDEC;
import util.KEY;

/**
 *
 * @author heymeowcat
 */
public class Login extends HttpServlet {

    @EJB
    private LoginFacadeRemote loginFacade;

    
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
//            List<Entitiy.Login> findAll = loginFacade.findAll();
//            
//            for(Entitiy.Login log :findAll){
//                out.write(log.getUsername());
//            }
            
            
//            String usn = request.getParameter("usn");
//            String psw = DigestUtils.md5Hex(request.getParameter("psn"));
//            String check = request.getParameter("check");
//
//            Entity.Login logindetails = loginFacade.loginProcess(usn, psw);
//
//            if (logindetails != null) {
//                HttpSession ses = request.getSession();
//                ses.setAttribute("employeeId", logindetails.getEmployees().getIdEmployees());
//
//                if (check != null) {
//                    String encryptedString = ENCDEC.encrypt(logindetails.getEmployees().getIdEmployees().toString(), new KEY().secretKey);
//                    Cookie cookie = new Cookie("employeeId", encryptedString);
//                    cookie.setMaxAge(60 * 60 * 24 * 30);
//                    response.addCookie(cookie);
//                }
//            }else{
//                out.print("no user");
//            }
        

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
