/**
 * 
 */
package org.ssii.busqueda.juegos.tresenraya;

import java.util.ArrayList;

import org.ssii.busqueda.juegos.tresenraya.Movimiento;

/**
 * Clase que implementa la representación del tablero del Tres en Raya
 * @author José del Sagrado
 * @since Sep 2013
 * @version 1.0
 * @see Movimiento
 * @see Jugador
 */
public class Tablero {
	public enum Contenido {VACIA, CRUZ, REDONDEL};  // Contenido de las celdas del tablero
	public static final int DIM = 3;				// Dimensión del tablero
	public Contenido[][] tablero;					// Tablero del tres en raya
	Movimiento movimiento;							// Movimiento asociado al tablero
	
    public enum Resultado {EN_JUEGO, GANA_CRUZ, GANA_REDONDEL, EMPATE}; // Estado del juego en función del tablero
 
    /**
     * Constructor nulo. Inicializa el tablero sin ninguna ficha.
     */
	public Tablero () {
		tablero = new Contenido[DIM][DIM];
		for (int i=0; i<DIM; i++)
			for (int j=0; j<DIM; j++)
				tablero[i][j] = Contenido.VACIA;
		movimiento = new Movimiento();
	}
	
	/**
	 * Constructor por copia. Crea un nuevo tablero copiando uno existente.
	 * @param t
	 */
	public Tablero (Tablero t) {
		tablero = new Contenido[DIM][DIM];
		for (int i=0; i<DIM; i++) 
			for (int j=0; j<DIM; j++)
				tablero[i][j] = t.tablero[i][j];
		movimiento = new Movimiento(t.movimiento.fila, t.movimiento.columna, t.movimiento.valor);
	}
	
	/**
	 * Comprueba si una celda del tablero está vacía.
	 * @param fil índice de la fila para especificar la posición de la celda
	 * @param col índice de la columna para especificar la posición de la celda
	 * @return <code>true</code> si la celda está vacía.
	 */
	public boolean casillaVacia (int fil, int col) {
		if (tablero[fil][col]==Contenido.VACIA) {
			return true;
		} else return false;
	}
	
	/**
	 * Pone una ficha en una celda vacía del tablero.
	 * @param fil índice de la fila para especificar la posición de la celda
	 * @param col índice de la columna para especificar la posición de la celda
	 * @param ficha <code>Contenido</code> de la celda 
	 * @return el nuevo <code>TableroTresEnRaya</code> obtenido o <code>null</code> 
	 * si la celda ya estaba ocupada.
	 */
	public Tablero poner (int fil, int col, Contenido ficha) {
		Tablero n_tablero;
		
		if (casillaVacia(fil,col)) {
			n_tablero = new Tablero ();
		
			for (int i = 0; i<DIM; i++)
				for(int j=0; j<DIM; j++)
					n_tablero.tablero[i][j] = tablero[i][j];
			n_tablero.tablero[fil][col] = ficha;
			n_tablero.movimiento = new Movimiento();
			n_tablero.movimiento.fijarPosicion(fil, col);
		
			return n_tablero;
		} else return null;
	}

	
	/**
	 * Pone una ficha en una celda vacía del tablero e inicializa el valor asociado a éste
	 * @param fil índice de la fila para especificar la posición de la celda
	 * @param col índice de la columna para especificar la posición de la celda
	 * @param ficha <code>Contenido</code> de la celda
	 * @param jugador_max si se establece a <code>true</code> indica que el jugador juega con 
	 * estrategia MAX, en otro caso juega con estrategia MIN
	 * @return el nuevo <code>Tablero</code> obtenido o <code>null</code> 
	 * si la celda ya estaba ocupada.
	 */
	public Tablero poner (int fil, int col, Contenido ficha, boolean jugador_max) {
		Tablero n_tablero;
		
		if (casillaVacia(fil,col)) {
			n_tablero = new Tablero ();
		
			for (int i = 0; i<DIM; i++)
				for(int j=0; j<DIM; j++)
					n_tablero.tablero[i][j] = tablero[i][j];
			n_tablero.tablero[fil][col] = ficha;
			n_tablero.movimiento = new Movimiento();
			n_tablero.movimiento.fijarPosicion(fil, col);
			
		    if (jugador_max)
		    	 n_tablero.movimiento.fijarValor(Integer.MIN_VALUE);
		    else n_tablero.movimiento.fijarValor(Integer.MAX_VALUE);
			return n_tablero;
		} else return null;
	}

