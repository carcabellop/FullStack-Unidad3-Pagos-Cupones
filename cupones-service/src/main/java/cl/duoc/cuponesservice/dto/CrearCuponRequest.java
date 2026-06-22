package cl.duoc.cuponesservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CrearCuponRequest {

    @NotBlank(message = "El codigo del cupon es obligatorio")
    private String codigo;

    @NotBlank(message = "La descripcion del cupon es obligatoria")
    private String descripcion;

    @NotNull(message = "El tipo de descuento es obligatorio")
    private Integer idTipoDescuento;

    @NotNull(message = "El valor del descuento es obligatorio")
    @DecimalMin(value = "1.0", message = "El valor del descuento debe ser mayor a cero")
    private BigDecimal valorDescuento;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;

    @NotNull(message = "El uso maximo es obligatorio")
    @Min(value = 1, message = "El uso maximo debe ser al menos 1")
    private Integer usoMaximo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdTipoDescuento() {
        return idTipoDescuento;
    }

    public void setIdTipoDescuento(Integer idTipoDescuento) {
        this.idTipoDescuento = idTipoDescuento;
    }

    public BigDecimal getValorDescuento() {
        return valorDescuento;
    }

    public void setValorDescuento(BigDecimal valorDescuento) {
        this.valorDescuento = valorDescuento;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getUsoMaximo() {
        return usoMaximo;
    }

    public void setUsoMaximo(Integer usoMaximo) {
        this.usoMaximo = usoMaximo;
    }
}
