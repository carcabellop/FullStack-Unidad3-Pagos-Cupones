package cl.duoc.pagosservice.service;

import cl.duoc.pagosservice.dto.CrearPagoRequest;
import cl.duoc.pagosservice.model.EstadoPago;
import cl.duoc.pagosservice.model.MetodoPago;
import cl.duoc.pagosservice.model.Pago;
import cl.duoc.pagosservice.repository.EstadoPagoRepository;
import cl.duoc.pagosservice.repository.MetodoPagoRepository;
import cl.duoc.pagosservice.repository.PagoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final EstadoPagoRepository estadoPagoRepository;

    public PagoService(PagoRepository pagoRepository,
                       MetodoPagoRepository metodoPagoRepository,
                       EstadoPagoRepository estadoPagoRepository) {
        this.pagoRepository = pagoRepository;
        this.metodoPagoRepository = metodoPagoRepository;
        this.estadoPagoRepository = estadoPagoRepository;
    }

    public List<Pago> listarPagos() {
        return pagoRepository.findAll();
    }

    public Pago buscarPorId(Long idPago) {
        return pagoRepository.findById(idPago)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + idPago));
    }

    public List<Pago> buscarPorPedidoId(Long pedidoId) {
        return pagoRepository.findByPedidoId(pedidoId);
    }

    public Pago crearPago(CrearPagoRequest request) {
        MetodoPago metodoPago = metodoPagoRepository.findById(request.getIdMetodoPago())
                .orElseThrow(() -> new RuntimeException("Metodo de pago no encontrado"));

        EstadoPago estadoPendiente = estadoPagoRepository.findByNombre("PENDIENTE")
                .orElseThrow(() -> new RuntimeException("Estado PENDIENTE no existe en la base de datos"));

        Pago pago = new Pago();
        pago.setPedidoId(request.getPedidoId());
        pago.setMetodoPago(metodoPago);
        pago.setEstadoPago(estadoPendiente);
        pago.setMonto(request.getMonto());
        pago.setFechaPago(LocalDateTime.now());
        pago.setCodigoTransaccion(request.getCodigoTransaccion());

        return pagoRepository.save(pago);
    }

    public Pago actualizarEstado(Long idPago, String nombreEstado) {
        Pago pago = buscarPorId(idPago);

        EstadoPago nuevoEstado = estadoPagoRepository.findByNombre(nombreEstado.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Estado de pago no encontrado: " + nombreEstado));

        pago.setEstadoPago(nuevoEstado);

        return pagoRepository.save(pago);
    }

    public void eliminarPago(Long idPago) {
        Pago pago = buscarPorId(idPago);
        pagoRepository.delete(pago);
    }
}