package cl.duoc.pagosservice.controller;

import cl.duoc.pagosservice.dto.ActualizarEstadoPagoRequest;
import cl.duoc.pagosservice.dto.CrearPagoRequest;
import cl.duoc.pagosservice.model.Pago;
import cl.duoc.pagosservice.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    public List<Pago> listarPagos() {
        return pagoService.listarPagos();
    }

    @GetMapping("/{idPago}")
    public Pago buscarPorId(@PathVariable Long idPago) {
        return pagoService.buscarPorId(idPago);
    }

    @GetMapping("/pedido/{pedidoId}")
    public List<Pago> buscarPorPedidoId(@PathVariable Long pedidoId) {
        return pagoService.buscarPorPedidoId(pedidoId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pago crearPago(@Valid @RequestBody CrearPagoRequest request) {
        return pagoService.crearPago(request);
    }

    @PutMapping("/{idPago}/estado")
    public Pago actualizarEstado(@PathVariable Long idPago,
                                 @Valid @RequestBody ActualizarEstadoPagoRequest request) {
        return pagoService.actualizarEstado(idPago, request.getEstado());
    }

    @DeleteMapping("/{idPago}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarPago(@PathVariable Long idPago) {
        pagoService.eliminarPago(idPago);
    }
}