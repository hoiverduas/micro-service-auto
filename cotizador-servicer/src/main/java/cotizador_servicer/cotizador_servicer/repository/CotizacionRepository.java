package cotizador_servicer.cotizador_servicer.repository;

import cotizador_servicer.cotizador_servicer.model.Cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CotizacionRepository extends JpaRepository<Cotizacion, Long> {
}