package eu.ammbra.bday.details;

import java.time.LocalDate;

public record Person(String firstName, String lastName, LocalDate date) {
}
