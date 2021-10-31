package vindas.milton.bl.productoConcreto;

import vindas.milton.bl.nodos.nodoBPlus.NodoBPlus;
import vindas.milton.bl.nodos.nodoBPlus.NodoBPlusHoja;
import vindas.milton.bl.nodos.nodoBPlus.NodoBPlusIntr;
import vindas.milton.bl.productoAbstracto.Arbol;

import java.util.Arrays;

public class ArbolBPlus implements Arbol {

    /**
     * Atributo que almacena el número de claves máxima del árbol por nodo
     */
    int m;

    /**
     * Atributo que designa el nodo raíz interno del árbol
     */
    NodoBPlusIntr raiz;

    /**
     * La primera hoja del árbol
     */
    NodoBPlusHoja primeraHoja;

    /**
     * Constructor default del árbol B+.
     * @param pGradoMax El grado máximo o número máximo de claves por nodo definido por el usuario
     */
    public ArbolBPlus(int pGradoMax) {
        this.m = pGradoMax;
        this.raiz = null;
    }

    /**
     * Método que define si el árbol está vacío o no
     * @return El booleano con el resultado
     */
    private boolean esVacio() {
        return primeraHoja == null;
    }

    @Override
    public String despliegue(int pOrden) {
        return null;
    }

    @Override
    public void insertar(int pClave) {
        if (esVacio()) { // Si el árbol está vacío, creamos nodo con clave y se define como primera hoja.

            this.primeraHoja = new NodoBPlusHoja(this.m, pClave);

        } else { // Si tiene elementos, se busca la hoja para agregar clave

            // Si aún no existe raíz, se define la primera hoja como la apropiada para agregar clave; si no es así se encuentra el nodo hoja correcto.
            NodoBPlusHoja nodoH = (this.raiz == null) ? this.primeraHoja : encontrarNodoHoja(pClave);

            // Si el nodo hoja seleccionado está lleno (devuelve false) y hay que realizar pasos extra; si no se agrega la clave normalmente
            if (!nodoH.agregarClave(pClave)) {

                // Paso 1: Se inicia con el split del nodo hoja
                nodoH.agregarClaveEnPos(pClave, nodoH.getNumClaves());

                // Paso 2: Se establece punto de división y se dividen claves
                int puntoMedio = getPuntoMedio();
                Integer[] mitadClaves = dividirClaves(nodoH, puntoMedio);

                // Paso 3. Resolver relaciones padre-hijos
                // A. Si el nodo hoja no tiene padre...
                if (nodoH.getPadre() == null) {

                    // Se crea el padre con sus claves
                    Integer[] clavesPadre = new Integer[this.m];

                    //IMPORTANTE: se coloca la menor clave de la mitad generada como la primera clave del padre. CLÁSICO en árboles B+!
                    clavesPadre[0] = mitadClaves[0];
                    NodoBPlusIntr padre = new NodoBPlusIntr(this.m, clavesPadre);

                    // Se establece el padre del nodo hoja y viceversa
                    nodoH.setPadre(padre);
                    padre.agregarHijo(nodoH);

                }

                // B. Si ya existe un padre...
                else {
                    // Se agrega la clave intermedia al padre existente
                    int nuevaClavePadre = mitadClaves[0];
                    nodoH.getPadre().getClaves()[nodoH.getPadre().getNumClaves() - 1] = nuevaClavePadre;
                    Arrays.sort(nodoH.getPadre().getClaves(), 0, nodoH.getPadre().getNumClaves());
                }

                // Paso 4: Se crea el nuevo nodo hoja para almacenar la mitad de claves creada anteriormente
                NodoBPlusHoja nuevoNodoHoja = new NodoBPlusHoja(this.m, mitadClaves, nodoH.getPadre());

                int posHijo = nodoH.getPadre().encontrarPosHijo(nodoH) + 1;
                nodoH.getPadre().agregarHijoconPos(nuevoNodoHoja, posHijo);

                // Paso 5: Se definen las hojas hermanas después de agregar nueva hoja
                nuevoNodoHoja.setHermanoDer(nodoH.getHermanoDer());
                if (nuevoNodoHoja.getHermanoDer() != null) {
                    nuevoNodoHoja.getHermanoDer().setHermanoIzq(nuevoNodoHoja);
                }
                nodoH.setHermanoDer(nuevoNodoHoja);
                nuevoNodoHoja.setHermanoIzq(nodoH);

                // Paso 6: Se actualiza la raíz y otros nodos internos
                if (this.raiz == null) {

                    this.raiz = nodoH.getPadre();

                } else {
                    NodoBPlusIntr nodoInterno = nodoH.getPadre();
                    while (nodoInterno != null) {
                        if (nodoInterno.estaSobrecargado()) {
                            dividirNodoInterno(nodoInterno);
                        }
                        else {
                            break;
                        }
                        nodoInterno = nodoInterno.getPadre();
                    }
                }
            }
        }
    }

