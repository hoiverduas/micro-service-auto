package vehiculo_servicer.vehiculo_servicer.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("AUTO")
public class Auto extends Vehiculo {
    private Integer numeroPuertas;
}
