package resol.Gabrielukf.modelo;

public class Videojuego {
    private long id;
    private String nombre;
    private String genero;
    private double precio;
    private int unidadesDisponibles;
    private int nivelReposicion;
    private int suspendido; // 1 = disponible, 0 = no disponible

    public Videojuego() { }

    public Videojuego(long id, String nombre, String genero, double precio,
                      int unidadesDisponibles, int nivelReposicion, int suspendido) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.precio = precio;
        this.unidadesDisponibles = unidadesDisponibles;
        this.nivelReposicion = nivelReposicion;
        this.suspendido = suspendido;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
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
