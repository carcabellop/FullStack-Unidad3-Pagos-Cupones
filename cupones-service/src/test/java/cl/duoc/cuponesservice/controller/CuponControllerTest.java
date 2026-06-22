package cl.duoc.cuponesservice.controller;

import cl.duoc.cuponesservice.dto.CrearCuponRequest;
import cl.duoc.cuponesservice.model.Cupon;
import cl.duoc.cuponesservice.model.EstadoCupon;
import cl.duoc.cuponesservice.model.TipoDescuento;
import cl.duoc.cuponesservice.service.CuponService;
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
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CuponControllerTest {

    private CuponService cuponService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        cuponService = Mockito.mock(CuponService.class);
        CuponController cuponController = new CuponController(cuponService);

        mockMvc = MockMvcBuilders.standaloneSetup(cuponController).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void listarCuponesDebeRetornarOk() throws Exception {
        Cupon cupon = new Cupon(
                1L,
                "BIENVENIDA10",
                "Descuento de bienvenida",
                new TipoDescuento(1, "PORCENTAJE"),
                new BigDecimal("10"),
                LocalDate.of(2026, 6, 22),
                LocalDate.of(2026, 12, 31),
                new EstadoCupon(1, "ACTIVO"),
                100,
                0
        );

        when(cuponService.listarCupones()).thenReturn(List.of(cupon));

        mockMvc.perform(get("/api/cupones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idCupon").value(1))
                .andExpect(jsonPath("$[0].codigo").value("BIENVENIDA10"))
                .andExpect(jsonPath("$[0].estadoCupon.nombre").value("ACTIVO"));
    }

    @Test
    void crearCuponDebeRetornarCreated() throws Exception {
        CrearCuponRequest request = new CrearCuponRequest();
        request.setCodigo("VERANO20");
        request.setDescripcion("Descuento de verano");
        request.setIdTipoDescuento(1);
        request.setValorDescuento(new BigDecimal("20"));
        request.setFechaInicio(LocalDate.of(2026, 6, 22));
        request.setFechaFin(LocalDate.of(2026, 12, 31));
        request.setUsoMaximo(50);

        Cupon cuponCreado = new Cupon(
                2L,
                "VERANO20",
                "Descuento de verano",
                new TipoDescuento(1, "PORCENTAJE"),
                new BigDecimal("20"),
                LocalDate.of(2026, 6, 22),
                LocalDate.of(2026, 12, 31),
                new EstadoCupon(1, "ACTIVO"),
                50,
                0
        );

        when(cuponService.crearCupon(any(CrearCuponRequest.class))).thenReturn(cuponCreado);

        mockMvc.perform(post("/api/cupones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idCupon").value(2))
                .andExpect(jsonPath("$.codigo").value("VERANO20"))
                .andExpect(jsonPath("$.tipoDescuento.nombre").value("PORCENTAJE"))
                .andExpect(jsonPath("$.estadoCupon.nombre").value("ACTIVO"));
    }
}
