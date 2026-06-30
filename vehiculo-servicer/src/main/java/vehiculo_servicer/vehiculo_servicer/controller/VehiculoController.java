package vehiculo_servicer.vehiculo_servicer.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vehiculo_servicer.vehiculo_servicer.dto.VehiculoRequestDTO;
import vehiculo_servicer.vehiculo_servicer.dto.VehiculoResponseDTO;
import vehiculo_servicer.vehiculo_servicer.service.VehiculoService;

import java.util.List;

@RestController
@RequestMapping("/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {

    private final VehiculoService vehiculoService;


    @GetMapping
    public ResponseEntity<List<VehiculoResponseDTO>> listarVehiculos(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.vehiculoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> getVehiculoById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.vehiculoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<VehiculoResponseDTO> createVehiculo(@RequestBody VehiculoRequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.vehiculoService.guardar(requestDTO));
    }

    @PutMapping
    public ResponseEntity<VehiculoResponseDTO> updateVehiculo(
            @PathVariable("id") Long id,
            @RequestBody VehiculoRequestDTO requestDTO
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.vehiculoService.actualizar(id,requestDTO));
    }

    @DeleteMapping
    public ResponseEntity<VehiculoResponseDTO> deleteVehiculo(@PathVariable Long id){
        this.vehiculoService.eliminar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
