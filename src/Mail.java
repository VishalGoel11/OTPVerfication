import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class Mail {
    public int send(String email) {
        String from = "<user_name>";
        String host = "smtp.gmail.com";
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", host);
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, "<Password>");
                    }
                });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(from);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(email));
            message.setSubject("OTP-Verification");
            Random rnd = new Random();
            int code = (int)(rnd.nextDouble()*100000);
            message.setText("Your Otp is : "+code+".\n It will expire after 1 minutes.");
            Transport.send(message);
            return code;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
