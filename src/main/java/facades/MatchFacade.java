package facades;

import dtos.MatchDto;
import entities.Match;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class MatchFacade {

    private static EntityManagerFactory emf;
    private static MatchFacade instance;

    public MatchFacade() {
    }

    public static MatchFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MatchFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<MatchDto> getAllMatches() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Match> query = em.createQuery("SELECT m FROM Match m", Match.class);
            List<Match> matches = query.getResultList();
            return MatchDto.getDtos(matches);
        } finally {
            em.close();
        }
    }

    public List<MatchDto> getAllMatchesFromLocation(Integer id) {
        List<MatchDto> matchDtoList = new ArrayList<>();
        EntityManager em = getEntityManager();

        TypedQuery<Match> query = em.createQuery("SELECT m FROM Match m JOIN m.location l WHERE l.id = :id", Match.class);
        query.setParameter("id", id);
        query.getResultList().forEach(match -> matchDtoList.add(new MatchDto(match)));
        return matchDtoList;
    }

    public List<MatchDto> getAllPlayersInMatch(Integer playerId) {
        List<MatchDto> matchDtoList = new ArrayList<>();
        EntityManager em = emf.createEntityManager();
        TypedQuery<Match> query = em.createQuery("SELECT m FROM Match m JOIN m.players p WHERE p.id= :id", Match.class);
        query.setParameter("id", playerId);
        query.getResultList().forEach(match -> matchDtoList.add(new MatchDto(match)));
        return matchDtoList;
    }
}
