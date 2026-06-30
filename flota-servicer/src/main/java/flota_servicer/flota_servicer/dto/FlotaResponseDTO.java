package flota_servicer.flota_servicer.dto;




import flota_servicer.flota_servicer.utils.EstadoFlota;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FlotaResponseDTO {

    private Long id;

    private String nombre;

    private String descripcion;

    private String ciudadOperacion;

    private String responsable;

    private EstadoFlota estado;

    private List<Long> vehiculosIds;

    private List<VehiculoDTO> vehiculos;
}