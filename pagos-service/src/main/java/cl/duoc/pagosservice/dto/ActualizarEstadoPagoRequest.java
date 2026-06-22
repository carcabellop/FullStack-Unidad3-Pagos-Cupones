package cl.duoc.pagosservice.dto;

import jakarta.validation.constraints.NotBlank;

public class ActualizarEstadoPagoRequest {

    @NotBlank(message = "El estado del pago es obligatorio")
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}