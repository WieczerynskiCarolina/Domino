package ar.edu.unlu.poo.Domino.Modelo;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Mesa {
    private int extremoIzquierdo;
    private int extremoDerecho;
    private LinkedList<Ficha> fichasEnMesa;

    public Mesa(){
        this.fichasEnMesa  = new LinkedList<>();
        this.extremoIzquierdo = -1;
        this.extremoDerecho = -1;
    }

    public boolean agregarFichaPorIzquierda(Ficha f){
        if(estaVacia() || !f.contieneValor(this.extremoIzquierdo)){
            return false;
        }

        this.fichasEnMesa.addFirst(f);

        this.extremoIzquierdo = f.getOtroValor(this.extremoIzquierdo);
        return true;
    }

    public boolean agregarFichaPorDerecha(Ficha f){
        if(estaVacia() || !f.contieneValor(this.extremoDerecho)){
            return false;
        }

        this.fichasEnMesa.addLast(f);

        this.extremoDerecho = f.getOtroValor(this.extremoDerecho);
        return true;
    }

    public int[] getExtremos(){
        return new int[]{this.extremoIzquierdo, this.extremoDerecho};
    }

    public List<Ficha> getFichasEnMesa(){
        return Collections.unmodifiableList(this.fichasEnMesa);
    }

    public int getExtremoIzquierdo(){
        return this.extremoIzquierdo;
    }

    public int getExtremoDerecho() {
        return this.extremoDerecho;
    }

    public void agregarPrimeraFicha(Ficha f){
        if(estaVacia()){
            this.fichasEnMesa.add(f);
            this.extremoIzquierdo = f.getValorA();
            this.extremoDerecho = f.getValorB();
        }
    }

    public boolean estaVacia(){
        return this.fichasEnMesa.isEmpty();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("MESA (Extremos: ");
        sb.append("[").append(this.extremoIzquierdo).append("] ... ");
        sb.append("[").append(this.extremoDerecho).append("])\n");

        sb.append("     Fichas jugadas: ");

        if (estaVacia()) {
            sb.append("(Vacía)");

        } else if (this.fichasEnMesa.size() == 1) {

            sb.append(this.fichasEnMesa.getFirst().toString());

        } else {

            int valorAnterior = this.extremoIzquierdo;

            for (Ficha f : this.fichasEnMesa) {
                if (f.getValorA() == valorAnterior) {
                    sb.append("[").append(f.getValorA()).append("|").append(f.getValorB()).append("] ");
                    valorAnterior = f.getValorB();

                } else if (f.getValorB() == valorAnterior) {
                    sb.append("[").append(f.getValorB()).append("|").append(f.getValorA()).append("] ");
                    valorAnterior = f.getValorA();
                }
            }
        }
        return sb.toString();
    }
}
