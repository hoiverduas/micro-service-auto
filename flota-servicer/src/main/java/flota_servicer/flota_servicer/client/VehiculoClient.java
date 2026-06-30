package flota_servicer.flota_servicer.client;



import flota_servicer.flota_servicer.dto.VehiculoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "vehiculo-service",
        url = "${services.vehiculo.url}"
)
public interface VehiculoClient {

    @GetMapping("/vehiculos/{id}")
    VehiculoDTO buscarPorId(@PathVariable("id") Long id);
}
