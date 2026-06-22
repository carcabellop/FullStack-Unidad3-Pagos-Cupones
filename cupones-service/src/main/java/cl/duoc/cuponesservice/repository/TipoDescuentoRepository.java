package cl.duoc.cuponesservice.repository;

import cl.duoc.cuponesservice.model.TipoDescuento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoDescuentoRepository extends JpaRepository<TipoDescuento, Integer> {

    Optional<TipoDescuento> findByNombre(String nombre);
}
