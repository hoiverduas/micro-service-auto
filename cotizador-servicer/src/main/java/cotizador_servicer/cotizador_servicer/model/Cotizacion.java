package cotizador_servicer.cotizador_servicer.model;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cotizaciones")
public class Cotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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