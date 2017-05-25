package org.ssii.busqueda.juegos.tresenraya;

/**
 * Clase que implementa un movimiento realizado por un jugador sobre el tablero del 
 * Tres en Raya
 * @author José del Sagrado
 * @since Sep 2013
 * @version 1.0
 * @see Tablero 
 * @see Jugador
 */
public class Movimiento {
	int fila;		// Fila para identificar la casilla del tablero en la que se hace el movimiento
	int columna;	// Columna para identificar la casilla del tablero en la que se hace el movimiento
	int valor;		// Valor asociado al tablero

	/**
	 * Constructor nulo. Define un movimiento inválido.
	 */
	public Movimiento() {
		fila = -1;
		columna = -1;
		valor = Integer.MIN_VALUE;
	}
	
	/**
	 * Constructor nulo. Define inicializa un movimiento con una posición inválida y
	 * le asigna el peor valor para el jugador 
	 * @param jugador_max si se establece a <code>true</code> que el jugador juega con 
	 * estrategia MAX, en otro caso juega con estrategia MIN
	 */
	public Movimiento (boolean jugador_max) {
		fila = -1;
		columna = -1;
		if (jugador_max)
			valor = Integer.MIN_VALUE;
		else valor = Integer.MAX_VALUE;
	}

	/**
	 * Construye un movimiento en función de la posición de la ficha en el 
	 * tablero y de la evaluación de este tras situar la ficha.
	 * @param fil fila de la casilla en la que se situará la ficha
	 * @param col columna de la casilla en la que se situará la ficha
	 * @param val valor de evaluación del tablero tras situar la ficha
	 */
	public Movimiento (int fil, int col, int val) {
		fila = fil;
		columna = col;
		valor = val;
	}
	
	/**
	 * @return Devuelve la fila de la casilla sobre la que se realiza el movimiento
	 */
	public int obtenerFila (){
		return fila;
	}
	
	/**
	 * @return Devuelve la columna de la casilla sobre la que se realiza el movimiento
	 */
	public int obtenerColumna () {
		return columna;
	}
	
	/**
	 * @return Devuelve el valor asociado al movimiento
	 */
	public int obtenerValor () {
		return valor;
	}
	
	/**
	 * Establece el valor de la fila en el movimiento
	 * @param fil fila de la casilla en la que se situará la ficha
	 */
	public void fijarFila (int fil) {
		fila = fil;
	}

	/**
	 * Establece el valor de la fila en el movimiento
	 * @param col columna de la casilla en la que se situará la ficha
	 */
	public void fijarColumna (int col) {
		columna = col;
	}
	
	/**
	 * Establece el valor del tablero obtenido tras el movimiento
	 * @param val valor de evaluación del tablero tras situar la ficha
	 */
	public void fijarValor (int val) {
		valor = val;
	}

	/**
	 * Establece la posición de la casilla en la que se situará la ficha
	 * @param fil fila de la casilla en la que se situará la ficha
	 * @param col columna de la casilla en la que se situará la ficha
	 */
	public void fijarPosicion (int fil, int col) {
		fila = fil;
		columna = col;
	}
	
	/**
	 * Establece un movimiento en función de la posición de la ficha en el 
	 * tablero y de la evaluación de este tras situar la ficha.
	 * @param fil fila de la casilla en la que se situará la ficha
	 * @param col columna de la casilla en la que se situará la ficha
	 * @param val valor de evaluación del tablero tras situar la ficha
	 */
	public void fijarMovimiento (int fil, int col, int val) {
		fila = fil;
		columna = col;
		valor = val;
	}
	
	/**
	 * Imprime el movimiento indicando la casilla (en la visualización
	 * se incrementa en uno el valor de la fila y de la columna) del tablero 
	 * y el valor de éste.
	 */
	public void print () {
		System.out.println("Movimiento = ("+(fila+1)+" "+(columna+1)+" "+valor+")");
	}
}	


