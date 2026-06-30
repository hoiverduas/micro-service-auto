package cotizador_servicer.cotizador_servicer.dto;



import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CotizacionResponseDTO {

    private Long id;

    private Long vehiculoId;

    private String tipo;

    private String marca;

    private String modelo;

    private String placa;

    private Double distanciaKm;

    private Double consumoPorKm;

    private Double costoViaje;
}