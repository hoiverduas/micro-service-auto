package vehiculo_servicer.vehiculo_servicer.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vehiculo_servicer.vehiculo_servicer.dto.VehiculoRequestDTO;
import vehiculo_servicer.vehiculo_servicer.dto.VehiculoResponseDTO;
import vehiculo_servicer.vehiculo_servicer.model.Auto;
import vehiculo_servicer.vehiculo_servicer.model.Electrico;
import vehiculo_servicer.vehiculo_servicer.model.Moto;
import vehiculo_servicer.vehiculo_servicer.model.Vehiculo;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VehiculoMapper {

    private static final Map<TipoVehiculo, Class<? extends Vehiculo>> ENTITY_BY_TYPE = Map.of(
            TipoVehiculo.AUTO, Auto.class,
            TipoVehiculo.MOTO, Moto.class,
            TipoVehiculo.ELECTRICO, Electrico.class
    );

    private static final Map<Class<? extends Vehiculo>, TipoVehiculo> TYPE_BY_ENTITY = Map.of(
            Auto.class, TipoVehiculo.AUTO,
            Moto.class, TipoVehiculo.MOTO,
            Electrico.class, TipoVehiculo.ELECTRICO
    );

    private final ObjectMapper objectMapper;



    public Vehiculo toEntity(VehiculoRequestDTO dto) {
        TipoVehiculo tipo = Optional.ofNullable(dto)
                .map(VehiculoRequestDTO::getTipo)
                .orElseThrow(() -> new RuntimeException("El tipo de vehículo es obligatorio"));

        Class<? extends Vehiculo> claseVehiculo = Optional.ofNullable(ENTITY_BY_TYPE.get(tipo))
                .orElseThrow(() -> new RuntimeException("Tipo de vehículo no soportado: " + tipo));

        return objectMapper.convertValue(dto, claseVehiculo);
    }

    public VehiculoResponseDTO toResponseDTO(Vehiculo vehiculo) {
        VehiculoResponseDTO responseDTO = objectMapper.convertValue(vehiculo, VehiculoResponseDTO.class);

        obtenerTipoPorEntidad(vehiculo)
                .ifPresent(responseDTO::setTipo);

        return responseDTO;
    }

    public Vehiculo actualizarEntidad(Vehiculo vehiculoActual, VehiculoRequestDTO requestDTO) {
        validarTipoNoCambie(vehiculoActual, requestDTO);
        return actualizarConObjectMapper(vehiculoActual, requestDTO);
    }

    private Optional<TipoVehiculo> obtenerTipoPorEntidad(Vehiculo vehiculo) {
        return TYPE_BY_ENTITY.entrySet()
                .stream()
                .filter(entry -> entry.getKey().isInstance(vehiculo))
                .map(Map.Entry::getValue)
                .findFirst();
    }

    private void validarTipoNoCambie(Vehiculo vehiculoActual, VehiculoRequestDTO requestDTO) {
        obtenerTipoPorEntidad(vehiculoActual)
                .ifPresent(tipoActual ->
                        Optional.ofNullable(requestDTO)
                                .map(VehiculoRequestDTO::getTipo)
                                .filter(tipoNuevo -> !Objects.equals(tipoNuevo, tipoActual))
                                .ifPresent(tipoNuevo -> {
                                    throw new RuntimeException("No se puede cambiar el tipo del vehículo");
                                })
                );
    }

    private Vehiculo actualizarConObjectMapper(Vehiculo vehiculoActual, VehiculoRequestDTO requestDTO) {
        try {
            return objectMapper.updateValue(vehiculoActual, requestDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error actualizando vehículo", e);
        }
    }
}