package vindas.milton.tl;

import vindas.milton.bl.logic.Gestor;
import vindas.milton.ui.TipoArbol;
import vindas.milton.ui.UI;

import java.io.IOException;

public class Controller {
    private UI interfaz;
    private TipoArbol menuArbol;
    private Gestor localGestor;

    public Controller() {
        interfaz = new UI();
        menuArbol = new TipoArbol();
    }

    public void iniciar() throws Exception {

        int opcion;
        int tipoArbol;
        int maxClaves = 0;

        localGestor = new Gestor();

        menuArbol.imprimirMensaje("*******************************************************************************");
        menuArbol.imprimirMensaje("*****     Bienvenido al programa de Estructuras de Datos 2 - Tarea 4     ******");
        menuArbol.imprimirMensaje("*******************************************************************************");

        do {
            menuArbol.mostrarMenu();
            tipoArbol = menuArbol.leerOpcion();


            if (tipoArbol<1 || tipoArbol>3) {
                menuArbol.imprimirMensaje("Opción incorrecta. Ingrese un tipo de árbol de la lista");
            }
            else {
                if (tipoArbol == 2) {
                    interfaz.mostrarGrado();
                    maxClaves = interfaz.leerOpcion();
                }
                localGestor.crearArbol(tipoArbol, maxClaves);
            }

        }
        while (tipoArbol<1 || tipoArbol>3);

        do {
            interfaz.mostrarMenu();
            opcion = interfaz.leerOpcion();
            procesarOpcion(opcion, tipoArbol);
        }
        while (opcion != 3);
    }

    public void procesarOpcion(int pOpcion, int pTipoArbol) throws Exception {
        switch (pOpcion) {
            case 1:
                agregarNumero();
                break;
            case 2:
                mostrarArbol(pTipoArbol);
                break;
            case 3:
                interfaz.imprimirMensaje("Usted ha seleccionado salir del sistema.");
                break;
            default:
                interfaz.imprimirMensaje("Opción incorrecta.");
                break;
        }
        interfaz.imprimirMensaje("Presione enter para continuar...");
        interfaz.leerTexto();
    }

    public void agregarNumero() throws IOException {
        int num;
        interfaz.imprimirMensaje("Digite el número que desea agregar al árbol: ");
        num = interfaz.leerOpcion();
        interfaz.imprimirMensaje(localGestor.agregarAArbol(num));
    }

    public void mostrarArbol(int pTipoArbol) throws IOException {
        int orden;
        if (pTipoArbol == 1) {
            interfaz.imprimirMensaje("Digite el tipo de orden para desplegar el árbol: 1. Preorden, 2. Inorden, 3. Postorden ");
            orden = interfaz.leerOpcion();
        }
        else {
            orden = 0;
        }
        interfaz.imprimirMensaje(localGestor.mostrarArbol(orden));
    }
}
