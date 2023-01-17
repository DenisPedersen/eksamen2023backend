package facades;

import dtos.LocationDto;
import entities.Location;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class LocationFacade {
    private static EntityManagerFactory emf;
    private static LocationFacade instance;

    public LocationFacade() {
    }

    public static LocationFacade getInstance(EntityManagerFactory _emf) {
        if(instance==null) {
            emf = _emf;
            instance = new LocationFacade();
        }
        return instance;
    }
    public LocationDto createLocation (LocationDto locationDto) {
        EntityManager em = emf.createEntityManager();
        Location location = new Location(locationDto);
        try {
            em.getTransaction().begin();
            em.persist(location);
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
        return new LocationDto(location);
    }

    public Boolean deleteLocation (int id) {
        EntityManager em = emf.createEntityManager();
        Location location = em.find(Location.class, id);

        try {
            em.getTransaction().begin();
            em.remove(location);
            em.getTransaction().commit();
            location = em.find(Location.class, location.getId());
        }finally {
            em.close();
        }
        if(location == null) {
            return true;
        }
        return false;
    }

    public List<LocationDto> getAllLocations() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Location> query = em.createQuery("SELECT l FROM Location l",Location.class);
        List<Location> locations = query.getResultList();
        return LocationDto.getDtos(locations);
    }
}
