package vindas.milton.dl;

import vindas.milton.bl.fabricaAbstracta.ArbolFactory;
import vindas.milton.bl.fabricaConcreta.AVLFactory;
import vindas.milton.bl.fabricaConcreta.BFactory;
import vindas.milton.bl.fabricaConcreta.BPlusFactory;
import vindas.milton.bl.fabricaConcreta.RojoNegroFactory;
import vindas.milton.bl.productoAbstracto.Arbol;


public class Data {
    private Arbol selectedTree;

    public Data() {
    }

    public void crearArbol(int opcion, int pClaves) {

        ArbolFactory fArbol;

        switch (opcion) {
            case 1 :
                fArbol = new AVLFactory();
                selectedTree = fArbol.crearArbol(pClaves);
                break;
            case 2 :
                fArbol = new BFactory();
                selectedTree = fArbol.crearArbol(pClaves);
                break;
            case 3 :
                fArbol = new BPlusFactory();
                selectedTree = fArbol.crearArbol(pClaves);
                break;
            case 4 :
                fArbol = new RojoNegroFactory();
                selectedTree = fArbol.crearArbol(pClaves);
                break;
        }

    }

    public void agregarAArbol(int pClave) {
        selectedTree.insertar(pClave);
    }

    public String mostrarArbol(int pOrden) {
        return selectedTree.despliegue(pOrden);
    }


}