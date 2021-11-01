package vindas.milton.bl.productoConcreto;

import vindas.milton.bl.nodos.NodoB;
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



    @Override
    public String despliegue(int pOrden) {
        return despliegue(raiz);
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
                Integer[] mitadClaves = dividirClavesHoja(nodoH, puntoMedio);

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
     * Método que define si el árbol está vacío o no
     * @return El booleano con el resultado
     */
    private boolean esVacio() {
        return primeraHoja == null;
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

    /**
     * Método que divide las claves en una hoja que alcanza su grado máximo
     * @param pNodoH El nodo hoja al que se le va a dividir sus llaves
     * @param pPosMedia El posición media en la que se va a realizar la división
     * @return El arreglo de claves dividido en dos
     */
    private Integer[] dividirClavesHoja(NodoBPlusHoja pNodoH, int pPosMedia) {

        Integer[] claves = pNodoH.getClaves();

        Integer[] mitadClaves = new Integer[this.m];

        for (int i = pPosMedia; i < claves.length; i++) {
            mitadClaves[i - pPosMedia] = claves[i];
            pNodoH.borrarClave(i);
        }

        return mitadClaves;
    }

    /**
     * Método que divide las claves de un nodo interno
     * @param pClaves Las claves actuales del nodo interno
     * @param pPosMedia La posición de división de claves
     * @return Retorna la mitad del arreglo de claves
     */
    private Integer[] dividirClavesIntr(Integer[] pClaves, int pPosMedia) {

        Integer[] mitadClaves = new Integer[this.m];

        pClaves[pPosMedia] = null;

        for (int i = pPosMedia + 1; i < pClaves.length; i++) {
            mitadClaves[i - pPosMedia - 1] = pClaves[i];
            pClaves[i] = null;
        }

        return mitadClaves;
    }

    /**
     * Método que divide los hijos de un nodo interno
     * @param pNodoBPlusIntr El nodo interno a dividir
     * @param pPosMedia La posición en la que se realiza la división
     * @return Un arreglo de nodos que contiene la mitad de la división
     */
    private NodoBPlus[] dividirHijos(NodoBPlusIntr pNodoBPlusIntr, int pPosMedia) {

        NodoBPlus[] hijos = pNodoBPlusIntr.getHijos();
        NodoBPlus[] mitadHijos = new NodoBPlus[this.m + 1];

        for (int i = pPosMedia + 1; i < hijos.length; i++) {
            mitadHijos[i - pPosMedia - 1] = hijos[i];
            pNodoBPlusIntr.removerHijo(i);
        }

        return mitadHijos;
    }

    /**
     * Método general que divide un nodo interno cuando se alcanza el máximo grado
     * @param pNodoIntr El nodo interno con grado máximo
     */
    private void dividirNodoInterno(NodoBPlusIntr pNodoIntr) {

        NodoBPlusIntr padre = pNodoIntr.getPadre();

        int posMedia = getPuntoMedio();
        int nuevaClavePadre = pNodoIntr.getClaves()[posMedia];
        Integer[] mitadClaves = dividirClavesIntr(pNodoIntr.getClaves(), posMedia);
        NodoBPlus[] mitadHijos = dividirHijos(pNodoIntr, posMedia);

        pNodoIntr.setNumClaves(calcularGradoporHijos(pNodoIntr.getHijos()));

        // Se definen padre y hermanos del nuevo nodo creado
        NodoBPlusIntr hermano = new NodoBPlusIntr(this.m, mitadClaves, mitadHijos);
        for (NodoBPlus hijo : mitadHijos) {
            if (hijo != null) {
                hijo.setPadre(hermano);
            }
        }

        hermano.setHermanoDer(pNodoIntr.getHermanoDer());
        if (hermano.getHermanoDer() != null) {
            hermano.getHermanoDer().setHermanoIzq(hermano);
        }

        pNodoIntr.setHermanoDer(hermano);
        hermano.setHermanoIzq(pNodoIntr);

        //Se crea nueva raíz y se agregan hijos
        if (padre == null) {

            Integer[] claves = new Integer[this.m];
            claves[0] = nuevaClavePadre;
            NodoBPlusIntr nuevaRaiz = new NodoBPlusIntr(this.m, claves);
            nuevaRaiz.agregarHijo(pNodoIntr);
            nuevaRaiz.agregarHijo(hermano);
            this.raiz = nuevaRaiz;

            pNodoIntr.setPadre(nuevaRaiz);
            hermano.setPadre(nuevaRaiz);

        } else {

            padre.getClaves()[padre.getNumClaves() - 1] = nuevaClavePadre;
            Arrays.sort(padre.getClaves(), 0, padre.getNumClaves());

            int posHijo = padre.encontrarPosHijo(pNodoIntr) + 1;
            padre.agregarHijoconPos(hermano, posHijo);
            hermano.setPadre(padre);
        }
    }

    /**
     * Método que calcula el grado de un nodo según los hijos que tiene
     * @param hijos Los hijos del nodo
     * @return Devuelve el entero con el número de claves actual del nodo.
     */
    private int calcularGradoporHijos(NodoBPlus[] hijos) {
        for (int i = 0; i < hijos.length; i++) {
            if (hijos[i] == null) {
                return i;
            }
        }
        return -1;
    }


    private String despliegue(NodoBPlus actual) {
        String result = "";
        NodoBPlusHoja nodoImpresionHoja;

        if (actual == null && primeraHoja == null) {
            result = result + "No se han ingresado claves en el árbol B+";
        }

        if (actual == null) {
            nodoImpresionHoja = primeraHoja;
            result = result + "Hojas: ";
            while (nodoImpresionHoja != null) {
                for (int i = 0; i < nodoImpresionHoja.getNumClaves(); i++) {
                    result = result + nodoImpresionHoja.getClaves()[i] + " " ;
                }
                result = result + "    ";
                nodoImpresionHoja = nodoImpresionHoja.getHermanoDer();
            }
        }
        else {
            int contInternos = 1;
            result = result + "Raíz:  ";
            NodoBPlusIntr raiz = (NodoBPlusIntr) actual;

            for (int i = 0; i < raiz.getNumClaves(); i++) {
                if (raiz.getClaves()[i] != null) {
                    result = result + raiz.getClaves()[i] + " " ;
                }
                else {
                    break;
                }
            }

            if (raiz.getHijos()[0].isHoja()) {
                result = result + "\n";
                nodoImpresionHoja = primeraHoja;
                result = result + "Hojas: ";
                while (nodoImpresionHoja != null) {
                    for (int i = 0; i < nodoImpresionHoja.getNumClaves(); i++) {
                        if (nodoImpresionHoja.getClaves()[i] != null) {
                            result = result + nodoImpresionHoja.getClaves()[i] + " ";
                        }
                    }
                    result = result + "    ";
                    nodoImpresionHoja = nodoImpresionHoja.getHermanoDer();
                }

            }
            else {
                NodoBPlusIntr nodoInicialIntr = (NodoBPlusIntr) raiz.getHijos()[0];
                boolean otroNodoInterno;
                do {
                    NodoBPlusIntr nodoImpresionIntr = nodoInicialIntr;
                    result = result + "\n";
                    result = result + "Intr" + contInternos + ": ";
                    while (nodoImpresionIntr != null) {
                        for (int i = 0; i < nodoImpresionIntr.getNumClaves(); i++) {
                            if (nodoImpresionIntr.getClaves()[i] != null) {
                                result = result + nodoImpresionIntr.getClaves()[i] + " ";
                            }
                            else {
                                break;
                            }
                        }
                        result = result + "    ";
                        nodoImpresionIntr = nodoImpresionIntr.getHermanoDer();
                    }
                    contInternos++;
                    if(!nodoInicialIntr.getHijos()[0].isHoja()) {
                        nodoInicialIntr = (NodoBPlusIntr) nodoInicialIntr.getHijos()[0];
                        otroNodoInterno = true;
                    }
                    else {
                        otroNodoInterno = false;
                    }

                }
                while (otroNodoInterno);

                result =  result + "\n";
                nodoImpresionHoja = primeraHoja;
                result += "Hojas: ";
                while (nodoImpresionHoja != null) {
                    for (int i = 0; i < nodoImpresionHoja.getNumClaves(); i++) {
                        result = result + nodoImpresionHoja.getClaves()[i] + " " ;
                    }
                    result = result + "    ";
                    nodoImpresionHoja = nodoImpresionHoja.getHermanoDer();
                }
            }


        }
        return result;
    }



}
