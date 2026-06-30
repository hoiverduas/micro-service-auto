package flota_servicer.flota_servicer.dto;




import flota_servicer.flota_servicer.utils.EstadoFlota;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FlotaRequestDTO {

    private String nombre;

    private String descripcion;

    private String ciudadOperacion;

    private String responsable;

    private EstadoFlota estado;

    private List<Long> vehiculosIds;
}