package br.vet.certvet.models;

import br.vet.certvet.dto.requests.ExameRequestDto;
import br.vet.certvet.models.contracts.Fillable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "exame")
public class Exame implements Fillable<ExameRequestDto> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String tipoExame;

    @Column(length = 2000)
    private String outrosExames;

    @Column(length = 2000)
    private String outrosCitologia;

    private String bioquimico;
    private String hematologia;
    private String citologia;
    private String imagem;
    private String imagemRegiaoCervical;
    private String imagemRegiaoAbdomen;
    private String imagemRegiaoMToracicos;
    private String imagemRegiaoMPelvicos;

    private boolean imagemRegiaoCabeca;
    private boolean imagemRegiaoTorax;

    @ManyToOne
    @JoinColumn(name = "prontuario_id")
    private Prontuario prontuario;

    public Exame(ExameRequestDto dto, Prontuario prontuario) {
        this.prontuario = prontuario;

        this.fill(dto);
    }

    @Override
    public void fill(ExameRequestDto dto) {
        this.tipoExame = dto.tipo_exame;
        this.outrosExames = dto.outros_exames;
        this.outrosCitologia = dto.outros_citologia;
        this.bioquimico = dto.bioquimico;
        this.hematologia = dto.hematologia;
        this.citologia = dto.citologia;
        this.imagem = dto.imagem;
        this.imagemRegiaoCervical = dto.imagem_regiao_cervical;
        this.imagemRegiaoAbdomen = dto.imagem_regiao_abdomen;
        this.imagemRegiaoMToracicos = dto.imagem_regiao_mtoracicos;
        this.imagemRegiaoMPelvicos = dto.imagem_regiao_mpelvicos;
        this.imagemRegiaoCabeca = dto.imagem_regiao_cabeca;
        this.imagemRegiaoTorax = dto.imagem_regiao_torax;
    }
}