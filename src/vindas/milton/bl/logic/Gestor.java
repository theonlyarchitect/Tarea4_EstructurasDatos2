package vindas.milton.bl.logic;

import vindas.milton.dl.Data;

public class Gestor {

    private final Data db;

    public Gestor() {
        this.db = new Data();
    }

    public void crearArbol(int pTipo, int pClaves) {
        db.crearArbol(pTipo, pClaves);
    }

    public String agregarAArbol(int pDato) {
        db.agregarAArbol(pDato);
        return "Se agregó el número " + pDato + " al árbol.";
    }

    public String mostrarArbol(int pOrden) {
        return db.mostrarArbol(pOrden);
    }

}
