package clases;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Vuelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String codigo;
    @OneToOne
    private Avion avion;
    private double duracion;
    private int pasajeros;
    @ManyToOne
    @JoinColumn(name = "aeropuerto_id")
    private Aeropuerto aeropuerto;


    public Vuelo(String codigo, Avion avion, double duracion, int pasajeros) {
        this.codigo = codigo;
        this.avion = avion;
        this.duracion = duracion;
        this.pasajeros = pasajeros;
    }

    public Vuelo(String codigo,Double duracion, int pasajeros) {
        this.codigo = codigo;
        this.duracion = duracion;
        this.pasajeros = pasajeros;
    }


    @Override
    public String toString() {
        return "Vuelo{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", avion=" + avion +
                ", duracion=" + duracion +
                ", pasajeros=" + pasajeros +
                '}';
    }

}