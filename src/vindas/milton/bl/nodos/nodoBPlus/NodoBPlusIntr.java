package vindas.milton.bl.nodos.nodoBPlus;

public class NodoBPlusIntr extends NodoBPlus{
    int gradoMax;
    int gradoMin;
    int numClaves;
    NodoBPlusIntr hermanoIzq;
    NodoBPlusIntr hermanoDer;
    Integer[] claves;
    NodoBPlus[] hijos;


    public NodoBPlusIntr(int m, Integer[] pClaves) {
        this.gradoMax = m;
        this.gradoMin = (int) Math.ceil(m / 2.0);
        this.numClaves = 0;
        this.claves = pClaves;
        this.hijos = new NodoBPlus[gradoMax + 1];
        this.isHoja = false;
    }

    public NodoBPlusIntr(int m, Integer[] pClaves, NodoBPlus[] pHijos) {
        this.gradoMax = m;
        this.gradoMin = (int) Math.ceil(m / 2.0);
        this.numClaves = calcularGradoNodo(pHijos);
        this.claves = pClaves;
        this.hijos = pHijos;
        this.isHoja = false;
    }


    /**
     * Método que agrega un hijo al nodo interno
     * @param pNodoHijo El nodo hijo
     */
    public void agregarHijo(NodoBPlus pNodoHijo) {
        this.hijos[numClaves] = pNodoHijo;
        this.numClaves++;
    }

    /**
     * Método que agrega un nodo hijo al nodo interno recibiendo la posición como parámetro
     * @param pHijo El nodo hijo por agregar
     * @param pPos La posición del nuevo nodo hijo en el nodo interno
     */
    public void agregarHijoconPos(NodoBPlus pHijo, int pPos) {
        for (int i = numClaves - 1; i >= pPos; i--) {
            hijos[i + 1] = hijos[i];
        }
        this.hijos[pPos] = pHijo;
        this.numClaves++;
    }

    /**
     * Método que devuelve la posición en el arreglo de nodos hijos del nodo interno
     * @param pHijo El nodo hijo a buscar
     * @return Retorna el entero con la posición
     */
    public int encontrarPosHijo(NodoBPlus pHijo) {
        for (int i = 0; i < hijos.length; i++) {
            if (hijos[i] == pHijo) {
                return i;
            }
        }
        return -1;
    }

    private int calcularGradoNodo(NodoBPlus[] pHijos) {
        for (int i = 0; i < pHijos.length; i++) {
            if (pHijos[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public void removerHijo(int pPos) {
        this.hijos[pPos] = null;
        this.numClaves--;
    }

    /**
     * Método que devuelve un booleano si el nodo está sobrecargado
     * @return El booleano con la evaluación
     */
    public boolean estaSobrecargado() {
        return numClaves == gradoMax + 1;
    }


    public int getGradoMax() {
        return gradoMax;
    }

    public void setGradoMax(int gradoMax) {
        this.gradoMax = gradoMax;
    }

    public int getGradoMin() {
        return gradoMin;
    }

    public void setGradoMin(int gradoMin) {
        this.gradoMin = gradoMin;
    }

    public int getNumClaves() {
        return numClaves;
    }

    public void setNumClaves(int numClaves) {
        this.numClaves = numClaves;
    }

    public NodoBPlusIntr getHermanoIzq() {
        return hermanoIzq;
    }

    public void setHermanoIzq(NodoBPlusIntr hermanoIzq) {
        this.hermanoIzq = hermanoIzq;
    }

    public NodoBPlusIntr getHermanoDer() {
        return hermanoDer;
    }

    public void setHermanoDer(NodoBPlusIntr hermanoDer) {
        this.hermanoDer = hermanoDer;
    }

    public Integer[] getClaves() {
        return claves;
    }

    public void setClaves(Integer[] claves) {
        this.claves = claves;
    }

    public NodoBPlus[] getHijos() {
        return hijos;
    }

    public void setHijos(NodoBPlus[] hijos) {
        this.hijos = hijos;
    }
}
