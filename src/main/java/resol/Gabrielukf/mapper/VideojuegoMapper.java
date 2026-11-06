package resol.Gabrielukf.mapper;

import resol.Gabrielukf.dto.VideojuegoDto;
import resol.Gabrielukf.dto.VideojuegoFormDto;
import resol.Gabrielukf.modelo.Videojuego;

import java.util.ArrayList;
import java.util.List;

public final class VideojuegoMapper {

    private VideojuegoMapper() { }

    public static VideojuegoDto toDto(Videojuego v) {
        boolean necesitaReposicion = v.getUnidadesDisponibles() < v.getNivelReposicion();
        return new VideojuegoDto(v.getId(), v.getNombre(), v.getPrecio(), necesitaReposicion);
    }

    public static List<VideojuegoDto> toDtoList(List<Videojuego> videojuegos) {
        List<VideojuegoDto> dtos = new ArrayList<>();
        for (Videojuego v : videojuegos) {
            dtos.add(toDto(v));
        }
        return dtos;
    }

    /** Convierte los datos cargados en la Vista en una entidad (sin id). */
    public static Videojuego toEntity(VideojuegoFormDto f) {
        Videojuego v = new Videojuego();
        v.setNombre(f.getNombre());
        v.setGenero(f.getGenero());
        v.setPrecio(f.getPrecio());
        v.setUnidadesDisponibles(f.getUnidadesDisponibles());
        v.setNivelReposicion(f.getNivelReposicion());
        v.setSuspendido(f.getSuspendido());
        return v;
    }
}
