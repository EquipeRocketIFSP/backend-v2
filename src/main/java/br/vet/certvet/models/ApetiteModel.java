package br.vet.certvet.models;

import br.vet.certvet.models.manifestacoes_clinicas.Apetite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "apetite")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ApetiteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "INT")
    private int code;

    @Enumerated(EnumType.STRING)
    private Apetite status;

    @OneToMany(mappedBy = "apetite")
    private List<ManifestacoesClinicas> manifestacoesClinicas;

    public int getId() {
        return id;
    }
}
