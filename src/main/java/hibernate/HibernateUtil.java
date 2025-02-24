package hibernate;

import clases.Aeropuerto;
import clases.Avion;
import clases.Vuelo;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try (StandardServiceRegistry registry = new
                    StandardServiceRegistryBuilder().build()) {
                sessionFactory = new MetadataSources(registry)
                        .addAnnotatedClass(Aeropuerto.class)
                        .addAnnotatedClass(Avion.class)
                        .addAnnotatedClass(Vuelo.class)
                        .buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}