package cl.duoc.cuponesservice.dto;

import jakarta.validation.constraints.NotBlank;

public class ActualizarEstadoCuponRequest {

    @NotBlank(message = "El estado del cupon es obligatorio")
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
