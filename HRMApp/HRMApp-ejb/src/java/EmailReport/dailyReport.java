/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmailReport;

import EmployeeInfo.Beans.AttendanceFacade;
import EmployeeInfo.Entity.Attendance;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

/**
 *
 * @author heymeowcat
 */
@Startup
@Singleton
@LocalBean
public class dailyReport {

    @EJB
    private AttendanceFacade attendanceFacade;

    @Resource
    TimerService timerService;

    private Date lastProgrammaticTimeout;
    private Date lastAutomaticTimeout;


    private static final Logger logger
            = Logger.getLogger("timersession.ejb.TimerSessionBean");

    public void setTimer(long intervalDuration) {
        logger.log(Level.INFO,
                "Setting a programmatic timeout for {0} milliseconds from now.",
                intervalDuration);
        Timer timer = timerService.createTimer(intervalDuration,
                "Created new programmatic timer");
    }

    @Timeout
    public void programmaticTimeout(Timer timer) {
        this.setLastProgrammaticTimeout(new Date());
        logger.info("Programmatic timeout occurred.");
    }

    @Schedule(minute = "0", hour = "0", persistent = false)
    public void automaticTimeout() {
        this.setLastAutomaticTimeout(new Date());
        logger.info("Automatic timeout occurred");
        SendingEmail s = new SendingEmail();

        String table = "<table class=\"highlight\">\n"
                + "                                        <thead>\n"
                + "                                            <tr>\n"
                + "                                                <th>Name</th>\n"
                + "                                                <th>Punch In</th>\n"
                + "                                                <th>Punch In Note</th>\n"
                + "                                                <th>Punch Out</th>\n"
                + "                                                <th>Punch Out Note</th>\n"
                + "                                                <th>Duration</th>\n"
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
                    + "                                            </tr>\n";
        }

        table += "\n"
                + "                                        </tbody>\n"
                + "                                    </table>";

      
        
        s.sendMail("", table);
    }

    /**
     * @return the lastTimeout
     */
    public String getLastProgrammaticTimeout() {
        if (lastProgrammaticTimeout != null) {
            return lastProgrammaticTimeout.toString();
        } else {
            return "never";
        }
    }

    /**
     * @param lastTimeout the lastTimeout to set
     */
    public void setLastProgrammaticTimeout(Date lastTimeout) {
        this.lastProgrammaticTimeout = lastTimeout;
    }

    /**
     * @return the lastAutomaticTimeout
     */
    public String getLastAutomaticTimeout() {
        if (lastAutomaticTimeout != null) {
            return lastAutomaticTimeout.toString();
        } else {
            return "never";
        }
    }

    /**
     * @param lastAutomaticTimeout the lastAutomaticTimeout to set
     */
    public void setLastAutomaticTimeout(Date lastAutomaticTimeout) {
        this.lastAutomaticTimeout = lastAutomaticTimeout;
    }

}
