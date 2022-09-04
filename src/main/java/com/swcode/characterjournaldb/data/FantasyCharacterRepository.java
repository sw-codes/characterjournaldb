package com.swcode.characterjournaldb.data;

import com.swcode.characterjournaldb.business.model.FantasyCharacter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FantasyCharacterRepository extends CrudRepository<FantasyCharacter, Long> {
}
