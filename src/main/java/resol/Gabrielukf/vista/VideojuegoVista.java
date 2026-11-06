package resol.Gabrielukf.vista;

import resol.Gabrielukf.dto.VideojuegoDto;
import resol.Gabrielukf.dto.VideojuegoFormDto;

import java.util.List;

/** Trabaja exclusivamente con DTO. */
public class VideojuegoVista {

    public int pedirId() {
        return Consola.leerEntero("ID del videojuego: ");
    }

    /** Pide los datos por consola y los devuelve en un DTO (la validación la hace el DAO). */
    public VideojuegoFormDto pedirDatos() {
        VideojuegoFormDto f = new VideojuegoFormDto();
        f.setNombre(Consola.leerTexto("Nombre: "));
        f.setGenero(Consola.leerTexto("Género: "));
        f.setPrecio(Consola.leerDecimal("Precio: "));
        f.setUnidadesDisponibles(Consola.leerEntero("Unidades disponibles: "));
        f.setNivelReposicion(Consola.leerEntero("Nivel de reposición: "));
        f.setSuspendido(Consola.leerEntero("¿Disponible para la venta? (1 = sí, 0 = no): "));
        return f;
    }

    public void mostrarVideojuego(VideojuegoDto v) {
        System.out.printf("ID: %d | %s | $%.2f | Necesita reposición: %s%n",
                v.getId(), v.getNombre(), v.getPrecio(),
                v.isNecesitaReposicion() ? "Sí" : "No");
    }

    public void mostrarVideojuegos(List<VideojuegoDto> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay videojuegos para mostrar.");
            return;
        }
        for (VideojuegoDto v : lista) {
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
