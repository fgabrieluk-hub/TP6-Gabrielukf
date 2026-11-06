package resol.Gabrielukf.dao;

import java.sql.SQLException;
import java.util.List;

import resol.Gabrielukf.excepciones.ReglaNegocioException;
import resol.Gabrielukf.excepciones.VentaInvalidaException;
import resol.Gabrielukf.excepciones.VideojuegoNoEncontradoException;
import resol.Gabrielukf.modelo.Venta;

public interface VentaDao {

    List<Venta> listarVentas() throws SQLException;

    Venta obtenerPorId(long id) throws SQLException, VentaInvalidaException;

    /**
     * Valida la venta, verifica stock, calcula descuento por volumen y total,
     * inserta la venta y descuenta las unidades del videojuego (en una transacción).
     * Devuelve la venta completa (id, descuento y total ya calculados).
     */
    Venta registrarVenta(Venta venta) throws SQLException, VentaInvalidaException,
            VideojuegoNoEncontradoException, ReglaNegocioException;

    List<Venta> listarPorVideojuego(long idVideojuego) throws SQLException, VideojuegoNoEncontradoException;

    List<Venta> listarVentasDelMes() throws SQLException;
}
