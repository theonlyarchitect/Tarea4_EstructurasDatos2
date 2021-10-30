package vindas.milton.bl.fabricaConcreta;

import vindas.milton.bl.fabricaAbstracta.ArbolFactory;
import vindas.milton.bl.productoAbstracto.Arbol;
import vindas.milton.bl.productoConcreto.ArbolRojoNegro;

public class RojoNegroFactory implements ArbolFactory {
    @Override
    public Arbol crearArbol(int pClaves) {
        return new ArbolRojoNegro(pClaves);
    }
}
