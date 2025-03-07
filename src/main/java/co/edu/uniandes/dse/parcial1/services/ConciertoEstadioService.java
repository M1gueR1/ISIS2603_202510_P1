package co.edu.uniandes.dse.parcial1.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoEstadioService {

    // Complete
    @Autowired
	private EstadioRepository estadioRepository;

	@Autowired
	private ConciertoRepository conciertoRepository;

    @Transactional
	public ConciertoEntity addConcierto(Long conciertoId, Long estadioId) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de asociarle un concierto al estadio con id = {0}", estadioId);
		Optional<ConciertoEntity> conciertoEntity = conciertoRepository.findById(conciertoId);
		Optional<EstadioEntity> estadioEntity = estadioRepository.findById(estadioId);

		if (conciertoEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró el concierto");

		if (estadioEntity.isEmpty())
			throw new EntityNotFoundException("No se encontró al estadio");
        if(estadioEntity.get().getCapacidadMaxima() < conciertoEntity.get().getCapacidadMaxima() ){
            throw new IllegalOperationException("La capacidad del concierto supera la del estadio");
        }
        if(estadioEntity.get().getPrecioAlquiler() > conciertoEntity.get().getPresupuesto() ){
            throw new IllegalOperationException("El precio del alquiler supera al presupueto del concierto");
        }
        //boolean siHayTiempo = true;
        List<ConciertoEntity> conciertos = estadioEntity.get().getConciertosProgramados();
        if(conciertos.size() >0){
            LocalDateTime primeraFecha = conciertos.get(0).getFechaConcierto();
            if(conciertos.size() > 1){
                for(int i = 1; i < conciertos.size(); i++){
                    ConciertoEntity conc = conciertos.get(i);
                    Duration duracion = Duration.between(primeraFecha, conc.getFechaConcierto());
                    if(duracion.getSeconds() < 172800){
                        throw new IllegalOperationException("nO EXISTE UNA DIFERENCIA DE 2 DIAS MINIMOS");
            
                    }
                }
            }
        }
        
        
        estadioEntity.get().getConciertosProgramados().add(conciertoEntity.get());
		
		log.info("Termina proceso de asociarle un concierto al estadio con id = {0}", estadioId);
		return conciertoEntity.get();
	}

}
