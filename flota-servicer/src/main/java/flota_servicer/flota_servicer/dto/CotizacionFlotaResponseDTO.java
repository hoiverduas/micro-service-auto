package flota_servicer.flota_servicer.dto;



import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CotizacionFlotaResponseDTO {

    private Long flotaId;

    private String nombreFlota;

    private Double distanciaKm;

    private List<CotizacionResponseDTO> cotizaciones;

    private CotizacionResponseDTO vehiculoMasEconomico;
}
