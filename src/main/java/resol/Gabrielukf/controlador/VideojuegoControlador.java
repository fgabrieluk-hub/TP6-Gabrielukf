package resol.Gabrielukf.controlador;

import resol.Gabrielukf.dao.VideojuegoDao;
import resol.Gabrielukf.dao.VideojuegoDaoImpl;
import resol.Gabrielukf.excepciones.ReglaNegocioException;
import resol.Gabrielukf.excepciones.VideojuegoNoEncontradoException;
import resol.Gabrielukf.mapper.VideojuegoMapper;
import resol.Gabrielukf.modelo.Videojuego;
import resol.Gabrielukf.vista.MenuVista;
import resol.Gabrielukf.vista.VideojuegoVista;

import java.sql.SQLException;

public class VideojuegoControlador {
    // Se depende de la interfaz, no de la implementación concreta
    private final VideojuegoDao videojuegoDao = new VideojuegoDaoImpl();
    private final VideojuegoVista vista = new VideojuegoVista();
    private final MenuVista menu = new MenuVista();

    public void gestionar() {
        int opcion;
        do {
            opcion = menu.mostrarMenuVideojuegos();
            try {
                switch (opcion) {
                    case 1 -> vista.mostrarVideojuegos(
                            VideojuegoMapper.toDtoList(videojuegoDao.listarVideojuegos()));
                    case 2 -> vista.mostrarVideojuego(
                            VideojuegoMapper.toDto(videojuegoDao.obtenerPorId(vista.pedirId())));
                    case 3 -> {
                        Videojuego v = VideojuegoMapper.toEntity(vista.pedirDatos());
                        videojuegoDao.agregarVideojuego(v);
                        vista.mostrarMensaje("Videojuego agregado con ID " + v.getId());
                    }
                    case 4 -> {
                        long id = vista.pedirId();
                        Videojuego actual = videojuegoDao.obtenerPorId(id);
                        vista.mostrarMensaje("Datos actuales:");
                        vista.mostrarVideojuego(VideojuegoMapper.toDto(actual));
                        Videojuego nuevo = VideojuegoMapper.toEntity(vista.pedirDatos());
                        nuevo.setId(id);
                        if (!videojuegoDao.actualizarVideojuego(nuevo)) {
                            throw new VideojuegoNoEncontradoException(id);
                        }
                        vista.mostrarMensaje("Videojuego actualizado.");
                    }
                    case 5 -> {
                        long id = vista.pedirId();
                        if (!videojuegoDao.eliminarVideojuego(id)) {
                            throw new VideojuegoNoEncontradoException(id);
                        }
                        vista.mostrarMensaje("Videojuego eliminado.");
                    }
                    case 6 -> vista.mostrarVideojuegos(
                            VideojuegoMapper.toDtoList(videojuegoDao.listarQueNecesitanReposicion()));
                    case 7 -> vista.mostrarVideojuegos(
                            VideojuegoMapper.toDtoList(videojuegoDao.listarDisponibles()));
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
