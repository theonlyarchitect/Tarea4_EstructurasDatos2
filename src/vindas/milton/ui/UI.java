package vindas.milton.ui;


public class UI extends ApplicationUI {

    @Override
    public void mostrarMenu() {
        out.println("╔═════════════════════════════╗");
        out.println("       Menú de opciones");
        out.println("╚═════════════════════════════╝");
        out.println("1. Insertar número en árbol.");
        out.println("2. Desplegar.");
        out.println("3. Salir.");
        out.println("═══════════════════════════════");
        out.println("Digite la opción: ");
    }

    public void mostrarGrado() {

        out.println("Ingrese el número máximo de claves (grado máximo) del árbol: ");
    }
}

