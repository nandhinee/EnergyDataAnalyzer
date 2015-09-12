/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Murugappan
 */
import java.io.File;
import java.io.IOException;
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




public class Mailing {
	private static final String PROPERTIES_FILE="WebContent/WEB-INF/properties/email.properties";
	String d_email = "energydatateam@gmail.com",
			d_password="sefall2014",
			d_host = "smtp.gmail.com",
			d_port = "465", 
			m_to , m_subject, m_text;


	public boolean MailingHTML(String m_to,String m_subject,String m_text) {
		this.m_subject=m_subject;
		this.m_text=m_text;
		this.m_to=m_to;
		boolean status =false;
                String mailContent=m_text;

		Properties props = new Properties();
		props.put("mail.smtp.user", d_email);
		props.put("mail.smtp.host", d_host);
		props.put("mail.smtp.port", d_port);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		//props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.socketFactory.port", d_port);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");

		SecurityManager security = System.getSecurityManager();

		try {
			Authenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(props, auth);
			//session.setDebug(true);

			MimeMessage msg = new MimeMessage(session);
			
			
			
			Multipart mp = new MimeMultipart();

			MimeBodyPart htmlPart = new MimeBodyPart();
			//htmlPart.setContent((new VelocityImpl()).getHtmlContent(), "text/html");
                        htmlPart.setContent(mailContent,"text/html");
                     //  htmlPart.setMessage();
			mp.addBodyPart(htmlPart);
			
			//Attachment
/*			MimeBodyPart attachment = new MimeBodyPart();
			attachment.setFileName("manual.pdf");
			attachment.setContent(attachmentData, "application/pdf");
			mp.addBodyPart(attachment);*/

			msg.setContent(mp);


			//			msg.setText(m_text);
			msg.setSubject(m_subject);
			msg.setFrom(new InternetAddress(d_email));
			msg.setSubject(m_subject);
			msg.setFrom(new InternetAddress(d_email));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(m_to));
			Transport.send(msg);
			status=true;
		} catch (Exception mex) {
			mex.printStackTrace();return status;
		}
		return status;
	}

/*	//Testing method
	public boolean MailingHTML(String m_to,String m_subject,String m_text, String htmlBody, byte[] attachmentData) throws IOException {
		this.m_subject=m_subject;
		this.m_text=m_text;
		this.m_to=m_to;
		boolean status =false;

		Properties properties = new Properties();
		properties.load(getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE));
//		properties.getProperty("mail.smtp.password", "CS441_CMS");
		
		properties.getProperty("mail.smtp.password");
		SecurityManager security = System.getSecurityManager();

		try {
			Authenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(properties, auth);
			//session.setDebug(true);

			MimeMessage msg = new MimeMessage(session);

			Multipart mp = new MimeMultipart();

			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent((new VelocityImpl()).getHtmlContent(), "text/html");
			mp.addBodyPart(htmlPart);
			
			//Attachment
			MimeBodyPart attachment = new MimeBodyPart();
			attachment.setFileName("manual.pdf");
			attachment.setContent(attachmentData, "application/pdf");
			mp.addBodyPart(attachment);

			msg.setContent(mp);


			//			msg.setText(m_text);
			msg.setSubject(m_subject);
			msg.setFrom(new InternetAddress(d_email));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(m_to));
			Transport.send(msg);
			status=true;
		} catch (Exception mex) {
			mex.printStackTrace();return status;
		}
		return status;
	}*/


	private class SMTPAuthenticator extends javax.mail.Authenticator {



		@Override
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(d_email, d_password);
		}
	}
//	public static void main(String[] args) {
//
//
//		//	   String m_to="varma_shravan@yahoo.com" ;
//		//	   String m_to="agunta2@uic.edu" ;
//		String m_to="mnachi2@uic.edu";
//		String m_subject = "Registration Confirmation";
//		String m_text="Registration Confirmation" ;
//
//		File file = new File(PROPERTIES_FILE);
//		System.out.println(file.getAbsolutePath());
//		try {
//			System.out.println(file.getCanonicalPath());
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		Mailing mail=new Mailing();
//		System.out.println(mail.MailingHTML(m_to, m_subject,m_text));
///*		try {
////			System.out.println(mail.MailingHTML(m_to, m_subject, m_text, null, null));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}*/
//
//
//	}

}