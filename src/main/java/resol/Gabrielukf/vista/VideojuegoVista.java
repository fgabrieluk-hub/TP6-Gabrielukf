package resol.Gabrielukf.vista;

import resol.Gabrielukf.modelo.Videojuego;

import java.util.List;

public class VideojuegoVista {

    public int pedirId() {
        return Consola.leerEntero("ID del videojuego: ");
    }

    /** Pide los datos y devuelve un Videojuego sin ID (sin validar: eso lo hace el modelo). */
    public Videojuego pedirDatos() {
        Videojuego v = new Videojuego();
        v.setNombre(Consola.leerTexto("Nombre: "));
        v.setGenero(Consola.leerTexto("Género: "));
        v.setPrecio(Consola.leerDecimal("Precio: "));
        v.setUnidadesDisponibles(Consola.leerEntero("Unidades disponibles: "));
        v.setNivelReposicion(Consola.leerEntero("Nivel de reposición: "));
        v.setSuspendido(Consola.leerEntero("¿Disponible para la venta? (1 = sí, 0 = no): "));
        return v;
    }

    public void mostrarVideojuego(Videojuego v) {
        System.out.printf("ID: %d | %s | %s | $%.2f | Stock: %d | Reposición: %d | %s%n",
                v.getId(), v.getNombre(), v.getGenero(), v.getPrecio(),
                v.getUnidadesDisponibles(), v.getNivelReposicion(),
                v.getSuspendido() == 1 ? "Disponible" : "Suspendido");
    }

    public void mostrarVideojuegos(List<Videojuego> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay videojuegos para mostrar.");
            return;
        }
        for (Videojuego v : lista) {
            mostrarVideojuego(v);
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarError(String mensaje) {
        System.out.println("[ERROR] " + mensaje);
    }
}
