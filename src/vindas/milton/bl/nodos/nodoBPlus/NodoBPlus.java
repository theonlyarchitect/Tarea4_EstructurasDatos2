package vindas.milton.bl.nodos.nodoBPlus;

public class NodoBPlus {
    NodoBPlusIntr padre;
    boolean isHoja;

    public NodoBPlusIntr getPadre() {
        return padre;
    }

    public void setPadre(NodoBPlusIntr padre) {
        this.padre = padre;
    }

    public boolean isHoja() {
        return isHoja;
    }

    public void setHoja(boolean hoja) {
        isHoja = hoja;
    }
}
