package br.vet.certvet.controllers;

import br.vet.certvet.dto.requests.AnimalRequestDto;
import br.vet.certvet.dto.responses.AnimalResponseDto;
import br.vet.certvet.models.Animal;
import br.vet.certvet.models.Clinica;
import br.vet.certvet.services.AnimalService;
import br.vet.certvet.services.ClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AnimalController {
    @Autowired
    private AnimalService animalService;

    @PostMapping("/animal")
    public ResponseEntity<AnimalResponseDto> create(@RequestBody @Valid AnimalRequestDto dto) {
        Animal animal = this.animalService.create(dto);

        return new ResponseEntity<>(new AnimalResponseDto(animal), HttpStatus.CREATED);
    }
}
