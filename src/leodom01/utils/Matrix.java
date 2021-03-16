/**
 * 
 */
package leodom01.utils;

import java.util.StringJoiner;


/**
 * Classe che implementa il concetto di Matrice, attenzione perchè non ho usato Exception per segnalare problemi eventuali
 * 
 * @author LeoDom01
 */
public class Matrix {

	/**
	 * La matrice dento la quale metto tutti i valori della matrice
	 */
	private double[][] values;
	
	/**
	 * Costruttore che permette di inizializzare l'array bidimensionale della matrice per poi inserire secondariamente i suoi valori.
	 * 
	 * @param row	Il numero di righe della matrice
	 * @param col	Il numero di colonne della matrice
	 */
	@SuppressWarnings("unused")
	private Matrix(int row, int col) {
		this.values = new double[row][col];
	}
	
	/**
	 * Costruttore pubblico per inizializzare una matrice uguale ad un array bidimensionale che contiene i valori della futura matrice.
	 * 
	 * @param values	L'array bidimensionale da cui prenderò i valori con cui riempiere la mia istanza di matrice
	 */
	public Matrix(double[][] values) {
		this.values = new double[values.length][values[0].length];
		for(int i = 0; i<values.length; i++) {
			for(int j = 0; j<values[0].length; j++) {
				this.values[i][j] = values[i][j];
			}
		}
	}
	
	/**
	 * Permette di settare i valori dentro la matrice, non volgio che l'utente possa farlo, ma per comodoità interna potrebbe servire a qualche metodo.
	 * 
	 * @param row	La riga a cui si trova il valore da modificare
	 * @param col	La colonna a cui si trova il valore da modificare
	 * @param value	Il valore da sostituire a quello precedentemente presente
	 */
	@SuppressWarnings("unused")
	private void setValue(int row, int col, double value) {
		this.values[row][col] = value;
	}
	
	/**
	 * Metodo per calcolare il detrminante, esegue il calcolo effettivo, det() incapsula solo questo calcola e verifica incompatibiltà della matrice.
	 * 
	 * @return	Il valore del determinante della matrice
	 */
	private double calcDet() {
		if(this.getRows() == 1) {
			return this.getValue(0, 0);
		}else {
			int det = 0;
			for(int i = 0; i<this.getRows(); i++) {
				det += java.lang.Math.pow(-1, i+this.getRows()-1)*(this.getValue(i, this.getRows()-1))*(this.extractMinor(i, this.getRows()-1).calcDet());
			}
			return det;
		}
	}
	
	/**
	 * Metodo pubblico per ottenere il determinante di una matrice, controlla il fatto che sia quadrata, in caso negativo ritorna NaN
	 * 
	 * @return	Il valore del determinante, NaN se la matrice non è quadrata
	 */
	public double det() {
		return isSquared() ? calcDet() : Double.NaN;
	}
	
	/**
	 * Ritorna la matrice minore, ovvero la matrice iniziale alla quale vengono tolti gli elementi a una data riga e colonna
	 * 
	 * @param row	La riga dalla quale togliere tutti gli elementi
	 * @param col	La colonna dalla quale togliete tutti gli elementi
	 * 
	 * @return		Una nuova matrice uguale alla chiamante ma senza la riga e la colonna indicate nei parametri
	 */
	public Matrix extractMinor(int row, int col) {
		if(!this.isSquared() || row>this.getRows() || col>this.getCols() || row<0 || col<0) {
			return null;
		}
		double[][] newMatVals = new double [this.getRows()-1][this.getCols()-1];
		for(int newMatXCoord = 0, i = 0; i<this.getRows(); i++) {
			for(int j = 0, newMatYCoord = 0; j<this.getCols(); j++) {
				if(i != row && j != col) {
					newMatVals[newMatXCoord][newMatYCoord] = this.getValue(i, j);
					newMatYCoord++;
				}
			}
			if(i != row) {
				newMatXCoord++;
			}
		}
		return new Matrix(newMatVals);	
	}
	
