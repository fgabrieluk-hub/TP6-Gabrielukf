package resol.Gabrielukf.dao;

import resol.Gabrielukf.excepciones.ReglaNegocioException;
import resol.Gabrielukf.excepciones.VideojuegoNoEncontradoException;
import resol.Gabrielukf.modelo.ConexionBD;
import resol.Gabrielukf.modelo.Videojuego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VideojuegoDaoImpl implements VideojuegoDao {

    private static final String COLUMNAS =
            "id, nombre, genero, precio, unidades_disponibles, nivel_reposicion, suspendido";

    @Override
    public List<Videojuego> listarVideojuegos() throws SQLException {
        return consultar("SELECT " + COLUMNAS + " FROM videojuegos ORDER BY id");
    }

    @Override
    public List<Videojuego> listarDisponibles() throws SQLException {
        return consultar("SELECT " + COLUMNAS + " FROM videojuegos WHERE suspendido = 1 ORDER BY id");
    }

    @Override
    public List<Videojuego> listarQueNecesitanReposicion() throws SQLException {
        return consultar("SELECT " + COLUMNAS + " FROM videojuegos "
                + "WHERE unidades_disponibles < nivel_reposicion ORDER BY id");
    }

    @Override
    public Videojuego obtenerPorId(long id) throws SQLException, VideojuegoNoEncontradoException {
        String sql = "SELECT " + COLUMNAS + " FROM videojuegos WHERE id = ?";
        try (Connection con = ConexionBD.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        throw new VideojuegoNoEncontradoException(id);
    }

    @Override
    public Videojuego agregarVideojuego(Videojuego v) throws SQLException, ReglaNegocioException {
        validar(v);
        String sql = "INSERT INTO videojuegos (nombre, genero, precio, unidades_disponibles, "
                + "nivel_reposicion, suspendido) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtener();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, v.getNombre());
            ps.setString(2, v.getGenero());
            ps.setDouble(3, v.getPrecio());
            ps.setInt(4, v.getUnidadesDisponibles());
            ps.setInt(5, v.getNivelReposicion());
            ps.setInt(6, v.getSuspendido());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    v.setId(keys.getLong(1));
                }
            }
        }
        return v;
    }

    @Override
    public boolean actualizarVideojuego(Videojuego v) throws SQLException, ReglaNegocioException {
        validar(v);
        String sql = "UPDATE videojuegos SET nombre = ?, genero = ?, precio = ?, "
                + "unidades_disponibles = ?, nivel_reposicion = ?, suspendido = ? WHERE id = ?";
        try (Connection con = ConexionBD.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, v.getNombre());
            ps.setString(2, v.getGenero());
            ps.setDouble(3, v.getPrecio());
            ps.setInt(4, v.getUnidadesDisponibles());
            ps.setInt(5, v.getNivelReposicion());
            ps.setInt(6, v.getSuspendido());
            ps.setLong(7, v.getId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminarVideojuego(long id) throws SQLException {
        try (Connection con = ConexionBD.obtener();
             PreparedStatement ps = con.prepareStatement("DELETE FROM videojuegos WHERE id = ?")) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ---------- Auxiliares ----------

    private List<Videojuego> consultar(String sql) throws SQLException {
        List<Videojuego> lista = new ArrayList<>();
        try (Connection con = ConexionBD.obtener();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    private Videojuego mapear(ResultSet rs) throws SQLException {
        return new Videojuego(
                rs.getLong("id"),
                rs.getString("nombre"),
                rs.getString("genero"),
                rs.getDouble("precio"),
                rs.getInt("unidades_disponibles"),
                rs.getInt("nivel_reposicion"),
                rs.getInt("suspendido"));
    }

    private void validar(Videojuego v) throws ReglaNegocioException {
        if (v.getNombre() == null || v.getNombre().isBlank()) {
            throw new ReglaNegocioException("El nombre no puede estar vacío.");
        }
        if (v.getPrecio() <= 0) {
            throw new ReglaNegocioException("El precio debe ser mayor que 0.");
        }
        if (v.getUnidadesDisponibles() < 0) {
            throw new ReglaNegocioException("Las unidades disponibles no pueden ser negativas.");
        }
        if (v.getNivelReposicion() < 0) {
            throw new ReglaNegocioException("El nivel de reposición no puede ser negativo.");
        }
        if (v.getSuspendido() != 0 && v.getSuspendido() != 1) {
            throw new ReglaNegocioException("El campo suspendido debe ser 1 (disponible) o 0 (no disponible).");
        }
    }
}
