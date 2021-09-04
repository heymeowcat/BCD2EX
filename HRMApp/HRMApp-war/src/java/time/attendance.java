/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package time;

import EmployeeInfo.Beans.AttendanceFacade;
import EmployeeInfo.Entity.Attendance;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author heymeowcat
 */
public class attendance extends HttpServlet {

    @EJB
    private AttendanceFacade attendanceFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            String table = "<table class=\"highlight\">\n"
                    + "                                        <thead>\n"
                    + "                                            <tr>\n"
                    + "                                                <th>Name</th>\n"
                    + "                                                <th>Punch In</th>\n"
                    + "                                                <th>Punch In Note</th>\n"
                    + "                                                <th>Punch Out</th>\n"
                    + "                                                <th>Punch Out Note</th>\n"
                    + "                                                <th>Duration</th>\n"
                    + "                                                <th>Work Shift</th>\n"
                    + "                                            </tr>\n"
                    + "                                        </thead>\n"
                    + "\n"
                    + "                                        <tbody>\n";

            for (Attendance attendance : attendanceFacade.findAll()) {
                table += "                                            <tr onclick=\"showAttedancerecord(" + attendance.getIdAttendance() + ");$('#peekattendance').modal('open');  \"  style=\"cursor: pointer\" >\n"
                        + "                                                <td>" + attendance.getEmployeeId().getFirstName() + " " + attendance.getEmployeeId().getLastName() + "</td>\n"
                        + "                                                <td>" + attendance.getPunchIn() + "</td>\n"
                        + "                                                <td>" + attendance.getPunchInNote() + "</td>\n"
                        + "                                                <td>" + attendance.getPunchOut() + "</td>\n"
                        + "                                                <td>" + attendance.getPunchOutNote() + "</td>\n"
                        + "                                                <td>" + attendance.getDuration() + "</td>\n"
                        + "                                                <td><button class='btn'>Work Shift</td>\n"
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
