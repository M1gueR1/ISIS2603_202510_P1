package co.edu.uniandes.dse.parcial1.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class EstadioEntity extends BaseEntity {

    private String nombre;
    private Long precioAlquiler;
    
    private String nombreCiudad;
    private int capacidadMaxima;

    @PodamExclude
	@OneToMany(mappedBy = "estadioAsignado", fetch = FetchType.LAZY)
	private List<ConciertoEntity> conciertosProgramados = new ArrayList<>();

}
