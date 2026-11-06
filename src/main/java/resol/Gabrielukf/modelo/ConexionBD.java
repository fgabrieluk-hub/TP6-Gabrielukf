package resol.Gabrielukf.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    // Ajustá estos datos a tu base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/videojuegos_db?serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    private ConexionBD() { }

    public static Connection obtener() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
