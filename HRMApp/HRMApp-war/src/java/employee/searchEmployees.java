/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employee;

import EmployeeInfo.Beans.EmployeesFacade;
import EmployeeInfo.Beans.UserroleFacade;
import EmployeeInfo.Entity.Employees;
import EmployeeInfo.Entity.Userrole;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author heymeowcat
 */
public class searchEmployees extends HttpServlet {

    @EJB
    private UserroleFacade userroleFacade;

    @EJB
    private EmployeesFacade employeesFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            String searchRole = request.getParameter("searchRole");
            String searchEmpName = request.getParameter("searchEmpname");


            List<Employees> searchEmployees;
            if (!searchRole.equals("Choose User Role")) {
                Userrole userrolesearched = userroleFacade.findUserRolebyName(searchRole);
                searchEmployees = employeesFacade.searchEmployees(searchEmpName, userrolesearched);

            } else {
                searchEmployees = employeesFacade.searchEmployeesFirstName(searchEmpName);
            }

            String table = "<table class=\"highlight\">\n"
                    + "                                        <thead>\n"
                    + "                                            <tr>\n"
                    + "                                                <th>Id</th>\n"
                    + "                                                <th>NIC</th>\n"
                    + "                                                <th>Name</th>\n"
                    + "                                                <th>User Role</th>\n"
                    + "                                                <th>Gender</th>\n"
                    + "                                            </tr>\n"
                    + "                                        </thead>\n"
                    + "\n"
                    + "                                        <tbody>\n";

            for (Employees emp : searchEmployees) {
                table += "                                            <tr onclick=\"showprofile(" + emp.getIdEmployees() + ");$('#peekprofile').modal('open');$('#peekprofile').modal('open')  \"  style=\"cursor: pointer\" >\n"
                        + "                                                <td>" + emp.getIdEmployees() + "</td>\n"
                        + "                                                <td>" + emp.getNic() + "</td>\n"
                        + "                                                <td>" + emp.getFirstName() + " " + emp.getLastName() + "</td>\n"
                        + "                                                <td>" + emp.getRole().getUserRole() + "</td>\n"
                        + "                                                <td>" + emp.getGender().getGenderName() + "</td>\n"
                        + "                                            </tr>\n";
            }

            table += "\n"
                    + "                                        </tbody>\n"
                    + "                                    </table>";

            out.write(table);

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
