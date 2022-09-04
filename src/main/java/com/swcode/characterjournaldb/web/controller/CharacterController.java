package com.swcode.characterjournaldb.web.controller;

import com.swcode.characterjournaldb.business.model.FantasyCharacter;
import com.swcode.characterjournaldb.data.FantasyCharacterRepository;
import com.swcode.characterjournaldb.data.FileStorageRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Controller
@RequestMapping("/characters")
@Log4j2
public class CharacterController {

    public static final String DISPOSITION = """
             attachment; filename="%s"
            """;
    private FantasyCharacterRepository characterRepository;
    private FileStorageRepository fileStorageRepository;

    public CharacterController(FantasyCharacterRepository characterRepository, FileStorageRepository fileStorageRepository) {
        this.characterRepository = characterRepository;
        this.fileStorageRepository = fileStorageRepository;
    }

    @ModelAttribute("fantasyCharacters")
    public Iterable<FantasyCharacter> getFantasyCharacters() {
        return characterRepository.findAll();
    }

    @ModelAttribute
    public FantasyCharacter getCharacter() {
        return new FantasyCharacter();

    }

    @GetMapping
    public String showCharacterPage(Model model) {
        return "characters";
    }

    @PostMapping
    public String saveCharacter(Model model, @Valid FantasyCharacter fantasyCharacter, Errors errors, @RequestParam("photoFilename") MultipartFile photoFile) throws IOException {
        log.info(photoFile.getOriginalFilename());
        log.info("errors: " + errors);
        if (!errors.hasErrors()) {
            try {
                fileStorageRepository.save(photoFile.getOriginalFilename(), photoFile.getInputStream());
                characterRepository.save(fantasyCharacter);
                return "redirect:characters";
            } catch (IOException e) {
                model.addAttribute("errorMessage", "System unable to accept photo files.");
                return "characters";
            }
        }
        return "characters";
    }

    @PostMapping(params = "delete=true")
    public String deleteCharacter(@RequestParam Optional<List<Long>> selections) {
        System.out.println(selections);
        if (selections.isPresent()) {
            characterRepository.deleteAllById(selections.get());
        }
        return "redirect:characters";
    }

    @PostMapping(params = "edit=true")
    public String editCharacter(@RequestParam Optional<List<Long>> selections, Model model) {
        System.out.println(selections);
        if (selections.isPresent()) {
            Optional<FantasyCharacter> fantasyCharacter = characterRepository.findById(selections.get().get(0));
            model.addAttribute("fantasyCharacter", fantasyCharacter);
        }
        return "characters";
    }

    @GetMapping("/images/{resource}")
    public ResponseEntity<Resource> getResource(@PathVariable String resource) {
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, format(DISPOSITION, resource))
                .body(fileStorageRepository.findByName(resource));
    }
}
