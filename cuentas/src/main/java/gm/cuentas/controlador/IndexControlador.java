package gm.cuentas.controlador;

import gm.cuentas.servicio.CuentaServicio;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
@ViewScoped
public class IndexControlador {

    @Autowired
    private CuentaServicio cuentaServicio;
    
}
