package vindas.milton.bl.fabricaAbstracta;

import vindas.milton.bl.productoAbstracto.Arbol;

public interface ArbolFactory {
    Arbol crearArbol(int pClaves);
}
