package resol.Gabrielukf.excepciones;

public class VideojuegoNoEncontradoException extends Exception {
    public VideojuegoNoEncontradoException(int id) {
        super("No existe un videojuego con ID " + id);
    }
}
