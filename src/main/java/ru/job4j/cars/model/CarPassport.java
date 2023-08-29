package ru.job4j.cars.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CAR_PASSPORT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CarPassport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @Column(name = "passport_number")
    @EqualsAndHashCode.Include
    private String passportNumber;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "current_owner_id")
    private Owner currentOwner;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "car_passport_owner", joinColumns = {
            @JoinColumn(name = "car_passport_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "owner_id", nullable = false, updatable = false)})
    private Set<Owner> owners = new HashSet<>();

    public CarPassport(int id, String passportNumber, Owner currentOwner) {
        this.id = id;
        this.passportNumber = passportNumber;
        this.currentOwner = currentOwner;
    }

    public CarPassport(String passportNumber, Owner currentOwner) {
        this.passportNumber = passportNumber;
        this.currentOwner = currentOwner;
    }
}
