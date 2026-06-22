package cl.duoc.cuponesservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipos_descuento")
public class TipoDescuento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_descuento")
    private Integer idTipoDescuento;

    @Column(name = "nombre", nullable = false, unique = true, length = 30)
    private String nombre;

    public TipoDescuento() {
    }

    public TipoDescuento(Integer idTipoDescuento, String nombre) {
        this.idTipoDescuento = idTipoDescuento;
        this.nombre = nombre;
    }

    public Integer getIdTipoDescuento() {
        return idTipoDescuento;
    }

    public void setIdTipoDescuento(Integer idTipoDescuento) {
        this.idTipoDescuento = idTipoDescuento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}