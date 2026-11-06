package resol.Gabrielukf;

import resol.Gabrielukf.controlador.VentaControlador;
import resol.Gabrielukf.controlador.VideojuegoControlador;
import resol.Gabrielukf.vista.MenuVista;

public class Main {
    public static void main(String[] args) {
        MenuVista menu = new MenuVista();
        VideojuegoControlador videojuegos = new VideojuegoControlador();
        VentaControlador ventas = new VentaControlador();

        int opcion;
        do {
            opcion = menu.mostrarMenuPrincipal();
            switch (opcion) {
                case 1 -> videojuegos.gestionar();
                case 2 -> ventas.gestionar();
                case 0 -> menu.mostrarMensaje("Hasta luego.");
                default -> menu.mostrarMensaje("Opción inválida.");
            }
        } while (opcion != 0);
    }
}
