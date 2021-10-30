package vindas.milton.ui;

public class TipoArbol extends ApplicationUI {

    @Override
    public void mostrarMenu() {
        out.println("╔═════════════════════════════╗");
        out.println("       Tipos de Árboles");
        out.println("╚═════════════════════════════╝");
        out.println("1. Árbol-AVL");
        out.println("2. Árbol-B");
        out.println("3. Árbol-B+");
        out.println("4. Árbol Rojo-Negro.");
        out.println("═══════════════════════════════");
        out.println("Digite su tipo de árbol: ");
    }
}