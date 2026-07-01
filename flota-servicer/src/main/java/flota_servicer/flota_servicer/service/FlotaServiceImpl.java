package flota_servicer.flota_servicer.service;

import feign.FeignException;
import flota_servicer.flota_servicer.client.CotizacionClient;
import flota_servicer.flota_servicer.client.VehiculoClient;
import flota_servicer.flota_servicer.dto.CotizacionFlotaResponseDTO;
import flota_servicer.flota_servicer.dto.CotizacionRequestDTO;
import flota_servicer.flota_servicer.dto.CotizacionResponseDTO;
import flota_servicer.flota_servicer.dto.FlotaRequestDTO;
import flota_servicer.flota_servicer.dto.FlotaResponseDTO;
import flota_servicer.flota_servicer.dto.VehiculoDTO;
import flota_servicer.flota_servicer.model.Flota;
import flota_servicer.flota_servicer.repository.FlotaRepository;
import flota_servicer.flota_servicer.utils.FlotaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlotaServiceImpl implements FlotaService {

    private final FlotaRepository flotaRepository;
    private final VehiculoClient vehiculoClient;
    private final CotizacionClient cotizacionClient;
    private final FlotaMapper flotaMapper;

    @Override
    public List<FlotaResponseDTO> listar() {
        return flotaRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public FlotaResponseDTO buscarPorId(Long id) {
        return flotaRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Flota no encontrada con id: " + id));
    }

    @Override
    public FlotaResponseDTO guardar(FlotaRequestDTO requestDTO) {
        return Optional.ofNullable(requestDTO)
                .map(this::validarVehiculos)
                .map(flotaMapper::toEntity)
                .map(flotaRepository::save)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("La solicitud de flota es obligatoria"));
    }

    @Override
    public FlotaResponseDTO actualizar(Long id, FlotaRequestDTO requestDTO) {
        return flotaRepository.findById(id)
                .map(flotaActual -> flotaMapper.actualizarDatos(
                        flotaActual,
                        validarVehiculos(requestDTO)
                ))
                .map(flotaRepository::save)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Flota no encontrada con id: " + id));
    }

    @Override
    public void eliminar(Long id) {
        flotaRepository.findById(id)
                .ifPresentOrElse(
                        flotaRepository::delete,
                        () -> {
                            throw new RuntimeException("Flota no encontrada con id: " + id);
                        }
                );
    }

    @Override
    public CotizacionFlotaResponseDTO cotizarFlota(Long idFlota, Double distanciaKm) {
        validarDistancia(distanciaKm);

        return flotaRepository.findById(idFlota)
                .map(flota -> generarCotizacionFlota(flota, distanciaKm))
                .orElseThrow(() -> new RuntimeException("Flota no encontrada con id: " + idFlota));
    }

    private FlotaRequestDTO validarVehiculos(FlotaRequestDTO requestDTO) {
        Optional.ofNullable(requestDTO)
                .map(FlotaRequestDTO::getVehiculosIds)
                .orElseGet(List::of)
                .forEach(this::consultarVehiculoPorId);

        return requestDTO;
    }

    private VehiculoDTO consultarVehiculoPorId(Long idVehiculo) {
        try {
            return Optional.ofNullable(vehiculoClient.buscarPorId(idVehiculo))
                    .orElseThrow(() -> new RuntimeException(
                            "Vehículo no encontrado con id: " + idVehiculo
                    ));

        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Vehículo no encontrado con id: " + idVehiculo);

        } catch (FeignException e) {
            throw new RuntimeException("Error consultando Vehiculo-Service para id: " + idVehiculo);
        }
    }

    private List<VehiculoDTO> consultarVehiculos(List<Long> vehiculosIds) {
        return Optional.ofNullable(vehiculosIds)
                .orElseGet(List::of)
                .stream()
                .map(this::consultarVehiculoPorId)
                .toList();
    }

    private CotizacionFlotaResponseDTO generarCotizacionFlota(Flota flota, Double distanciaKm) {
        List<CotizacionResponseDTO> cotizaciones = Optional.ofNullable(flota.getVehiculosIds())
                .orElseGet(List::of)
                .stream()
                .map(this::consultarVehiculoPorId)
                .map(vehiculo -> flotaMapper.toCotizacionRequestDTO(vehiculo, distanciaKm))
                .map(this::consultarCotizador)
                .toList();

        CotizacionResponseDTO vehiculoMasEconomico = cotizaciones.stream()
                .min(Comparator.comparing(CotizacionResponseDTO::getCostoViaje))
                .orElseThrow(() -> new RuntimeException("La flota no tiene vehículos para cotizar"));

        return CotizacionFlotaResponseDTO.builder()
                .flotaId(flota.getId())
                .nombreFlota(flota.getNombre())
                .distanciaKm(distanciaKm)
                .cotizaciones(cotizaciones)
                .vehiculoMasEconomico(vehiculoMasEconomico)
                .build();
    }

    private CotizacionResponseDTO consultarCotizador(CotizacionRequestDTO requestDTO) {
        try {
            return Optional.ofNullable(cotizacionClient.cotizar(requestDTO))
                    .orElseThrow(() -> new RuntimeException("Cotizador-Service no respondió"));

        } catch (FeignException e) {
            throw new RuntimeException("Error consultando Cotizador-Service");
        }
    }

    private void validarDistancia(Double distanciaKm) {
        Optional.ofNullable(distanciaKm)
                .filter(distancia -> distancia > 0)
                .orElseThrow(() -> new RuntimeException("La distancia debe ser mayor a cero"));
    }

    private FlotaResponseDTO toResponseDTO(Flota flota) {
        List<VehiculoDTO> vehiculos = consultarVehiculos(flota.getVehiculosIds());

        return flotaMapper.toResponseDTO(flota, vehiculos);
    }
}