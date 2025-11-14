package ar.edu.unlu.poo.Domino;

import ar.edu.unlu.poo.Domino.Controlador.ControladorConsola;
import ar.edu.unlu.poo.Domino.Vista.VistaConsola;

public class App {
    public static void main(String[] args){
        VistaConsola vista = new VistaConsola();

        ControladorConsola controlador = new ControladorConsola(vista);

        controlador.iniciar();
    }
}
