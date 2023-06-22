package br.vet.certvet.models;

import br.vet.certvet.models.especializacoes.TipoExameEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class TipoExame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nome;

    @OneToMany(mappedBy = "tipoExame")
    private List<Exame> exames;

    @OneToMany(mappedBy = "pai")
    private List<TipoExame> filhos = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tipo_exame_id")
    private TipoExame pai;
}
