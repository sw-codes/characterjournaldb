package com.swcode.characterjournaldb.data;

import com.swcode.characterjournaldb.business.model.FantasyCharacter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
public class FantasyCharacterDataLoader implements ApplicationRunner {

    private FantasyCharacterRepository characterRepository;

    public FantasyCharacterDataLoader(FantasyCharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (characterRepository.count() == 0) {
            List<FantasyCharacter> fantasyCharacters = List.of(
//                    new FantasyCharacter(null, "laura", "human", "female", "warlock"),
//                    new FantasyCharacter(null, "fred", "undead", "male", "hunter"),
//                    new FantasyCharacter(null, "obi-wan", "human", "male", "jedi knight"),
//                    new FantasyCharacter(null, "anakin", "human", "male", "jedi padawan"),
//                    new FantasyCharacter(null, "Gotrek", "dwarf", "male", "slayer")
            );
            characterRepository.saveAll(fantasyCharacters);
        }
    }
}
