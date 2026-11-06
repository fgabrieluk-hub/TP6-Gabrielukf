package resol.Gabrielukf.dto;

/** Datos del videojuego que necesita la Vista para el listado general. */
public class VideojuegoDto {
    private long id;
    private String nombre;
    private double precio;
    private boolean necesitaReposicion;

    public VideojuegoDto() { }

    public VideojuegoDto(long id, String nombre, double precio, boolean necesitaReposicion) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.necesitaReposicion = necesitaReposicion;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public boolean isNecesitaReposicion() { return necesitaReposicion; }
    public void setNecesitaReposicion(boolean necesitaReposicion) { this.necesitaReposicion = necesitaReposicion; }

    @Override
    public String toString() {
        return "VideojuegoDto{id=" + id + ", nombre='" + nombre + "', precio=" + precio
                + ", necesitaReposicion=" + necesitaReposicion + "}";
    }
}
