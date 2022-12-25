package backend.clases.email;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PasswordGenerator {

	// usados para la generaci?n aleatoria
	private final char[] LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private final char[] DIGITS = "0123456789".toCharArray();
	private Random r;

	/**
	 * Indica si debe generarse un digito o una letra
	 *
	 */
	public enum CharType {
		DIGIT, LETTER;
	}

	/**
	 * Constructor del generador de contrase?as
	 */
	public PasswordGenerator() {
		r = new Random();
	}

	/**
	 * Genera una letra aletoria
	 * 
	 * @return letra aleatoria generada
	 */
	private char getRandomLetter() {
		return LETTERS[r.nextInt(LETTERS.length)];
	}

	/**
	 * Genera un digito aleatorio
	 * 
	 * @return digito aleatorio generado
	 */
	private char getRandomDigit() {
		return DIGITS[r.nextInt(DIGITS.length)];
	}

	/**
	 * Genera un orden de prueba aleatorio
	 * 
	 * @return lista que contiene el orden en el que probar (DIGIT, LETTER) o
	 *         (LETTER, DIGIT)
	 */
	private List<CharType> getRandomOrder() {
		List<CharType> generationOrder = Arrays.asList(CharType.values());
		Collections.shuffle(generationOrder, r);
		return generationOrder;
	}

	private boolean holdsConditions(int length, int numDigits, int numLetters, int size, int minDigits,
			int minLetters) {
		int remainingChars = size - length;
		return minDigits - numDigits <= remainingChars && minLetters - numLetters <= remainingChars;
	}

	// - Si se cumplen las condiciones:
	// - Si se ha alcanzado la longitud deseada devolver el string (vac?o)
	// - Si no se ha alcanzado la longitud deseada:
	// - Generar una secuencia de prueba aleatoria (utilizando el m?todo)
	// getRandomOrder y probar/iterar sobre las distintas posibilidades (DIGIT,
	// LETTER).
	// - Si la posibilidad probada ha devuelto null, se sabe que ese camino es
	// incorrecto.
	// - Si la posibilidad probada ha devuelto una cadena v?lida (distinta de null),
	// concatenarla a un caracter aleatorio del tipo correspondiente y devolverla.
	// - Si no se cumplen las condiciones devolver null
	private String generateRec(int length, int numDigits, int numLetters, int size, int minDigits, int minLetters) {
		// TODO T5. Implementar recursividad
		if (holdsConditions(length, numDigits, numLetters, size, minDigits, minLetters)) {
			if (length == size)
				return "";

			String password = null;
			char randomChar = 0;

			for (CharType charType : getRandomOrder()) {
				switch (charType) {
				case DIGIT:
					password = generateRec(length + 1, numDigits + 1, numLetters, size, minDigits, minLetters);
					randomChar = getRandomDigit();
					break;

				case LETTER:
					password = generateRec(length + 1, numDigits, numLetters + 1, size, minDigits, minLetters);
					randomChar = getRandomLetter();
					break;
				}

				if (password != null)
					return randomChar + password;
			}
		}

		return null;
	}

	public String generate(int size, int minDigits, int minLetters) {
		if (minDigits + minLetters > size)
			throw new IllegalArgumentException(String.format(
					"Incompatible restrictions. minDigits (%d) + minLetters (%d) cannot be greater than size (%d)",
					minDigits, minLetters, size));

		return generateRec(0, 0, 0, size, minDigits, minLetters);
	}

}