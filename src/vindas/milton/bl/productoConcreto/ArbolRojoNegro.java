package vindas.milton.bl.productoConcreto;

import vindas.milton.bl.nodos.NodoRojoNegro;
import vindas.milton.bl.nodos.NodoRojoNegro;
import vindas.milton.bl.productoAbstracto.Arbol;

public class ArbolRojoNegro implements Arbol {
    /**
     * El nodo raíz del árbol
     */
    NodoRojoNegro raiz;
    
    public ArbolRojoNegro(int pClaves) {
    }
    
        /*-------------------------------------------------------------------------------------
                                         Métodos Públicos
    --------------------------------------------------------------------------------------*/
    /**
     * Método que retorna el nodo raíz
     * @return Retorna el nodo raíz
     */
    public NodoRojoNegro getRaiz() {
        return raiz;
    }

    /**
     * Método que establece el nodo raíz del árbol
     * @param raiz El nodo a ser establecido como raíz
     */
    public void setRaiz(NodoRojoNegro raiz) {
        this.raiz = raiz;
    }
    
    /**
     * Método público para agregar dato al árbol binario
     * @param pDato El entero que representa el dato por agregarse
     */
    @Override
    public void insertar(int pDato) {
        raiz = agregarRecursivo(raiz, pDato);
        colorear(raiz);
    }

    /**
     * Método público que retorna los elementos del árbol según el tipo de orden determinado.
     * @param orden El entero con el tipo de orden (1. PreOrden, 2. InOrden, 3. PostOrden)
     * @return Retorna el String con el contenido del árbol en el orden seleccionado.
     */
    @Override
    public String despliegue(int orden) {
        String result = "";
        switch (orden) {
            case 1:
                result= result + mostrarPreOrden(getRaiz());
                break;
            case 2:
                result= result + mostrarInOrden(getRaiz());
                break;
            case 3:
                result= result + mostrarPostOrden(getRaiz());
                break;
            default:
                result = "Opción incorrecta.";
        }
        return result;
    }
    /*-------------------------------------------------------------------------------------
                                         Métodos Privados
    --------------------------------------------------------------------------------------*/
    /**
     * Método que asigna colores a las hojas.
     */
    private void colorear(NodoRojoNegro selected){
        if(selected == null){
            return;
        }
        if(selected.equals(raiz)){
            selected.setColor(false); //color negro.
        }

        Boolean currentcolor = selected.getColor();

        if(selected.getDer() != null){
            selected.getDer().setColor(!currentcolor);
            colorear(selected.getDer());
        }
        if(selected.getIzq() != null) {
            selected.getIzq().setColor(!currentcolor);
            colorear(selected.getIzq());
        }
    }

    /**
     * Método que retorna la altura de un nodo
     * @param actual El nodo de consulta de la altura
     * @return Retorna el entero con la altura del nodo
     */
    private int altura(NodoRojoNegro actual)
    {
        return actual == null ? 0 : actual.getAltura();
    }

    /**
     * Método que actualiza la altura de un nodo después de la inserción
     * @param actual El nodo al cual se le actualiza la altura
     */
    private void actualizarAltura(NodoRojoNegro actual) {
        actual.setAltura(1 + Math.max(altura(actual.getDer()), altura(actual.getIzq())));
    }

    /**
     * Método que calcula el factor de equilibrio de un nodo
     * @param actual El nodo al que se le calcula el factor de equilibrio
     * @return Retorna el entero que representa el factor de equilibrio
     */
    private int factorEq(NodoRojoNegro actual) {
        return (actual == null) ? 0 : altura(actual.getDer()) - altura(actual.getIzq());
    }

    /**
     * Método privado recursivo que administra la posición del nuevo nodo en el árbol
     * @param actual El nodo raíz
     * @param pDato El entero por agregarse
     * @return Retorna el nodo que se establece como raíz.
     */
    private NodoRojoNegro agregarRecursivo(NodoRojoNegro actual, int pDato) {
        if (actual == null)
            actual = new NodoRojoNegro(pDato);
        else if (pDato < actual.getDato()) {
            actual.setIzq(agregarRecursivo(actual.getIzq(), pDato));
        }
        else if (pDato > actual.getDato()) {
            actual.setDer(agregarRecursivo(actual.getDer(), pDato));
        }
        else
            ;  // Dato duplicado...
        return equilibrarAVL(actual);
    }

