package flota_servicer.flota_servicer.dto;



import lombok.Getter;
import lombok.Setter;





@Getter
@Setter
public class VehiculoDTO {

    private Long id;

    private String tipo;

    private String marca;

    private String modelo;

    private String placa;

    private Integer anio;

    private Double consumoPorKm;

    private Integer numeroPuertas;

    private Integer cilindraje;

    private Double capacidadBateria;

    private Double nivelCarga;
}