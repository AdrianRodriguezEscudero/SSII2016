package org.ssii.busqueda.juegos.tresenraya;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import org.ssii.busqueda.juegos.tresenraya.Tablero.Contenido;

/**
 * Clase que implementa las estrategias de juego que desarrollan distintos jugadores 
 * durante una partida de Tres en Raya. Existen dos estrategias: hombre (interactúa
 * a través de consola) y máquina (seleccina la jugada utilizando una estrategia 
 * minimax).
 * @author José del Sagrado
 * @since Sep 2013
 * @version 1.0
 * @see Tablero 
 * @see Movimiento
 */
public class Jugador {
	public enum Tipo {HOMBRE, MAQUINA};  // Clases de jugador
	
	public Tipo tipo;					 // Tipo de jugador		
	public Contenido ficha;				 // Ficha asignada al jugador
	
	/**
	 * Constructor. Define el tipo de jugador y la ficha que utiliza
	 * @param t <code>Tipo</code> de jugador
	 * @param f <code>Contenido</code> que indica la ficha utilizada
	 */
	public Jugador (Tipo t, Contenido f) {
		tipo = t;
		ficha = f;
	}
	
	/**
	 * Lee la jugada desde la consola
	 * @param br <code>BufferedReader</code> que se encarga de leer la entrada desde consola
	 * @return el <code>Movimiento</code> a realizar
	 */
	public Movimiento leeJugada (BufferedReader br) {
		Movimiento movimiento;
		String respuesta;
		
		movimiento = new Movimiento();
		try {
			System.out.println("Introduzca la fila de la celda en la que situará la ficha (1-3): ");
			respuesta = br.readLine();
		    movimiento.fijarFila(Integer.parseInt(respuesta)-1);
			System.out.println("Introduzca la columna de la celda en la que situará la ficha (1-3): ");
			respuesta = br.readLine();
		    movimiento.fijarColumna(Integer.parseInt(respuesta)-1);
		} catch (IOException e) {
			System.out.println("ERROR leyendo la jugada del usuario");
		}
		
		return movimiento;
	}
	
	/**
	 * Obtiene la ficha del oponente
	 * @param f_jugador_actual <code>Contenido</code> que indica la ficha del jugador actual
	 * @return <code>Contenido</code> que indica la ficha del jugador contrario
	 */
	public Contenido fichaOponente (Contenido f_jugador_actual) {
		if (f_jugador_actual == Contenido.CRUZ)
			return Contenido.REDONDEL;
		else return Contenido.CRUZ;
	}

	/**
	 * Devuelve el movimiento que tiene asociado el máximo valor de evaluación
	 * @param m1 primer <code>Movimiento</code> a comparar
	 * @param m2 segundo <code>Movimiento</code> a comparar
	 * @return el mayor de <code>m1</code> y <code>m2</code>
	 */
	public Movimiento maximo (Movimiento m1, Movimiento m2) {
		
		if (m1.valor >= m2.valor) {
			return m1;
		} else {
			return m2;
		}
	}
	
	/**
	 * Algoritmo para el jugador MAX en la búsqueda minimax
	 * @param t <code>Tablero</code> a partir del cuál comienza la búsqueda
	 * @param ficha <code>Contenido</code> que indica la ficha del jugador MAX
	 * @param nivel en el árbol de búsqueda (la raíz se sitúa en el nivel 0)
	 * @param profundidad máxima para la búsqueda
	 * @return el mejor <code>Movimiento</code> encontrado
	 * @see Movimiento min (Tablero t, Contenido ficha, int nivel, int profundidad)
	 * @see Movimiento maximo (Movimiento m1, Movimiento m2)
	 */
	public Movimiento max (Tablero t, Contenido ficha, int nivel, int profundidad) {
		Movimiento mejor;
		
		mejor = new Movimiento (true);
		if (nivel!=0)
			mejor.fijarPosicion(t.movimiento.fila, t.movimiento.columna);
		
		if ((t.ganador()) || (t.completo()) || (nivel == profundidad)) {
			int eval;
			eval = t.evaluacion(ficha, true);
			mejor = new Movimiento (t.movimiento.fila, t.movimiento.columna, eval);
		} else {
			ArrayList<Tablero> sucesores;
			sucesores = new ArrayList<Tablero>();
			sucesores = t.jugadas(ficha, true);
			while (!sucesores.isEmpty()) {
				Tablero nt = sucesores.remove(0);
				mejor = maximo (mejor, min(nt, fichaOponente(ficha), nivel+1, profundidad));
				if (nivel > 0) // nodo intermedio: conservar el movimiento del tablero
					mejor.fijarPosicion(t.movimiento.fila, t.movimiento.columna);
			}
		}
		return mejor;
	}

