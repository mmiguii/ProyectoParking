package backend.clases.email;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Esta clase permite crear un nuevo password con caracteres y digitos de manera
 * aleatoria cuando el usuario en cuestion olvida su password en cuestion. El
 * nuevo password generado es enviado al correo del usuario en cuestion.
 * 
 * @author Miguel Aroztegi, Eduardo Sanjurjo e ikerlekuona
 *
 */
public class PasswordGenerator {

	private final char[] LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private final char[] DIGITS = "0123456789".toCharArray();
	private Random random;

	/**
	 * Indica si debe generarse un digito o una letra
	 *
	 */
	public enum CharType {
		DIGIT, LETTER;
	}

	/**
	 * Constructor del generador de passwords
	 * 
	 */
	public PasswordGenerator() {
		random = new Random();
	}

	/**
	 * Genera una letra aletoria
	 * 
	 * @return letra aleatoria generada
	 */
	private char getRandomLetter() {
		return LETTERS[random.nextInt(LETTERS.length)];
	}

	/**
	 * Genera un digito aleatorio
	 * 
	 * @return digito aleatorio generado
	 */
	private char getRandomDigit() {
		return DIGITS[random.nextInt(DIGITS.length)];
	}

	/**
	 * Genera un orden de prueba aleatorio
	 * 
	 * @return lista que contiene el orden en el que probar (DIGIT, LETTER) o
	 *         (LETTER, DIGIT)
	 */
	private List<CharType> getRandomOrder() {
		List<CharType> generationOrder = Arrays.asList(CharType.values());
		Collections.shuffle(generationOrder, random);
		return generationOrder;
	}

	/**
	 * Metodo que determina si se cumplen ciertas condiciones basadas en la longitud
	 * actual del password, el numero actual de digitos y letras en el password y el
	 * size deseado del password, y el numero minimo requerido de digitos y letras.
	 * Las condiciones se cumplen si el numero de digitos y letras restantes
	 * necesarios para cumplir con los requisitos minimos es menor o igual al numero
	 * de caracteres restantes en el password. Si se cumplen estas condiciones, el
	 * metodo devuelve verdadero, de lo contrario, devuelve falso.
	 * 
	 */
	private boolean holdsConditions(int length, int numDigits, int numLetters, int size, int minDigits,
			int minLetters) {
		int remainingChars = size - length;
		return minDigits - numDigits <= remainingChars && minLetters - numLetters <= remainingChars;
	}

	/**
	 * Metodo recursivo que genera un password aleatorio de una longitud
	 * especificada y ciertas condiciones de numeros y letras. Devuelve null si las
	 * condiciones no se cumplen o si no se encuentra un password valido.
	 * 
	 */
	private String generateRec(int length, int numDigits, int numLetters, int size, int minDigits, int minLetters) {
		// Comprueba si la longitud actual, el numero de digitos y letras y el size
		// deseado cumplen con las condiciones especificadas
		if (holdsConditions(length, numDigits, numLetters, size, minDigits, minLetters)) {
			// Si la longitud actual es igual al size deseado, devuelve una cadena vacia
			if (length == size)
				return "";

			// Inicializa el password y el caracter aleatorio a null y 0 respectivamente
			String password = null;
			char randomChar = 0;

			// Itera sobre una secuencia de tipos de caracteres aleatorios
			for (CharType charType : getRandomOrder()) {
				switch (charType) {

				// Si el tipo de caracter es un digito, llama a la funcion recursivamente con el
				// numero de digitos actualizado
				case DIGIT:
					password = generateRec(length + 1, numDigits + 1, numLetters, size, minDigits, minLetters);
					// Asigna un digito aleatorio a la variable "randomChar"
					randomChar = getRandomDigit();
					break;

				// Si el tipo de caracter es una letra, llama a la función recursivamente con el
				// numero de letras actualizado
				case LETTER:
					password = generateRec(length + 1, numDigits, numLetters + 1, size, minDigits, minLetters);
					// Asigna una letra aleatoria a la variable "randomChar"
					randomChar = getRandomLetter();
					break;
				}

				// Si se ha encontrado un password no nulo, devuelve "randomChar" concatenado
				// con "password" y sale del bucle
				if (password != null)
					return randomChar + password;
			}
		}
		// Si no se cumplen las condiciones o no se ha encontrado un password no nulo,
		// devuelve nulo
		return null;
	}

	/**
	 * Metodo que llama al metodo recursivo "generateRec" para generar un password
	 * aleatorio de una longitud especificada y ciertas condiciones de numeros y
	 * letras. Lanza una excepcion si las condiciones de numeros y letras son
	 * incompatibles con el size deseado del password.
	 * 
	 */
	public String generate(int size, int minDigits, int minLetters) {
		if (minDigits + minLetters > size)
			throw new IllegalArgumentException(String.format(
					"Incompatible restrictions. minDigits (%d) + minLetters (%d) cannot be greater than size (%d)",
					minDigits, minLetters, size));

		return generateRec(0, 0, 0, size, minDigits, minLetters);
	}

}