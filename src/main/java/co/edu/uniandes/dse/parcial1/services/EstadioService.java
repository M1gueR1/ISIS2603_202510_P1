package co.edu.uniandes.dse.parcial1.services;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EstadioService {

    // Complete
    @Autowired
	EstadioRepository estadioRepository;

    @Transactional
	public EstadioEntity createEstadio(EstadioEntity estadio) throws IllegalOperationException {
		log.info("Inicia proceso de creación de un estadio");


        if(estadio.getNombreCiudad().length()< 3 ){
            throw new IllegalOperationException("La longitud del nombre de la ciudad es emnro a 3");
        }
        if(estadio.getCapacidadMaxima() < 1000){
            throw new IllegalOperationException("La capacidad maxima no supera los 1000");
        }
        if(estadio.getPrecioAlquiler() < 100000){
            throw new IllegalOperationException("El presupuesto no supera");
        }
		return estadioRepository.save(estadio);
	}

}
