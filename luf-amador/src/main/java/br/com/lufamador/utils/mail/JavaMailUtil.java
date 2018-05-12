package br.com.lufamador.utils.mail;

import org.springframework.stereotype.Component;

/***
 * 
 * @author alexalvesdesouza
 *
 */
@Component
public class JavaMailUtil {

//
//  private Properties getProperties() {
//
//    Properties props = new Properties();
//    props.put("mail.smtp.host", "smtp.gmail.com");
//    props.put("mail.smtp.socketFactory.port", "465");
//    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//    props.put("mail.smtp.auth", "true");
//    props.put("mail.smtp.port", "465");
//
//    return props;
//  }
//
//  public Boolean sendEmail(String msg, String assunto) {
//
//    Properties props = getProperties();
//
//    Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
//      protected PasswordAuthentication getPasswordAuthentication() {
//        return new PasswordAuthentication("openlinkti@gmail.com",
//                                          "caj29al05");
//      }
//    });
//
//    /** Ativa Debug para sessão */
//    session.setDebug(true);
//
//    try {
//
//      Message message = new MimeMessage(session);
//      message.setFrom(new InternetAddress("openlinkti@gmail.com"));
//
//      //rmlocacoes4@gmail.com
//      Address[] toUser = InternetAddress.parse("rmlocacoes4@gmail.com, alvesdesouzaalex@gmail.com");
//
//      message.setContent(msg,"text/html; charset=UTF-8");
//      message.setRecipients(Message.RecipientType.TO, toUser);
//      message.setSubject(assunto);// Assunto
//      //message.setText(msg);
//      /** Método para enviar a mensagem criada */
//      Transport.send(message);
//
//
//    } catch (MessagingException e) {
//      throw new RuntimeException(e);
//    }
//
//    return true;
//  }

}
