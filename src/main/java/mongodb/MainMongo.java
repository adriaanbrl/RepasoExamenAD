package mongodb;

import clases.Aeropuerto;
import clases.Avion;
import clases.Vuelo;

import java.util.ArrayList;
import java.util.List;

public class MainMongo {
    public static void main(String[] args) {
        MongoDBConfig.getCollection();
        Aeropuerto aeropuerto = new Aeropuerto("MAD", "Barajas", null, null);
        AeropuertoDAO.insertAeropuerto(aeropuerto);

        Avion avion1 = new Avion("Boeing", "747", "Iberia");
        AvionDAO.insertAvion(avion1);

        Avion avion2 = new Avion("Airbus", "A380", "Iberia");
        AvionDAO.insertAvion(avion2);

        Vuelo vuelo = new Vuelo("1", avion1, 100.0, 10);
        VueloDAO.insertVuelo(vuelo);

        Vuelo vuelo2 = new Vuelo("2", avion2, 200.0, 20);
        VueloDAO.insertVuelo(vuelo2);

        List<Avion> aviones = new ArrayList<>();
        aviones.add(avion1);
        aviones.add(avion2);

        List<Vuelo> vuelos = new ArrayList<>();
        vuelos.add(vuelo);
        vuelos.add(vuelo2);

        aeropuerto.setAviones(aviones);
        aeropuerto.setVuelos(vuelos);



        vuelo.setDuracion(150.0);
        VueloDAO.updateVuelo(vuelo);

        AeropuertoDAO.updateAeropuerto(aeropuerto);

        System.out.println(AeropuertoDAO.getAeropuertos());
        System.out.println(AvionDAO.getAviones());
        System.out.println(VueloDAO.getVuelos());

        System.out.println(VueloDAO.getPasajeros15());
        AeropuertoDAO.deleteAeropuerto(aeropuerto);

    }
}
