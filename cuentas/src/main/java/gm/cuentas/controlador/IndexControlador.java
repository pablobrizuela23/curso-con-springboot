package gm.cuentas.controlador;

import gm.cuentas.modelo.Cuenta;
import gm.cuentas.servicio.CuentaServicio;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;

import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Data
@ViewScoped
public class IndexControlador {

    @Autowired
    private CuentaServicio cuentaServicio;
    private List<Cuenta> cuentas;
    private Cuenta cuentaSeleccionada;

    private static final Logger logger=
           LoggerFactory.getLogger(IndexControlador.class);

    @PostConstruct
    public void init(){
        cargarDatos();
    }

    public void cargarDatos(){
        this.cuentas = cuentaServicio.listarCuenta();
        cuentas.forEach((cuenta) -> logger.info(cuenta.toString()));
    }

    public void agregarCuenta(){
        logger.info("se crea objeto cuentaSeleccionada para el caso de agregar");
        this.cuentaSeleccionada = new Cuenta();
    }

    public void guardarCuenta(){
        logger.info("cuenta a guardar: " + this.cuentaSeleccionada);
        //agregar
        if (this.cuentaSeleccionada.getIdCuenta() == null) {
            this.cuentaServicio.guardarCuenta(this.cuentaSeleccionada);
            this.cuentas.add(this.cuentaSeleccionada);
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Cuenta agregada"));
            
        }else{
            //modificar
            this.cuentaServicio.guardarCuenta(this.cuentaSeleccionada);
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Cuenta modificada"));
        }

        //se oculta la ventana
        PrimeFaces.current().executeScript("PF('ventanaModalCuenta').hide()");
        //actualizamos la ventana
        PrimeFaces.current().ajax().update("forma-cuentas:mensajes","forma-cuentas:cuentas-tabla");

        //reset
        this.cuentaSeleccionada=null;
    }

    public void eliminarCuenta(){
        logger.info("Cuenta a eliminar " + this.cuentaSeleccionada);
        this.cuentaServicio.eliminarCuenta(this.cuentaSeleccionada);
        //eliminar el registro de la lista
        this.cuentas.remove(this.cuentaSeleccionada);
        //reseteamos el objeto seleccionado de la tabla
        this.cuentaSeleccionada=null;
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Cuenta eliminada"));
        PrimeFaces.current().ajax().update("forma-cuentas:mensajes",
                "forma-cuentas:cuentas-tabla");
    }

}
