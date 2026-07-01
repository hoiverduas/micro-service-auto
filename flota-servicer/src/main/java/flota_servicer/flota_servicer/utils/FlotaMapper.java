package flota_servicer.flota_servicer.utils;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import flota_servicer.flota_servicer.dto.CotizacionRequestDTO;
import flota_servicer.flota_servicer.dto.FlotaRequestDTO;
import flota_servicer.flota_servicer.dto.FlotaResponseDTO;
import flota_servicer.flota_servicer.dto.VehiculoDTO;
import flota_servicer.flota_servicer.model.Flota;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FlotaMapper {

    private final ObjectMapper objectMapper;

    public FlotaMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper.copy()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Flota toEntity(FlotaRequestDTO requestDTO) {
        return Optional.ofNullable(requestDTO)
                .map(dto -> objectMapper.convertValue(dto, Flota.class))
                .orElseThrow(() -> new RuntimeException("La solicitud de flota es obligatoria"));
    }

    public Flota actualizarDatos(Flota flotaActual, FlotaRequestDTO requestDTO) {
        try {
            return objectMapper.updateValue(flotaActual, requestDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error actualizando los datos de la flota", e);
        }
    }

    public FlotaResponseDTO toResponseDTO(Flota flota, List<VehiculoDTO> vehiculos) {
        FlotaResponseDTO responseDTO = objectMapper.convertValue(flota, FlotaResponseDTO.class);
        responseDTO.setVehiculos(vehiculos);
        return responseDTO;
    }

    public CotizacionRequestDTO toCotizacionRequestDTO(VehiculoDTO vehiculo, Double distanciaKm) {
        Double consumoPorKm = Optional.ofNullable(vehiculo.getConsumoPorKm())
                .orElseThrow(() -> new RuntimeException(
                        "El vehículo con id " + vehiculo.getId() + " no tiene consumoPorKm configurado"
                ));

        CotizacionRequestDTO requestDTO = objectMapper.convertValue(vehiculo, CotizacionRequestDTO.class);

        requestDTO.setVehiculoId(vehiculo.getId());
        requestDTO.setDistanciaKm(distanciaKm);
        requestDTO.setConsumoPorKm(consumoPorKm);

        return requestDTO;
    }
}