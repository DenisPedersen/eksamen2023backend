package facades;

import dtos.PlayerDto;
import entities.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PlayerFacadeTest {
    private static EntityManagerFactory emf;
    private static PlayerFacade playerFacade;

    Player p1, p2;

    public PlayerFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        playerFacade = PlayerFacade.getInstance(emf);
    }
    @BeforeEach
    public void setup() {
        EntityManager em = emf.createEntityManager();
        p1 = new Player("John","123","a@a.dk","kampklar");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Player.deleteAllRows").executeUpdate();
            em.persist(p1);
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
    }
    @Test
    void getAllPlayers() {
        int expected = 1;
        List<PlayerDto> playerDtoList = playerFacade.getAllPlayers();
        assertEquals(expected, playerDtoList.size());
    }

    @Test
    void deletePlayerTest() {
        boolean response = playerFacade.deletePlayer(p1.getId());
        assertEquals(true, response);
    }
    @Test
    void createPlayerTest() {
        PlayerDto newPlayer = new PlayerDto("Nikolaj", "888", "nikolaj@cphbusiness.dk","kampklar");
        PlayerDto result = playerFacade.createPlayer(newPlayer);
        assertNotNull(result);
        assertEquals(newPlayer.getName(), result.getName());
    }

}

