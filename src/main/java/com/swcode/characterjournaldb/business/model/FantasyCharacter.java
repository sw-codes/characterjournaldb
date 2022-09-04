package com.swcode.characterjournaldb.business.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FantasyCharacter {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "name can't be empty")
    private String name;

    @NotEmpty(message = "race field cannot be empty")
    private String race;

    @NotEmpty(message = "gender can't be empty")
    private String gender;

    @NotEmpty(message = "class can't be empty")
    private String characterClass;

    private String photoFilename;
}
