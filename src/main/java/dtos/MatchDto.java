package dtos;

import entities.Location;
import entities.Match;
import entities.Player;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link entities.Match} entity
 */
public class MatchDto implements Serializable {
    private final Integer id;
    @Size(max = 45)
    @NotNull
    private final String opponentTeam;
    @Size(max = 45)
    @NotNull
    private final String judge;
    @Size(max = 45)
    @NotNull
    private final String type;
    @NotNull
    private final Byte indoor;
    @NotNull
    private  Location location;
    private final List<PlayerInnerDto> players = new ArrayList<>();

    public MatchDto(Match match) {
        this.id = match.getId();
        this.opponentTeam = match.getOpponentTeam();
        this.judge = match.getJudge();
        this.type = match.getType();
        this.indoor = match.getIndoor();
        match.getPlayers().forEach(player -> players.add(new MatchDto.PlayerInnerDto(player)));
    }

    public static List<MatchDto> getDtos (List<Match> matches) {
        List<MatchDto> matchDtoList = new ArrayList<>();
        matches.forEach(match -> matchDtoList.add(new MatchDto(match)));
        return matchDtoList;
    }
    public Integer getId() {
        return id;
    }

    public String getOpponentTeam() {
        return opponentTeam;
    }

    public String getJudge() {
        return judge;
    }

    public String getType() {
        return type;
    }

    public Byte getIndoor() {
        return indoor;
    }

    public Location getLocation() {
        return location;
    }

    public List<PlayerInnerDto> getPlayers() {
        return players;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchDto entity = (MatchDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.opponentTeam, entity.opponentTeam) &&
                Objects.equals(this.judge, entity.judge) &&
                Objects.equals(this.type, entity.type) &&
                Objects.equals(this.indoor, entity.indoor) &&
                Objects.equals(this.location, entity.location) &&
                Objects.equals(this.players, entity.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, opponentTeam, judge, type, indoor, location, players);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "opponentTeam = " + opponentTeam + ", " +
                "judge = " + judge + ", " +
                "type = " + type + ", " +
                "indoor = " + indoor + ", " +
                "location = " + location + ", " +
                "players = " + players + ")";
    }

    /**
     * A DTO for the {@link entities.Location} entity
     */
    public static class LocationInnerDto implements Serializable {
        private final Integer id;
        @Size(max = 45)
        @NotNull
        private final String address;
        @Size(max = 45)
        @NotNull
        private final String city;

        public LocationInnerDto(Integer id, String address, String city) {
            this.id = id;
            this.address = address;
            this.city = city;
        }
        public LocationInnerDto (Location location) {
            this.id = location.getId();
            this.address = location.getAddress();
            this.city = location.getCity();
        }

        public Integer getId() {
            return id;
        }

        public String getAddress() {
            return address;
        }

        public String getCity() {
            return city;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LocationInnerDto entity = (LocationInnerDto) o;
            return Objects.equals(this.id, entity.id) &&
                    Objects.equals(this.address, entity.address) &&
                    Objects.equals(this.city, entity.city);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, address, city);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "address = " + address + ", " +
                    "city = " + city + ")";
        }
    }

    /**
     * A DTO for the {@link entities.Player} entity
     */
    public static class PlayerInnerDto implements Serializable {
        private final Integer id;
        @Size(max = 45)
        @NotNull
        private final String name;
        @Size(max = 45)
        @NotNull
        private final String phone;
        @Size(max = 45)
        @NotNull
        private final String email;
        @Size(max = 45)
        @NotNull
        private final String status;

        public PlayerInnerDto(Integer id, String name, String phone, String email, String status) {
            this.id = id;
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.status = status;
        }
        public PlayerInnerDto (Player player) {
            this.id = player.getId();
            this.name = player.getName();
            this.phone = player.getPhone();
            this.email = player.getEmail();
            this.status = player.getStatus();
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }

        public String getStatus() {
            return status;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PlayerInnerDto entity = (PlayerInnerDto) o;
            return Objects.equals(this.id, entity.id) &&
                    Objects.equals(this.name, entity.name) &&
                    Objects.equals(this.phone, entity.phone) &&
                    Objects.equals(this.email, entity.email) &&
                    Objects.equals(this.status, entity.status);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, phone, email, status);
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "name = " + name + ", " +
                    "phone = " + phone + ", " +
                    "email = " + email + ", " +
                    "status = " + status + ")";
        }
    }
}