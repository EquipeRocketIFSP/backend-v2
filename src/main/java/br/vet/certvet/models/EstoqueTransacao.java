package br.vet.certvet.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Table
@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueTransacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private boolean status;

    @Column(nullable = false)
    private BigDecimal quantidade;

    private String motivo;

    @Column(nullable = false)
    private LocalDate data;
}
