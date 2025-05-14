package gm.zona_fit.servicio;
import gm.zona_fit.modelo.Cliente;
import gm.zona_fit.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//con esta anotacion  indicamos que esta clase pertece a la fabrica de spring
@Service
//indicamos el nombre de la clase y quee sta implementa de la interface del mismo paquete
public class ClienteServicio implements IClienteServicio {

    //la siguienten anotacion indica que se va  autoimplemetar de la clase de datos que es la antesesora de esta
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = clienteRepositorio.findAll();

        return clientes;
    }

    @Override
    public Cliente buscarClientePorId(Integer idCliente) {

        //el else indica que en caso de que no se encuentra ningun dato
        //el objeto se ponga nulo
        Cliente cliente = clienteRepositorio.findById(idCliente).orElse(null);

        return cliente;
    }

    @Override
    public void guardarCliente(Cliente cliente) {
        clienteRepositorio.save(cliente);
    }

    @Override
    public void eliminarCliente(Cliente cliente) {
        clienteRepositorio.delete(cliente);
    }
}
