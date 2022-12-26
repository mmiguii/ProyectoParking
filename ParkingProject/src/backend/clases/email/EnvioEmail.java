package backend.clases.email;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * La funcion de esta clase email es enviar el dni al correo electronico del
 * trabajador, de tal forma que sea posible recuperar su dni.
 * 
 * @author Miguel Aroztegi, Eduardo Jorge Sanjurjo e Iker Lekuona
 */

public class EnvioEmail {

	private static Logger logger = Logger.getLogger(EnvioEmail.class.getName());

	/**
	 * Metodo que envia un correo electronico a un destinatario especificado a
	 * traves de una cuenta de correo electronico de Gmail.
	 * 
	 * @author Miguel Aroztegi, Eduardo Jorge Sanjurjo e Iker Lekuona
	 * @since 1.1
	 * @version 1.6.2
	 * @param destinatario : correo destino al que se debe enviar el mensaje.
	 * @param asunto       : asunto del correo electrónico que se enviara al
	 *                     destinatario.
	 * @param cuerpo       : contiene al completo el mensaja que se enviara al
	 *                     destinatario.
	 */
	public static void enviarConGMail(String destinatario, String asunto, String cuerpo) {

		String remitente = "noreply.servicioparkingdeusto@gmail.com"; // Para la direccion nomcuenta@gmail.com

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtp.port", "587");
		props.setProperty("mail.smtp.user", remitente);
		props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		props.setProperty("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(remitente));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			message.setSubject(asunto);
			message.setText(cuerpo, "ISO-8859-1", "html");

			Transport transport = session.getTransport("smtp");

			transport.connect(remitente, "labttufhikfhedbw");

			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();

			logger.info("Correo enviado");

		} catch (MessagingException me) {
			logger.severe(String.format("%s %s", me.getMessage(), me.getCause().getMessage()));
		}
	}

	/**
	 * Metodo que recibe las caracteristicas del usuario y crea el mensage para
	 * enviarselo mediante el correo oficial de la aplicacion Parking.
	 * 
	 * @param destinatario : correo destino al que se debe enviar el mensaje.
	 * @param nombre:      Nombre personal del usuario del la cuenta Parking y dueno
	 *                     del correo destinatario.
	 * @param password:    password nuevo del usuario.
	 */

	public static void bienvenida(String destinatario, String nombre, String password) {
		String asunto = "Recuperación credenciales (Password) acceso plataforma - Parking";
		String cuerpo = "Hola " + nombre
				+ ": Nos dirigimos a usted para que pueda recuperar su password y acceda a la plataforma del Parking. Este es su nuevo password: '"
				+ password + "'. Un saludo";
		enviarConGMail(destinatario, asunto, cuerpo);
	}

}