    /**
     * Método que encuentra el nodo hoja que contiene una clave
     * @param pClave El entero que representa la clave a buscar
     * @return Retorna el nodo hoja que contiene la clave
     */
    private NodoBPlusHoja encontrarNodoHoja(int pClave) {

        Integer[] claves = this.raiz.getClaves();
        int i;

        for (i = 0; i < this.raiz.getNumClaves() - 1; i++) {
            if (pClave < claves[i]) {
                break;
            }
        }

        NodoBPlus nodoHijo = this.raiz.getHijos()[i];
        if (nodoHijo instanceof NodoBPlusHoja) {
            return (NodoBPlusHoja) nodoHijo;
        } else {
            return encontrarNodoHoja((NodoBPlusIntr) nodoHijo, pClave);
        }
    }

    /**
     * Método que devuelve el nodo hoja que contiene una clave
     * @param pNodo El nodo interno usado como parámetro para la búsqueda
     * @param pClave El entero que representa la clave a buscar
     * @return Retorna el nodo hoja que contiene la clave
     */
    private NodoBPlusHoja encontrarNodoHoja(NodoBPlusIntr pNodo, int pClave) {

        Integer[] claves = pNodo.getClaves();
        int i;

        for (i = 0; i < pNodo.getNumClaves() - 1; i++) {
            if (pClave < claves[i]) {
                break;
            }
        }
        NodoBPlus nodoHijo = pNodo.getHijos()[i];
        if (nodoHijo instanceof NodoBPlusHoja) {
            return (NodoBPlusHoja) nodoHijo;
        } else {
            return encontrarNodoHoja((NodoBPlusIntr) pNodo.getHijos()[i], pClave);
        }
    }

    /**
     * Método que retorna la posición del punto medio de un nodo del árbol para realizar la división
     * @return El entero con la posición media
     */
    private int getPuntoMedio() {
        return (int) Math.ceil((this.m + 1) / 2.0) - 1;
    }

    private Integer[] dividirClaves(NodoBPlusHoja pNodoH, int pPosMedia) {

        Integer[] claves = pNodoH.getClaves();

        Integer[] mitadClaves = new Integer[this.m];

        for (int i = pPosMedia; i < claves.length; i++) {
            mitadClaves[i - pPosMedia] = claves[i];
            pNodoH.borrarClave(i);
        }

        return mitadClaves;
    }

    private void dividirNodoInterno(NodoBPlusIntr pNodoIntr) {

        NodoBPlusIntr padre = pNodoIntr.getPadre();

        /*int midpoint = getMidpoint();
        int newParentKey = in.keys[midpoint];
        Integer[] halfKeys = splitKeys(in.keys, midpoint);
        Node[] halfPointers = splitChildPointers(in, midpoint);

        in.degree = linearNullSearch(in.childPointers);

        InternalNode sibling = new InternalNode(this.m, halfKeys, halfPointers);
        for (Node pointer : halfPointers) {
            if (pointer != null) {
                pointer.parent = sibling;
            }
        }

        sibling.rightSibling = in.rightSibling;
        if (sibling.rightSibling != null) {
            sibling.rightSibling.leftSibling = sibling;
        }
        in.rightSibling = sibling;
        sibling.leftSibling = in;

        if (parent == null) {

            Integer[] keys = new Integer[this.m];
            keys[0] = newParentKey;
            InternalNode newRoot = new InternalNode(this.m, keys);
            newRoot.appendChildPointer(in);
            newRoot.appendChildPointer(sibling);
            this.root = newRoot;

            in.parent = newRoot;
            sibling.parent = newRoot;

        } else {

            parent.keys[parent.degree - 1] = newParentKey;
            Arrays.sort(parent.keys, 0, parent.degree);

            int pointerIndex = parent.findIndexOfPointer(in) + 1;
            parent.insertChildPointer(sibling, pointerIndex);
            sibling.parent = parent;
        }*/
    }

}
