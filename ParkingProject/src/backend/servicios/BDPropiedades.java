package backend.servicios;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Lee un archivo de configuración y luego imprime sus propiedades en la
 * consola.
 * 
 * @author Miguel Aroztegi, Eduardo Jorge Sanjurjo e Iker Lekuona
 */
public class BDPropiedades {

	public static void main(String[] args) {
		Properties properties = new Properties();

		try (FileReader reader = new FileReader("configuracion.properties")) {
			properties.load(reader);
			System.out.format("Driver: %s%n", properties.getProperty("Driver"));
			System.out.format("URL: %s%n", properties.getProperty("URL"));
			System.out.format("Path: %s%n", properties.getProperty("Path"));
			System.out.format("Admin: %s%n", properties.getProperty("Admin"));
			System.out.format("E-mail de servicio de acceso: %s%n", properties.getProperty("E-mail"));
		} catch (FileNotFoundException e) {
			System.out.println("Fichero de propiedades no encontrado");
		} catch (IOException e) {
			System.out.println("Error al cargar fichero de propiedades:");
		}
	}
}
