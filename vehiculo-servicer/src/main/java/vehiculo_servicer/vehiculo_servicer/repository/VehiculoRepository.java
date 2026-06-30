package vehiculo_servicer.vehiculo_servicer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import vehiculo_servicer.vehiculo_servicer.model.Vehiculo;

public interface VehiculoRepository extends JpaRepository<Vehiculo,Long> {
}
