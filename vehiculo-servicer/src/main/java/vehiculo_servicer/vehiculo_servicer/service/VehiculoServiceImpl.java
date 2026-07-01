package vehiculo_servicer.vehiculo_servicer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vehiculo_servicer.vehiculo_servicer.dto.VehiculoRequestDTO;
import vehiculo_servicer.vehiculo_servicer.dto.VehiculoResponseDTO;
import vehiculo_servicer.vehiculo_servicer.model.Auto;
import vehiculo_servicer.vehiculo_servicer.model.Electrico;
import vehiculo_servicer.vehiculo_servicer.model.Moto;
import vehiculo_servicer.vehiculo_servicer.model.Vehiculo;
import vehiculo_servicer.vehiculo_servicer.repository.VehiculoRepository;
import vehiculo_servicer.vehiculo_servicer.utils.VehiculoMapper;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
public class VehiculoServiceImpl implements VehiculoService {


    private final VehiculoRepository vehiculoRepository;
    private final VehiculoMapper vehiculoMapper;


    @Override
    public List<VehiculoResponseDTO> listar() {
        return this.vehiculoRepository.findAll()
                .stream()
                .map(vehiculoMapper::toResponseDTO)
                .toList();
    }

    @Override
    public VehiculoResponseDTO buscarPorId(Long id) {
        return vehiculoRepository.findById(id)
                .map(vehiculoMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Vehiculo no encintrado con :" + id));
    }

    @Override
    public VehiculoResponseDTO guardar(VehiculoRequestDTO requestDTO) {
        return Optional.ofNullable(requestDTO)
                .map(vehiculoMapper::toEntity)
                .map(vehiculoRepository::save)
                .map(vehiculoMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("La solicitude del vehiculo es obligatoria"));
    }

    @Override
    public List<VehiculoResponseDTO> guardarTodos(List<VehiculoRequestDTO> requestDTOs) {
        return Optional.ofNullable(requestDTOs)
                .filter(lista -> !lista.isEmpty())
                .map(lista -> lista.stream()
                        .map(vehiculoMapper::toEntity)
                        .toList()
                )
                .map(vehiculoRepository::saveAll)
                .orElseThrow(() -> new RuntimeException("La lista de vehículos es obligatoria"))
                .stream()
                .map(vehiculoMapper::toResponseDTO)
                .toList();
    }

    @Override
    public VehiculoResponseDTO actualizar(Long id, VehiculoRequestDTO requestDTO) {
        return vehiculoRepository.findById(id)
                .map(vehiculoActual -> vehiculoMapper.actualizarEntidad(vehiculoActual,requestDTO))
                .map(vehiculoRepository::save)
                .map(vehiculoMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado con id :" + id));
    }

    @Override
    public void eliminar(Long id) {

        vehiculoRepository.findById(id)
                .ifPresentOrElse(
                        vehiculoRepository::delete,
                        ()->{
                            throw new RuntimeException("Vehiculo no encontrado con id : " + id);
                        }
                );
    }
}
