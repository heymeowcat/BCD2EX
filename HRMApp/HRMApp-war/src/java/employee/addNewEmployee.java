/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employee;

import EmployeeInfo.Beans.EmployeesFacade;
import EmployeeInfo.Beans.GenderFacade;
import EmployeeInfo.Beans.LoginFacade;
import EmployeeInfo.Beans.UserroleFacade;
import EmployeeInfo.Entity.Employees;
import EmployeeInfo.Entity.Gender;
import EmployeeInfo.Entity.Login;
import EmployeeInfo.Entity.Userrole;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author heymeowcat
 */
public class addNewEmployee extends HttpServlet {

    @EJB
    private UserroleFacade userroleFacade;

    @EJB
    private GenderFacade genderFacade;

    @EJB
    private LoginFacade loginFacade;

    @EJB
    private EmployeesFacade employeesFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            String newUserRole = request.getParameter("newRole");
            String newUserNic = request.getParameter("newNic");
            String newUserFname = request.getParameter("newFname");
            String newUserLname = request.getParameter("newLname");
            String newUsername = request.getParameter("newUsn");
            String newUserPassword = DigestUtils.md5Hex(request.getParameter("newPsw"));
            String newUserGender = request.getParameter("newGender");

            Employees employee = new Employees();
            employee.setNic(newUserNic);
            employee.setFirstName(newUserFname);
            employee.setLastName(newUserLname);
            employee.setGender(genderFacade.findGenderId(newUserGender));
            employee.setRole(userroleFacade.findUserRolebyName(newUserRole));

            int savedempid = employeesFacade.saveEmployee(employee);

            Login newLogin = new Login(0, savedempid);
            newLogin.setLoginRole(newUserRole);
            newLogin.setUsername(newUsername);
            newLogin.setPassword(newUserPassword);
            newLogin.setStatus("enabled");

            loginFacade.saveLogin(newLogin);

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
