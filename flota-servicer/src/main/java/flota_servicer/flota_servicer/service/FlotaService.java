package flota_servicer.flota_servicer.service;



import flota_servicer.flota_servicer.dto.FlotaRequestDTO;
import flota_servicer.flota_servicer.dto.FlotaResponseDTO;

import java.util.List;



import flota_servicer.flota_servicer.dto.CotizacionFlotaResponseDTO;


public interface FlotaService {

    List<FlotaResponseDTO> listar();

    FlotaResponseDTO buscarPorId(Long id);

    FlotaResponseDTO guardar(FlotaRequestDTO requestDTO);

    FlotaResponseDTO actualizar(Long id, FlotaRequestDTO requestDTO);

    void eliminar(Long id);

    CotizacionFlotaResponseDTO cotizarFlota(Long idFlota, Double distanciaKm);
}