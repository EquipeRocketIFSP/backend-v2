package br.vet.certvet.services.implementation;

import br.vet.certvet.dto.requests.PasswordResetEmailRequestDto;
import br.vet.certvet.dto.requests.PasswordResetRequestDto;
import br.vet.certvet.exceptions.BadGatewayException;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.models.Usuario;
import br.vet.certvet.services.ClinicaService;
import br.vet.certvet.services.EmailService;
import br.vet.certvet.services.PasswordResetService;
import br.vet.certvet.services.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class PasswordResetServiceImpl implements PasswordResetService {
    @Autowired
    private ClinicaService clinicaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    @Override
    public void sendPasswordResetEmail(PasswordResetEmailRequestDto dto) {
        final String subject = "CertVet - Redefinição de Senha";
        final String hash = DigestUtils.sha256Hex(dto.email + dto.clinica + LocalDateTime.now());

        Clinica clinica = this.clinicaService.findOne(dto.clinica);
        Usuario usuario = this.usuarioService.findOne(dto.email, clinica);
        usuario.setResetPasswordToken(hash);

        String message = "<h1>Redefinir senha - CertVet</h1>" +
                "<br/>" +
                "<p>Click <a href=\"http://localhost:3000/redefinir-senha?t=" + hash + "\">aqui</a> para redefinir a sua senha</p>" +
                "<b>Aviso: Esse link pode ser utilizado apenas uma vez.</b>";

        try {
            this.emailService.sendTextMessage(dto.email, subject, message);
            this.usuarioService.edit(usuario);
        } catch (Exception e) {
            PasswordResetServiceImpl.log.error(e.getMessage());

            throw new BadGatewayException("Não foi possivel enviar o e-mail de recuperação");
        }
    }

    @Override
    public void resetPassword(PasswordResetRequestDto dto) {
        Usuario usuario = this.usuarioService.findOne(dto.token);
        usuario.setPassword(dto.senha);
        usuario.setResetPasswordToken(null);

        this.usuarioService.edit(usuario);
    }
}
