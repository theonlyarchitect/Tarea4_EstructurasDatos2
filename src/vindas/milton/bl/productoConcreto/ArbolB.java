package vindas.milton.bl.productoConcreto;

import vindas.milton.bl.nodos.NodoB;
import vindas.milton.bl.productoAbstracto.Arbol;

public class ArbolB implements Arbol {
    /**
     * El nodo raíz del árbol
     */
    NodoB raiz;

    /**
     * El número máximo de claves que puede tener el nodo
     */
    int T;


    /**
     * Constructor por defecto de la clase
     */
    public ArbolB(int clavesMaximo) {
        this.raiz = new NodoB(clavesMaximo);
        this.T = clavesMaximo;
    }


    /*-------------------------------------------------------------------------------------
                                         Métodos Públicos
    --------------------------------------------------------------------------------------*/

    public void insertar(int pClave) {
        NodoB auxRaiz = raiz;
        if (auxRaiz.getN() == T) { // Si el nodo raíz está a su máxima capacidad...
            NodoB nuevaRaiz = new NodoB(T);
            raiz = nuevaRaiz;
            nuevaRaiz.setHoja(false);
            nuevaRaiz.setN(0);
            nuevaRaiz.setHijo(0, auxRaiz); // Paso 1. La raíz anterior es hija de la nueva raíz
            dividir(nuevaRaiz, 0, auxRaiz); // Paso 2. Ejecutar la división del nodo
            insertar(nuevaRaiz, pClave); // Paso 3. Insertar la nueva clave
        } else {
            insertar(auxRaiz, pClave);
        }
    }

    /**
     * Método público que retorna los elementos del árbol en una cadena de String
     * @return Retorna el String con el contenido del árbol
     */
    public String despliegue(int pOrden) {
        return "Raíz: " + despliegue(raiz);
    }


    /*-------------------------------------------------------------------------------------
                                         Métodos Privados
    --------------------------------------------------------------------------------------*/

    /**
     * Método que divide un nodo y asciende cuando el nodo alcanza el número máximo de llaves y se inserta
     * @param padre El nodo que funciona como padre durante la división
     * @param pos La posición en la que hay que realizar la división
     * @param lleno El nodo lleno al que hay que dividir
     */
    private void dividir(NodoB padre, int pos, NodoB lleno) {
        NodoB nuevo = new NodoB(T);
        int gradoMin = (T+1)/2; // Se obtiene el grado mínimo de nodo a partir del número máximo de llaves definido
        nuevo.setHoja(lleno.isHoja());
        nuevo.setN(gradoMin - 1);
        for (int j = 0; j < gradoMin - 1; j++) { // Se llena el nodo nuevo con los valores más altos del nodo lleno
            nuevo.setClave(j, lleno.getClave(j + gradoMin));
            lleno.setClave(j+gradoMin, 0);
        }
        if (!lleno.isHoja()) { // Si el nodo lleno no es hoja, se setea la mitad de los hijos del nodo lleno al nuevo
            for (int j = 0; j < gradoMin; j++) {
                nuevo.setHijo(j, lleno.getHijo(j + gradoMin));
            }
        }
        lleno.setN(gradoMin - 1); // Se actualiza el número actual de llaves en el nodo que estaba lleno después de la división
        for (int j = padre.getN(); j >= pos + 1; j--) { // Se reordenan los nodos hijos del padre antes de insertar nuevo nodo
            padre.setHijo(j + 1, padre.getHijo(j));
        }
        padre.setHijo(pos + 1, nuevo); // Se setea el nuevo nodo como hijo del nodo padre

        for (int j = padre.getN() - 1; j >= pos; j--) { // Se actualizan las claves del nodo padre antes de insertar nueva clave
            padre.setClave(j + 1, padre.getClave(j));
        }
        padre.setClave(pos, lleno.getClave(gradoMin - 1));
        lleno.setClave(gradoMin-1, 0);
        padre.setN(padre.getN() + 1);
    }



    /**
     * Método privado recursivo que administra la posición del nuevo nodo en el árbol
     * @param actual El nodo actual
     * @param pClave El entero por agregarse
     */
    private void insertar(NodoB actual, int pClave) {
        if (actual.isHoja()) {
            int i = 0;
            for (i = actual.getN() - 1; i >= 0 && pClave < actual.getClave(i); i--) {
                actual.setClave(i + 1, actual.getClave(i));
            }
            actual.setClave(i + 1, pClave);
            actual.setN(actual.getN() + 1);
        } else {
            int i = 0;
            for (i = actual.getN() - 1; i >= 0 && pClave < actual.getClave(i); i--) {
            }
            ;
            i++;
            NodoB tmp = actual.getHijo(i);
            if (tmp.getN() == T) {
                dividir(actual, i, tmp);
                if (pClave > actual.getClave(i)) {
                    i++;
                }
            }
            insertar(actual.getHijo(i), pClave);
        }

    }


     /*-------------------------------------------------------------------------------------
                            Métodos privados de despliegue
    --------------------------------------------------------------------------------------*/


    private String despliegue(NodoB actual) {
        String result = "";
        assert (actual == null);
        for (int i = 0; i < actual.getN(); i++) {
            result = result + actual.getClave(i) + " ";
        }
        result =  result + "\n";
        if (!actual.isHoja()) {

            for (int i = 0; i < actual.getN() + 1; i++) {
                if (actual.getHijo(i).getHijo(0) == null) {
                    result = result + "Hoja: " + despliegue(actual.getHijo(i));
                } else {
                    result = result + "Intr: " + despliegue(actual.getHijo(i));
                }
            }
        }
        return result;
    }
}
