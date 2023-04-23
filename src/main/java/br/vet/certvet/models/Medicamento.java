package br.vet.certvet.models;

import br.vet.certvet.contracts.apis.anvisa.MedicationAPIResponse;
import br.vet.certvet.exceptions.specializations.medicamento.MedicamentoUnprocessableEntityException;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Objects;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String codigoRegistro;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String principioAtivo;

    @Column(nullable = false)
    private String viaUso;

    @Column(nullable = false)
    private String concentracao;

    @Column(nullable = false)
    private String fabricante;

    @Column(nullable = false)
    private String nomeReferencia;

    @Column(nullable = false)
    private LocalDateTime vencimentoRegistro;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Medicamento that = (Medicamento) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public static Medicamento factory(MedicationAPIResponse response) {
        Medicamento medicamento = new Medicamento();

        medicamento.nome = response.nomeComercial();
        medicamento.codigoRegistro = response.numeroRegistro();
        medicamento.principioAtivo = response.principioAtivo();
        medicamento.viaUso = (String) ((ArrayList<?>) response.apresentacoes().get(0).get("viasAdministracao")).get(0);
        medicamento.concentracao = (String) response.apresentacoes().get(0).get("apresentacao");
        medicamento.fabricante = response.empresa().get("razaoSocial");
        medicamento.nomeReferencia = response.medicamentoReferencia();
        medicamento.vencimentoRegistro = LocalDateTime.parse(response.dataVencimentoRegistro().replace("-0300", ""));

        long expireAt = medicamento.vencimentoRegistro.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        if (expireAt <= now)
            throw new MedicamentoUnprocessableEntityException("O registro desse medicamento está vencido");

        return medicamento;
    }
}
