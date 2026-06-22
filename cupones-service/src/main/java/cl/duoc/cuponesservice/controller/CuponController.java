package cl.duoc.cuponesservice.controller;

import cl.duoc.cuponesservice.dto.ActualizarEstadoCuponRequest;
import cl.duoc.cuponesservice.dto.CrearCuponRequest;
import cl.duoc.cuponesservice.model.Cupon;
import cl.duoc.cuponesservice.service.CuponService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cupones")
public class CuponController {

    private final CuponService cuponService;

    public CuponController(CuponService cuponService) {
        this.cuponService = cuponService;
    }

    @GetMapping
    public List<Cupon> listarCupones() {
        return cuponService.listarCupones();
    }

    @GetMapping("/{idCupon}")
    public Cupon buscarPorId(@PathVariable Long idCupon) {
        return cuponService.buscarPorId(idCupon);
    }

    @GetMapping("/codigo/{codigo}")
    public Cupon buscarPorCodigo(@PathVariable String codigo) {
        return cuponService.buscarPorCodigo(codigo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cupon crearCupon(@Valid @RequestBody CrearCuponRequest request) {
        return cuponService.crearCupon(request);
    }

    @PutMapping("/{idCupon}/estado")
    public Cupon actualizarEstado(@PathVariable Long idCupon,
                                  @Valid @RequestBody ActualizarEstadoCuponRequest request) {
        return cuponService.actualizarEstado(idCupon, request.getEstado());
    }

    @PutMapping("/codigo/{codigo}/usar")
    public Cupon registrarUso(@PathVariable String codigo) {
        return cuponService.registrarUso(codigo);
    }

    @DeleteMapping("/{idCupon}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarCupon(@PathVariable Long idCupon) {
        cuponService.eliminarCupon(idCupon);
    }
}
