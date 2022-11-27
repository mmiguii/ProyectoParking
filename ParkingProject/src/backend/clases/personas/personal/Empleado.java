package backend.clases.personas.personal;

/**
 * La función de esta clase Empleado es definir y crear un objeto de tipo
 * empleado que desciende de un objeto Trabajador.
 * 
 * @author Miguel Aroztegi, Eduardo Jorge Sanjurjo e Iker Lekuona
 */

public class Empleado extends Trabajador {

	/** Constructor vacio de la clase ClienteSubscrito */
	public Empleado() {
		super();
	}

	/**
	 * 
	 * @param nombre:        nombre del empleado.
	 * @param apellido:      apellido del empleado.
	 * @param dni:           dni del empleado.
	 * @param email:         email del empleado.
	 * @param puesto:        puesto que ocupa. (empleado en este caso).
	 * @param fechaComienzo: fecha de comienzo en el parking.
	 * @param antiguedad:    anos que lleva en el parking.
	 * @param salario:       salario mensual del empleado.
	 */
	public Empleado(String nombre, String apellido, String dni, String email, String puesto, long fechaComienzo,
			int antiguedad, double salario) {
		super(nombre, apellido, dni, email, puesto, fechaComienzo, antiguedad, salario);
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
