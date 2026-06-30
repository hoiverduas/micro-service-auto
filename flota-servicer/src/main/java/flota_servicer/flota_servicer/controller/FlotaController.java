package flota_servicer.flota_servicer.controller;



import flota_servicer.flota_servicer.dto.CotizacionFlotaResponseDTO;
import flota_servicer.flota_servicer.dto.FlotaRequestDTO;
import flota_servicer.flota_servicer.dto.FlotaResponseDTO;
import flota_servicer.flota_servicer.service.FlotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flotas")
@RequiredArgsConstructor
public class FlotaController {

    private final FlotaService flotaService;

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(flotaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlotaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(flotaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<FlotaResponseDTO> guardar(@RequestBody FlotaRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flotaService.guardar(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlotaResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody FlotaRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(flotaService.actualizar(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        flotaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idFlota}/cotizar")
    public ResponseEntity<CotizacionFlotaResponseDTO> cotizarFlota(
            @PathVariable Long idFlota,
            @RequestParam Double distanciaKm
    ) {
        return ResponseEntity.ok(flotaService.cotizarFlota(idFlota, distanciaKm));
    }
}