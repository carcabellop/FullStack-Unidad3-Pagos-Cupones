package cl.duoc.cuponesservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "estados_cupon")
public class EstadoCupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_cupon")
    private Integer idEstadoCupon;

    @Column(name = "nombre", nullable = false, unique = true, length = 30)
    private String nombre;

    public EstadoCupon() {
    }

    public EstadoCupon(Integer idEstadoCupon, String nombre) {
        this.idEstadoCupon = idEstadoCupon;
        this.nombre = nombre;
    }

    public Integer getIdEstadoCupon() {
        return idEstadoCupon;
    }

    public void setIdEstadoCupon(Integer idEstadoCupon) {
        this.idEstadoCupon = idEstadoCupon;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
