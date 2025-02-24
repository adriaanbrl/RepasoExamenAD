package hibernate;

import clases.Aeropuerto;
import clases.Avion;
import clases.Vuelo;

import java.util.List;

public class MainHibernate {
    public static void main(String[] args) {

        // 1. Crear un aeropuerto e insertarlo en la BD
        Aeropuerto aeropuerto = new Aeropuerto("Barajas","B34");
        AeropuertoDAO.crear(aeropuerto);
        System.out.println("Aeropuerto insertado: " + aeropuerto.getNombre());

        // 2. Crear dos aviones y asignarlos al aeropuerto, insertarlos en la BD
        Avion avion1 = new Avion("Boeing","737","BritisAirways");
        AvionDAO.crear(avion1);  // Aquí persistes el avión para que tenga un ID asignado
        Avion avion2 = new Avion("Airbus","A320","Iberia");
        AvionDAO.crear(avion2);

        // Asignar el aeropuerto a los aviones y actualizar la relación bidireccional
        aeropuerto.addAvion(avion1);
        aeropuerto.addAvion(avion2);
        AvionDAO.actualizar(avion1);//actualizo avion para que tenga un aeropuerto
        AvionDAO.actualizar(avion2);
        System.out.println("Aviones insertados: " + avion1.getModelo() + ", " + avion2.getModelo());

        // 3. Crear un vuelo para cada avión
        // Asignar relaciones: cada vuelo tiene un aeropuerto y un avión
        // Actualizar la relación en el aeropuerto y en los aviones
        Vuelo vuelo1 = new Vuelo("AF345",2.0,10);
        VueloDAO.crear(vuelo1);
        vuelo1.setAeropuerto(aeropuerto);//le añado un aeropuerto al vuelo
        vuelo1.setAvion(avion1);//le añado un avion al vuelo
        VueloDAO.actualizar(vuelo1);//actualizo cambios
        Vuelo vuelo2 = new Vuelo("2LRSo",1.30,20);
        VueloDAO.crear(vuelo2);
        vuelo2.setAeropuerto(aeropuerto);
        vuelo2.setAvion(avion2);
        VueloDAO.actualizar(vuelo2);

        System.out.println("Vuelos insertados: Vuelo 1 con " + vuelo1.getPasajeros() + " pasajeros, Vuelo 2 con " + vuelo2.getPasajeros() + " pasajeros");

        // 4. Actualizar los datos de uno de los vuelos (por ejemplo, cambiar pasajeros de vuelo1)
        vuelo1.setPasajeros(15); // Cambio de 10 a 15 pasajeros
        VueloDAO.actualizar(vuelo1);//actualizo cambios
        System.out.println("Vuelo actualizado: Vuelo 1 ahora tiene " + vuelo1.getPasajeros() + " pasajeros");

        // 5. Mostrar todos los aeropuertos, aviones y vuelos de la BD
        System.out.println("\nListado de Aeropuertos:");
        List<Aeropuerto> listaAeropuertos = AeropuertoDAO.findAll();
        for (Aeropuerto a : listaAeropuertos) {
            System.out.println("Aeropuerto: " + a.getNombre() + " (ID: " + a.getCodigo() + ")");
        }

        System.out.println("\nListado de Aviones:");
        List<Avion> listaAviones = AvionDAO.findAll();
        for (Avion a : listaAviones) {
            System.out.println("Avión: " + a.getModelo() + " (ID: " + a.getId() + ") - Aeropuerto: " + a.getAeropuerto().getNombre());
        }

        System.out.println("\nListado de Vuelos:");
        List<Vuelo> listaVuelos = VueloDAO.leerTodosVuelos();
        for (Vuelo v : listaVuelos) {
            System.out.println("Vuelo ID: " + v.getCodigo() + " - Pasajeros: " + v.getPasajeros()
                    + " - Avión: " + v.getAvion().getModelo() + " - Aeropuerto: " + v.getAeropuerto().getNombre());
        }

        // 6. Buscar en la BD los vuelos de más de 15 pasajeros y mostrar la información del vuelo, su avión y aeropuerto
        System.out.println("\nVuelos con más de 15 pasajeros:");
        List<Vuelo> vuelosMas15 = VueloDAO.findVuelosMasDePasajeros(15);
        for (Vuelo v : vuelosMas15) {
            System.out.println("Vuelo ID: " + v.getCodigo() + " - Pasajeros: " + v.getPasajeros()
                    + " - Avión: " + v.getAvion().getModelo() + " - Aeropuerto: " + v.getAeropuerto().getNombre());
        }

        // 7. Eliminar un aeropuerto. Se elimina el aeropuerto creado y se verifica que no se borran los aviones ni los vuelos.
        AeropuertoDAO.remove(aeropuerto);
        System.out.println("\nAeropuerto eliminado.");

        // Verificar que los aviones y vuelos siguen existiendo:
        System.out.println("\nListado de Aviones tras eliminar el aeropuerto:");
        listaAviones = AvionDAO.findAll();
        for (Avion a : listaAviones) {
            String nombreAero = (a.getAeropuerto() != null) ? a.getAeropuerto().getNombre() : "Sin aeropuerto";
            System.out.println("Avión: " + a.getModelo() + " (ID: " + a.getId() + ") - Aeropuerto: " + nombreAero);
        }

        System.out.println("\nListado de Vuelos tras eliminar el aeropuerto:");
        listaVuelos = VueloDAO.leerTodosVuelos();
        for (Vuelo v : listaVuelos) {
            String nombreAero = (v.getAeropuerto() != null) ? v.getAeropuerto().getNombre() : "Sin aeropuerto";
            System.out.println("Vuelo ID: " + v.getCodigo() + " - Pasajeros: " + v.getPasajeros()
                    + " - Avión: " + v.getAvion().getModelo() + " - Aeropuerto: " + nombreAero);
        }

        //HibernateUtil.getSessionFactory();
    }
}
