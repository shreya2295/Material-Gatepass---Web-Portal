package web;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.Calendar;
import java.util.Random;
import javax.swing.JOptionPane;
import model.*;

public class SendMail {
  
  public void mailsend (String s2, String s1, int i){  

          final String username = "gatepass.leos@gmail.com";
          final String password = "gatepass123";
	  String mailid= null;
          Properties props = new Properties();
          props.put("mail.smtp.auth", "true");
          props.put("mail.smtp.starttls.enable", "true");
          props.put("mail.smtp.host", "smtp.gmail.com");
          props.put("mail.smtp.port", "587");
          Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
     try
	{ Class.forName("com.mysql.jdbc.Driver");
	  Connection con=DriverManager.getConnection ("jdbc:mysql://localhost/material_gatepass","root","Walnut01");
           PreparedStatement st = con.prepareStatement("select emailid from users where userid='"+ s2 +"'");
         ResultSet rs = st.executeQuery();
      		while(rs.next()){
      		mailid=rs.getString(1);
               }
      }catch(Exception e)
		{
          	JOptionPane.showMessageDialog(null, e.getMessage());
 		}
    
      try{
       Message message = new MimeMessage(session);
       message.setFrom(new InternetAddress("gatepass.leos@gmail.com"));
       message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(mailid));
       message.setSubject("[Due Item] Pending Items for Return");
       message.setText("Your Material ID "+ s1+ " is due for return in "+ i +" days");
       Transport.send(message);
       System.out.println("Mail sent succesfully!");
       }
           catch (MessagingException e) {
           throw new RuntimeException(e);
       }

 } 
}
