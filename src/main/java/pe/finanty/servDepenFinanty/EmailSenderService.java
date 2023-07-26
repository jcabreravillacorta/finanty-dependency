package pe.finanty.servDepenFinanty;


import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSenderService {

    private final Properties properties = new Properties();

    private Session session;

    private void init(String ipHost, Integer port, String sender) {

        properties.put("mail.smtp.host", ipHost); //"172.23.2.110"
        properties.put("mail.smtp.ssl.trust", "*");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", port); //25
        properties.put("mail.smtp.auth", "false");
        properties.put("mail.smtp.mail.sender", sender); //"reporte01@conecta.com.pe"

        session = Session.getDefaultInstance(properties);
    }

    public void sendEmail(String[] to, String content, String subject, String ipHost, Integer port, String sender) {
        String sb = String.join(",", to);
        init(ipHost, port, sender);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress((String) properties.get("mail.smtp.mail.sender")));
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(sb));
            message.setSubject(subject);
            message.setContent(content, "text/html; charset=ISO-8859-1");
            Transport t = session.getTransport("smtp");
            t.connect((String) properties.get("mail.smtp.user"), "password");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (MessagingException me) {
            System.out.println(me.getMessage());
        }

    }
}
