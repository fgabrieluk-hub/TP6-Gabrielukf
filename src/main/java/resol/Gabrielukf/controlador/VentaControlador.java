package resol.Gabrielukf.controlador;

import resol.Gabrielukf.excepciones.ReglaNegocioException;
import resol.Gabrielukf.excepciones.VentaInvalidaException;
import resol.Gabrielukf.excepciones.VideojuegoNoEncontradoException;
import resol.Gabrielukf.modelo.Venta;
import resol.Gabrielukf.modelo.VentaModelo;
import resol.Gabrielukf.vista.MenuVista;
import resol.Gabrielukf.vista.VentaVista;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class VentaControlador {
    private final VentaModelo modelo = new VentaModelo();
    private final VentaVista vista = new VentaVista();
    private final MenuVista menu = new MenuVista();

    public void gestionar() {
        int opcion;
        do {
            opcion = menu.mostrarMenuVentas();
            try {
                switch (opcion) {
                    case 1 -> vista.mostrarVentas(modelo.listar());
                    case 2 -> vista.mostrarVenta(modelo.buscarPorId(vista.pedirIdVenta()));
                    case 3 -> {
                        int idVideojuego = vista.pedirIdVideojuego();
                        int cantidad = vista.pedirCantidad();
                        LocalDate fecha = vista.pedirFecha();
                        Venta v = modelo.registrar(idVideojuego, fecha, cantidad);
                        vista.mostrarMensaje("Venta registrada correctamente:");
                        vista.mostrarVenta(v);
                    }
                    case 4 -> vista.mostrarVentas(modelo.buscarPorVideojuego(vista.pedirIdVideojuego()));
                    case 5 -> {
                        List<Venta> ventas = modelo.ventasDelMesActual();
                        vista.mostrarReporteMensual(YearMonth.now(), ventas, VentaModelo.sumarTotales(ventas));
                    }
                    case 0 -> { }
                    default -> vista.mostrarError("Opción inválida.");
                }
            } catch (VentaInvalidaException | VideojuegoNoEncontradoException | ReglaNegocioException e) {
                vista.mostrarError(e.getMessage());
            } catch (SQLException e) {
                vista.mostrarError("Error de base de datos: " + e.getMessage());
            }
        } while (opcion != 0);
    }
}
