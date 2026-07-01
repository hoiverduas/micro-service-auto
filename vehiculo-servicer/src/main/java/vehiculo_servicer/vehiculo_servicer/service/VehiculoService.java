package vehiculo_servicer.vehiculo_servicer.service;



import vehiculo_servicer.vehiculo_servicer.model.Vehiculo;

import java.util.List;



import vehiculo_servicer.vehiculo_servicer.dto.VehiculoRequestDTO;
import vehiculo_servicer.vehiculo_servicer.dto.VehiculoResponseDTO;

import java.util.List;

public interface VehiculoService {

    List<VehiculoResponseDTO> listar();

    VehiculoResponseDTO buscarPorId(Long id);

    VehiculoResponseDTO guardar(VehiculoRequestDTO requestDTO);

    List<VehiculoResponseDTO> guardarTodos(List<VehiculoRequestDTO> requestDTOs);

    VehiculoResponseDTO actualizar(Long id, VehiculoRequestDTO requestDTO);

    void eliminar(Long id);
}