	/**
	 * Estrae una sottomatrice salla matrice chiamante.
	 * 
	 * @param startRow	La riga da considerare la prima riga della nuova sottomatrice
	 * @param startCol	La colonna da considerare la prima colonna della nuova sottomatrice
	 * @param rowCount	Il numero di righe da inserire nella sottomatrice (a partire da startRow)
	 * @param colCount	Il numero di colonne da inserire nella sottomatrice (a partire da startCol)
	 * 
	 * @return 			Una nuova matrice che contiene rowCount e colCount numero di righe a partire da startRow e startCol
	 */
	public Matrix extractSubMatrix(int startRow, int startCol, int rowCount, int colCount) {
		if(startRow+rowCount>this.getRows() || startCol+colCount>this.getCols() || startRow<0 || startCol<0) {
			return null;
		}
		double[][] newMatVals = new double[rowCount][colCount];
		for(int i = startRow; i< startRow+rowCount; i++) {
			for(int j = startCol; j<startCol+colCount; j++) {
				newMatVals[i-startRow][j-startCol] = this.getValue(i-1, j-1);
			}
		}
		return new Matrix(newMatVals);
	}
	
	/**
	 * Ritorna il numero di colonne della matrice.
	 * 
	 * @return	Il numero di colonne della matrice
	 */
	public int getCols() {
		return values[0].length;
	}

	/**
	 * Ritorna il numero di righe della matrice.
	 * 
	 * @return	Il numero delle righe della matrice
	 */
	public int getRows() {
		return values.length;
	}
	
	/**
	 * Ritorna un dato valore a date coordinate della matrice.
	 * 
	 * @param row	L'indice della riga da cui prendere l'elemento da ritornare
	 * @param col	L'indice della colonna da cui predere l'elemento da ritornare
	 * 
	 * @return		L'elemento che si trova alla riga row e alla colonna col
	 */
	public double getValue(int row, int col) {
		return values[row][col];
	}
	
	/**
	 * Verifica che la matrice sia quadrata.
	 * 
	 * @return	True se la matrice è quadrata, false se non lo è
	 */
	public boolean isSquared() {
		return this.values.length == this.values[0].length;
	}
	
	/**
	 * Esegue una moltiplicazione RICO fra due matrici, è necessario che esse abbaimo dimensione lxm mxn, se così non fosse allora sarà ritorenato NULL
	 * 
	 * @param m	La matrice da moltiplicare con la matrice chiamante
	 * @return	La matrice prodotto delle due matrici
	 */
	public Matrix mul(Matrix m) {
		if(this.getCols() != m.getRows()) {
			return null;
		}
		double [][] newMatVals = new double[this.getRows()][m.getCols()];
		for(int i = 0; i<this.getRows(); i++) {
			for(int j = 0; j<m.getCols(); j++) {
				double newCell = 0;
				for(int k = 0; k<this.getRows(); k++) {
					newCell += this.getValue(j, k)* m.getValue(j, k);
				}
				newMatVals[j][i] = newCell;
			}
		}
		return new Matrix(newMatVals);
	}
	
	/**
	 * Esegue una somma fra due matrici, se le dimensioni delle matrici non sono compatibili allora verrà ritornato NULL
	 * 
	 * @param m	La matrice da sommare alla matrice chiamante
	 * @return	Una nuova matrice somma delle due matrici date
	 */
	public Matrix sum(Matrix m) {
		if(this.getRows() != m.getRows() || this.getCols() != m.getCols()) {
			return null;
		}
		double[][] newMatVals = new double[this.getRows()][this.getCols()];
		for(int i = 0; i<this.getRows(); i++) {
			for(int j = 0; j<this.getCols(); j++) {
				newMatVals[i][j] = this.getValue(i, j)+m.getValue(i, j);
			}
		}
		return new Matrix(newMatVals);
	}
	
	/**
	 * Override del metodo toString, viene stampata come: | valore valore ... valore | per ogni riga, una sotto l'altra 
	 */
	public String toString() {
		StringJoiner finalStr = new StringJoiner("\n");
		for(double[] row : values) {
			StringJoiner str = new StringJoiner(" ", "|", "|");
			for(double val : row) {
				str.add(Double.toString(val));
			}
			finalStr.add(str.toString());
		}
		return finalStr.toString();
	}
}


