	/**
	 * Devuelve el movimiento que tiene asociado el mínimo valor de evaluación
	 * @param m1 primer <code>Movimiento</code> a comparar
	 * @param m2 segundo <code>Movimiento</code> a comparar
	 * @return el menor de <code>m1</code> y <code>m2</code>
	 */
	public Movimiento minimo (Movimiento m1, Movimiento m2) {
		if (m1.valor <= m2.valor) {
			return m1;
		} else {
			return m2;
		}
	}
	
	/**
	 * Algoritmo para el jugador MIN en la búsqueda minimax
	 * @param t <code>Tablero</code> a partir del cuál comienza la búsqueda
	 * @param ficha <code>Contenido</code> que indica la ficha del jugador MIN
	 * @param nivel en el árbol de búsqueda (la raíz se sitúa en el nivel 0)
	 * @param profundidad máxima para la búsqueda
	 * @return el mejor <code>Movimiento</code> encontrado
	 * @see Movimiento max (Tablero t, Contenido ficha, int nivel, int profundidad)
	 * @see Movimiento minimo (Movimiento m1, Movimiento m2)
	 */
	public Movimiento min (Tablero t, Contenido ficha, int nivel, int profundidad) {
		Movimiento mejor;
		
		mejor = new Movimiento (false);
		if (nivel!=0)
			mejor.fijarPosicion(t.movimiento.fila, t.movimiento.columna);
		
		if ((t.ganador()) || (t.completo()) || (nivel == profundidad)) {
			int eval;
			eval = t.evaluacion(ficha, false);
			mejor = new Movimiento (t.movimiento.fila, t.movimiento.columna, eval);
		} else {
			ArrayList<Tablero> sucesores;
			sucesores = new ArrayList<Tablero>();
			sucesores = t.jugadas(ficha, false);
			while (!sucesores.isEmpty()) {
				Tablero nt = sucesores.remove(0);
				mejor = minimo (mejor, max (nt, fichaOponente(ficha), nivel+1, profundidad));
				if (nivel>0){ // nodo intermedio: conservar el movimiento del tablero
					mejor.fijarPosicion(t.movimiento.fila, t.movimiento.columna);
				}
			}
		}
		return mejor;
	}

	/**
	 * Busca una jugada aplicando el algoritmo minimax
	 * @param t <code>Tablero</code> (que indica la situación actual de la partida)
	 * a partir del cuál comienza la búsqueda
	 * @param profundidad profundidad máxima para la búsqueda
	 * @return el mejor <code>Movimiento</code> encontrado
	 * @see Movimiento max (Tablero tablero, Contenido ficha, int profundidad)
	 */
	public Movimiento buscaJugada (Tablero t, int profundidad) {
		Movimiento movimiento;
		Tablero tablero_actual;
		
		movimiento = new Movimiento();
		tablero_actual = new Tablero(t);
		tablero_actual.movimiento = new Movimiento(true);
		movimiento = max (tablero_actual, ficha, 0, profundidad);
		return movimiento;
	}
	
	/**
	 * Algoritmo para el jugador MAX que incorpora poda alfa-beta a la búsqueda minimax
	 * @param t <code>Tablero</code> a partir del cuál comienza la búsqueda
	 * @param ficha <code>Contenido</code> que indica la ficha del jugador MAX
	 * @param nivel en el árbol de búsqueda (la raíz se sitúa en el nivel 0)
	 * @param profundidad máxima para la búsqueda
	 * @param alpha mejor valor para MAX
	 * @param beta mejor valor para MIN
	 * @return el mejor <code>Movimiento</code> encontrado
	 * @see Movimiento beta_min (Tablero t, Contenido ficha, int nivel, int profundidad, int alpha, int beta)
	 * @see Movimiento maximo (Movimiento m1, Movimiento m2)
	 */
	public Movimiento alpha_max (Tablero t, Contenido ficha, int nivel, int profundidad, int alpha, int beta) {
		Movimiento mejor;
		
		mejor = new Movimiento (true);
		
		// TODO: implementar la poda alpha para el jugador max
		
		return mejor;
	}

	/**
	 * Algoritmo para el jugador MIN que incorpora poda alfa-beta a la búsqueda minimax
	 * @param t <code>Tablero</code> a partir del cuál comienza la búsqueda
	 * @param ficha <code>Contenido</code> que indica la ficha del jugador MIN
	 * @param nivel en el árbol de búsqueda (la raíz se sitúa en el nivel 0)
	 * @param profundidad máxima para la búsqueda
	 * @param alpha mejor valor para MAX
	 * @param beta mejor valor para MIN
	 * @return el mejor <code>Movimiento</code> encontrado
	 * @see Movimiento alpha_max (Tablero t, Contenido ficha, int nivel, int profundidad, int alpha, int beta)
	 * @see Movimiento minimo (Movimiento m1, Movimiento m2)
	 */
	public Movimiento beta_min (Tablero t, Contenido ficha, int nivel, int profundidad, int alpha, int beta) {
		Movimiento mejor;
		
		mejor = new Movimiento (false);
		
		// TODO: implementar la poda beta para el jugador min
		
		return mejor;
	}

}
