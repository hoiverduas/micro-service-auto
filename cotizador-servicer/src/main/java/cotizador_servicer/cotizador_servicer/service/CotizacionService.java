package cotizador_servicer.cotizador_servicer.service;



import cotizador_servicer.cotizador_servicer.dto.CotizacionRequestDTO;
import cotizador_servicer.cotizador_servicer.dto.CotizacionResponseDTO;

import java.util.List;

public interface CotizacionService {

    CotizacionResponseDTO cotizar(CotizacionRequestDTO requestDTO);

    List<CotizacionResponseDTO> listar();

    CotizacionResponseDTO buscarPorId(Long id);

    void eliminar(Long id);
}