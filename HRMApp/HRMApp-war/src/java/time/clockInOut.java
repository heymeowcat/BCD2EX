/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package time;

import EmployeeInfo.Beans.AttendanceFacade;
import EmployeeInfo.Beans.EmployeesFacade;
import EmployeeInfo.Beans.WorkshiftsFacade;
import EmployeeInfo.Entity.Attendance;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
public class clockInOut extends HttpServlet {

    @EJB
    private EmployeesFacade employeesFacade;

    @EJB
    private WorkshiftsFacade workshiftsFacade;

    @EJB
    private AttendanceFacade attendanceFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            int empid = Integer.parseInt(request.getParameter("empid"));
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd  HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String newdt = dtf.format(now);

            List<Attendance> searchClockIn = attendanceFacade.searchClockIn(newdt, employeesFacade.findEmployeesid(empid));
            System.out.println("Search size - > " + searchClockIn.size());

            if (searchClockIn.size() == 0) {
                Attendance newAttendance = new Attendance();
                newAttendance.setPunchIn(newdt);
                newAttendance.setPunchInNote("");
                newAttendance.setPunchOut("");
                newAttendance.setPunchOutNote("");
                newAttendance.setDuration("");
                newAttendance.setWorkShiftId(workshiftsFacade.findWorkShiftbyName("HR Manager Morning Shift"));
                newAttendance.setEmployeeId(employeesFacade.findEmployeesid(empid));
                attendanceFacade.saveAttendance(newAttendance);
                out.write(" <span class=\"left\" id=\"punchInOutDateTime\"></span>\n"
                        + "                                        <div class=\"input-field col s12 hiddendiv\">\n"
                        + "                                            <input id=\"newPunchinNote\" type=\"text\" class=\"validate\">\n"
                        + "                                            <label for=\"newPunchinNote\">Clock In Note</label>\n"
                        + "                                        </div>\n"
                        + "                                        <div class=\"input-field col s12 \">\n"
                        + "                                            <input id=\"newPunchoutNode\" type=\"text\" class=\"validate\">\n"
                        + "                                            <label for=\"newPunchoutNode\">Clock Out Note</label>\n"
                        + "                                        </div>\n"
                        + "                                        <button onclick=\"addClockInOut();\" class=\"left blue lighten-1 btn white-text\">Clock Out</button>");
            } else {
                Attendance attendanceGet = searchClockIn.get(0);
                if (attendanceGet.getPunchOut().equals("")) {
                    attendanceGet.setPunchOut(newdt);
                    attendanceGet.setPunchOutNote("");
                    String time1 = attendanceGet.getPunchIn();
                    String time2 = newdt;
                    try {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
                        Date date1 = format.parse(time1);
                        Date date2 = format.parse(time2);

                        long difference = Math.abs(date2.getTime() - date1.getTime());

                        long diffSeconds = difference / 1000 % 60;
                        long diffMinutes = difference / (60 * 1000) % 60;
                        long diffHours = difference / (60 * 60 * 1000) % 24;
                        long diffDays = difference / (24 * 60 * 60 * 1000);

                        attendanceGet.setDuration(diffHours+":"+diffMinutes +":"+diffSeconds);
                        attendanceFacade.updateAttendance(attendanceGet);
                        out.write(" <span class=\"left\" id=\"punchInOutDateTime\"></span>\n"
                                + "                                        <button class=\"left blue lighten-1 btn white-text\">Finished</button>");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

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
