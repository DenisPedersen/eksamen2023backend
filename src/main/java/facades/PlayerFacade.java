package facades;

import dtos.LocationDto;
import dtos.MatchDto;
import dtos.PlayerDto;
import entities.Location;
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

    public PlayerDto createPlayer (PlayerDto playerDto) {
        EntityManager em = emf.createEntityManager();
        Player player = new Player(playerDto);
        try {
            em.getTransaction().begin();
            em.persist(player);
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
        return new PlayerDto(player);
    }

    public Boolean deletePlayer (int id) {
        EntityManager em = emf.createEntityManager();
        Player player = em.find(Player.class, id);
        try {
            em.getTransaction().begin();
            em.remove(player);
            em.getTransaction().commit();
            player = em.find(Player.class, player.getId());
        }finally {
            em.close();
        }
        if (player == null) {
            return true;
        }
        return false;
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
