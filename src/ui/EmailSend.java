/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * https://www.google.com/settings/security/lesssecureapps
 */

package ui;

/**
 *
 * @author Naveen
 */
import java.util.*;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import sql.dao.TentRodProperties;
import sql.entity.Car;
import sql.entity.Customer;
public class EmailSend {
    public Customer customer;
    public Car car;

    public EmailSend() {
    }

    public EmailSend(Customer customer,Car car){
        this.customer = customer;
        this.car = car;
    }

    public void sending(){
        try{
            TentRodProperties tentRodProperties = new TentRodProperties();
            String host ="smtp.gmail.com" ;
            String user = tentRodProperties.getSysEmail();
            String pass = tentRodProperties.getSysPass();
            String to = tentRodProperties.getNotiEmail();
            String cc = customer.getEmail();
            String from = tentRodProperties.getSysEmail();
//            String user = "tentroad1234@gmail.com";
//            String pass = "tent1234";
//            String to = "tentroad1234@gmail.com";
//            String cc = customer.getEmail();
//            String from = "tentroad1234@gmail.com";
            //email's headline
            String subject = "จองรถ ทะเบียน "+car.getLicense() + " จากคุณ " + customer.getName();
            //email's message body
            String messageText = "ฉันสนใจรถที่มีเลขทะเบียน "+car.getLicense() +"กรุณาติดต่อกลับที่ Tel: " + customer.getTel()+ " หรือ Email: " + customer.getEmail();
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            InternetAddress[] address2 = {new InternetAddress(cc)};
            msg.setRecipients(Message.RecipientType.CC, address2);
            msg.setSubject(subject); msg.setSentDate(new Date());
            msg.setText(messageText);
            MimeMultipart multipart = new MimeMultipart("mixed");

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            //locate image file
            byte[] pic = car.getImage();
            ByteArrayDataSource source = new ByteArrayDataSource(pic, "image/*");
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setHeader("Content-ID","image/*");
            messageBodyPart.setFileName("car.png");
            multipart.addBodyPart(messageBodyPart);

            // Create the HTML text part of the message.
            String html = "<html>"
                    + "<head>"
                    + "<title>" + subject + "</title>"
                    + "<head>"
                    + "<body>" +
                    "<H1>" + "แจ้งเตือนการจองรถ" + "</H1>" +

                    "<table>" +
                    "<tr><td>"+"Customer Name : "+"</td><td>"+customer.getTitle()+customer.getName()+" "+customer.getSurname()+"</td></tr>" +
                    "<tr><td>"+"Brand : "+"</td><td>"+car.getBrand()+"</td></tr>" +
                    "<tr><td>"+"Model : "+"</td><td>"+car.getModel()+"</td></tr>" +
                    "<tr><td>"+"Sub Model : "+"</td><td>"+car.getSubModel()+"</td></tr>" +
                    "<tr><td>"+"Color : "+"</td><td>"+car.getColor()+"</td></tr>" +
                    "<tr><td>"+"License : "+"</td><td>"+car.getLicense()+"</td></tr>" +
                    "<tr><td>"+"วันทำรายการ : "+"</td><td>"+new Date()+"</td></tr>" +
                    "</table>"

                    + "</body>"
                    + "</html>";

            MimeBodyPart htmlTextPart = new MimeBodyPart();
            htmlTextPart.setContent(html, "text/html;charset=UTF-8");
            multipart.addBodyPart(htmlTextPart);


            msg.setContent(multipart);
            Transport transport=mailSession.getTransport("smtp");
            transport.connect(host, user, pass);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            System.out.println("message send successfully");
        }catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println(ex);
        }

    }
}
