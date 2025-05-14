package gm.zona_fit.controlador;

import gm.zona_fit.modelo.Cliente;
import gm.zona_fit.servicio.IClienteServicio;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.primefaces.PrimeFaces;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import org.slf4j.Logger;

@Component
@Data
@ViewScoped
public class IndexControlador {

    @Autowired
    IClienteServicio clienteServicio;
    private List<Cliente> clientes;
    private Cliente clienteSeleccionado;
    //constante
    private static final Logger logger = LoggerFactory.getLogger
            (IndexControlador.class);

    @PostConstruct
    public void init() {
        cargarDatos();

    }

    public void cargarDatos() {

        this.clientes = this.clienteServicio.listarClientes();
        this.clientes.forEach(cliente -> logger.info(cliente.toString()));
    }

    public void agregarCliente() {
        this.clienteSeleccionado = new Cliente();


    }

    public void guardarCliente() {
        logger.info("cliente a guardar: " + this.clienteSeleccionado);

        //funcion de agregar
        //SI EL CLIENTE SELECCIONBADO ES NULO SIGNIFICA QUE ES UN NUEVO REGISTRO
        if (this.clienteSeleccionado.getId() == null) {

            this.clienteServicio.guardarCliente(this.clienteSeleccionado);
            this.clientes.add(this.clienteSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cliente Agregado"));


            //UPDATE
            //SI EL CLEINTE SELECCIONADO TIENE DATOS SIGNIFICA QUE YA HAY REGISTRO Y ES UPDATE
        } else {
            this.clienteServicio.guardarCliente(this.clienteSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cliente Actualizado"));


        }
        //ocultar ventana modal
        PrimeFaces.current().executeScript("PF('ventanaModalCliente').hide()");
        //Actualizamos la tabla con AJAX
        PrimeFaces.current().ajax().update("forma-clientes:mensaje", "forma-clientes:clientes-tabla");
        //Reset del objeto clienteSeleccionado
        this.clienteSeleccionado = null;


    }

    public void eliminarCliente() {


        logger.info("Cliente a eliminar: " + this.clienteSeleccionado);
        //estos metodos son de tu backend de spring ya estan programados desde tu proyecto pasado
        this.clienteServicio.eliminarCliente(this.clienteSeleccionado);

        //eliminamos al cliente solo de la tabla para no refrescar toda la bd asi se ahorran recursos
        this.clientes.remove(this.clienteSeleccionado);
        //reset de la variable clienteseleccionado
        //si no se hace esto podriann surgir errores ya que se quedaria guardado el id anterior
        this.clienteSeleccionado = null;
        //se manda mensaje
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cliente Eliminado"));
        PrimeFaces.current().ajax().update("forma-clientes:mensajes",
                "forma-clientes:clientes-tabla");

    }

}