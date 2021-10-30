package vindas.milton.ui;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

public abstract class ApplicationUI {
    protected BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    protected PrintStream out = System.out;


    public void imprimirMensaje(String mensaje) {
        out.println(mensaje);
    }

    public String leerTexto() throws IOException {
        return in.readLine();
    }

    public int leerOpcion() throws IOException {
        return Integer.parseInt(in.readLine());
    }

    public abstract void mostrarMenu();

}

