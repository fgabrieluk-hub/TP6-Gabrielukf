package resol.Gabrielukf.controlador;

import java.sql.SQLException;
import java.time.YearMonth;
import java.util.List;

import resol.Gabrielukf.dao.VentaDao;
import resol.Gabrielukf.dao.VentaDaoImpl;
import resol.Gabrielukf.dto.VentaDto;
import resol.Gabrielukf.excepciones.ReglaNegocioException;
import resol.Gabrielukf.excepciones.VentaInvalidaException;
import resol.Gabrielukf.excepciones.VideojuegoNoEncontradoException;
import resol.Gabrielukf.mapper.VentaMapper;
import resol.Gabrielukf.modelo.Venta;
import resol.Gabrielukf.vista.MenuVista;
import resol.Gabrielukf.vista.VentaVista;

public class VentaControlador {
    // Se depende de la interfaz, no de la implementación concreta
    private final VentaDao ventaDao = new VentaDaoImpl();
    private final VentaVista vista = new VentaVista();
    private final MenuVista menu = new MenuVista();

    public void gestionar() {
        int opcion;
        do {
            opcion = menu.mostrarMenuVentas();
            try {
                switch (opcion) {
                    case 1 -> vista.mostrarVentas(VentaMapper.toDtoList(ventaDao.listarVentas()));
                    case 2 -> vista.mostrarVenta(
                            VentaMapper.toDto(ventaDao.obtenerPorId(vista.pedirIdVenta())));
                    case 3 -> {
                        Venta venta = new Venta();
                        venta.setIdVideojuego(vista.pedirIdVideojuego());
                        venta.setCantidad(vista.pedirCantidad());
                        venta.setFecha(vista.pedirFecha());
                        Venta registrada = ventaDao.registrarVenta(venta);
                        vista.mostrarMensaje("Venta registrada correctamente:");
                        vista.mostrarVenta(VentaMapper.toDto(registrada));
                    }
                    case 4 -> vista.mostrarVentas(VentaMapper.toDtoList(
                            ventaDao.listarPorVideojuego(vista.pedirIdVideojuego())));
                    case 5 -> {
                        List<VentaDto> ventas = VentaMapper.toDtoList(ventaDao.listarVentasDelMes());
                        vista.mostrarReporteMensual(YearMonth.now(), ventas, sumarTotales(ventas));
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

    private double sumarTotales(List<VentaDto> ventas) {
        double suma = 0;
        for (VentaDto v : ventas) {
            suma += v.getTotal();
        }
        return suma;
    }
}
