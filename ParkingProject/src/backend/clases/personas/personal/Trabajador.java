package backend.clases.personas.personal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * La función de esta clase Trabajador es definir un objeto de tipo trabajador.
 * Este objeto totalmente abstracto extenderán los dos tipos posibles de
 * trabajadores: los managers y los empleados.
 * 
 * 
 * @author Miguel Aroztegi, Eduardo Jorge Sanjurjo e Iker Lekuona
 */

public abstract class Trabajador {

	/** Atributos genericos de un objeto Trabajador */
	private String nombreUsuario;
	private String password;
	private String dni;
	private String email;
	private String puesto; // Manager o empleado
	private long fechaComienzo;
	private int antiguedad;
	private double salario;

	/** Constructor vacio de la clase Trabajador */
	public Trabajador() {
		super();
	}

	public Trabajador(String nombreUsuario, String password, String dni, String email, String puesto,
			long fechaComienzo, int antiguedad, double salario) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.password = password;
		this.dni = dni;
		this.email = email;
		this.puesto = puesto;
		this.fechaComienzo = fechaComienzo;
		this.antiguedad = antiguedad;
		this.salario = salario;
	}

	/**
	 * Metodo getter del atributo nombre de usuario del trabajador.
	 * 
	 * @return nombreUsuario:nombre del trabajador.
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * Metodo setter del atributo nombre de usuario del trabajador.
	 * 
	 * @param nombreUsuario: nombre del trabajador.
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * Metodo getter del atributo password del trabajador.
	 * 
	 * @return password: password del trabajador.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Metodo setter del atributo password del trabajador.
	 * 
	 * @param password: password del trabajador.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Metodo getter del atributo dni del trabajador.
	 * 
	 * @return dni: dni del trabajador.
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * Metodo setter del atributo dni del trabajador.
	 * 
	 * @param dni:dni del trabajador
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}

	/**
	 * Metodo getter del atributo email del manager.
	 * 
	 * @return email: email del manager.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Metodo setter del atributo email del manager.
	 * 
	 * @param email: email del manager.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Metodo getter del atributo puesto del trabajador.
	 * 
	 * @return puesto: puesto del trabajador
	 */
	public String getPuesto() {
		return puesto;
	}

	/**
	 * Metodo setter del atributo puesto del trabajador
	 * 
	 * @param puesto: puesto del trabajador
	 */
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	/**
	 * Metodo getter del atributo fechaComienzo del trabajador.
	 * 
	 * @return fechaComienzo: fecha de comienzo del trabajador.
	 */
	public long getFechaComienzo() {
		return fechaComienzo;
	}

	/**
	 * Metodo setter del atributo fechaComienzo del trabajador.
	 * 
	 * @param fechaComienzo: fecha de comienzo del trabajador.
	 */
	public void setFechaComienzo(long fechaComienzo) {
		this.fechaComienzo = fechaComienzo;
	}

	/**
	 * Metodo getter del atributo antiguedad del trabajador.
	 * 
	 * @return antiguedad: antiguedad del trabajador.
	 */
	public int getAntiguedad() {
		return antiguedad;
	}

	/**
	 * Metodo setter del atributo antiguedad del trabajador.
	 * 
	 * @param antiguedad: antiguedad del trabajador.
	 */
	public void setAntiguedad(int antiguedad) {
		this.antiguedad = antiguedad;
	}

	/**
	 * Metodo getter del atributo salario del trabajador.
	 * 
	 * @return salario: salario del trabajador.
	 */
	public double getSalario() {
		return salario;
	}

	/**
	 * Metodo setter del atributo salario del trabajador.
	 * 
	 * @param salario: salario del trabajador.
	 */
	public void setSalario(double salario) {
		this.salario = salario;
	}

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

	@Override
	public String toString() {
		return String.format("%s, %s, %s, %s, %s, %s, %d, %.2f", nombreUsuario, password, dni, email, puesto,
				sdf.format(new Date(fechaComienzo)), antiguedad, salario);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Trabajador) {
			return nombreUsuario == ((Trabajador) obj).nombreUsuario;
		} else {
			return false;
		}
	}

}
