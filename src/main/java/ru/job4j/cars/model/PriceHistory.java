package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "price_history")
public class PriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private float price;
    @Column(name = "start_period")
    private LocalDateTime startPeriod;
    @Column(name = "end_period")
    private LocalDateTime endPeriod;
    @ManyToOne
    @JoinColumn(name = "auto_post_id")
    private Post post;
}
