package resol.Gabrielukf.vista;

import resol.Gabrielukf.modelo.Venta;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class VentaVista {

    public int pedirIdVenta() {
        return Consola.leerEntero("ID de la venta: ");
    }

    public int pedirIdVideojuego() {
        return Consola.leerEntero("ID del videojuego: ");
    }

    public int pedirCantidad() {
        return Consola.leerEntero("Cantidad: ");
    }

    public LocalDate pedirFecha() {
        return Consola.leerFecha("Fecha (AAAA-MM-DD, Enter = hoy): ");
    }

    public void mostrarVenta(Venta v) {
        System.out.printf("ID: %d | Fecha: %s | Videojuego: %s | Cantidad: %d | Descuento: %d%% | Total: $%.2f%n",
                v.getId(), v.getFecha(), v.getNombreVideojuego(), v.getCantidad(),
                v.getDescuento(), v.getTotal());
    }

    public void mostrarVentas(List<Venta> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay ventas para mostrar.");
            return;
        }
        for (Venta v : lista) {
            mostrarVenta(v);
        }
    }

    public void mostrarReporteMensual(YearMonth mes, List<Venta> ventas, double totalMes) {
        System.out.println("\n--- REPORTE DE VENTAS DEL MES " + mes + " ---");
        mostrarVentas(ventas);
        System.out.println("Cantidad de ventas: " + ventas.size());
        System.out.printf("Total recaudado: $%.2f%n", totalMes);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarError(String mensaje) {
        System.out.println("[ERROR] " + mensaje);
    }
}
