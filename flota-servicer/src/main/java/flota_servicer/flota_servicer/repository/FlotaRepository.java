package flota_servicer.flota_servicer.repository;



import flota_servicer.flota_servicer.model.Flota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlotaRepository extends JpaRepository<Flota, Long> {
}