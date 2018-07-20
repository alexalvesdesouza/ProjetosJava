package br.com.pgi.model.util;

import java.io.Serializable;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailApp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public static void main(String[] args) {
		enviagmail(null, null, null);
	}

	public static void enviagmail(String assunto, String mensagem,
			String destino) {
		Properties props = new Properties();
		/** Par�metros de conex�o com servidor Gmail */
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								"alvesdesouzaalex@gmail.com", "Arroba30@50");
					}
				});

		/** Ativa Debug para sess�o */
		session.setDebug(true);

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("alvesdesouzaalex@gmail.com")); // Remetente

			Address[] toUser = InternetAddress // Destinat�rio(s)
					.parse(destino);

			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(assunto);// Assunto
			message.setText(mensagem);
			/** M�todo para enviar a mensagem criada */
			Transport.send(message);

			System.out.println("Feito!!!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
