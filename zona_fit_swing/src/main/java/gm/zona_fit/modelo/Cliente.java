package gm.zona_fit.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
//con la siguienter notacion de Loombok sirve para que en automatico se generen
//los metodos getter y setter y todo lo del java bean en auto
@Data
//agregamos constructor vacio
@NoArgsConstructor
//constructor con todos los argumentos
@AllArgsConstructor
//Agregamos el tostring
@ToString
//Se4 agrega equals and hash code
@EqualsAndHashCode
public class Cliente {
    @Id
    //usamos generationtype identity en vez de auto por qeu estamos trabajando
    //con datos autoincrementables en la bd y para evitar errores
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //como estamos trabajando con objetos en vez de usar int
    //usamos integer para que su valor sea null y no 0
    private Integer id;
    private String nombre;
    private String apellido;
    private Integer membresia;


}