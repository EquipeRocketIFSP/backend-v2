package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.ClinicaInicialRequestDto;
import br.vet.certvet.dto.requests.ClinicaRequestDto;
import br.vet.certvet.dto.requests.FuncionarioRequestDto;
import br.vet.certvet.dto.requests.VeterinarioRequestDto;
import br.vet.certvet.exceptions.BadGatewayException;
import br.vet.certvet.exceptions.ConflictException;
import br.vet.certvet.exceptions.NotFoundException;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.repositories.ClinicaRepository;
import br.vet.certvet.services.ClinicaService;
import br.vet.certvet.services.EmailService;
import br.vet.certvet.services.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Slf4j
public class ClinicaServiceImpl implements ClinicaService {

    @Autowired
    private ClinicaRepository clinicaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    @Override
    @Transactional(rollbackFor = {SQLException.class, RuntimeException.class})
    public Clinica create(ClinicaInicialRequestDto dto) {
        Optional<Clinica> response = this.clinicaRepository.findByCnpj(dto.clinicaCnpj());

        if (response.isPresent())
            throw new ConflictException("Clínica já existe.");

        Usuario usuario;
        Clinica clinica = this.clinicaRepository.saveAndFlush(new Clinica(dto));

        if (dto.donoCrmv() != null && !dto.donoCrmv().isEmpty()) {
            usuario = this.usuarioService.create(ClinicaServiceImpl.getDonoResponsavelTecnicoDto(dto), clinica);

            clinica.setResponsavelTecnico(usuario);
            this.clinicaRepository.saveAndFlush(clinica);
        } else usuario = this.usuarioService.create(ClinicaServiceImpl.getDonoDto(dto), clinica);

        this.sendCodigoClinicaThoughEmail(clinica, usuario);

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
    public Clinica findOne(String code) {
        Optional<Clinica> response = this.clinicaRepository.findByCode(code);

        if (response.isEmpty())
            throw new NotFoundException("Clínica não existe.");

        return response.get();
    }

    @Override
    public Clinica edit(ClinicaRequestDto dto, Clinica clinica) {
        clinica.fill(dto);

        return this.clinicaRepository.saveAndFlush(clinica);
    }

    private void sendCodigoClinicaThoughEmail(Clinica clinica, Usuario to) {
        final String subject = "Código da Clínica - CertVet";
        final String message = "<h1>Código da Clínica - CertVet</h1>" +
                "<br/>" +
                "<p>Clínica cadastrada com sucesso.</p>" +
                "<p>O código de acesso da Clínica é: " + clinica.getCode() + "</p>";

        try {
            this.emailService.sendTextMessage(to.getUsername(), subject, message);
        } catch (Exception e) {
            ClinicaServiceImpl.log.error(e.getMessage());

            throw new BadGatewayException("Não foi possivel enviar o e-mail com o código da clínica");
        }
    }

    private static FuncionarioRequestDto getDonoDto(ClinicaInicialRequestDto dto) {
        return ClinicaServiceImpl.getDonoResponsavelTecnicoDto(dto);
    }

    private static VeterinarioRequestDto getDonoResponsavelTecnicoDto(ClinicaInicialRequestDto dto) {
        VeterinarioRequestDto usuarioDto = new VeterinarioRequestDto();

        usuarioDto.nome = dto.donoNome();
        usuarioDto.email = dto.donoEmail();
        usuarioDto.cpf = dto.donoCpf();
        usuarioDto.rg = dto.donoRg();
        usuarioDto.celular = dto.donoCelular();
        usuarioDto.telefone = dto.donoTelefone();
        usuarioDto.logradouro = dto.donoLogradouro();
        usuarioDto.numero = dto.donoNumero();
        usuarioDto.cep = dto.donoCep();
        usuarioDto.bairro = dto.donoBairro();
        usuarioDto.cidade = dto.donoCidade();
        usuarioDto.estado = dto.donoEstado();
        usuarioDto.senha = dto.donoSenha();
        usuarioDto.crmv = dto.donoCrmv();
        usuarioDto.is_admin = true;

        return usuarioDto;
    }
}
