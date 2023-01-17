package entities;

import dtos.LocationDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@NamedQuery(name="Location.deleteAllRows", query = "DELETE from Location ")
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @NotNull
    @Column(name = "address", nullable = false, length = 45)
    private String address;

    @Size(max = 45)
    @NotNull
    @Column(name = "city", nullable = false, length = 45)
    private String city;

    @OneToMany(mappedBy = "location")
    private List<Match> matches = new ArrayList<>();

    public Location(Integer id, String address, String city) {
        this.id = id;
        this.address = address;
        this.city = city;
    }

    public Location() {

    }

    public Location(String address, String city) {
        this.address = address;
        this.city = city;
    }

    public Location(LocationDto locationDto) {
        if(locationDto.getId() != null) {
            this.id = locationDto.getId();
        }
        this.address = locationDto.getAddress();
        this.city = locationDto.getCity();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

}