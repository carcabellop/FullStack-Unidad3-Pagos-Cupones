package cl.duoc.cuponesservice.service;

import cl.duoc.cuponesservice.dto.CrearCuponRequest;
import cl.duoc.cuponesservice.model.Cupon;
import cl.duoc.cuponesservice.model.EstadoCupon;
import cl.duoc.cuponesservice.model.TipoDescuento;
import cl.duoc.cuponesservice.repository.CuponRepository;
import cl.duoc.cuponesservice.repository.EstadoCuponRepository;
import cl.duoc.cuponesservice.repository.TipoDescuentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CuponServiceTest {

    private CuponRepository cuponRepository;
    private EstadoCuponRepository estadoCuponRepository;
    private TipoDescuentoRepository tipoDescuentoRepository;
    private CuponService cuponService;

    @BeforeEach
    void setUp() {
        cuponRepository = Mockito.mock(CuponRepository.class);
        estadoCuponRepository = Mockito.mock(EstadoCuponRepository.class);
        tipoDescuentoRepository = Mockito.mock(TipoDescuentoRepository.class);

        cuponService = new CuponService(
                cuponRepository,
                estadoCuponRepository,
                tipoDescuentoRepository
        );
    }

    @Test
    void crearCuponDebeGuardarCuponActivo() {
        CrearCuponRequest request = new CrearCuponRequest();
        request.setCodigo("BIENVENIDA10");
        request.setDescripcion("Descuento de bienvenida");
        request.setIdTipoDescuento(1);
        request.setValorDescuento(new BigDecimal("10"));
        request.setFechaInicio(LocalDate.of(2026, 6, 22));
        request.setFechaFin(LocalDate.of(2026, 12, 31));
        request.setUsoMaximo(100);

        TipoDescuento tipoDescuento = new TipoDescuento(1, "PORCENTAJE");
        EstadoCupon estadoCupon = new EstadoCupon(1, "ACTIVO");

        when(cuponRepository.existsByCodigo("BIENVENIDA10")).thenReturn(false);
        when(tipoDescuentoRepository.findById(1)).thenReturn(Optional.of(tipoDescuento));
        when(estadoCuponRepository.findByNombre("ACTIVO")).thenReturn(Optional.of(estadoCupon));

        when(cuponRepository.save(any(Cupon.class))).thenAnswer(invocation -> {
            Cupon cupon = invocation.getArgument(0);
            cupon.setIdCupon(1L);
            return cupon;
        });

        Cupon resultado = cuponService.crearCupon(request);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdCupon());
        assertEquals("BIENVENIDA10", resultado.getCodigo());
        assertEquals("PORCENTAJE", resultado.getTipoDescuento().getNombre());
        assertEquals("ACTIVO", resultado.getEstadoCupon().getNombre());
        assertEquals(new BigDecimal("10"), resultado.getValorDescuento());
        assertEquals(0, resultado.getUsosActuales());
    }
}
