package resol.Gabrielukf.excepciones;

public class VideojuegoNoEncontradoException extends Exception {
    public VideojuegoNoEncontradoException(long id) {
        super("No existe un videojuego con ID " + id);
    }
}
