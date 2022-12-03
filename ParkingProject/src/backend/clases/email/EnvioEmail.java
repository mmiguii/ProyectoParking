package backend.clases.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

/**
 * La función de esta clase email es enviar el dni al correo electronico del
 * trabajador, de tal forma que sea posible recuperar su dni.
 * 
 * @author Miguel Aroztegi, Eduardo Jorge Sanjurjo e Iker Lekuona
 */

public class EnvioEmail {
	/**
	 * La función de este metodo es enviar el email al manager correspondiemte. Para
	 * ello utilizamos el servidor de correo de Gmail y una cuenta previamente
	 * creada para ello.
	 * 
	 * @author Miguel Aroztegi, Eduardo Jorge Sanjurjo e Iker Lekuona
	 * @since 1.1
	 * @version 1.6.2
	 * @param destinatario : correo destino al que se debe enviar el mensaje.
	 * @param asunto       : asunto del correo electrónico que se enviará al
	 *                     destinatario.
	 * @param cuerpo       : contiene al completo el mensaja que se enviará al
	 *                     destinatario.
	 */
	public static void enviarConGMail(String destinatario, String asunto, String cuerpo) {

		String remitente = "noreply.servicioparkingdeusto@gmail.com"; // Para la dirección nomcuenta@gmail.com

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

			JOptionPane.showMessageDialog(null, "Correo enviado");

		} catch (MessagingException me) {
			me.printStackTrace(); // Si se produce un error
			System.out.println("el correro no existe");
		}
	}

	/**
	 * El metodo recibe las caracteristicas del usuario y crea el mensage para
	 * enviarselo mediante el correo oficial de la aplicacion Parking.
	 * 
	 * @param destinatario : correo destino al que se debe enviar el mensaje.
	 * @param nombre:      Nombre personal del usuario del la cuenta Parking y dueno
	 *                     del correo destinatario.
	 * @param dni:         dni antigua del usuario, para que la pueda recordar.
	 */

	public static void bienvenida(String destinatario, String nombre, String dni) {
		String asunto = "Recuperación credenciales (DNI) acceso plataforma - Parking";
		String cuerpo = "Buenos dias " + nombre + ":\n"
				+ "Nos dirigimos a usted para que pueda recuperar su DNI y acceder a la" + " plataforma del Parking. \n"
				+ "\n	DNI de recuperación: " + dni;

		enviarConGMail(destinatario, asunto, cuerpo);
	}

}
