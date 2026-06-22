package cl.duoc.cuponesservice.repository;

import cl.duoc.cuponesservice.model.EstadoCupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoCuponRepository extends JpaRepository<EstadoCupon, Integer> {

    Optional<EstadoCupon> findByNombre(String nombre);
}
