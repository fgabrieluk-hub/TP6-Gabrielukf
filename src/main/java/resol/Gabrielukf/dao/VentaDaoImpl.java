package resol.Gabrielukf.dao;

import resol.Gabrielukf.excepciones.ReglaNegocioException;
import resol.Gabrielukf.excepciones.VentaInvalidaException;
import resol.Gabrielukf.excepciones.VideojuegoNoEncontradoException;
import resol.Gabrielukf.modelo.ConexionBD;
import resol.Gabrielukf.modelo.Venta;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VentaDaoImpl implements VentaDao {

    private static final String SELECT_VENTAS =
            "SELECT v.id, v.fecha, v.id_videojuego, g.nombre, v.cantidad, v.descuento, v.total "
          + "FROM ventas v JOIN videojuegos g ON g.id = v.id_videojuego ";

    @Override
    public List<Venta> listarVentas() throws SQLException {
        return consultar(SELECT_VENTAS + "ORDER BY v.id", null);
    }

    @Override
    public Venta obtenerPorId(long id) throws SQLException, VentaInvalidaException {
        List<Venta> r = consultar(SELECT_VENTAS + "WHERE v.id = ?", ps -> ps.setLong(1, id));
        if (r.isEmpty()) {
            throw new VentaInvalidaException("No existe una venta con ID " + id);
        }
        return r.get(0);
    }

    @Override
    public List<Venta> listarPorVideojuego(long idVideojuego)
            throws SQLException, VideojuegoNoEncontradoException {
        try (Connection con = ConexionBD.obtener();
             PreparedStatement ps = con.prepareStatement("SELECT id FROM videojuegos WHERE id = ?")) {
            ps.setLong(1, idVideojuego);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new VideojuegoNoEncontradoException(idVideojuego);
                }
            }
        }
        return consultar(SELECT_VENTAS + "WHERE v.id_videojuego = ? ORDER BY v.id",
                ps -> ps.setLong(1, idVideojuego));
    }

    @Override
    public List<Venta> listarVentasDelMes() throws SQLException {
        LocalDate inicio = LocalDate.now().withDayOfMonth(1);
        LocalDate fin = inicio.plusMonths(1);
        return consultar(SELECT_VENTAS + "WHERE v.fecha >= ? AND v.fecha < ? ORDER BY v.fecha, v.id",
                ps -> {
                    ps.setDate(1, Date.valueOf(inicio));
                    ps.setDate(2, Date.valueOf(fin));
                });
    }

    @Override
    public Venta registrarVenta(Venta venta) throws SQLException, VentaInvalidaException,
            VideojuegoNoEncontradoException, ReglaNegocioException {

        if (venta.getCantidad() <= 0) {
            throw new VentaInvalidaException("La cantidad vendida debe ser mayor que cero.");
        }
        if (venta.getFecha() == null || venta.getFecha().isAfter(LocalDate.now())) {
            throw new VentaInvalidaException("La fecha de la venta no puede ser futura.");
        }

        try (Connection con = ConexionBD.obtener()) {
            con.setAutoCommit(false);
            try {
                String nombre;
                double precio;
                int stock;
                int suspendido;

                try (PreparedStatement ps = con.prepareStatement(
                        "SELECT nombre, precio, unidades_disponibles, suspendido "
                      + "FROM videojuegos WHERE id = ? FOR UPDATE")) {
                    ps.setLong(1, venta.getIdVideojuego());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new VideojuegoNoEncontradoException(venta.getIdVideojuego());
                        }
                        nombre = rs.getString("nombre");
                        precio = rs.getDouble("precio");
                        stock = rs.getInt("unidades_disponibles");
                        suspendido = rs.getInt("suspendido");
                    }
                }

                if (suspendido == 0) {
                    throw new ReglaNegocioException("El videojuego '" + nombre
                            + "' está suspendido y no está disponible para la venta.");
                }
                if (stock < venta.getCantidad()) {
                    throw new ReglaNegocioException("Stock insuficiente: hay " + stock
                            + " unidad(es) y se solicitaron " + venta.getCantidad() + ".");
                }

                int descuento = calcularDescuento(venta.getCantidad());
                double total = calcularTotal(precio, venta.getCantidad(), descuento);

                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO ventas (fecha, id_videojuego, cantidad, descuento, total) "
                      + "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                    ps.setDate(1, Date.valueOf(venta.getFecha()));
                    ps.setLong(2, venta.getIdVideojuego());
                    ps.setInt(3, venta.getCantidad());
                    ps.setInt(4, descuento);
                    ps.setDouble(5, total);
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        keys.next();
                        venta.setId(keys.getLong(1));
                    }
                }

                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE videojuegos SET unidades_disponibles = unidades_disponibles - ? WHERE id = ?")) {
                    ps.setInt(1, venta.getCantidad());
                    ps.setLong(2, venta.getIdVideojuego());
                    ps.executeUpdate();
                }

                con.commit();
                venta.setNombreVideojuego(nombre);
                venta.setDescuento(descuento);
                venta.setTotal(total);
                return venta;

            } catch (SQLException | VideojuegoNoEncontradoException | ReglaNegocioException e) {
                con.rollback();
                throw e;
            }
        }
    }

    // ---------- Reglas de negocio ----------

    /** 0–1 → 0%, 2–4 → 5%, 5–9 → 10%, 10+ → 15%. */
    public static int calcularDescuento(int cantidad) {
        if (cantidad >= 10) return 15;
        if (cantidad >= 5) return 10;
        if (cantidad >= 2) return 5;
        return 0;
    }

    public static double calcularTotal(double precio, int cantidad, int descuento) {
        double importe = precio * cantidad;
        return importe - (importe * descuento / 100.0);
    }

    // ---------- Auxiliares ----------

    @FunctionalInterface
    private interface Parametros {
        void asignar(PreparedStatement ps) throws SQLException;
    }

    private List<Venta> consultar(String sql, Parametros p) throws SQLException {
        List<Venta> lista = new ArrayList<>();
        try (Connection con = ConexionBD.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if (p != null) {
                p.asignar(ps);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    private Venta mapear(ResultSet rs) throws SQLException {
        return new Venta(
                rs.getLong("id"),
                rs.getDate("fecha").toLocalDate(),
                rs.getLong("id_videojuego"),
                rs.getString("nombre"),
                rs.getInt("cantidad"),
                rs.getInt("descuento"),
                rs.getDouble("total"));
    }
}
