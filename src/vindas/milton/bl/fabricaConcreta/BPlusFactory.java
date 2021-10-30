package vindas.milton.bl.fabricaConcreta;

import vindas.milton.bl.fabricaAbstracta.ArbolFactory;
import vindas.milton.bl.productoAbstracto.Arbol;
import vindas.milton.bl.productoConcreto.ArbolBPlus;

public class BPlusFactory implements ArbolFactory {
    @Override
    public Arbol crearArbol(int pClaves) {
        return new ArbolBPlus(pClaves);
    }
}
