package leodom01.utils;
/**
 * Classe di utility per funzioni matematiche
 * 
 * @author Leodom01
 * @author Fondamenti di Informatica T-2
 */
public final class Math {
	/**
	 * Calcola il massimo comun divisore (m.c.d.) tra due interi.
	 * 
	 * @param a	Primo valore di cui calcolcare l'MCD
	 * @param b	Primo valore di cui calcolcare l'MCD
	 *   
	 * @return MCD dei due valori
	 */
	public static int mcd(int a, int b) {
		int resto;
		if (b > a) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		do {
			resto = a % b;
			a = b;
			b = resto;
		} while (resto != 0);
		return a;
	}

	/**
	 * Calcola MCM di due valori
	 * 
	 * @param a	Primo valore
	 * @param b	Secondo valore
	 * 
	 * @return MCM dei due valori
	 */
	public static int mcm(int a, int b) {
		return (a * b) / mcd(a, b);
	}
}
