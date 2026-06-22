package cl.duoc.pagosservice.service;

import cl.duoc.pagosservice.dto.CrearPagoRequest;
import cl.duoc.pagosservice.model.EstadoPago;
import cl.duoc.pagosservice.model.MetodoPago;
import cl.duoc.pagosservice.model.Pago;
import cl.duoc.pagosservice.repository.EstadoPagoRepository;
import cl.duoc.pagosservice.repository.MetodoPagoRepository;
import cl.duoc.pagosservice.repository.PagoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PagoServiceTest {

    private PagoRepository pagoRepository;
    private MetodoPagoRepository metodoPagoRepository;
    private EstadoPagoRepository estadoPagoRepository;
    private PagoService pagoService;

    @BeforeEach
    void setUp() {
        pagoRepository = Mockito.mock(PagoRepository.class);
        metodoPagoRepository = Mockito.mock(MetodoPagoRepository.class);
        estadoPagoRepository = Mockito.mock(EstadoPagoRepository.class);

        pagoService = new PagoService(
                pagoRepository,
                metodoPagoRepository,
                estadoPagoRepository
        );
    }

    @Test
    void crearPagoDebeGuardarPagoPendiente() {
        CrearPagoRequest request = new CrearPagoRequest();
        request.setPedidoId(10L);
        request.setIdMetodoPago(1);
        request.setMonto(new BigDecimal("15000"));
        request.setCodigoTransaccion("TX-001");

        MetodoPago metodoPago = new MetodoPago(1, "TARJETA_DEBITO");
        EstadoPago estadoPago = new EstadoPago(1, "PENDIENTE");

        when(metodoPagoRepository.findById(1)).thenReturn(Optional.of(metodoPago));
        when(estadoPagoRepository.findByNombre("PENDIENTE")).thenReturn(Optional.of(estadoPago));

        when(pagoRepository.save(any(Pago.class))).thenAnswer(invocation -> {
            Pago pago = invocation.getArgument(0);
            pago.setIdPago(1L);
            return pago;
        });

        Pago resultado = pagoService.crearPago(request);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdPago());
        assertEquals(10L, resultado.getPedidoId());
        assertEquals("TARJETA_DEBITO", resultado.getMetodoPago().getNombre());
        assertEquals("PENDIENTE", resultado.getEstadoPago().getNombre());
        assertEquals(new BigDecimal("15000"), resultado.getMonto());
    }
}