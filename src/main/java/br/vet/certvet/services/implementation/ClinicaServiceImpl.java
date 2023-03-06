package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.ClinicaInicialRequestDto;
import br.vet.certvet.dto.requests.ClinicaRequestDto;
import br.vet.certvet.dto.requests.FuncionarioRequestDto;
import br.vet.certvet.dto.requests.VeterinarioRequestDto;
import br.vet.certvet.exceptions.ConflictException;
import br.vet.certvet.exceptions.NotFoundException;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.repositories.ClinicaRepository;
import br.vet.certvet.services.ClinicaService;
import br.vet.certvet.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClinicaServiceImpl implements ClinicaService {

    @Autowired
    private ClinicaRepository clinicaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public Clinica create(ClinicaInicialRequestDto dto) {
        Optional<Clinica> response = this.clinicaRepository.findByCnpj(dto.clinica_cnpj);

        if (response.isPresent())
            throw new ConflictException("Clínica já existe.");

        Clinica clinica = this.clinicaRepository.saveAndFlush(new Clinica(dto));

        if (dto.dono_crmv != null && !dto.dono_crmv.isEmpty()) {
            Usuario usuario = this.usuarioService.create(ClinicaServiceImpl.getDonoResponsavelTecnicoDto(dto), clinica);

            clinica.setResponsavelTecnico(usuario);
            this.clinicaRepository.saveAndFlush(clinica);
        } else this.usuarioService.create(ClinicaServiceImpl.getDonoDto(dto), clinica);

        return clinica;
    }

    @Override
    public Clinica findById(Long id) {
        Optional<Clinica> response = this.clinicaRepository.findById(id);

        if (response.isEmpty())
            throw new NotFoundException("Clínica não existe.");

        return response.get();
    }

    @Override
    public Clinica findByCnpj(String cnpj) {
        Optional<Clinica> response = this.clinicaRepository.findByCnpj(cnpj);

        if (response.isEmpty())
            throw new NotFoundException("Clínica não existe.");

        return response.get();
    }

    @Override
    public Clinica edit(ClinicaRequestDto dto, Clinica clinica) {
        clinica.fill(dto);

        return this.clinicaRepository.saveAndFlush(clinica);
    }

    private static FuncionarioRequestDto getDonoDto(ClinicaInicialRequestDto dto) {
        return ClinicaServiceImpl.getDonoResponsavelTecnicoDto(dto);
    }

    private static VeterinarioRequestDto getDonoResponsavelTecnicoDto(ClinicaInicialRequestDto dto) {
        VeterinarioRequestDto usuarioDto = new VeterinarioRequestDto();

        usuarioDto.nome = dto.dono_nome;
        usuarioDto.email = dto.dono_email;
        usuarioDto.cpf = dto.dono_cpf;
        usuarioDto.rg = dto.dono_rg;
        usuarioDto.celular = dto.dono_celular;
        usuarioDto.telefone = dto.dono_telefone;
        usuarioDto.logradouro = dto.dono_logradouro;
        usuarioDto.numero = dto.dono_numero;
        usuarioDto.cep = dto.dono_cep;
        usuarioDto.bairro = dto.dono_bairro;
        usuarioDto.cidade = dto.dono_cidade;
        usuarioDto.estado = dto.dono_estado;
        usuarioDto.senha = dto.dono_senha;
        usuarioDto.crmv = dto.dono_crmv;
        usuarioDto.is_admin = true;

        return usuarioDto;
    }
}
