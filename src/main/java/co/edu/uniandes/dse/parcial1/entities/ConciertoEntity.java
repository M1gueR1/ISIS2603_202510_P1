package co.edu.uniandes.dse.parcial1.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class ConciertoEntity extends BaseEntity {

    private String nombre;
    private Long presupuesto;
    private String nombreArtista;
    private LocalDateTime fechaConcierto;
    private int capacidadMaxima;

    @ManyToOne
    private EstadioEntity estadioAsignado;


}
