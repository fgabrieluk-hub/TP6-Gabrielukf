package resol.Gabrielukf.dao;

import java.sql.SQLException;
import java.util.List;

import resol.Gabrielukf.excepciones.ReglaNegocioException;
import resol.Gabrielukf.excepciones.VideojuegoNoEncontradoException;
import resol.Gabrielukf.modelo.Videojuego;

public interface VideojuegoDao {

    List<Videojuego> listarVideojuegos() throws SQLException;

    /** Videojuegos no suspendidos (suspendido = 1). */
    List<Videojuego> listarDisponibles() throws SQLException;

    /** Videojuegos con unidadesDisponibles < nivelReposicion. */
    List<Videojuego> listarQueNecesitanReposicion() throws SQLException;

    Videojuego obtenerPorId(long id) throws SQLException, VideojuegoNoEncontradoException;

    /** Valida precio > 0 y unidadesDisponibles >= 0 antes de insertar. */
    Videojuego agregarVideojuego(Videojuego videojuego) throws SQLException, ReglaNegocioException;

    /** @return true si se actualizó algún registro, false si el id no existe. */
    boolean actualizarVideojuego(Videojuego videojuego) throws SQLException, ReglaNegocioException;

    /** @return true si se eliminó algún registro, false si el id no existe. */
    boolean eliminarVideojuego(long id) throws SQLException;
}
