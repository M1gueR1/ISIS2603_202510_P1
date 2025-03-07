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
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ConciertoService.class})
public class ConciertoServiceTest {
    @Autowired
	private ConciertoService conciertoService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

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
			ConciertoEntity conciertoEntity = factory.manufacturePojo(ConciertoEntity.class);
			entityManager.persist(conciertoEntity);
			conciertoList.add(conciertoEntity);
		}
	}

    @Test
	void testCreateConcierto() throws IllegalOperationException {
		ConciertoEntity newEntity = factory.manufacturePojo(ConciertoEntity.class);
		newEntity.setCapacidadMaxima(20);
        newEntity.setPresupuesto(1000000L);
        LocalDateTime d = LocalDateTime.now().plusDays(3);
        newEntity.setFechaConcierto(d);
		ConciertoEntity result = conciertoService.createConcierto(newEntity);

		assertNotNull(result);
        ConciertoEntity entity = entityManager.find(ConciertoEntity.class, result.getId());

		assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getCapacidadMaxima(), entity.getCapacidadMaxima());
        assertEquals(newEntity.getNombre(), entity.getNombre());

		
	}

    @Test
	void testCreateConciertoMal() throws IllegalOperationException {
		
		assertThrows(IllegalOperationException.class, ()->{
            ConciertoEntity newEntity = factory.manufacturePojo(ConciertoEntity.class);
            newEntity.setCapacidadMaxima(1);
            newEntity.setPresupuesto(1000000L);
            LocalDateTime d = LocalDateTime.now().plusDays(3);
            newEntity.setFechaConcierto(d);
            conciertoService.createConcierto(newEntity);
		});

		
	}

}
