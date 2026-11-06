package resol.Gabrielukf.dto;

import java.time.LocalDate;

public class VentaDto {
    private long id;
    private LocalDate fecha;
    private String nombreVideojuego;
    private int cantidad;
    private int descuento; // porcentaje aplicado
    private double total;

    public VentaDto() { }

    public VentaDto(long id, LocalDate fecha, String nombreVideojuego,
                    int cantidad, int descuento, double total) {
        this.id = id;
        this.fecha = fecha;
        this.nombreVideojuego = nombreVideojuego;
        this.cantidad = cantidad;
        this.descuento = descuento;
        this.total = total;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getNombreVideojuego() { return nombreVideojuego; }
    public void setNombreVideojuego(String n) { this.nombreVideojuego = n; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public int getDescuento() { return descuento; }
    public void setDescuento(int descuento) { this.descuento = descuento; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    @Override
    public String toString() {
        return "VentaDto{id=" + id + ", fecha=" + fecha + ", videojuego='" + nombreVideojuego
                + "', cantidad=" + cantidad + ", descuento=" + descuento + "%, total=" + total + "}";
    }
}
