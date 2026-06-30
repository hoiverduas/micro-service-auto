package vehiculo_servicer.vehiculo_servicer.dto;



import lombok.Getter;
import lombok.Setter;
import vehiculo_servicer.vehiculo_servicer.utils.TipoVehiculo;


@Getter
@Setter
public class VehiculoRequestDTO {

    private TipoVehiculo tipo;

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
