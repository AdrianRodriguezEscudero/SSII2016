package org.ssii.busqueda.juegos.tresenraya;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.ssii.busqueda.juegos.tresenraya.Tablero.Contenido;
import org.ssii.busqueda.juegos.tresenraya.Tablero.Resultado;
import org.ssii.busqueda.juegos.tresenraya.Jugador.Tipo;

/**
 * Clase para desarrollar una partida del juego del Tres en Raya
 * @author José del Sagrado
 * @since Sep 2013
 * @version 1.0
 * @see Movimiento
 * @see Tablero
 * @see Jugador
 */
public class Partida {
	Tablero tablero;					// Tablero en el que se desarrolla la partida
	Jugador[] jugador;					// Jugadores
	public static final int NRO_J = 2;	// Número de jugadores
	public int profundidad;				// Límite de profundidad en la búsqueda

	/**
	 * Constructor. Inicializa el tablero y la profundidad.
	 */
	public Partida () {
		tablero = new Tablero();
		jugador = new Jugador[NRO_J];
		profundidad = 0;
	}

	/**
	 * Define los turnos de juego, las fichas de cada jugador y el límite de profundidad
	 * para la búsqueda
	 * @param br <code>BufferedReader</code> que se encarga de leer la entrada desde consola
	 */
	public void inicializarJugadores (BufferedReader br) {
		int turno_humano, ficha_humano;
		int profundidad_busqueda;
		String respuesta;
		
		try {	
			System.out.println("Indique el turno (1-2) para jugar: ");
			respuesta = br.readLine();
			turno_humano = Integer.parseInt(respuesta)-1; // leer el turno
			System.out.println("Indique la ficha que quiere utilizar X(1)ó 0(2): ");
			respuesta = br.readLine();
			ficha_humano = Integer.parseInt(respuesta); // leer el tipo de ficha
			System.out.println("Indique la profundidad de exploración en la búsqueda [1-9]: ");
	        respuesta = br.readLine();
			profundidad_busqueda = Integer.parseInt(respuesta); // leer la profundidad
			
			if (ficha_humano == 1) {
				jugador[turno_humano] = new Jugador (Tipo.HOMBRE, Contenido.CRUZ);
			    jugador[(turno_humano+1)%2] = new Jugador (Tipo.MAQUINA, Contenido.REDONDEL);
			} else if (ficha_humano == 2) {
				jugador[turno_humano] = new Jugador (Tipo.HOMBRE,Contenido.REDONDEL);
			    jugador[(turno_humano+1)%2] = new Jugador (Tipo.MAQUINA, Contenido.CRUZ);
			}
			profundidad = profundidad_busqueda;
		} catch (IOException e) {
			System.out.println("ERROR leyendo los datos de inicialización de la partida");
			e.printStackTrace();
		}
	}
	
	/**
	 * Comprueba si la partida ha finalizado	
	 * @return <code>true</code> si la partida ha concluido
	 */
	public boolean acabada () {
		if (tablero.estado() != Resultado.EN_JUEGO)
			return true;
		else return false;
	}
	
	/**
	 * Muestra el resultado de la partida
	 * @param turno del jugador que ha finalizado el juego (ha hecho el último movimiento)
	 */
	public void resultado (int turno) {
		Resultado r;
		
		r = tablero.estado();
		
		switch (r.ordinal()) {
			case 1: // Gana cruz
				System.out.println("¡¡¡Gana X!!!");
				if (jugador[turno].tipo==Tipo.HOMBRE)
					System.out.println("Enhorabuena, el hombre vence a la máquina");
				else System.out.println("Lo siento, la máquina vence al hombre");
				break;
			case 2: // Gana redondel
				System.out.println("¡¡¡Gana O!!!");
				if (jugador[turno].tipo==Tipo.HOMBRE)
					System.out.println("Enhorabuena, el hombre vence a la máquina");
				else System.out.println("Lo siento, la máquina vence al hombre");
				break;
			case 3: // Empate
				System.out.println("¡¡¡Empate!!!");
				break;
		}
	}

	/**
	 * Realiza la jugada del jugador al que le corresponde el turno de juego
	 * @param j índice del jugador al que le toca mover ficha
	 * @param br <code>BufferedReader</code> que se encarga de leer la entrada desde consola 
	 */
	public void juega (int j, BufferedReader br) {
		Movimiento movimiento;
		Tablero n_tablero;
		
		// Obtiene la jugada del jugador
		if (jugador[j].tipo == Tipo.HOMBRE)
			movimiento = jugador[j].leeJugada(br);
		else {
			movimiento = jugador[j].buscaJugada(tablero, profundidad); // no tiene en cuenta el turno, siempre max
		}
		movimiento.print();
		
		// Ejecuta la jugada
		n_tablero = new Tablero();
		n_tablero = tablero.poner(movimiento.obtenerFila(), movimiento.obtenerColumna(), jugador[j].ficha);
        
		tablero = new Tablero (n_tablero);
	}
	
	/**
	 * Desarrollo de una partida del juego del Tres en Raya en la que uno de los
	 * jugadores utiliza la búsqueda minimax.
	 * @param args no recibe argumentos
	 */
	public static void main(String[] args) {
		Partida partida;
		int turno_jugador;
		BufferedReader ibr;
		
		try {
			// Habilitar la lectura por consola
			ibr = new BufferedReader(new InputStreamReader (System.in));
			
			// Inicializar el tablero
			partida = new Partida();
			
			// Inicializar los jugadores: asignar fichas y turno
			partida.inicializarJugadores(ibr);		
		
			// Jugar la partida
			turno_jugador = -1;
			partida.tablero.print();
			do {
				turno_jugador = (turno_jugador+1)%2;
				partida.juega(turno_jugador, ibr);
				partida.tablero.print();
			} while (!partida.acabada());
			
			// Mostrar el resultado de la partida
			partida.resultado(turno_jugador);
			ibr.close();
		} catch (IOException e) {
			System.out.println("ERROR leyendo datos durante la partida");
			e.printStackTrace();
		}
	}

}
