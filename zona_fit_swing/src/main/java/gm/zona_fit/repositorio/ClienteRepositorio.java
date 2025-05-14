package gm.zona_fit.repositorio;


import gm.zona_fit.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

//la clase de la cual extiende esta en este caso es un archivo del framework de spring
//en esta clase vienen varios metodos genericos que se puden usar como seleccionar todo
//entre otros metodos, para revisar a mas detalle puedes entrar a la clase y revisarla
//Esta clase de la que extendemos tiene muchos metodos genericos y simnples para un repositorio
//interface publica, nombre interf, extiende,           clase de java bean, tipo de dato del ID
public interface ClienteRepositorio extends JpaRepository<Cliente,Integer>{


}