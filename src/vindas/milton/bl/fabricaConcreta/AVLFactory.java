package vindas.milton.bl.fabricaConcreta;

import vindas.milton.bl.fabricaAbstracta.ArbolFactory;
import vindas.milton.bl.productoAbstracto.Arbol;
import vindas.milton.bl.productoConcreto.ArbolAVL;

public class AVLFactory implements ArbolFactory {
    @Override
    public Arbol crearArbol(int pClaves) {
        return new ArbolAVL(pClaves);
    }
}
