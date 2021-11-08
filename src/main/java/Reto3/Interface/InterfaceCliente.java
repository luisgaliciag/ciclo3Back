package Reto3.Interface;

import Reto3.Modelo.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface InterfaceCliente extends CrudRepository<Cliente, Integer> {

}
