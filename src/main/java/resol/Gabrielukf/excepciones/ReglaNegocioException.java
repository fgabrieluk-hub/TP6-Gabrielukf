package resol.Gabrielukf.excepciones;

/** Se lanza cuando se viola una regla de negocio (stock insuficiente, precio inválido, etc.). */
public class ReglaNegocioException extends Exception {
    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
