package dtos;

import entities.Location;
import entities.Match;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A DTO for the {@link entities.Location} entity
 */
public class LocationDto implements Serializable {
    private Integer id;
    @Size(max = 45)
    @NotNull
    private final String address;
    @Size(max = 45)
    @NotNull
    private final String city;
    private List<MatchDto> matches = new ArrayList<>();

    public LocationDto(Integer id, String address, String city, List<MatchDto> matches) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.matches = matches;
    }

    public LocationDto(String address, String city) {
        this.address = address;
        this.city = city;
    }

    public LocationDto(Location location) {
        if(location.getId() != null) {
            this.id = location.getId();
        }
        this.address = location.getAddress();
        this.city = location.getCity();
        if(location.getMatches() != null) {
            for(Match match : location.getMatches()) {
                this.matches.add(new MatchDto(match));
            }
        }
    }

    public static List<LocationDto> getDtos(List<Location> locations) {
        List<LocationDto> locationDtos = new ArrayList<>();
        locations.forEach(location -> locationDtos.add(new LocationDto(location)));
        return locationDtos;
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

    public List<MatchDto> getMatches() {
        return matches;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "address = " + address + ", " +
                "city = " + city + ", " +
                "matches = " + matches + ")";
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * A DTO for the {@link entities.Match} entity
     */
    public static class MatchInnerDto1 implements Serializable {
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
        private final Set<PlayerInnerDto> players;

        public MatchInnerDto1(Integer id, String opponentTeam, String judge, String type, Byte indoor, Set<PlayerInnerDto> players) {
            this.id = id;
            this.opponentTeam = opponentTeam;
            this.judge = judge;
            this.type = type;
            this.indoor = indoor;
            this.players = players;
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

        public Set<PlayerInnerDto> getPlayers() {
            return players;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" +
                    "id = " + id + ", " +
                    "opponentTeam = " + opponentTeam + ", " +
                    "judge = " + judge + ", " +
                    "type = " + type + ", " +
                    "indoor = " + indoor + ", " +
                    "players = " + players + ")";
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
}