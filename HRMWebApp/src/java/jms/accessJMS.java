/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jms;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.proxy.dwr.Util;

/**
 *
 * @author heymeowcat
 */
public class accessJMS {

    Gson gson = new Gson();
    String wholeTrainList = "";

    private Message createJMSMessageFormyTopic(Session session, Object messageData) throws JMSException {
        TextMessage tm = session.createTextMessage();
        tm.setText(messageData.toString());
        return tm;
    }

    public void sendJMSMessageToMyTopic(String messageData) throws JMSException, NamingException {
        Context c = new InitialContext();
        ConnectionFactory cf = (ConnectionFactory) c.lookup("jmsTopicConnFactory");
        Connection conn = null;
        Session s = null;
        try {
            conn = cf.createConnection();
            s = conn.createSession(false, s.AUTO_ACKNOWLEDGE);
            Destination destination = (Destination) c.lookup("myTopic");
            MessageProducer mp = s.createProducer(destination);
            mp.send(createJMSMessageFormyTopic(s, messageData));
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (JMSException e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Cannot close session", e);
                }
            }
            if (conn != null) {
                conn.close();
            }
        }

    }

    public void readJMSMessageToMyTopic() throws JMSException, NamingException {
        WebContext wctx = WebContextFactory.get();
        String currentPage = wctx.getCurrentPage();
        Collection sessions = wctx.getScriptSessionsByPage(currentPage);
        Util utilAll = new Util(sessions);
        Context c = new InitialContext();
        String data = "";
        ConnectionFactory topicConnectionFactory = (ConnectionFactory) c.lookup("jmsTopicConnFactory");
        Connection conn = null;
        Session s = null;
        Session session = null;
        try {
            conn = topicConnectionFactory.createConnection();
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic myTopic = (Topic) c.lookup("myTopic");
            MessageConsumer messageConsumer = session.createSharedDurableConsumer(myTopic, "WEBconsumer1");
            conn.start();
            while (true) {
                Message m = messageConsumer.receiveNoWait();
                if (m != null) {
                    if (m instanceof TextMessage) {
                        TextMessage message = (TextMessage) m;
                        data = message.getText();



                        wholeTrainList = "";
                   
                            utilAll.setValue("trainContainer", wholeTrainList);

                        
                    }
                }
            }

        } catch (JMSException | NamingException e) {
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Cannot close session", e);
                }
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

}
