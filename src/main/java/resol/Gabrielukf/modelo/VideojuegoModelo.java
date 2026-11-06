package resol.Gabrielukf.modelo;

import resol.Gabrielukf.excepciones.ReglaNegocioException;
import resol.Gabrielukf.excepciones.VideojuegoNoEncontradoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VideojuegoModelo {

    private static final String COLUMNAS =
            "id, nombre, genero, precio, unidades_disponibles, nivel_reposicion, suspendido";

    private Videojuego mapear(ResultSet rs) throws SQLException {
        return new Videojuego(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("genero"),
                rs.getDouble("precio"),
                rs.getInt("unidades_disponibles"),
                rs.getInt("nivel_reposicion"),
                rs.getInt("suspendido"));
    }

    private List<Videojuego> consultar(String sql) throws SQLException {
        List<Videojuego> lista = new ArrayList<>();
        try (Connection con = ConexionBD.obtener();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    // ---------- CRUD ----------

    public List<Videojuego> listar() throws SQLException {
        return consultar("SELECT " + COLUMNAS + " FROM videojuegos ORDER BY id");
    }

    public Videojuego buscarPorId(int id) throws SQLException, VideojuegoNoEncontradoException {
        String sql = "SELECT " + COLUMNAS + " FROM videojuegos WHERE id = ?";
        try (Connection con = ConexionBD.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        throw new VideojuegoNoEncontradoException(id);
    }

    public void agregar(Videojuego v) throws SQLException, ReglaNegocioException {
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
                    v.setId(keys.getInt(1));
                }
            }
        }
    }

    public void actualizar(Videojuego v)
            throws SQLException, ReglaNegocioException, VideojuegoNoEncontradoException {
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
            ps.setInt(7, v.getId());
            if (ps.executeUpdate() == 0) {
                throw new VideojuegoNoEncontradoException(v.getId());
            }
        }
    }

    public void eliminar(int id) throws SQLException, VideojuegoNoEncontradoException {
        try (Connection con = ConexionBD.obtener();
             PreparedStatement ps = con.prepareStatement("DELETE FROM videojuegos WHERE id = ?")) {
            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new VideojuegoNoEncontradoException(id);
            }
        }
    }

    // ---------- Reglas de negocio ----------

    /** Videojuegos disponibles para la venta (suspendido = 1). */
    public List<Videojuego> listarDisponibles() throws SQLException {
        return consultar("SELECT " + COLUMNAS + " FROM videojuegos WHERE suspendido = 1 ORDER BY id");
    }

    /** Necesitan reposición si unidadesDisponibles < nivelReposicion. */
    public List<Videojuego> listarNecesitanReposicion() throws SQLException {
        return consultar("SELECT " + COLUMNAS + " FROM videojuegos "
                + "WHERE unidades_disponibles < nivel_reposicion ORDER BY id");
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
