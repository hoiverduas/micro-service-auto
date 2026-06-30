package cotizador_servicer.cotizador_servicer.service;


import cotizador_servicer.cotizador_servicer.dto.CotizacionRequestDTO;
import cotizador_servicer.cotizador_servicer.dto.CotizacionResponseDTO;
import cotizador_servicer.cotizador_servicer.model.Cotizacion;
import cotizador_servicer.cotizador_servicer.repository.CotizacionRepository;
import cotizador_servicer.cotizador_servicer.service.CotizacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CotizacionServiceImpl implements CotizacionService {

    private final CotizacionRepository cotizacionRepository;

    @Override
    public CotizacionResponseDTO cotizar(CotizacionRequestDTO requestDTO) {
        return Optional.ofNullable(requestDTO)
                .map(this::toEntity)
                .map(this::calcularCotizacion)
                .map(cotizacionRepository::save)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("La solicitud de cotización es obligatoria"));
    }

    @Override
    public List<CotizacionResponseDTO> listar() {
        return cotizacionRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public CotizacionResponseDTO buscarPorId(Long id) {
        return cotizacionRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada con id: " + id));
    }

    @Override
    public void eliminar(Long id) {
        cotizacionRepository.findById(id)
                .ifPresentOrElse(
                        cotizacionRepository::delete,
                        () -> {
                            throw new RuntimeException("Cotización no encontrada con id: " + id);
                        }
                );
    }

    private Cotizacion calcularCotizacion(Cotizacion cotizacion) {
        Double distanciaKm = Optional.ofNullable(cotizacion.getDistanciaKm())
                .orElseThrow(() -> new RuntimeException("La distancia es obligatoria"));

        Double consumoPorKm = Optional.ofNullable(cotizacion.getConsumoPorKm())
                .orElseThrow(() -> new RuntimeException("El consumo por km es obligatorio"));

        Double valorUnidad = obtenerValorUnidadPorTipo(cotizacion.getTipo());

        Double costoViaje = distanciaKm * consumoPorKm * valorUnidad;

        cotizacion.setCostoViaje(costoViaje);

        return cotizacion;
    }

    private Double obtenerValorUnidadPorTipo(String tipo) {
        return switch (tipo) {
            case "AUTO", "MOTO" -> 16000.0;
            case "ELECTRICO" -> 950.0;
            default -> throw new RuntimeException("Tipo de vehículo no soportado: " + tipo);
        };
    }

    private Cotizacion toEntity(CotizacionRequestDTO requestDTO) {
        Cotizacion cotizacion = new Cotizacion();

        cotizacion.setVehiculoId(requestDTO.getVehiculoId());
        cotizacion.setTipo(requestDTO.getTipo());
        cotizacion.setMarca(requestDTO.getMarca());
        cotizacion.setModelo(requestDTO.getModelo());
        cotizacion.setPlaca(requestDTO.getPlaca());
        cotizacion.setDistanciaKm(requestDTO.getDistanciaKm());
        cotizacion.setConsumoPorKm(requestDTO.getConsumoPorKm());

        return cotizacion;
    }

    private CotizacionResponseDTO toResponseDTO(Cotizacion cotizacion) {
        return CotizacionResponseDTO.builder()
                .id(cotizacion.getId())
                .vehiculoId(cotizacion.getVehiculoId())
                .tipo(cotizacion.getTipo())
                .marca(cotizacion.getMarca())
                .modelo(cotizacion.getModelo())
                .placa(cotizacion.getPlaca())
                .distanciaKm(cotizacion.getDistanciaKm())
                .consumoPorKm(cotizacion.getConsumoPorKm())
                .costoViaje(cotizacion.getCostoViaje())
                .build();
    }
}