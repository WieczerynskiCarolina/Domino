package ar.edu.unlu.poo.Domino.Modelo;

public class Ficha {
    private int valorA;
    private int valorB;

    public Ficha(int a, int b){
        this.valorA = a;
        this.valorB = b;
    }

    public int getValorA() {
        return valorA;
    }

    public int getValorB() {
        return valorB;
    }

    public Boolean esDoble(){
        return valorA == valorB;
    }

    public int getSumaPuntos(){
        return valorA + valorB;
    }

    public Boolean contieneValor(int valor){
        return valorA == valor || valorB == valor;
    }

    public int getOtroValor(int valor){
        return (valor == valorA)? valorB : valorA;
    }

    @Override
    public String toString() {
        return "[" + this.valorA + "|" + this.valorB + "]";
    }
}
