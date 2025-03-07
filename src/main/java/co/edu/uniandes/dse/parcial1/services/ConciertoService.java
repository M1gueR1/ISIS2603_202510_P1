package co.edu.uniandes.dse.parcial1.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoService {

    // Complete
    @Autowired
	ConciertoRepository conciertoRepository;

    @Transactional
	public ConciertoEntity createConcierto(ConciertoEntity concierto) throws IllegalOperationException {
		log.info("Inicia proceso de creación de un concierto");

        LocalDateTime tiempoHoy = LocalDateTime.now();
        Duration diferencia = Duration.between(tiempoHoy, concierto.getFechaConcierto());
        
        if(diferencia.isNegative()){
            throw new IllegalOperationException("La fecha del concierto no puede estar en el apsado");
        }
        if(concierto.getCapacidadMaxima() < 10){
            throw new IllegalOperationException("La capacidad del concierto debe superar 10 personas");
        }
        if(concierto.getPresupuesto() < 1000){
            throw new IllegalOperationException("El presupuesto no supera los 1000 dolares");
        }
		return conciertoRepository.save(concierto);
	}

}
