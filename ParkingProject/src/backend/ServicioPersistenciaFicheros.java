package backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import backend.customer.OrdinaryCustomer;
import backend.customer.SubscriberCustomer;
import backend.parking.Floor;
import backend.parking.ParkingSpace;

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

	public ArrayList<OrdinaryCustomer> ordinaryCustomerReader() {

		Path CSVPath = Paths.get("./clientesOrdinarios.txt");
		ArrayList<String> load = readFile(CSVPath.toFile());
		ArrayList<OrdinaryCustomer> ret = new ArrayList<OrdinaryCustomer>();
		for (int i = 0; i < load.size(); i++) {
			// Partimos la línea en partes por el delimitador
			StringTokenizer tokenizer = new StringTokenizer(load.get(i), ";");
			while (tokenizer.hasMoreTokens()) {
				// Creamos un usuario nuevo vacio
				OrdinaryCustomer nOC = new OrdinaryCustomer();
				// Añadimos los datos al objeto
				nOC.setLicensePlate(tokenizer.nextToken());
				nOC.setVehicleType(Integer.parseInt(tokenizer.nextToken()));
				nOC.setFare(Double.parseDouble(tokenizer.nextToken()));
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ZZ");
				nOC.setHoraDeEntrada(ZonedDateTime.parse(tokenizer.nextToken(), formatter));
				// Lo añadimos a la lista de usuarios con los datos introducidos
				ret.add(nOC);
			}
		}

		return ret;
	}

	public ArrayList<SubscriberCustomer> subscriberCustomerReader() {

		Path CSVPath = Paths.get("");
		ArrayList<String> load = readFile(CSVPath.toFile());
		ArrayList<SubscriberCustomer> ret = new ArrayList<SubscriberCustomer>();
		for (int i = 0; i < load.size(); i++) {
			// Partimos la línea en partes por el delimitador
			StringTokenizer tokenizer = new StringTokenizer(load.get(i), ";");
			while (tokenizer.hasMoreTokens()) {
				// Creamos un usuario nuevo vacio
				SubscriberCustomer nSC = new SubscriberCustomer();
				// Añadimos los datos al objeto
				nSC.setLicensePlate(tokenizer.nextToken());
				nSC.setVehicleType(Integer.parseInt(tokenizer.nextToken()));
				nSC.setFeeType(Integer.parseInt(tokenizer.nextToken()));
				nSC.setFee(Integer.parseInt(tokenizer.nextToken()));
				// Lo añadimos a la lista de usuarios con los datos introducidos
				ret.add(nSC);
			}
		}

		return ret;
	}

	public ArrayList<Floor> floorReader() {

		Path CSVPath = Paths.get("");
		ArrayList<String> load = readFile(CSVPath.toFile());
		ArrayList<Floor> ret = new ArrayList<Floor>();
		for (int i = 0; i < load.size(); i++) {
			// Partimos la línea en partes por el delimitador
			StringTokenizer tokenizer = new StringTokenizer(load.get(i), ";");
			while (tokenizer.hasMoreTokens()) {
				// Creamos un usuario nuevo vacio
				Floor nF = new Floor();
				// Añadimos los datos al objeto
				nF.setFloorNumber(Integer.parseInt(tokenizer.nextToken()));
				nF.setOrdinaryParkingSpace(Integer.parseInt(tokenizer.nextToken()));
				nF.setElectricParkingSpace(Integer.parseInt(tokenizer.nextToken()));
				nF.setDisabledParkingSpace(Integer.parseInt(tokenizer.nextToken()));
				// Lo añadimos a la lista de usuarios con los datos introducidos
				ret.add(nF);
			}
		}

		return ret;
	}

	public ArrayList<ParkingSpace> parkingSpaceReader() {

		Path CSVPath = Paths.get("");
		ArrayList<String> load = readFile(CSVPath.toFile());
		ArrayList<ParkingSpace> ret = new ArrayList<ParkingSpace>();
		for (int i = 0; i < load.size(); i++) {
			// Partimos la línea en partes por el delimitador
			StringTokenizer tokenizer = new StringTokenizer(load.get(i), ";");
			while (tokenizer.hasMoreTokens()) {
				// Creamos un usuario nuevo vacio
				ParkingSpace nPS = new ParkingSpace();
				// Añadimos los datos al objeto
				nPS.setSpaceNumber(Integer.parseInt(tokenizer.nextToken()));
				nPS.setSpaceFull(Boolean.parseBoolean(tokenizer.nextToken()));
				nPS.setVehicleType(Integer.parseInt(tokenizer.nextToken()));
				// Lo añadimos a la lista de usuarios con los datos introducidos
				ret.add(nPS);
			}
		}
		return ret;
	}

	public void ordinaryCustomerWriter(ArrayList<OrdinaryCustomer> ordinaryCustomers) {
		Path rutacsv = Paths.get("./src/files/clientesOrdinarios.txt");
		String content = "";
		
		int index = 0;
		for (OrdinaryCustomer ordinaryCustomer : ordinaryCustomers) {
			OrdinaryCustomer nOC = ordinaryCustomers.get(index);
			if (index == ordinaryCustomers.size() - 1) {
				content = String.format("%s;%d;%.2f;%s", ordinaryCustomer.getLicensePlate(),
						ordinaryCustomer.getVehicleType(), ordinaryCustomer.getFare(), ordinaryCustomer.getHoraDeEntrada());
				writeFile(rutacsv.toFile(), content);
			} else {
				content = String.format("%s;%d;%.2f;%s%n", ordinaryCustomer.getLicensePlate(),
						ordinaryCustomer.getVehicleType(), ordinaryCustomer.getFare(), ordinaryCustomer.getHoraDeEntrada());
				writeFile(rutacsv.toFile(), content);
				index++;
			}
		}
	}

	public void subscriberCustomerReader(ArrayList<SubscriberCustomer> subscriberCustomers) {
		String content = "";
		int index = 0;
		for (SubscriberCustomer subscriberCustomer : subscriberCustomers) {
			SubscriberCustomer nSC = subscriberCustomers.get(index);
			if (index == subscriberCustomers.size() - 1) {
				content = String.format("%s;%d;%d;%d;%d", subscriberCustomer.getLicensePlate(),
						subscriberCustomer.getVehicleType(), subscriberCustomer.getFeeType(),
						subscriberCustomer.getFee());
			} else {
				content = String.format("%s;%d;%d;%d;%d", subscriberCustomer.getLicensePlate(),
						subscriberCustomer.getVehicleType(), subscriberCustomer.getFeeType(),
						subscriberCustomer.getFee());
				index++;
			}
		}
	}

	public void floorWriter(ArrayList<Floor> floors) {
		String content = "";
		int index = 0;
		for (Floor floor : floors) {
			Floor nF = floors.get(index);
			if (index == floors.size() - 1) {
				content = String.format("%d;%d;%d;%d", floor.getFloorNumber(), floor.getOrdinaryParkingSpace(),
						floor.getElectricParkingSpace(), floor.getDisabledParkingSpace());
			} else {
				content = String.format("%d;%d;%d;%d%n", floor.getFloorNumber(), floor.getOrdinaryParkingSpace(),
						floor.getElectricParkingSpace(), floor.getDisabledParkingSpace());
				index++;
			}
		}
	}

	public void parkingSpaceWriter(ArrayList<ParkingSpace> parkingSpaces) {
		String content = "";
		int index = 0;
		for (ParkingSpace parkingSpace : parkingSpaces) {
			ParkingSpace nPS = parkingSpaces.get(index);
			if (index == parkingSpaces.size() - 1) {
				content = String.format("%d;%b;%d", parkingSpace.getSpaceNumber(), parkingSpace.isSpaceFull(),
						parkingSpace.getVehicleType());
			} else {
				content = String.format("%d;%b;%d%n", parkingSpace.getSpaceNumber(), parkingSpace.isSpaceFull(),
						parkingSpace.getVehicleType());
				index++;
			}
		}
	}

}
