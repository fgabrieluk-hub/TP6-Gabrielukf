package resol.Gabrielukf.controlador;

import resol.Gabrielukf.excepciones.ReglaNegocioException;
import resol.Gabrielukf.excepciones.VideojuegoNoEncontradoException;
import resol.Gabrielukf.modelo.Videojuego;
import resol.Gabrielukf.modelo.VideojuegoModelo;
import resol.Gabrielukf.vista.MenuVista;
import resol.Gabrielukf.vista.VideojuegoVista;

import java.sql.SQLException;

public class VideojuegoControlador {
    private final VideojuegoModelo modelo = new VideojuegoModelo();
    private final VideojuegoVista vista = new VideojuegoVista();
    private final MenuVista menu = new MenuVista();

    public void gestionar() {
        int opcion;
        do {
            opcion = menu.mostrarMenuVideojuegos();
            try {
                switch (opcion) {
                    case 1 -> vista.mostrarVideojuegos(modelo.listar());
                    case 2 -> vista.mostrarVideojuego(modelo.buscarPorId(vista.pedirId()));
                    case 3 -> {
                        Videojuego v = vista.pedirDatos();
                        modelo.agregar(v);
                        vista.mostrarMensaje("Videojuego agregado con ID " + v.getId());
                    }
                    case 4 -> {
                        int id = vista.pedirId();
                        Videojuego actual = modelo.buscarPorId(id);
                        vista.mostrarMensaje("Datos actuales:");
                        vista.mostrarVideojuego(actual);
                        Videojuego nuevo = vista.pedirDatos();
                        nuevo.setId(id);
                        modelo.actualizar(nuevo);
                        vista.mostrarMensaje("Videojuego actualizado.");
                    }
                    case 5 -> {
                        modelo.eliminar(vista.pedirId());
                        vista.mostrarMensaje("Videojuego eliminado.");
                    }
                    case 6 -> vista.mostrarVideojuegos(modelo.listarNecesitanReposicion());
                    case 7 -> vista.mostrarVideojuegos(modelo.listarDisponibles());
                    case 0 -> { }
                    default -> vista.mostrarError("Opción inválida.");
                }
            } catch (VideojuegoNoEncontradoException | ReglaNegocioException e) {
                vista.mostrarError(e.getMessage());
            } catch (SQLException e) {
                vista.mostrarError("Error de base de datos: " + e.getMessage());
            }
        } while (opcion != 0);
    }
}
