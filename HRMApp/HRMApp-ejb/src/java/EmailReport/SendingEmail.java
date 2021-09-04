package EmailReport;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author HEYMEOWCAT
 */
public class SendingEmail {

    String useremail;

    
    
    
    public void sendMail(String useremail,String html) {
        this.useremail = useremail;
        final String email = EmailInfo.EMAIL;
        final String password = EmailInfo.PASSWORD;
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "*");

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(useremail));

            message.setSubject("Siyarata HRM Daily Report");
            message.setText("Daily Attendance Report");
            Multipart mp = new MimeMultipart();
            MimeBodyPart htmlPart = new MimeBodyPart();
            
            

            htmlPart.setContent(html, "text/html");
            mp.addBodyPart(htmlPart);
            message.setContent(mp);
            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}