	/**
	 * Desarrolla, a partir del tablero actual, todos los tableros que se pueden
	 * obtener con un movimiento del jugador cuya ficha se indica.
	 * @param f_jugador identifica la ficha del jugador según el <code>Contenido</code> 
	 * de las celdas
	 * @param jugador_max si se establece a <code>true</code> indica que el jugador juega con 
	 * estrategia MAX, en otro caso juega con estrategia MIN
	 * @return <code>ArrayList</code> con los <code>Tablero</code>s obtenidos
	 */
	public ArrayList<Tablero> jugadas (Contenido f_jugador, boolean jugador_max) {
		ArrayList<Tablero> sucesores;
		Tablero n_tablero;
		
		sucesores = new ArrayList<Tablero>();
		for (int i=0; i<DIM; i++) {
			for (int j=0; j<DIM; j++) {
				if (casillaVacia(i,j)) {
					n_tablero = new Tablero();
					n_tablero = poner(i, j, f_jugador, jugador_max);
					sucesores.add(n_tablero);
				}
			}
		}
		return sucesores;
	}

	/**
	 * Comprueba si hay un ganador
	 * @return <code>true</code> si hay tres fichas del mismo tipo en línea
	 */
	public boolean ganador () {
		int i, j;

		// comprueba filas 
		for (i = 0; i<DIM; i++)
			if ((tablero[i][0]!=Contenido.VACIA) && (tablero[i][0] == tablero[i][1]) && (tablero[i][1] == tablero[i][2]))
				return true;
		
		// comprueba columnas
		for (j = 0; j<DIM; j++)
			if ((tablero[0][j]!=Contenido.VACIA) && (tablero[0][j] == tablero[1][j]) && (tablero[1][j] == tablero[2][j]))
				return true;
		
		// comprueba diagonales
		if ((tablero[0][0]!=Contenido.VACIA) && (tablero[0][0] == tablero[1][1]) && (tablero[1][1] == tablero[2][2]))
			return true;
		if ((tablero[2][0]!=Contenido.VACIA) && (tablero[2][0] == tablero[1][1]) && (tablero[1][1] == tablero[0][2]))
			return true;
		return false;
	}

	/**
	 * Comprueba si hay tres en linea para un determinado tipo de ficha (CRUZ o REDONDEL) 
	 * @param ficha identifica la ficha según el <code>Contenido</code> de las celdas
	 * @return <code>true</code> si existe un tres en raya
	 */
	public boolean gana (Contenido ficha) {
		int i, j;

		// comprueba filas 
		for (i = 0; i<DIM; i++)
			if ((ficha == tablero[i][0]) && (tablero[i][0] == tablero[i][1]) && (tablero[i][1] == tablero[i][2]))
				return true;
		
		// comprueba columnas
		for (j = 0; j<DIM; j++)
			if ((ficha == tablero[0][j]) && (tablero[0][j] == tablero[1][j]) && (tablero[1][j] == tablero[2][j]))
				return true;
		
		// comprueba diagonales
		if ((ficha == tablero[0][0]) && (tablero[0][0] == tablero[1][1]) && (tablero[1][1] == tablero[2][2]))
			return true;
		if ((ficha == tablero[2][0]) && (tablero[2][0] == tablero[1][1]) && (tablero[1][1] == tablero[0][2]))
			return true;
		return false;
	}
	
	/**
	 * Comprueba si pierde el jugador que juega con la ficha indicada
	 * @param ficha identifica la ficha según el <code>Contenido</code> de las celdas
	 * @return <code>true</code> si pierde la partida
	 */
	public boolean pierde (Contenido ficha) {
		Contenido ficha_oponente;
		
		if (ficha == Contenido.CRUZ) {
			ficha_oponente = Contenido.REDONDEL;
		} else ficha_oponente = Contenido.CRUZ;
		
		return (gana (ficha_oponente));
	}

	/**
	 * Comprueba si todas las celdas del tablero están ocupadas por fichas
	 * @return <code>true</code> si el tablero está completo
	 */
	public boolean completo () {
	    for (int i=0; i<DIM; i++) 
	    	for (int j=0; j<DIM; j++) 
	    		if (casillaVacia(i, j))
	    			return false;
	    return true;
	}
	
	/**
	 * Devuelve el estado del juego en función del contenido del tablero
	 * @return el <code>Resultado</code> del juego
	 */
	public Resultado estado () {
		Resultado r;
		
		if (gana(Contenido.CRUZ))
			r = Resultado.GANA_CRUZ;
		else if (gana(Contenido.REDONDEL))
			r = Resultado.GANA_REDONDEL;
		else if (completo())
			r = Resultado.EMPATE;
		else r = Resultado.EN_JUEGO;
		
		return r;
	}
	
	/**
	 * Calcula el número de filas que pueden completarse
	 * con las fichas de un determinado tipo. 
	 * @param ficha <code>Contenido</code> que indica el tipo de las fichas
	 * @return número de filas que contienen sólo fichas
	 * del tipo indicado
	 */
	public int filasDominadas (Contenido ficha) {
		int fd;
		
		fd = 0;
		for (int i=0; i<DIM; i++)
			if (((tablero[i][0] == ficha) || (tablero[i][0] == Contenido.VACIA)) && 
			    ((tablero[i][1] == ficha) || (tablero[i][1] == Contenido.VACIA)) &&
			    ((tablero[i][2] == ficha) || (tablero[i][2] == Contenido.VACIA)))
				fd++;
		return fd;
	}
	
