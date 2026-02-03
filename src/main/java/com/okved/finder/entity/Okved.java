package com.okved.finder.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_okved")
@Builder
public class Okved {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    protected Long id;

    /**
     * Код ОКВЭД (например, "01.11.1").
     */
    @Column(name = "code", nullable = false)
    private String code;

    /**
     * Название вида экономической деятельности.
     */
    @Column(name = "name", nullable = false, columnDefinition = "varchar")
    private String name;


}