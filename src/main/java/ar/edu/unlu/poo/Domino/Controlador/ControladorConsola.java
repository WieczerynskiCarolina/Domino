package ar.edu.unlu.poo.Domino.Controlador;

import ar.edu.unlu.poo.Domino.Modelo.Ficha;
import ar.edu.unlu.poo.Domino.Modelo.Jugador;
import ar.edu.unlu.poo.Domino.Modelo.Lado;
import ar.edu.unlu.poo.Domino.Modelo.Partida;
import ar.edu.unlu.poo.Domino.Observer.Observador;
import ar.edu.unlu.poo.Domino.Vista.VistaConsola;

import java.util.ArrayList;
import java.util.HashMap;

public class ControladorConsola implements Observador {
    private Partida partida;
    private VistaConsola vista;
    private HashMap<String, Integer> puntajesTotales;
    private int puntaje_limite;

    public ControladorConsola(VistaConsola vista){
        this.vista = vista;
        this.partida = null;
        this.puntajesTotales = new HashMap<>();
    }

    public void iniciar(){
        vista.mostrarBienvenida();

        configurarPartida();

        this.puntaje_limite = vista.pedirPuntajeLimite();

        String nombreGanadorJuego = null;
        int numeroRonda = 1;

        while(nombreGanadorJuego == null){
            vista.mostrarMensaje("\n--- INICIANDO RONDA " + numeroRonda + " ---");

            iniciarNuevaRonda();
            iniciarBucleJuego();

            Jugador ganadorRonda = gestionarFinRonda();

            int puntosGanados = 0;
            for(Jugador j: partida.getJugadores()){
                if(!j.getNombre().equals(ganadorRonda.getNombre())){
                    puntosGanados += j.sumarPuntosDeMano();
                }
            }

            int puntajePrevio = this.puntajesTotales.get(ganadorRonda.getNombre());
            int puntajeNuevo = puntajePrevio + puntosGanados;
            this.puntajesTotales.put(ganadorRonda.getNombre(), puntajeNuevo);

            vista.mostrarPuntajesTotales(this.puntajesTotales, numeroRonda, this.puntaje_limite);

            if(puntajeNuevo >= puntaje_limite){
                nombreGanadorJuego = ganadorRonda.getNombre();
            } else{
                vista.mostrarMensaje("Presiona Enter para la siguiente ronda...");
                vista.esperarEnter();
                numeroRonda++;
            }
        }

        vista.mostrarGanadorJuego(nombreGanadorJuego);

    }

    public void iniciarNuevaRonda(){
        ArrayList<Jugador> jugadoresDeLaRonda = new ArrayList<>();

        for(String nombreJugador: this.puntajesTotales.keySet()){
            jugadoresDeLaRonda.add(new Jugador(nombreJugador));
        }

        this.partida = new Partida(jugadoresDeLaRonda);
        this.partida.agregarObservador(this);

        this.partida.iniciarPartida();
    }

    private void configurarPartida(){
        int cantJugadores;

        do{
            cantJugadores = vista.pedirCantidadJugadores();
            if(cantJugadores < 2 || cantJugadores > 4){
                vista.mostrarMensaje("Error: Se puede jugar de 2 a 4 jugadores.");
            }
        } while(cantJugadores < 2 || cantJugadores > 4);


        this.puntajesTotales.clear();
        for(int i=0; i < cantJugadores; i++){
            String nombre = vista.pedirNombreJugador(i + 1);
            this.puntajesTotales.put(nombre, 0);
        }
    }

    private void iniciarBucleJuego(){
        while(!partida.estaTerminada()){
            gestionarTurno();
        }
    }

    private void gestionarTurno() {
        boolean turnoTerminado = false;

        while(!turnoTerminado){
            vista.mostrarMenu();
            int opcion = vista.obtenerOpcion();

            switch(opcion){
                case 1: // Jugar
                    turnoTerminado = gestionarJugada();
                    break;
                case 2: // pasar
                    turnoTerminado = gestionarPase();
                    break;
                default:
                    vista.mostrarMensaje("Error: opción no válida. Intente de nuevo.");
            }
        }
    }

    private boolean gestionarJugada() {
        int idx = vista.pedirIndiceFicha();
        int l;
        Lado lado = Lado.DERECHA;

        if(!partida.getMesa().estaVacia()){
            l = vista.pedirLado();
            if(l == 1){
              lado = Lado.IZQUIERDA;
            }
        }

        if(idx < 0 || idx >= partida.getJugadorActual().getMano().size()){
            vista.mostrarMensaje("Error: Índice de ficha no válido.");
            return false;
        }

        Ficha ficha = partida.getJugadorActual().getMano().get(idx);

        if(partida.jugarFicha(ficha, lado)){
            return true;
        } else{
            vista.mostrarMensaje("Error: No puedes jugar con esta ficha ahí.");
            return false;
        }
    }

    private boolean gestionarPase() {
        Jugador actual = partida.getJugadorActual();
        int[] extremos = partida.getMesa().getExtremos();

        if(actual.puedeJugar(extremos)){
            vista.mostrarMensaje("Error: No puedes pasar. ¡Tienes fichas para jugar!");
            return false;
        }

        if(!partida.getPozo().estaVacia()){
            vista.mostrarMensaje("No tienes jugada. Robando automáticamente del pozo...");
            partida.jugadorActualRoba();
            return false;
        }

        vista.mostrarMensaje(actual.getNombre() + " pasa el turno.");
        partida.pasarTurno();
        return true;
    }

    @Override
    public void actualizar(){
        String nombre = partida.getJugadorActual().getNombre();
        String mano = partida.getJugadorActual().getMano().toString();
        String mesa = partida.getMesa().toString();
        boolean pozoVacio = partida.getPozo().estaVacia();

        vista.mostrarEstadoJuego(nombre, mano, mesa, pozoVacio);
    }

    private Jugador gestionarFinRonda(){
        ArrayList<Jugador> jugadores = partida.getJugadores();
        Jugador ganador = null;
        int minPuntos = Integer.MAX_VALUE;
        String mensajeFinal;

        for(Jugador j: jugadores){
            if(j.tieneManoVacia()){
                ganador = j;
                break;
            }
        }

        if(ganador != null){
            mensajeFinal = "¡El ganador de la ronda es " + ganador.getNombre() + " al quedarse sin fichas!";
        } else{
            for(Jugador j: jugadores){
                int puntos = j.sumarPuntosDeMano();
                if(puntos < minPuntos){
                    minPuntos = puntos;
                    ganador = j;
                }
            }

            mensajeFinal = "¡Juego bloqueado! El ganador de la ronda es " + ganador.getNombre() + " con " + minPuntos + " puntos.";
        }

        vista.mostrarFinRonda(mensajeFinal);

        return ganador;
    }
}
