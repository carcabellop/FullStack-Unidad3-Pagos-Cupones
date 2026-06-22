package cl.duoc.cuponesservice.service;

import cl.duoc.cuponesservice.dto.CrearCuponRequest;
import cl.duoc.cuponesservice.model.Cupon;
import cl.duoc.cuponesservice.model.EstadoCupon;
import cl.duoc.cuponesservice.model.TipoDescuento;
import cl.duoc.cuponesservice.repository.CuponRepository;
import cl.duoc.cuponesservice.repository.EstadoCuponRepository;
import cl.duoc.cuponesservice.repository.TipoDescuentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuponService {

    private final CuponRepository cuponRepository;
    private final EstadoCuponRepository estadoCuponRepository;
    private final TipoDescuentoRepository tipoDescuentoRepository;

    public CuponService(CuponRepository cuponRepository,
                        EstadoCuponRepository estadoCuponRepository,
                        TipoDescuentoRepository tipoDescuentoRepository) {
        this.cuponRepository = cuponRepository;
        this.estadoCuponRepository = estadoCuponRepository;
        this.tipoDescuentoRepository = tipoDescuentoRepository;
    }

    public List<Cupon> listarCupones() {
        return cuponRepository.findAll();
    }

    public Cupon buscarPorId(Long idCupon) {
        return cuponRepository.findById(idCupon)
                .orElseThrow(() -> new RuntimeException("Cupon no encontrado con ID: " + idCupon));
    }

    public Cupon buscarPorCodigo(String codigo) {
        return cuponRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Cupon no encontrado con codigo: " + codigo));
    }

    public Cupon crearCupon(CrearCuponRequest request) {
        if (cuponRepository.existsByCodigo(request.getCodigo())) {
            throw new RuntimeException("Ya existe un cupon con el codigo: " + request.getCodigo());
        }

        TipoDescuento tipoDescuento = tipoDescuentoRepository.findById(request.getIdTipoDescuento())
                .orElseThrow(() -> new RuntimeException("Tipo de descuento no encontrado"));

        EstadoCupon estadoActivo = estadoCuponRepository.findByNombre("ACTIVO")
                .orElseThrow(() -> new RuntimeException("Estado ACTIVO no existe en la base de datos"));

        Cupon cupon = new Cupon();
        cupon.setCodigo(request.getCodigo().toUpperCase());
        cupon.setDescripcion(request.getDescripcion());
        cupon.setTipoDescuento(tipoDescuento);
        cupon.setValorDescuento(request.getValorDescuento());
        cupon.setFechaInicio(request.getFechaInicio());
        cupon.setFechaFin(request.getFechaFin());
        cupon.setEstadoCupon(estadoActivo);
        cupon.setUsoMaximo(request.getUsoMaximo());
        cupon.setUsosActuales(0);

        return cuponRepository.save(cupon);
    }

    public Cupon actualizarEstado(Long idCupon, String nombreEstado) {
        Cupon cupon = buscarPorId(idCupon);

        EstadoCupon nuevoEstado = estadoCuponRepository.findByNombre(nombreEstado.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Estado de cupon no encontrado: " + nombreEstado));

        cupon.setEstadoCupon(nuevoEstado);

        return cuponRepository.save(cupon);
    }

    public Cupon registrarUso(String codigo) {
        Cupon cupon = buscarPorCodigo(codigo.toUpperCase());

        if (cupon.getUsosActuales() >= cupon.getUsoMaximo()) {
            throw new RuntimeException("El cupon alcanzo su limite de usos");
        }

        cupon.setUsosActuales(cupon.getUsosActuales() + 1);

        return cuponRepository.save(cupon);
    }

    public void eliminarCupon(Long idCupon) {
        Cupon cupon = buscarPorId(idCupon);
        cuponRepository.delete(cupon);
    }
}
