package ar.edu.unlu.poo.Domino.Modelo;
import ar.edu.unlu.poo.Domino.Observer.Observable;
import ar.edu.unlu.poo.Domino.Observer.Observador;

import java.util.ArrayList;
import java.util.List;

public class Partida implements Observable {
    private ArrayList<Jugador> jugadores;
    private Pozo pozo;
    private Mesa mesa;
    private Jugador jugadorActual;
    private boolean partidaTerminada;
    private int pasesConsecutivos = 0;
    private List<Observador> observadores;

    public Partida(ArrayList<Jugador> jugadores){
        this.jugadores = jugadores;
        this.mesa = new Mesa();
        this.pozo = new Pozo();
        this.jugadorActual = null;
        this.observadores = new ArrayList<>();
    }

    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    public void iniciarPartida(){
        this.pozo.mezclar();

        this.repartirFichas();

        this.determinarJugadorInicial();

        notificarObservadores();
    }

    private void determinarJugadorInicial(){
        Ficha mejorDoble = null;
        Ficha fichaMasAlta = null;
        Jugador jugadorQueInicia = null;

        // buscamos ficha doble más alta
        for(Jugador j: this.jugadores){
            for(Ficha f: j.getMano()){
                if(f.esDoble()){
                    if(mejorDoble == null || f.getSumaPuntos() > mejorDoble.getSumaPuntos()){
                        mejorDoble = f;
                        jugadorQueInicia = j;
                    }
                }
            }
        }

        // si no se encontró dobles, buscamos la ficha más alta
        if(jugadorQueInicia == null){
            for(Jugador j: this.jugadores){
                for(Ficha f: j.getMano()){
                    if(fichaMasAlta == null || f.getSumaPuntos() > fichaMasAlta.getSumaPuntos()){
                        fichaMasAlta = f;
                        jugadorQueInicia = j;
                    }
                }
            }
        }

        this.jugadorActual = jugadorQueInicia;
    }

    private void repartirFichas(){
        int fichasARepartir = 7;

        for(Jugador j: this.jugadores){
            for(int i = 0; i < fichasARepartir; i++){
                if(!this.pozo.estaVacia()){
                    Ficha fichaRepartida = this.pozo.robarFicha();
                    j.agregarFichaAMano(fichaRepartida);
                }
            }
        }
    }

    public Mesa getMesa(){
        return this.mesa;
    }

    public Pozo getPozo() {
        return this.pozo;
    }

    public ArrayList<Jugador> getJugadores(){
        return this.jugadores;
    }

    public boolean jugarFicha(Ficha f, Lado lado){
        if(!this.jugadorActual.getMano().contains(f)){
            return false;
        }

        boolean jugadaExitosa = false;

        // Caso de que sea la primera jugada de la partida
        if(this.mesa.estaVacia()){
            this.mesa.agregarPrimeraFicha(f);
            jugadaExitosa = true;
        } else{
            // Caso normal, la mesa ya tiene fichas
            if(lado == Lado.IZQUIERDA){
                jugadaExitosa = this.mesa.agregarFichaPorIzquierda(f);
            } else{
                jugadaExitosa = this.mesa.agregarFichaPorDerecha(f);
            }
        }

        // Si la jugada fue exitosa, actualiza el estado del juego
        if(jugadaExitosa){
            this.jugadorActual.removerFichaDeMano(f);
            this.pasesConsecutivos = 0;

            if(this.jugadorActual.tieneManoVacia()){
                this.partidaTerminada = true;
            } else{
                this.siguienteTurno();
            }

            notificarObservadores();
        }

        return jugadaExitosa;
    }

    public boolean jugadorActualRoba(){
        if(!pozo.estaVacia()){
            Ficha f = pozo.robarFicha();
            jugadorActual.agregarFichaAMano(f);
            notificarObservadores();
            return true;
        }
        return false;
    }

    public void pasarTurno(){
        this.pasesConsecutivos++;

        if(this.pasesConsecutivos >= this.jugadores.size()){
            this.partidaTerminada = true;
        } else{
            this.siguienteTurno();
        }

        notificarObservadores();
    }

    private void siguienteTurno(){
        int indiceActual = this.jugadores.indexOf(this.jugadorActual);

        int indiceSiguiente = (indiceActual + 1) % this.jugadores.size();

        this.jugadorActual = this.jugadores.get(indiceSiguiente);
    }

    public boolean estaTerminada(){
        return partidaTerminada;
    }

    @Override
    public void agregarObservador(Observador observador){
        observadores.add(observador);
    }

    @Override
    public void quitarObservador(Observador observador){
        observadores.remove(observador);
    }

    @Override
    public void notificarObservadores(){
        for(Observador observador: observadores){
            observador.actualizar();
        }
    }
}