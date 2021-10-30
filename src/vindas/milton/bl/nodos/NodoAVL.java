package vindas.milton.bl.nodos;

public class NodoAVL {
    int dato;
    NodoAVL izq;
    NodoAVL der;
    int altura;

    public NodoAVL() {
    }

    public NodoAVL(int dato) {
        this.dato = dato;
        this.izq = null;
        this.der = null;
        this.altura = 0;
    }

    public int getDato() {
        return dato;
    }

    public void setDato(int dato) {
        this.dato = dato;
    }

    public NodoAVL getIzq() {
        return izq;
    }

    public void setIzq(NodoAVL izq) {
        this.izq = izq;
    }

    public NodoAVL getDer() {
        return der;
    }

    public void setDer(NodoAVL der) {
        this.der = der;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
}
