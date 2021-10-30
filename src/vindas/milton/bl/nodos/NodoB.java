package vindas.milton.bl.nodos;

public class NodoB {
    /**
     * Entero que indica la cantidad de claves actual en el nodo
     */
    int n;

    /**
     * Arreglo de las claves que contiene un nodo del árbol
     */
    int claves[];

    /**
     * Arreglo con los nodos hijos del nodo actual
     */
    NodoB hijos[];

    /**
     * Variable booleana que indica si el nodo es una hoja
     */
    boolean hoja;


    public NodoB() {
    }

    public NodoB(int clavesMaximo) {
        this.n = 0;
        this.hoja = true;
        this.claves = new int[clavesMaximo];
        this.hijos = new NodoB [clavesMaximo+1];
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getClave(int pos) {
        return this.claves[pos];
    }

    public void setClave(int pos, int clave) {
        this.claves[pos] = clave;
    }

    public NodoB getHijo(int pos) {
        return this.hijos[pos];
    }

    public void setHijo(int pos, NodoB hijo) {
        this.hijos[pos] = hijo;
    }

    public boolean isHoja() {
        return hoja;
    }

    public void setHoja(boolean hoja) {
        this.hoja = hoja;
    }
}
