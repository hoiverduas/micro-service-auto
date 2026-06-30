package cotizador_servicer.cotizador_servicer.controller;



import cotizador_servicer.cotizador_servicer.dto.CotizacionRequestDTO;
import cotizador_servicer.cotizador_servicer.dto.CotizacionResponseDTO;
import cotizador_servicer.cotizador_servicer.service.CotizacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cotizaciones")
@RequiredArgsConstructor
public class CotizacionController {

    private final CotizacionService cotizacionService;

    @PostMapping
    public ResponseEntity<CotizacionResponseDTO> cotizar(
            @RequestBody CotizacionRequestDTO requestDTO
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cotizacionService.cotizar(requestDTO));
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(cotizacionService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CotizacionResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cotizacionService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cotizacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}