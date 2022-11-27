package backend.clases.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * La función de esta clase email es enviar el dni al correo electronico
 * del trabajador, de tal forma que sea posible recuperar su dni.
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
	 * @version 1.3
	 * @param destinatario : correo destino al que se debe enviar el mensaje.
	 * @param asunto       : asunto del correo electrónico que se enviará al
	 *                     destinatario.
	 * @param cuerpo       : contiene al completo el mensaja que se enviará al
	 *                     destinatario.
	 */
	public static void enviarConGMail(String destinatario, String asunto, String cuerpo) {

		// Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el
		// remitente también.
		String remitente = "noreply.asistenciaParking@gmail.com"; // Para la dirección nomcuenta@gmail.com

		Properties props = System.getProperties();
		props.put("mail.smtp.host", "smtp.gmail.com"); // El servidor SMTP de Google
		props.put("mail.smtp.user", remitente);
		props.put("mail.smtp.clave", "ParkingCompany"); // La clave de la cuenta
		props.put("mail.smtp.auth", "true"); // Usar autenticación mediante usuario y clave
		props.put("mail.smtp.starttls.enable", "true"); // Para conectar de manera segura al servidor SMTP
		props.put("mail.smtp.port", "587"); // El puerto SMTP seguro de Google

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(remitente));
			message.addRecipients(Message.RecipientType.TO, destinatario); // Se podrían anadir varios de la misma
																			// manera
			message.setSubject(asunto);
			message.setText(cuerpo);
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", remitente, "ParkingCompany");
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
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
	 * @param dni:  dni antigua del usuario, para que la pueda recordar.
	 */

	public static void bienvenida(String destinatario, String nombre, String dni) {
		String asunto = "Recuperación de dni - Parking";
		String cuerpo = "Buenos dias " + nombre + ":\n"
				+ "Nos dirigimos a usted para que pueda recuperar su dni y acceder a la"
				+ " plataforma del Parking. \n" + "\n	dni de recuperación: " + dni;

		enviarConGMail(destinatario, asunto, cuerpo);
	}

}
