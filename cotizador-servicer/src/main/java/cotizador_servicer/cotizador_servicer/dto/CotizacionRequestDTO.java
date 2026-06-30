package cotizador_servicer.cotizador_servicer.dto;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CotizacionRequestDTO {

    private Long vehiculoId;

    private String tipo;

    private String marca;

    private String modelo;

    private String placa;

    private Double distanciaKm;

    private Double consumoPorKm;
}