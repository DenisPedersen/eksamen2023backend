package facades;

import dtos.MatchDto;
import dtos.PlayerDto;
import entities.Match;
import entities.Player;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class PlayerFacade {
    private static EntityManagerFactory emf;
    private static PlayerFacade instance;

    public PlayerFacade() {
    }
    public static PlayerFacade getInstance(EntityManagerFactory _emf) {
        if(instance==null) {
            emf=_emf;
            instance = new PlayerFacade();
        }
        return instance;
    }

    public List<PlayerDto> getAllPlayers() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Player> query = em.createQuery("SELECT p FROM Player p", Player.class);
        List<Player> players = query.getResultList();
        return PlayerDto.getDtos(players);
    }

    public List<PlayerDto> getAllPlayersInAMatch(int matchId) {
        List<PlayerDto> playerDtoList = new ArrayList<>();
        EntityManager em = emf.createEntityManager();
        TypedQuery<Player> query = em.createQuery("SELECT p FROM Player p JOIN p.matches b WHERE b.id= :id", Player.class);
        query.setParameter("id",matchId);
        query.getResultList().forEach(player -> playerDtoList.add(new PlayerDto(player)));
        return playerDtoList;
    }

}
