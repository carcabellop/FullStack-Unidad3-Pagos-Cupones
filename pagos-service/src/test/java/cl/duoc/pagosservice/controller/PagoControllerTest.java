package cl.duoc.pagosservice.controller;

import cl.duoc.pagosservice.dto.CrearPagoRequest;
import cl.duoc.pagosservice.model.EstadoPago;
import cl.duoc.pagosservice.model.MetodoPago;
import cl.duoc.pagosservice.model.Pago;
import cl.duoc.pagosservice.service.PagoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PagoControllerTest {

    private PagoService pagoService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        pagoService = Mockito.mock(PagoService.class);
        PagoController pagoController = new PagoController(pagoService);

        mockMvc = MockMvcBuilders.standaloneSetup(pagoController).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void listarPagosDebeRetornarOk() throws Exception {
        Pago pago = new Pago(
                1L,
                10L,
                new MetodoPago(1, "EFECTIVO"),
                new EstadoPago(1, "PENDIENTE"),
                new BigDecimal("5000"),
                LocalDateTime.of(2026, 6, 22, 10, 0),
                "TX-001"
        );

        when(pagoService.listarPagos()).thenReturn(List.of(pago));

        mockMvc.perform(get("/api/pagos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPago").value(1))
                .andExpect(jsonPath("$[0].pedidoId").value(10))
                .andExpect(jsonPath("$[0].estadoPago.nombre").value("PENDIENTE"));
    }

    @Test
    void crearPagoDebeRetornarCreated() throws Exception {
        CrearPagoRequest request = new CrearPagoRequest();
        request.setPedidoId(20L);
        request.setIdMetodoPago(1);
        request.setMonto(new BigDecimal("12000"));
        request.setCodigoTransaccion("TX-002");

        Pago pagoCreado = new Pago(
                2L,
                20L,
                new MetodoPago(1, "TARJETA_DEBITO"),
                new EstadoPago(1, "PENDIENTE"),
                new BigDecimal("12000"),
                LocalDateTime.of(2026, 6, 22, 11, 0),
                "TX-002"
        );

        when(pagoService.crearPago(any(CrearPagoRequest.class))).thenReturn(pagoCreado);

        mockMvc.perform(post("/api/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idPago").value(2))
                .andExpect(jsonPath("$.pedidoId").value(20))
                .andExpect(jsonPath("$.metodoPago.nombre").value("TARJETA_DEBITO"))
                .andExpect(jsonPath("$.estadoPago.nombre").value("PENDIENTE"));
    }
}