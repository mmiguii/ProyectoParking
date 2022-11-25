package backend.clases.personal;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Trabajador {

	private String nombre;
	private String apellido;
	private String dni;
	private String puesto; // Manager o empleado
	private long fechaComienzo;
	private int antiguedad;
	private double salario;

	public Trabajador() {
		super();
	}

	public Trabajador(String nombre, String apellido, String dni, String puesto, long fechaComienzo, int antiguedad,
			double salario) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.puesto = puesto;
		this.fechaComienzo = fechaComienzo;
		this.antiguedad = antiguedad;
		this.salario = salario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public long getFechaComienzo() {
		return fechaComienzo;
	}

	public void setFechaComienzo(long fechaComienzo) {
		this.fechaComienzo = fechaComienzo;
	}

	public int getAntiguedad() {
		return antiguedad;
	}

	public void setAntiguedad(int antiguedad) {
		this.antiguedad = antiguedad;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

	@Override
	public String toString() {
		return String.format("%s, %s, %s, %s, %s, %d, %.2f", nombre, apellido, dni, puesto,
				sdf.format(new Date(fechaComienzo)), antiguedad, salario);
	}

}
