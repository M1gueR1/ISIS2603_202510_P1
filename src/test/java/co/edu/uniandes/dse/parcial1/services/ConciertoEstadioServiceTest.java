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
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({EstadioService.class, ConciertoService.class, ConciertoEstadioService.class})
public class ConciertoEstadioServiceTest {

    @Autowired
    private EstadioService estadioService;

    @Autowired
    private ConciertoEstadioService conciertoEstadioService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<EstadioEntity> estadioList = new ArrayList<>();
    private List<ConciertoEntity> conciertoList = new ArrayList<>();

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
    void testAddConcierto() throws IllegalOperationException, EntityNotFoundException {
		EstadioEntity newEntity = estadioList.get(0);
        newEntity.setCapacidadMaxima(10000000);
		newEntity.setNombreCiudad("BogotaaaaaLinda");
        newEntity.setPrecioAlquiler(100L);
        newEntity.setConciertosProgramados(new ArrayList<ConciertoEntity>());

        ConciertoEntity newEntity2 = factory.manufacturePojo(ConciertoEntity.class);
		newEntity2.setCapacidadMaxima(20);
        newEntity2.setPresupuesto(10000000L);
        LocalDateTime d = LocalDateTime.now().plusDays(3);
        newEntity2.setFechaConcierto(d);
        entityManager.persist(newEntity2);
        entityManager.persist(newEntity);

        ConciertoEntity newEntity3 = factory.manufacturePojo(ConciertoEntity.class);
		newEntity3.setCapacidadMaxima(20);
        newEntity3.setPresupuesto(1000000L);
        LocalDateTime d3 = LocalDateTime.now().plusDays(9);
        newEntity3.setFechaConcierto(d3);
        entityManager.persist(newEntity3);
        //entityManager.persist(newEntity);
        
		ConciertoEntity result = conciertoEstadioService.addConcierto(newEntity.getId(), newEntity2.getId());

		assertNotNull(result);
        EstadioEntity entity = entityManager.find(EstadioEntity.class, result.getId());

		assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getCapacidadMaxima(), entity.getCapacidadMaxima());
        assertEquals(newEntity.getNombreCiudad(), entity.getNombreCiudad());
        assertEquals(newEntity.getPrecioAlquiler(), entity.getPrecioAlquiler());

		
	}

    
    @Test
    void testAddConciertoConciertoNoExiste() throws IllegalOperationException, EntityNotFoundException {
		

		assertThrows(EntityNotFoundException.class, ()->{
            EstadioEntity newEntity = estadioList.get(0);
        newEntity.setCapacidadMaxima(10000000);
		newEntity.setNombreCiudad("BogotaaaaaLinda");
        newEntity.setPrecioAlquiler(100L);
        newEntity.setConciertosProgramados(new ArrayList<ConciertoEntity>());

        ConciertoEntity newEntity2 = factory.manufacturePojo(ConciertoEntity.class);
		newEntity2.setCapacidadMaxima(20);
        newEntity2.setPresupuesto(10000000L);
        newEntity2.setId(0L);
        //entityManager.persist(newEntity2);
        entityManager.persist(newEntity);

        LocalDateTime d = LocalDateTime.now().plusDays(3);
        newEntity2.setFechaConcierto(d);

        conciertoEstadioService.addConcierto(newEntity.getId(), newEntity2.getId());

		});
	}

    @Test
    void testAddConciertoEstudioNoExiste() throws IllegalOperationException, EntityNotFoundException {
		

		assertThrows(EntityNotFoundException.class, ()->{
            EstadioEntity newEntity = estadioList.get(0);
        newEntity.setCapacidadMaxima(10000000);
		newEntity.setNombreCiudad("BogotaaaaaLinda");
        newEntity.setPrecioAlquiler(100L);
        newEntity.setConciertosProgramados(new ArrayList<ConciertoEntity>());
        newEntity.setId(0L);

        ConciertoEntity newEntity2 = factory.manufacturePojo(ConciertoEntity.class);
		newEntity2.setCapacidadMaxima(20);
        newEntity2.setPresupuesto(10000000L);
        //newEntity2.setId(0L);
        entityManager.persist(newEntity2);
        //entityManager.persist(newEntity);

        LocalDateTime d = LocalDateTime.now().plusDays(3);
        newEntity2.setFechaConcierto(d);

        conciertoEstadioService.addConcierto(newEntity.getId(), newEntity2.getId());

		});
	}

    @Test
    void testAddConciertoFechaNo() throws IllegalOperationException, EntityNotFoundException {
		

		assertThrows(IllegalOperationException.class, ()->{
            EstadioEntity newEntity = estadioList.get(0);
        newEntity.setCapacidadMaxima(10000000);
		newEntity.setNombreCiudad("BogotaaaaaLinda");
        newEntity.setPrecioAlquiler(100L);
        newEntity.setConciertosProgramados(new ArrayList<ConciertoEntity>());
        newEntity.setId(0L);

        ConciertoEntity newEntity2 = factory.manufacturePojo(ConciertoEntity.class);
		newEntity2.setCapacidadMaxima(20);
        newEntity2.setPresupuesto(10000000L);
        //newEntity2.setId(0L);
        entityManager.persist(newEntity2);
        entityManager.persist(newEntity);

        ConciertoEntity newEntity3 = factory.manufacturePojo(ConciertoEntity.class);
		newEntity3.setCapacidadMaxima(20);
        newEntity3.setPresupuesto(1000000L);
        LocalDateTime d3 = LocalDateTime.now().plusDays(2);
        newEntity3.setFechaConcierto(d3);
        entityManager.persist(newEntity3);

        LocalDateTime d = LocalDateTime.now().plusDays(3);
        newEntity2.setFechaConcierto(d);

        conciertoEstadioService.addConcierto(newEntity.getId(), newEntity2.getId());

		});
	}


}
