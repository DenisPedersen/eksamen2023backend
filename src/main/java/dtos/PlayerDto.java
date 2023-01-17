package dtos;

import entities.Player;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the {@link entities.Player} entity
 */
public class PlayerDto implements Serializable {
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
    private final List<MatchInnerDto> matches = new ArrayList<>();

   /* public PlayerDto(Integer id, String name, String phone, String email, String status, List<MatchInnerDto> matches) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.matches = matches;
    }*/
    public PlayerDto(Player player) {
        this.id = player.getId();
        this.name = player.getName();
        this.phone = player.getPhone();
        this.email = player.getEmail();
        this.status = player.getStatus();
    }

    public static List<PlayerDto> getDtos(List<Player> players) {
        List<PlayerDto> playerDtos = new ArrayList<>();
        players.forEach(player -> playerDtos.add(new PlayerDto(player)));
        return playerDtos;
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

    public List<MatchInnerDto> getMatches() {
        return matches;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "phone = " + phone + ", " +
                "email = " + email + ", " +
                "status = " + status + ", " +
                "matches = " + matches + ")";
    }

    /**
     * A DTO for the {@link entities.Match} entity
     */
    public static class MatchInnerDto implements Serializable {
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
        private final LocationInnerDto location;

        public MatchInnerDto(Integer id, String opponentTeam, String judge, String type, Byte indoor, LocationInnerDto location) {
            this.id = id;
            this.opponentTeam = opponentTeam;
            this.judge = judge;
            this.type = type;
            this.indoor = indoor;
            this.location = location;
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

        public LocationInnerDto getLocation() {
            return location;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "opponentTeam = " + opponentTeam + ", " +
                    "judge = " + judge + ", " +
                    "type = " + type + ", " +
                    "indoor = " + indoor + ", " +
                    "location = " + location + ")";
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
            public String toString() {
                return getClass().getSimpleName() + "(" +
                        "id = " + id + ", " +
                        "address = " + address + ", " +
                        "city = " + city + ")";
            }
        }
    }
}