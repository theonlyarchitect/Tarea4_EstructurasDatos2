package vindas.milton.bl.nodos;

public class NodoRojoNegro {
    int dato;
    NodoRojoNegro izq;
    NodoRojoNegro der;
    int altura;

    boolean color; // 0 is black and 1 is red.

    public NodoRojoNegro() {
    }

    public NodoRojoNegro(int dato) {
        this.dato = dato;
        this.izq = null;
        this.der = null;
        this.altura = 0;
        this.setColor(true);//Cualquier nodo nuevo es rojo.
    }

    public int getDato() {
        return dato;
    }

    public void setDato(int dato) {
        this.dato = dato;
    }

    public NodoRojoNegro getIzq() {
        return izq;
    }

    public void setIzq(NodoRojoNegro izq) {
        this.izq = izq;
    }

    public NodoRojoNegro getDer() {
        return der;
    }

    public void setDer(NodoRojoNegro der) {
        this.der = der;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public boolean getColor() {return color;}

    public void setColor(boolean color) { this.color = color; }
}
