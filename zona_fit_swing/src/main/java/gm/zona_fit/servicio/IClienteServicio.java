package gm.zona_fit.servicio;
import gm.zona_fit.modelo.Cliente;
import java.util.List;

//en esta interface se define la plantilla de metodos para heredar
//recuerda que esa es la funcion de una interface
//definir metodos genericos qeu puedes implementar despues en clases hijas
public interface IClienteServicio {

    public List<Cliente> listarClientes();


    public Cliente buscarClientePorId(Integer idCliente);

    public void guardarCliente(Cliente cliente);

    public void eliminarCliente(Cliente cliente);



}