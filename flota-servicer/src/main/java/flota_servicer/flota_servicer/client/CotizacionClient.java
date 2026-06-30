package flota_servicer.flota_servicer.client;

import flota_servicer.flota_servicer.dto.CotizacionRequestDTO;
import flota_servicer.flota_servicer.dto.CotizacionResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "cotizador-service",
        url = "${services.cotizador.url}"
)
public interface CotizacionClient {

    @PostMapping("/cotizaciones")
    CotizacionResponseDTO cotizar(CotizacionRequestDTO requestDTO);
}
