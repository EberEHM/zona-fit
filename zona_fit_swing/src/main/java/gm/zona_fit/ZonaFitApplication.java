package gm.zona_fit;

import gm.zona_fit.modelo.Cliente;
import gm.zona_fit.servicio.IClienteServicio;
import org.hibernate.resource.beans.internal.BeansMessageLogger_$logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;
//esta notacion es la que activa el springboot
//@SpringBootApplication
public class ZonaFitApplication implements CommandLineRunner {
	//inyeccion de dependecias de la clase antesesora servicio
	@Autowired
	private IClienteServicio clienteServicio;

	//recuarda que estos atributos son constantes con sus atributos
	//la siguiente linea sirve para poder mandar infromacion a consola
	private static final Logger logger = LoggerFactory.getLogger(ZonaFitApplication.class);
	//nl sera igual al salto de linea asi que se guarda en nl para usarlo facil
	String nl = System.lineSeparator();

	public static void main(String[] args) {
		logger.info("Iniciando la aplicacion");

		//levantar la fabrica de spring y clases necesarias
		//tambien se llama y ejecuta el metodo run que impoortamos
		SpringApplication.run(ZonaFitApplication.class, args);


	}

	@Override
	public void run(String... args) throws Exception {
		//Ahora en este metodo run se manda llamar otro metodo
		//este metodo contiene toda la logica de la app
		zonaFitApp();
	}

	//metodo zonafit
	private void zonaFitApp(){
		logger.info(nl+"%%%%%%%%%%aplicacion zona fit%%%%%"+nl);
	var salir = false;
	var consola = new Scanner(System.in);

	while (!salir){

		var opcion = mostrarMenu(consola);
		salir = ejecutarOpciones(consola, opcion);
		logger.info(nl);
	}

	}

	//recuerda quee stos son metodos, recuerda qeu estos se componen por...:
	//private: el nivel de seguridad del metodo
	//int: el tipo de dato que retorna si es que retorna alguno
	//nombreDelMetodo
	//y dentro del metodo los atributos que retorna junto con su tipo de dato
	//en este caso es un tipo Scanner
	private int mostrarMenu(Scanner consola){
		logger.info("" +
				"1. Listar clientes "+nl +
				"2. Buscar cliente " +nl+
				"3. Agregar cliente " +nl+
				"4. Modificar cliente " +nl+
				"5. Eliminar cliente " +nl+
				"6. Salir" +nl+
				"Elige una opcion: ");

		 return   Integer.parseInt(consola.nextLine());


	}

	/// ////////////////Para leer las opciones////////////////////////////////
	private boolean ejecutarOpciones(Scanner consola, int opcion){
	var salir = false;



	switch (opcion){

		case 1 -> {
			logger.info(nl+"--------LISTADO DE CLIENTES-------------"+nl);
			List<Cliente> clientes = clienteServicio.listarClientes();
			//Nos permite imprimir en consola la lista clientes
			//iteramos con foreach usando funcion landa y toString
			clientes.forEach(cliente -> logger.info(cliente.toString()));

		}

		case 2 ->{
			logger.info(nl+"--------BUSCAR CLIENTE POR ID-------------"+nl);
			var idCliente = Integer.parseInt(consola.nextLine());
			Cliente cliente = clienteServicio.buscarClientePorId(idCliente);

			if (cliente != null)
				logger.info("Cliente encontrado "+ cliente);
			else
				logger.info("Cliente no encontrado"+ cliente);


		}

	case 3 -> {
			logger.info("---------Agregar cliente--------------");
			logger.info("Nombre:");
			var nombre = consola.nextLine();
			logger.info("Apellido");
			var apellido = consola.nextLine();
			logger.info("Membresia");
			var membresia = Integer.parseInt(consola.nextLine());
			var cliente = new Cliente();
			cliente.setNombre(nombre);
			cliente.setApellido(apellido);
			cliente.setMembresia(membresia);
			clienteServicio.guardarCliente(cliente);
			logger.info("Cliente agregado "+ cliente );


	}
	case 4 -> {
			logger.info("--------------Modificar cliente----------------");
			logger.info("Ingresa el ID");
			var idCliente = Integer.parseInt(consola.nextLine());
			Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
			if (cliente != null){

				logger.info("Nombre: ");
				var nombre = consola.nextLine();
				logger.info("Apellido: ");
				var apellido = consola.nextLine();
				logger.info("Membresia: ");
				var membresia = Integer.parseInt(consola.nextLine());
				cliente.setNombre(nombre);
				cliente.setApellido(apellido);
				cliente.setMembresia(membresia);
				clienteServicio.guardarCliente(cliente);
				logger.info("Cliente modificado " + cliente);


			}else {

				logger.info("No se encontro el registro");

			}



	}

	case 5 ->{

			logger.info("-----Eliminar cliente-------------");
			logger.info("Ingresa el ID a eliminar: ");
			var idCliente = Integer.parseInt(consola.nextLine());
			var cliente = clienteServicio.buscarClientePorId(idCliente);
			if (cliente != null){
				clienteServicio.eliminarCliente(cliente);
				logger.info("Cliente eliminado "+cliente);
			} else {

				logger.info("Cliente no encontrado ");

			}


	}
	case 6 -> {

			logger.info("Hasta pronto !!  ");
			salir = true;


		}
	}

		return salir;




	}

}
