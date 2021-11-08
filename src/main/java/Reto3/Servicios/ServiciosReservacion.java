package Reto3.Servicios;

import Reto3.Modelo.Reservacion;
import Reto3.Repositorio.ReservacionRepositorio;
import Reto3.Repositorio.contadorClientes;
import Reto3.Repositorio.statusReservas;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author LuisGalicia
 */
@Service
public class ServiciosReservacion {

    @Autowired
    private ReservacionRepositorio metodosCrud;

    /**
     * 
     * @return getAll
     */
    public List<Reservacion> getAll() {
        return metodosCrud.getAll();
    }
    
    /**
     * 
     * @return getReservation
     */
    public Optional<Reservacion> getReservation(int reservationId) {
        return metodosCrud.getReservation(reservationId);
    }

    /**
     * 
     * @return save
     */
    public Reservacion save(Reservacion reservation) {
        if (reservation.getIdReservation() == null) {
            return metodosCrud.save(reservation);
        } else {
            Optional<Reservacion> e = metodosCrud.getReservation(reservation.getIdReservation());
            if (e.isEmpty()) {
                return metodosCrud.save(reservation);
            } else {
                return reservation;
            }
        }
    }

    /**
     * 
     * @return Reservation update 
     */
    public Reservacion update(Reservacion reservacion) {
        if (reservacion.getIdReservation() != null) {
            Optional<Reservacion> e = metodosCrud.getReservation(reservacion.getIdReservation());
            if (!e.isEmpty()) {

                if (reservacion.getStartDate() != null) {
                    e.get().setStartDate(reservacion.getStartDate());
                }
                if (reservacion.getDevolutionDate() != null) {
                    e.get().setDevolutionDate(reservacion.getDevolutionDate());
                }
                if (reservacion.getStatus() != null) {
                    e.get().setStatus(reservacion.getStatus());
                }
                metodosCrud.save(e.get());
                return e.get();
            } else {
                return reservacion;
            }
        } else {
            return reservacion;
        }
    }

    /**
     * 
     * @return Reservation delete 
     */
    public boolean deleteReservation(int reservationId) {
        Boolean aBoolean = getReservation(reservationId).map(reservation -> {
            metodosCrud.delete(reservation);
            return true;
        }).orElse(false);
        return aBoolean;
    }
    
    /**
     * 
     * REPORTES
     */
    public statusReservas reporteStatusServicio(){
        List<Reservacion> completed = metodosCrud.reservacionStatusRepositorio("completed");
        List<Reservacion> cancelled = metodosCrud.reservacionStatusRepositorio("cancelled");
        return new statusReservas(completed.size(), cancelled.size());
    }

    public List<Reservacion> reporteTiempoServicio(String datoA, String datoB){
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Date datoUno = new Date();
        Date datoDos = new Date();
        
        try{
            datoUno = parser.parse(datoA);
            datoDos = parser.parse(datoB);
        }catch(ParseException evt){
            evt.printStackTrace();
        }if(datoUno.before(datoDos)){
            return metodosCrud.reservacionTiempoRepositorio(datoUno, datoDos);
        }else{
            return new ArrayList<>();
        }
    }
    
    public List<contadorClientes> reporteClientesServicio(){
        return metodosCrud.getClientesRepositorio();
    }
    
}