	/**
	 * Calcula el número de columnas que pueden completarse
	 * con las fichas de un determinado tipo. 
	 * @param ficha <code>Contenido</code> que indica el tipo de las fichas
	 * @return número de columnas que contienen sólo fichas
	 * del tipo indicado
	 */
	public int columnasDominadas (Contenido ficha) {
		int cd;
		
		cd = 0;
		for (int j=0; j<DIM; j++)
			if (((tablero[0][j] == ficha) || (tablero[0][j] == Contenido.VACIA)) && 
			    ((tablero[1][j] == ficha) || (tablero[1][j] == Contenido.VACIA)) &&
			    ((tablero[2][j] == ficha) || (tablero[2][j] == Contenido.VACIA)))
				cd++;
		return cd;
	}

	/**
	 * Calcula el número de diagonales que pueden completarse
	 * con las fichas de un determinado tipo. 
	 * @param ficha <code>Contenido</code> que indica el tipo de las fichas
	 * @return número de diagonales que contienen sólo fichas
	 * del tipo indicado
	 */
	public int diagonalesDominadas (Contenido ficha) {
		int dd;
		
		dd = 0;
		if (((ficha == tablero[0][0]) || (tablero[0][0] == Contenido.VACIA)) &&
			((ficha == tablero[1][1]) || (tablero[1][1] == Contenido.VACIA)) && 
			((ficha == tablero[2][2]) || (tablero[2][2] == Contenido.VACIA)))
			dd++;
		if (((ficha == tablero[0][2]) || (tablero[0][2] == Contenido.VACIA)) &&
			((ficha == tablero[1][1]) || (tablero[1][1] == Contenido.VACIA)) && 
			((ficha == tablero[2][0]) || (tablero[2][0] == Contenido.VACIA)))
			dd++;
		return dd;
	}
	
	/**
	 * Calcula el número de filas, columnas y diagonales que pueden completarse
	 * con las fichas de un determinado tipo.
	 * @param ficha <code>Contenido</code> que indica el tipo de las fichas
	 * @return número de filas, columnas y diagonales que contienen sólo fichas
	 * del tipo indicado
	 */
	public int evaluacion (Contenido ficha, boolean jugador_max) {
		int val;
		
		int fils_dom_max, fils_dom_min;
		int cols_dom_max, cols_dom_min;
		int diags_dom_max, diags_dom_min;
		Contenido ficha_max, ficha_min;
		
		if (ganador()) {
				if (gana(ficha)) { //gana el jugador para el que se está evaluando
					if (jugador_max)
						 val = Integer.MAX_VALUE; // valor mayor que cualquier otro valor de evaluación para el jugador max
					else val = Integer.MIN_VALUE; // valor menor que cualquier otro valor de evaluación para el jugador min
				} else {
					if (jugador_max) // gana el oponente
						 val = Integer.MIN_VALUE;
					else val = Integer.MAX_VALUE;
				}
		} else {
			if (jugador_max) {
				ficha_max = ficha;
				if (ficha_max == Contenido.CRUZ)
					 ficha_min = Contenido.REDONDEL;
				else ficha_min = Contenido.CRUZ;		
			} else {
				ficha_min = ficha;
				if (ficha_min == Contenido.CRUZ)
					 ficha_max = Contenido.REDONDEL;
				else ficha_max = Contenido.CRUZ;
			}
			
			fils_dom_max = filasDominadas (ficha_max);
			cols_dom_max = columnasDominadas (ficha_max);
			diags_dom_max = diagonalesDominadas (ficha_max);
			
			fils_dom_min = filasDominadas (ficha_min);
			cols_dom_min = columnasDominadas (ficha_min);
			diags_dom_min = diagonalesDominadas (ficha_min);

			val = (fils_dom_max + cols_dom_max + diags_dom_max) - (fils_dom_min + cols_dom_min + diags_dom_min) ;
		}
		return val;
	}

	/**
	 * Imprime la disposición del tablero actual
	 */
	public void print() {
		Contenido celda;
		
	    System.out.println();
	    for (int i=0; i<DIM; i++) {
	    	for (int j=0; j<DIM; j++) {
	    		celda = tablero[i][j];
	        	switch (celda) {
	        	 	case VACIA:
	        	 		System.out.print("   ");
	        	 		break;
	        	 	case CRUZ:
	        	 		System.out.print(" X ");
	        	 		break;
	        	 	case REDONDEL:
	        	 		System.out.print(" 0 ");
	        	 		break;
	        	 }
	        	 if (j <DIM -1) System.out.print("|");
	        	 
	         }
	         System.out.println();
	         if (i < DIM - 1) {
	            System.out.println("-----------");
	         }
	      }
	}
	
}
