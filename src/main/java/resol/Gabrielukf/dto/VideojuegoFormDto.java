package resol.Gabrielukf.dto;

/**
 * Datos que la Vista carga por consola al agregar/actualizar un videojuego.
 * Viaja de la Vista al Controlador (sentido inverso a VideojuegoDto).
 */
public class VideojuegoFormDto {
    private String nombre;
    private String genero;
    private double precio;
    private int unidadesDisponibles;
    private int nivelReposicion;
    private int suspendido;

    public VideojuegoFormDto() { }

    public VideojuegoFormDto(String nombre, String genero, double precio,
                             int unidadesDisponibles, int nivelReposicion, int suspendido) {
        this.nombre = nombre;
        this.genero = genero;
        this.precio = precio;
        this.unidadesDisponibles = unidadesDisponibles;
        this.nivelReposicion = nivelReposicion;
        this.suspendido = suspendido;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getUnidadesDisponibles() { return unidadesDisponibles; }
    public void setUnidadesDisponibles(int u) { this.unidadesDisponibles = u; }
    public int getNivelReposicion() { return nivelReposicion; }
    public void setNivelReposicion(int n) { this.nivelReposicion = n; }
    public int getSuspendido() { return suspendido; }
    public void setSuspendido(int suspendido) { this.suspendido = suspendido; }
}
