package backend.clases.email;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void enviarConGMail(String destinatario, String asunto, String cuerpo) {
		
		Properties props = new Properties();
		
		try (FileReader fr = new FileReader("configuracion.properties")){
			props.load(fr);
			
			String remitente = props.getProperty("E-mail"); // Para la direccion nomcuenta@gmail.com

			props.getProperty("mail.smtp.host");
			props.getProperty("mail.smtp.ssl.trust");
			props.getProperty("mail.smtp.starttls.enable");			
			props.getProperty("mail.smtp.port");
			props.getProperty("mail.smtp.user");			
			props.getProperty("mail.smtp.ssl.protocols");
			props.getProperty("mail.smtp.auth");

			Session session = Session.getDefaultInstance(props);
			
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(remitente));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
				message.setSubject(asunto);
				message.setText(cuerpo, props.getProperty("charset"), props.getProperty("subtype"));
	
				Transport transport = session.getTransport(props.getProperty("protocol"));
	
				transport.connect(remitente, "labttufhikfhedbw");
	
				transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
				transport.close();
	
				logger.info("Correo enviado");
	
			} catch (MessagingException me) {
				logger.severe(String.format("%s %s", me.getMessage(), me.getCause().getMessage()));
			}
		} catch (FileNotFoundException e) {
			logger.info("No se ha podido encontrar el fichero de propiedades");
		} catch (IOException e) {
			logger.info("No se ha podido leer del fichero de propiedades");
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
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */

	public static void bienvenida(String destinatario, String nombre, String password)
			throws FileNotFoundException, IOException {
		String asunto = "Recuperación credenciales (Password) acceso plataforma - Parking";
		String cuerpo = "Hola " + nombre
				+ ": Nos dirigimos a usted para que pueda recuperar su password y acceda a la plataforma del Parking. Este es su nuevo password: '"
				+ password + "'. Un saludo";
		enviarConGMail(destinatario, asunto, cuerpo);
	}	
}
