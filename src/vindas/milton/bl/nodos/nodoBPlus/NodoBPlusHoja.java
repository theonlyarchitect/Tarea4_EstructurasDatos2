package vindas.milton.bl.nodos.nodoBPlus;

import java.util.Arrays;

/**
 * Clase que representa un nodo hoja del árbol B+
 */
public class NodoBPlusHoja extends NodoBPlus {

    /**
     * El número máximo de objetos/datos que se pueden almacenar en el nodo
     */
    int maxNum;
    /**
     * El número mínimo de objetos/datos que se pueden almacenar en el nodo
     */
    int minNum;
    /**
     * El número actual de objetos (clave + dato) almacenados actualmente
     */
    int numClaves;

    /**
     * El nodo hoja que es hermano izquierdo del nodo actual
     */
    NodoBPlusHoja hermanoIzq;

    /**
     * El nodo hoja que es hermano derecho del nodo actual
     */
    NodoBPlusHoja hermanoDer;

    /**
     * El arreglo de claves (o claves+datos) del nodo.
     */
    Integer[] claves;

    /**
     * Constructor con parámetros del nodo hoja del árbol B+
     * @param m El grado máximo del árbol
     * @param pClave La clave a ingresar en el árbol
     */
    public NodoBPlusHoja(int m, int pClave) {
        this.maxNum = m - 1;
        this.minNum = (int) (Math.ceil(m / 2) - 1);
        this.claves = new Integer[m];
        this.numClaves = 0;
        this.agregarClave(pClave);
        this.isHoja = true;
    }

    /**
     *
     * @param m El grado máximo del árbol B+
     * @param pClaves Las claves que contiene el nodo hoja del árbol B+
     * @param padre El nodo interno padre del nodo hoja
     */
    public NodoBPlusHoja(int m, Integer[] pClaves, NodoBPlusIntr padre) {
        this.maxNum = m - 1;
        this.minNum = (int) (Math.ceil(m / 2) - 1);
        this.claves = pClaves;
        this.numClaves = calcularGradoNodo(pClaves);
        this.padre = padre;
        this.isHoja = true;
    }


    public boolean agregarClave(int pClave) {
        if (this.estaLleno()) {
            return false;
        } else {
            this.claves[numClaves] = pClave;
            numClaves++;
            Arrays.sort(this.claves, 0, numClaves);
            return true;
        }
    }

    public void agregarClaveEnPos(int pClave, int pPos) {
        claves[pPos] = pClave;
        numClaves++;
        Arrays.sort(this.claves, 0, numClaves);
    }

    /**
     * Método que elimina una clave de un nodo
     * @param pPos La posición de la clave por eliminar en el nodo
     */
    public void borrarClave(int pPos) {
        this.claves[pPos] = null;
        numClaves--;
    }

    /**
     * Método que devuelve un booleano si el nodo está lleno y no puede aceptar más claves
     * @return El booleano con la evaluación
     */
    public boolean estaLleno() {
        return numClaves == maxNum;
    }

    /**
     * Método que calcula el grado del nodo según las claves que contiene
     * @param pClaves El arreglo de enteros de claves
     * @return Devuelve el número de claves en el nodo
     */
    private int calcularGradoNodo(Integer[] pClaves) {
        for (int i = 0; i < pClaves.length; i++) {
            if (pClaves[i] == null) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Método que devuelve un booleano indicando si el nodo cumple con la restricción mínima de m/2 -1 de claves
     * @return El booleano con el resultado
     */
    public boolean esDeficiente() {
        return numClaves < minNum;
    }


    public boolean isPrestable() {
        return numClaves > minNum;
    }

    public boolean isFusionable() {
        return numClaves == minNum;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public int getMinNum() {
        return minNum;
    }

    public void setMinNum(int minNum) {
        this.minNum = minNum;
    }

    public int getNumClaves() {
        return numClaves;
    }

    public void setNumClaves(int numClaves) {
        this.numClaves = numClaves;
    }

    public NodoBPlusHoja getHermanoIzq() {
        return hermanoIzq;
    }

    public void setHermanoIzq(NodoBPlusHoja hermanoIzq) {
        this.hermanoIzq = hermanoIzq;
    }

    public NodoBPlusHoja getHermanoDer() {
        return hermanoDer;
    }

    public void setHermanoDer(NodoBPlusHoja hermanoDer) {
        this.hermanoDer = hermanoDer;
    }

    public Integer[] getClaves() {
        return claves;
    }

    public void setClaves(Integer[] claves) {
        this.claves = claves;
    }

}