    /**
     * Método que revisa y equilibra el árbol AVL al insertarse un nuevo dato
     * @param actual El nodo a revisar/equilibrar
     * @return Retorna el nodo equilibrado
     */
    private NodoRojoNegro equilibrarAVL(NodoRojoNegro actual) {
        actualizarAltura(actual);
        int factorEq = factorEq(actual);

        // Si el factor de equilibrio del nodo actual es 2
        if (factorEq > 1) {
            // Si el factor de equilibrio del nodo derecho es -1, aplicar RDI
            if (altura(actual.getDer().getDer()) < altura(actual.getDer().getIzq())) {
                actual = rotacionDobleIzquierda(actual);
            }
            // Si el factor de equilibrio del nodo derecho es 1 o 0, aplicar RSI
            else {
                actual = rotacionSimpleIzquierda(actual);
            }
        }
        else if (factorEq < -1) {
            // Si el factor de equilibrio del nodo izquierdo es 1, aplicar RDD
            if (altura(actual.getIzq().getDer()) > altura(actual.getIzq().getIzq())) {
                actual = rotacionDobleDerecha(actual);
            }
            // Si el factor de equilibrio del nodo izquierdo es -1 o 0, aplicar RSD
            else {
                actual = rotacionSimpleDerecha(actual);
            }
        }
        return actual;
    }

    /**
     * Método que realiza una rotación simple a la izquierda con un nodo como parámetro
     * @param p El nodo utilizado como padre para la rotación
     * @return Retorna el nodo que quedó como padre después de la rotación
     */
    private NodoRojoNegro rotacionSimpleIzquierda(NodoRojoNegro p) {
        NodoRojoNegro q = p.getDer();
        NodoRojoNegro b = q.getIzq();
        q.setIzq(p);
        p.setDer(b);
        actualizarAltura(p);
        actualizarAltura(q);
        return q;
    }


    /**
     * Método que realiza una rotación simple a la derecha con un nodo como parámetro
     * @param p El nodo utilizado como padre para la rotación
     * @return Retorna el nodo que quedó como padre después de la rotación
     */
    private NodoRojoNegro rotacionSimpleDerecha(NodoRojoNegro p) {
        NodoRojoNegro q = p.getIzq();
        NodoRojoNegro b = q.getDer();
        q.setDer(p);
        p.setIzq(b);
        actualizarAltura(p);
        actualizarAltura(q);
        return q;
    }

    /**
     * Método que realiza una rotación doble a la izquierda con un nodo como parámetro
     * @param p El nodo utilizado como padre para la rotación
     * @return Retorna el nodo después de la rotación
     */
    private NodoRojoNegro rotacionDobleIzquierda(NodoRojoNegro p) {
        p.setDer(rotacionSimpleDerecha(p.getDer()));
        p = rotacionSimpleIzquierda(p);
        return p;
    }

    /**
     * Método que realiza una rotación doble a la derecha con un nodo como parámetro
     * @param p El nodo utilizado como padre para la rotación
     * @return Retorna el nodo después de la rotación
     */
    private NodoRojoNegro rotacionDobleDerecha(NodoRojoNegro p) {
        p.setIzq(rotacionSimpleIzquierda(p.getIzq()));
        p = rotacionSimpleDerecha(p);
        return p;
    }


     /*-------------------------------------------------------------------------------------
                            Métodos privados de despliegue
    --------------------------------------------------------------------------------------*/

    /**
     * Método privado que retorna los elementos del árbol según inorden
     * @param nodo El nodo raíz
     */
    private String mostrarInOrden(NodoRojoNegro nodo) {
        String result = "";
        if (nodo != null) {
            result = result + mostrarInOrden(nodo.getIzq());
            result = result + " - " + nodo.getDato() + " color: ";
            if(nodo.getColor()){
                result += " Rojo ";
            } else
            {
                result += " Negro ";
            }
            result = result + mostrarInOrden(nodo.getDer());
        }
        return result;
    }

    /**
     * Método privado que retorna los elementos del árbol según preorden
     * @param nodo El nodo raíz
     */
    private String mostrarPreOrden(NodoRojoNegro nodo) {
        String result = "";
        if (nodo != null) {
            result = result + " - " + nodo.getDato() + " color: ";
            if(nodo.getColor()){
                result += " Rojo ";
            } else
            {
                result += " Negro ";
            }
            result = result + mostrarPreOrden(nodo.getIzq());
            result = result + mostrarPreOrden(nodo.getDer());
        }
        return result;
    }

    /**
     * Método privado que retorna los elementos del árbol según postorden
     * @param nodo El nodo raíz
     */
    private String mostrarPostOrden(NodoRojoNegro nodo) {
        String result = "";
        if (nodo != null) {
            result = result + mostrarPostOrden(nodo.getIzq());
            result = result + mostrarPostOrden(nodo.getDer());
            result = result + " - " + nodo.getDato() + " color: ";
            if(nodo.getColor()){
                result += " Rojo ";
            } else
            {
                result += " Negro ";
            }
        }
        return result;
    }

}
