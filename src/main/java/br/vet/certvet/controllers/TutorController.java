//package br.vet.certvet.controllers;
//
//import br.vet.certvet.config.security.service.TokenService;
//import br.vet.certvet.dto.requests.CadastroTutorDto;
//import br.vet.certvet.models.Usuario;
//import br.vet.certvet.services.TutorService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.net.URI;
//import java.util.Optional;
//
//@CrossOrigin
//@RestController
//@RequestMapping("/api/tutor")
//@Slf4j
//public class TutorController extends BaseController{
//
//    @Autowired
//    private TutorService tutorService;
//
//    @Autowired
//    private TokenService tokenService;
//
//    @PostMapping
//    public ResponseEntity<Usuario> registerTutor(@RequestBody CadastroTutorDto tutorDto) {
//        log.info("Tutor a ser cadastrado: " + tutorDto);
//        Optional<Usuario> referenceTutor = tutorService.create(tutorDto);
//        return referenceTutor.isEmpty()
//                ? ResponseEntity.badRequest().build()
//                : ResponseEntity.created(URI.create("/api/tutor/" + referenceTutor.get().getId().toString())).build();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Usuario> getOne(
//            @PathVariable Long id,
//            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth
//    ) {
//        Optional<Usuario> referenceTutor = tutorService.findByIdAndClinica(id, getClinicaFromRequester(auth));
//        return referenceTutor.isEmpty()
//                ? ResponseEntity.notFound().build()
//                : ResponseEntity.ok(referenceTutor.get());
//    }
//}
