package br.vet.certvet.models;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String tipo;
    private String caminho;

    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Prontuario prontuario;
}
