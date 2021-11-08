package Reto3.Interface;

import Reto3.Modelo.Reservacion;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface InterfaceReservacion extends CrudRepository<Reservacion, Integer> {
    public List<Reservacion> findAllByStatus (String status);
    
    public List<Reservacion> findAllByStartDateAfterAndStartDateBefore (Date dateOne, Date dateTwo);
    
    @Query ("SELECT c.client, COUNT(c.client) FROM Reservacion AS c GROUP BY c.client ORDER BY COUNT(c.client)DESC")
    public List<Object[]> countTotalReservationsByClient();
    
}
