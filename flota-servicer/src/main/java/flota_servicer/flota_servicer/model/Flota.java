package flota_servicer.flota_servicer.model;



import flota_servicer.flota_servicer.utils.EstadoFlota;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "flotas")
public class Flota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String descripcion;

    private String ciudadOperacion;

    private String responsable;

    @Enumerated(EnumType.STRING)
    private EstadoFlota estado;

    @ElementCollection
    @CollectionTable(
            name = "flota_vehiculos",
            joinColumns = @JoinColumn(name = "flota_id")
    )
    @Column(name = "vehiculo_id")
    private List<Long> vehiculosIds;
}