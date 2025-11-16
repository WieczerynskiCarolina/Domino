package ar.edu.unlu.poo.Domino.Modelo;

import java.util.ArrayList;
import java.util.Collections;

public class Jugador {
    private String nombre;
    private ArrayList<Ficha> mano;

    public Jugador(String nombre){
        this.nombre = nombre;
        this.mano = new ArrayList<>();
    }

    public void agregarFichaAMano(Ficha ficha){
        this.mano.add(ficha);
    }

    public ArrayList<Ficha> getMano() {
        return new ArrayList<>(this.mano);
    }

    public String getNombre(){
        return nombre;
    }

    public Boolean tieneManoVacia(){
        return mano.isEmpty();
    }

    public int sumarPuntosDeMano(){
        int puntos = 0;
        for(Ficha f: mano){
            puntos += f.getSumaPuntos();
        }
        return puntos;
    }

    public void removerFichaDeMano(Ficha f){
        this.mano.remove(f);
    }

    public boolean puedeJugar(int[] extremosMesa){
        int extIzq = extremosMesa[0];
        int extDer = extremosMesa[1];

        if(extIzq == -1){
            return !this.mano.isEmpty();
        }

        for(Ficha f: this.mano){
            if(f.contieneValor(extIzq) || f.contieneValor(extDer)){
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.nombre).append(": ");
        sb.append(this.mano.toString());
        return sb.toString();
    }
}
