package facades;

import dtos.MatchDto;
import entities.Location;
import entities.Match;
import entities.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchFacadeTest {
    private static EntityManagerFactory emf;
    private static MatchFacade matchFacade;

    Match m1, m2;
    Location l1, l2;

    Player p1, p2;

    public MatchFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        matchFacade = MatchFacade.getInstance(emf);
    }

    @BeforeEach
    public void setup() {
        EntityManager em = emf.createEntityManager();
        l1 = new Location(1,"Byvej 2", "Brønderslev");
        m1 = new Match( "Holdet","Mads","slutspil", (byte) 0,  l1);
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Match.deleteAllRows").executeUpdate();
            em.persist(l1);
            em.persist(m1);
            em.getTransaction().commit();
        }finally {
            em.close();
        }
    }
    @Test
    void getAll() {
        int expected = 0;
        List<MatchDto> matchDtoList = matchFacade.getAllMatches();
        assertEquals(expected, matchDtoList.size());
    }
    @Test
    void deleteMatchTest() {
        boolean response = matchFacade.deleteMatch(m1.getId());
        assertEquals(true, response);
    }
    @Test
    void updateMatchTest() {
        MatchDto newMatch = new MatchDto("Nyhold", "Kristian", "slutspil", (byte) 0);
        newMatch.setId(m1.getId());
        matchFacade.updateMatch(newMatch);
        MatchDto result = matchFacade.getMatchById(m1.getId());
        assertEquals(newMatch.getJudge(), result.getJudge());
    }
}
