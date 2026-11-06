package resol.Gabrielukf.modelo;

import java.time.LocalDate;

public class Venta {
    private long id;
    private LocalDate fecha;
    private long idVideojuego;
    private String nombreVideojuego; // solo para listados (JOIN)
    private int cantidad;
    private int descuento;           // porcentaje
    private double total;

    public Venta() { }

    public Venta(long id, LocalDate fecha, long idVideojuego, String nombreVideojuego,
                 int cantidad, int descuento, double total) {
        this.id = id;
        this.fecha = fecha;
        this.idVideojuego = idVideojuego;
        this.nombreVideojuego = nombreVideojuego;
        this.cantidad = cantidad;
        this.descuento = descuento;
        this.total = total;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public long getIdVideojuego() { return idVideojuego; }
    public void setIdVideojuego(long idVideojuego) { this.idVideojuego = idVideojuego; }
    public String getNombreVideojuego() { return nombreVideojuego; }
    public void setNombreVideojuego(String n) { this.nombreVideojuego = n; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public int getDescuento() { return descuento; }
    public void setDescuento(int descuento) { this.descuento = descuento; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}
