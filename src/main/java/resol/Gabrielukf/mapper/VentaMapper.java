package resol.Gabrielukf.mapper;

import resol.Gabrielukf.dto.VentaDto;
import resol.Gabrielukf.modelo.Venta;

import java.util.ArrayList;
import java.util.List;

public final class VentaMapper {

    private VentaMapper() { }

    public static VentaDto toDto(Venta v) {
        return new VentaDto(v.getId(), v.getFecha(), v.getNombreVideojuego(),
                v.getCantidad(), v.getDescuento(), v.getTotal());
    }

    public static List<VentaDto> toDtoList(List<Venta> ventas) {
        List<VentaDto> dtos = new ArrayList<>();
        for (Venta v : ventas) {
            dtos.add(toDto(v));
        }
        return dtos;
    }
}
