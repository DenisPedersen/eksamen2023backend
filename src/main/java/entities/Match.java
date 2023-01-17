package entities;

import dtos.MatchDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@NamedQuery(name="Match.deleteAllRows", query = "DELETE from Match")
@Table(name = "`match`")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "opponent_team", nullable = false, length = 45)
    private String opponentTeam;

    @Size(max = 45)
    @NotNull
    @Column(name = "judge", nullable = false, length = 45)
    private String judge;

    @Size(max = 45)
    @NotNull
    @Column(name = "type", nullable = false, length = 45)
    private String type;

    @NotNull
    @Column(name = "indoor", nullable = false)
    private Byte indoor;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @ManyToMany
    @JoinTable(name = "match_has_player",
            joinColumns = @JoinColumn(name = "match_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    private Set<Player> players = new LinkedHashSet<>();

    public Match(Integer id, String opponentTeam, String judge, String type, Byte indoor, Location location, Set<Player> players) {
        this.id = id;
        this.opponentTeam = opponentTeam;
        this.judge = judge;
        this.type = type;
        this.indoor = indoor;
        this.location = location;
        this.players = players;
    }

    public Match() {

    }

    public Match(String opponentTeam, String judge, String type, Byte indoor) {
        this.opponentTeam = opponentTeam;
        this.judge = judge;
        this.type = type;
        this.indoor = indoor;
    }

    public Match(String opponentTeam, String judge, String type, Byte indoor, Location location) {
        this.opponentTeam = opponentTeam;
        this.judge = judge;
        this.type = type;
        this.indoor = indoor;
        this.location = location;
    }

    public Match(MatchDto matchDto) {
        if(matchDto.getId() != null) {
            this.id = matchDto.getId();
        }
        this.opponentTeam = matchDto.getOpponentTeam();
        this.judge = matchDto.getJudge();
        this.type = matchDto.getType();
        this.indoor = matchDto.getIndoor();
        this.location = matchDto.getLocation();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpponentTeam() {
        return opponentTeam;
    }

    public void setOpponentTeam(String opponentTeam) {
        this.opponentTeam = opponentTeam;
    }

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Byte getIndoor() {
        return indoor;
    }

    public void setIndoor(Byte indoor) {
        this.indoor = indoor;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

}