package clases;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Aeropuerto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String codigo;
    private String nombre;
    @OneToMany(mappedBy = "aeropuerto", cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Avion> aviones;
    @OneToMany(mappedBy = "aeropuerto", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<Vuelo> vuelos = new ArrayList<>();



    public void addAvion(Avion avion) {
        if (aviones == null) {
            aviones = new ArrayList<>();
        }
        aviones.add(avion);
        avion.setAeropuerto(this);
    }

    public Aeropuerto(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public Aeropuerto(String codigo, String nombre, List<Vuelo> vuelos, List<Avion> aviones) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.vuelos = vuelos;
        this.aviones = aviones;
    }

    @Override
    public String toString() {
        return "Aeropuerto{" +
                "id=" + id +
                ", coidgo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", aviones=" + aviones +
                ", vuelos=" + vuelos +
                '}';
    }

}