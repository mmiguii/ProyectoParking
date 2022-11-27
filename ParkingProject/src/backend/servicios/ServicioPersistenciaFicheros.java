package backend.servicios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.StringTokenizer;

import backend.clases.infraestructura.Planta;
import backend.clases.infraestructura.Plaza;
import backend.clases.personas.clientes.ClienteOrdinario;
import backend.clases.personas.clientes.ClienteSubscrito;

public class ServicioPersistenciaFicheros {

	private static ArrayList<String> readFile(File path) {
		ArrayList<String> fileContent = new ArrayList<String>();
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			// Leemos la cabecera para posteriormente evitarla
			reader.readLine();
			String lineContent;
			while ((lineContent = reader.readLine()) != null) {
				fileContent.add(lineContent);
			}
		} catch (FileNotFoundException e) {
			// Si no se encuentra el fichero al intentar abrirlo
			System.out.println("No ha sido posible encontrar el fichero introducido");
		} catch (IOException e) {
			// Si hay problemas al leer del fichero
			System.out.println("No ha sido posible realizar la lectura del archivo");
		}
		return fileContent;
	}

	private static void writeFile(File path, String content) {
		// Dejando el true damos opcion a appendear
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
			writer.newLine();
			writer.write(content);
			

		} catch (IOException e) {
			System.out.format("No se ha podido escribir en la ruta indicada: %s", path.toString());

		}
	}

	public ArrayList<ClienteOrdinario> ordinarioReader() {
		Path CSVPath = Paths.get("clientesOrdinarios.txt");
		ArrayList<String> load = readFile(CSVPath.toFile());
		ArrayList<ClienteOrdinario> ret = new ArrayList<ClienteOrdinario>();
		for (int i = 0; i < load.size(); i++) {
			// Partimos la línea en partes por el delimitador
			StringTokenizer tokenizer = new StringTokenizer(load.get(i), ";");
			while (tokenizer.hasMoreTokens()) {
				// Creamos un usuario nuevo vacio
				ClienteOrdinario ordinario = new ClienteOrdinario();
				// Añadimos los datos al objeto
				ordinario.setMatricula(tokenizer.nextToken());
				ordinario.setTipoVehiculo(tokenizer.nextToken());
				ordinario.setTarifa(Double.parseDouble(tokenizer.nextToken()));
				ordinario.setFechaEntrada(Long.parseLong(tokenizer.nextToken()));
				// Lo añadimos a la lista de usuarios con los datos introducidos
				ret.add(ordinario);
			}
		}
		return ret;
	}

	
	public ArrayList<ClienteSubscrito> subscritoReader() {
		Path CSVPath = Paths.get("");
		ArrayList<String> load = readFile(CSVPath.toFile());
		ArrayList<ClienteSubscrito> ret = new ArrayList<ClienteSubscrito>();
		for (int i = 0; i < load.size(); i++) {
			// Partimos la línea en partes por el delimitador
			StringTokenizer tokenizer = new StringTokenizer(load.get(i), ";");
			while (tokenizer.hasMoreTokens()) {
				// Creamos un usuario nuevo vacio
				ClienteSubscrito subscrito = new ClienteSubscrito();
				// Añadimos los datos al objeto
				subscrito.setMatricula(tokenizer.nextToken());
				subscrito.setTipoVehiculo(tokenizer.nextToken());
				subscrito.setTipoCuota(tokenizer.nextToken());
				subscrito.setPrecioCuota(Double.parseDouble(tokenizer.nextToken()));
				// Lo añadimos a la lista de usuarios con los datos introducidos
				ret.add(subscrito);
			}
		}
		return ret;
	}

	public ArrayList<Planta> plantaReader() {
		Path CSVPath = Paths.get("");
		ArrayList<String> load = readFile(CSVPath.toFile());
		ArrayList<Planta> ret = new ArrayList<Planta>();
		for (int i = 0; i < load.size(); i++) {
			// Partimos la línea en partes por el delimitador
			StringTokenizer tokenizer = new StringTokenizer(load.get(i), ";");
			while (tokenizer.hasMoreTokens()) {
				// Creamos un usuario nuevo vacio
				Planta planta = new Planta();
				// Añadimos los datos al objeto
				planta.setNumeroPlanta(Integer.parseInt(tokenizer.nextToken()));
				planta.setCantidadPlazasNormales(Integer.parseInt(tokenizer.nextToken()));
				planta.setCantidadPlazasElectricas(Integer.parseInt(tokenizer.nextToken()));
				planta.setCantidadPlazasDiscapacitados(Integer.parseInt(tokenizer.nextToken()));
				// Lo añadimos a la lista de usuarios con los datos introducidos
				ret.add(planta);
			}
		}
		return ret;
	}

	public ArrayList<Plaza> plazaReader() {
		Path CSVPath = Paths.get("");
		ArrayList<String> load = readFile(CSVPath.toFile());
		ArrayList<Plaza> ret = new ArrayList<Plaza>();
		for (int i = 0; i < load.size(); i++) {
			// Partimos la línea en partes por el delimitador
			StringTokenizer tokenizer = new StringTokenizer(load.get(i), ";");
			while (tokenizer.hasMoreTokens()) {
				// Creamos un usuario nuevo vacio
				Plaza nPS = new Plaza();
				// Añadimos los datos al objeto
				nPS.setNumeroPlaza(Integer.parseInt(tokenizer.nextToken()));
				nPS.setEstadoPlaza(Boolean.parseBoolean(tokenizer.nextToken()));
				nPS.setTipoPlaza(tokenizer.nextToken());
				// Lo añadimos a la lista de usuarios con los datos introducidos
				ret.add(nPS);
			}
		}
		return ret;
	}

	public void ordinarioWriter(ArrayList<ClienteOrdinario> ordinaryCustomers) {
		Path rutacsv = Paths.get("./src/files/clientesOrdinarios.txt");
		String content = "";
		
		int index = 0;
		for (ClienteOrdinario ordinaryCustomer : ordinaryCustomers) {
			if (index == ordinaryCustomers.size() - 1) {
				content = String.format("%s;%s;%.2f;%s", ordinaryCustomer.getMatricula(),
						ordinaryCustomer.getTipoVehiculo(), ordinaryCustomer.getTarifa(), ordinaryCustomer.getFechaEntrada());
				writeFile(rutacsv.toFile(), content);
			} else {
				content = String.format("%s;%s;%.2f;%s%n", ordinaryCustomer.getMatricula(),
						ordinaryCustomer.getTipoVehiculo(), ordinaryCustomer.getTarifa(), ordinaryCustomer.getFechaEntrada());
				writeFile(rutacsv.toFile(), content);
				index++;
			}
		}
	}

	public void subscritoWriter(ArrayList<ClienteSubscrito> subscriberCustomers) {
		@SuppressWarnings("unused")
		String content = "";
		int index = 0;
		for (ClienteSubscrito subscriberCustomer : subscriberCustomers) {
			if (index == subscriberCustomers.size() - 1) {
				content = String.format("%s;%s;%s;%.2f", subscriberCustomer.getMatricula(),
						subscriberCustomer.getTipoVehiculo(), subscriberCustomer.getTipoCuota(),
						subscriberCustomer.getPrecioCuota());
			} else {
				content = String.format("%s;%s;%s;%.2f;%n", subscriberCustomer.getMatricula(),
						subscriberCustomer.getTipoVehiculo(), subscriberCustomer.getTipoCuota(),
						subscriberCustomer.getPrecioCuota());
				index++;
			}
		}
	}

	public void floorWriter(ArrayList<Planta> floors) {
		@SuppressWarnings("unused")
		String content = "";
		int index = 0;
		for (Planta floor : floors) {
			if (index == floors.size() - 1) {
				content = String.format("%d;%d;%d;%d", floor.getNumeroPlanta(), floor.getCantidadPlazasNormales(),
						floor.getCantidadPlazasElectricas(), floor.getCantidadPlazasDiscapacitados());
			} else {
				content = String.format("%d;%d;%d;%d%n", floor.getNumeroPlanta(), floor.getCantidadPlazasNormales(),
						floor.getCantidadPlazasElectricas(), floor.getCantidadPlazasDiscapacitados());
				index++;
			}
		}
	}

	public void plazaWriter(ArrayList<Plaza> parkingSpaces) {
		@SuppressWarnings("unused")
		String content = "";
		int index = 0;
		for (Plaza parkingSpace : parkingSpaces) {
			if (index == parkingSpaces.size() - 1) {
				content = String.format("%d;%b;%d", parkingSpace.getNumeroPlaza(), parkingSpace.isEstadoPlaza(),
						parkingSpace.getTipoPlaza());
			} else {
				content = String.format("%d;%b;%d%n", parkingSpace.getNumeroPlaza(), parkingSpace.isEstadoPlaza(),
						parkingSpace.getTipoPlaza());
				index++;
			}
		}
	}

}
