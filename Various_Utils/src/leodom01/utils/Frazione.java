package leodom01.utils;

import java.lang.Math;

/**
 * Classe che implementa il concetto di frazione 
 * 
 * @author Leodom01
 * @author Fondamenti di Informatica T-2
 */
public class Frazione {
	
	/**
	 * Il numeratore della frazione.
	 */
	private int num;
	
	/**
	 * Il denominatore della funzione.
	 */
	private int den;

	/**
	 * Costruttore della Frazione.
	 * 
	 * @param num	Il numeratore della frazione
	 * @param den	Il denominatore della frazione
	 */
	public Frazione(int num, int den) {
		boolean negativo = num * den < 0;
		this.num = negativo ? -Math.abs(num) : Math.abs(num);
		this.den = Math.abs(den);
	}

	/**
	 * Costruttore della Frazione a singolo parametro, per numeri interi.
	 * 
	 * @param num	Il numeratore della frazione con denominatore 1
	 */
	public Frazione(int num) {
		this(num, 1);
	}

	/**
	 * Getter per il numeratore della frazione.
	 * 
	 * @return	Il numeratore della frazione
	 */
	public int getNum() {
		return num;
	}

	/**
	 * Getter per il denominatore della frazione.
	 * 
	 * @return	Il denominatore della frazione
	 */
	public int getDen() {
		return den;
	}

	/**
	 * Calcola la funzione ridotta ai minimi termini e la ritorna come nuovo oggetto.
	 *
	 * @return Una nuova funzione equivalente all'attuale, ridotta ai minimi termini.
	 */
	public Frazione minTerm() {
		if (getNum()==0) return new Frazione(getNum(), getDen());
		int mcd = leodom01.utils.Math.mcd(Math.abs(getNum()), getDen());
		int n = getNum() / mcd;
		int d = getDen() / mcd;
		return new Frazione(n, d);
	}

	/**
	 * Equals che considera il valore della funzione, anche se hanno denominatore diverso basta che il rapporto fra numeratore e denominatore sia uguale.
	 * 
	 * @param f	La funzione con cui confrontare la funzione
	 * 
	 * @return	True se le frazioni sono equivalenti, false se non lo sono
	 */
	public boolean equals(Frazione f) {
		return f.getNum() * getDen() == f.getDen() * getNum();
	}
	
	@Override
	public String toString() {
		return getNum() + "/" + getDen();
	}
	
	/**
	 * Ritorna la frazione somma di due funzioni, ridotta ai minimi termini.
	 * 
	 * @param f	La frazione da sommare
	 * 
	 * @return	La frazione somma delle due frazioni ridotta ai minimi termini
	 */
	public Frazione sum(Frazione f) {
		int n = num * f.den + den * f.num;
		int d = den * f.den;
		Frazione fSum = new Frazione(n, d);
		return fSum.minTerm();
	}
	
	/**
	 * Effettua la somma di due frazioni portandole tutte due allo stesso denominatore e poi sommando i numeratori.
	 * 
	 * @param f	La seconda frazione da sommare
	 * 
	 * @return	Una nuova funzione equivalente alla somma delle due frazioni ma ridotta ai minimi terimini
	 */
	public Frazione sumWithMcm(Frazione f) {
		int mcm = leodom01.utils.Math.mcm(this.den, f.den);
		int thisMultiplier = mcm/this.den;
		int fMultiplier = mcm/f.den;
		int d = mcm;
		int n = (this.num*thisMultiplier)+(f.num*fMultiplier);
		return new Frazione(n, d).minTerm();
	}
	
	/**
	 * Esegue la sottrazione fra due frazioni.
	 * 
	 * @param f	La frazione da sottrarre alla frazione chiamante
	 * 
	 * @return	Ritorna una nuova funzione esito della sottrazione rodotta ai minimi termini
	 */
	public Frazione sub(Frazione f) {
		return this.sum(new Frazione(-f.getNum(), f.getDen())).minTerm();
	}
	
	/**
	 * Esegue una moltiplicazione fra due frazioni.
	 * 
	 * @param f	La frazione da moltiplicare con la frazione chiamante
	 * 
	 * @return	Ritorna una nuova frazione prodotto delle due e ridotta ai minimi termini
	 */
	public Frazione mul(Frazione f) {
		int n = this.num * f.num;
		int d = this.den * f.den;
		Frazione fMul = new Frazione(n, d);
		return fMul.minTerm();
	}
	
	/**
	 * Esegue una divisione fra due frazioni, attenzione, se la seconda è pari a 0 allora avverrà una divisione per 0 che è non ammissibile.
	 * 
	 *TODO: Implementa un sistema di controllo nel caso in cui il divisore sia uguale a 0
	 * 
	 * @param f	La funzione divisore
	 * 
	 * @return	Una nuova frazione quoziente ridotta ai minimi termini
	 */
	public Frazione div(Frazione f) {
		return this.mul(f.reciprocal()).minTerm();
	}
	
	/**
	 * Ritorna la funzione reciproca della funzione chiamante, attenzione al caso in cui il numeratore sia uguale a 0.
	 * 
	 * @return	Una nuova funzione, reciproca della prima e ridotta ai minimi termini
	 */
	public Frazione reciprocal() {
		return new Frazione(this.den, this.num).minTerm();
	}
	
	/**
	 * CompareTo fra due frazioni, si calcola il valore del rapporto fra numeratore e denominatore.
	 * 
	 * @param f	La funzione da confrontare con la funzione chiamante
	 * 
	 * @return	Valore minore di 0 se la seconda è maggiore della prima, uguale a 0 se sono uguali, maggiore di 0 se la chiamante è maggiore della frazione parametro
	 */
	public int compareTo(Frazione f) {
		int mcm = leodom01.utils.Math.mcm(this.den, f.den);
		int thisMultiplier = mcm/this.den;
		int fMultiplier = mcm/f.den;
		return this.num*thisMultiplier - f.num*fMultiplier;
	}
	
	/**
	 * Ritorna il valore del rapporto fra numeratore e denominatore come double.
	 * 
	 * @return	Il valore del rapporto fra numeratore e denominatore
	 */
	public double getDouble() {
		return (double)num/(double)den;
	}

}
