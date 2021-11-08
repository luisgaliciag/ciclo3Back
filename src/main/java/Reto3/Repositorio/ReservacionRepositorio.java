package Reto3.Repositorio;

import Reto3.Interface.InterfaceReservacion;
import Reto3.Modelo.Cliente;
import Reto3.Modelo.Reservacion;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReservacionRepositorio {

    @Autowired
    private InterfaceReservacion crud4;

    public List<Reservacion> getAll() {
        return (List<Reservacion>) crud4.findAll();
    }

    public Optional<Reservacion> getReservation(int id) {
        return crud4.findById(id);
    }

    public Reservacion save(Reservacion reservation) {
        return crud4.save(reservation);
    }

    public void delete(Reservacion reservacion) {
        crud4.delete(reservacion);
    }

    public List<Reservacion> reservacionStatusRepositorio(String status){
        return crud4.findAllByStatus(status);
    }
    
    public List<Reservacion> reservacionTiempoRepositorio(Date a, Date b){
        return crud4.findAllByStartDateAfterAndStartDateBefore(a, b);
    }
    
    public List<contadorClientes> getClientesRepositorio(){
        List<contadorClientes> res = new ArrayList<>();
        List<Object[]> report = crud4.countTotalReservationsByClient();
        for(int i=0; i<report.size(); i++){
            res.add(new contadorClientes((Long)report.get(i)[1],(Cliente) report.get(i)[0]));
        }
        return res;
    }
    
}
