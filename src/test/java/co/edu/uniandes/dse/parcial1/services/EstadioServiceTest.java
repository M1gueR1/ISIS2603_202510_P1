package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({EstadioService.class})
public class EstadioServiceTest {

    @Autowired
	private EstadioService estadioService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<EstadioEntity> estadioList = new ArrayList<>();

    @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

    private void clearData() {
		entityManager.getEntityManager().createQuery("delete from EstadioEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from ConciertoEntity").executeUpdate();
	}

    private void insertData() {
		for (int i = 0; i < 3; i++) {
			EstadioEntity estadioEntity = factory.manufacturePojo(EstadioEntity.class);
			entityManager.persist(estadioEntity);
			estadioList.add(estadioEntity);
		}
	}

    @Test
	void testCreateEstadio() throws IllegalOperationException {
		EstadioEntity newEntity = estadioList.get(0);
        newEntity.setCapacidadMaxima(10000000);
		newEntity.setNombreCiudad("BogotaaaaaLinda");
        newEntity.setPrecioAlquiler(1000000000L);
        newEntity.setConciertosProgramados(new ArrayList<ConciertoEntity>());
        
		EstadioEntity result = estadioService.createEstadio(newEntity);

		assertNotNull(result);
        EstadioEntity entity = entityManager.find(EstadioEntity.class, result.getId());

		assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getCapacidadMaxima(), entity.getCapacidadMaxima());
        assertEquals(newEntity.getNombreCiudad(), entity.getNombreCiudad());
        assertEquals(newEntity.getPrecioAlquiler(), entity.getPrecioAlquiler());

		
	}

    @Test
	void testCreateConciertoMal() throws IllegalOperationException {
		
		assertThrows(IllegalOperationException.class, ()->{
            EstadioEntity newEntity = factory.manufacturePojo(EstadioEntity.class);
            newEntity.setCapacidadMaxima(1);
            newEntity.setPrecioAlquiler(1000000L);
            newEntity.setConciertosProgramados(new ArrayList<ConciertoEntity>());
            newEntity.setNombreCiudad("BogotaaaaaaaLinda");
            estadioService.createEstadio(newEntity);
		});

		
	}

}