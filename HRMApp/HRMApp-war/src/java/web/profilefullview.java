/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author heymeowcat
 */
public class profilefullview extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            String employeeid = request.getParameter("q");
            
//            out.write("<i class='material-icons right waves-effect modal-close " + Dcolor + "'>close</i>");
//            out.write("<ul class='" + Acolor + " collapsible 'style='border-color: " + Ccolor + "'>");
//            out.write("<li class='active'>");
//            out.write("<div class='" + Acolor + " collapsible-header' style='border-color: " + Ccolor + "'><b class='" + Dcolor + "'>Profile</b></div>");
//            out.write("<div class='" + Ecolor + " collapsible-body' style='border-color: " + Ccolor + "'>");
//            out.write("<div class='center'>");
//            out.write("<img style='height: 150px; width: 150px' src=' " + up + " '  class='circle responsive-img hide-on-small-and-down animated fadeIn'>");
//            out.write("<img style='height: 100px; width: 100px' src='" + up + "'  class='circle responsive-img hide-on-med-and-up animated fadeIn'>");
//            out.write("<br>");
//            out.write("<h4 class='hide-on-small-and-down'>" + fn + " " + ln + "</h4>");
//            out.write("<h5 class='hide-on-med-and-up'>" + fn + " " + ln + "</h5>");
//            out.write("<div id ='followun'>");
//            try {
//                if (loggeduid == uid) {
//
//                } else {
//                    java.sql.ResultSet iffillow = DB.search("SELECT receiver FROM `follow` WHERE `sender`='" + loggeduid + "' AND receiver='" + uid + "' ");
//                    if (iffillow.next()) {
//                        out.write("<a onclick='unfollow(" + loggeduid + "," + uid + "); refreshhhh()' class='" + Dcolor + " " + Bcolor + " btn-small' >Unfollow</a>");
//                    } else {
//                        out.write("<a onclick='follow(" + loggeduid + "," + uid + "); refreshhhh()' class='" + Dcolor + " " + Bcolor + " btn-small' >Follow</a>");
//                    }
//                }
//                out.write("</div>");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            out.write("</div>");
//            out.write("<br>");
//            out.write("<br>");
//            out.write("<div class='row center'>");
//            try {
//                java.sql.ResultSet postcount = DB.search("SELECT COUNT(`users_idusers`) FROM `post` WHERE `users_idusers`='" + uid + "' ");
//                if (postcount.next()) {
//                    usrpostcount = postcount.getString(1);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            out.write("<div class='col s4 waves-effect'> <span class='transparent '>Posts</span><br><b>" + usrpostcount + "</b></div>");
//            try {
//                java.sql.ResultSet postcount = DB.search("SELECT DISTINCT COUNT(`sender`) FROM `follow` WHERE `receiver`='" + uid + "' ");
//                if (postcount.next()) {
//                    followercount = postcount.getString(1);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            out.write("<div class='col s4 waves-effect'> <span class='transparent '>Followers</span><br><b>" + followercount + "</b></div>");
//            try {
//                java.sql.ResultSet postcount = DB.search("SELECT DISTINCT COUNT(`receiver`) FROM `follow` WHERE `sender`='" + uid + "' ");
//                if (postcount.next()) {
//                    followingcount = postcount.getString(1);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            out.write("<div class='col s4 waves-effect'> <span class='transparent '>Following</span><br><b>" + followingcount + "</b></div>");
//            out.write("</div>");
//            out.write("</div>");
//            out.write("</li>");
//            out.write("<li>");
//            out.write("<div class='" + Acolor + " " + Dcolor + " collapsible-header' style='border-color: " + Ccolor + "'><b>Posts</b></div>");
//            out.write("<div class='" + Ecolor + " collapsible-body' style='border-color: " + Ccolor + "' >");
out.write("hello profile" +employeeid);
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
