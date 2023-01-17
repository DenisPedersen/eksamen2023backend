package facades;

import dtos.LocationDto;
import entities.Location;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LocationFacadeTest {
    private static EntityManagerFactory emf;
    private static LocationFacade locationFacade;

    Location l1, l2;

    public LocationFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        locationFacade = LocationFacade.getInstance(emf);
    }
    @BeforeEach
    public void setup() {
        EntityManager em = emf.createEntityManager();
        l1 = new Location("Bygaden 112", "Aarhus");
        l2 = new Location("Hovedgaden 2", "Lyngby");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Location.deleteAllRows").executeUpdate();
            em.persist(l1);
            em.persist(l2);
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
    }
    @Test
    void createLocationTest () {
        LocationDto newLocation = new LocationDto("Lyngbyvej 1999", "Rungsted");
        LocationDto result = locationFacade.createLocation(newLocation);
        assertNotNull(result);
        assertEquals(newLocation.getAddress(), result.getAddress());
    }

    @Test
    void deleteLocationTest () {
        boolean response = locationFacade.deleteLocation(l1.getId());
        assertEquals(true, response);
    }

    @Test
    void getAllLocations() {
        int expected = 2;
        List<LocationDto> locationDtos = locationFacade.getAllLocations();
        assertEquals(expected, locationDtos.size());
    }
}
