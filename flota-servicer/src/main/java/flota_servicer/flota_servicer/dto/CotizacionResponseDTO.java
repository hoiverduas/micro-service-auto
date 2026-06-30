package flota_servicer.flota_servicer.dto;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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