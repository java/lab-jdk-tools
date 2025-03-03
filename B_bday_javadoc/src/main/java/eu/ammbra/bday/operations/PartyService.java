package eu.ammbra.bday.operations;

import eu.ammbra.bday.store.DataStore;
import eu.ammbra.bday.details.Cake;
import eu.ammbra.bday.details.Celebration;
import eu.ammbra.bday.details.Party;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;


///Intermediates connection with the {@link DataStore} methods that get events
///and maps potential {@link Celebration} objects to {@link Party}.
///
public class PartyService {


     ///Data store instance used to retrieve celebration store.
     ///
    private final DataStore dataStore;

     ///Constructs a new PartyService instance with the specified store store.
     ///
     ///@param dataStore the store store instance
     ///
	 public PartyService(DataStore dataStore) {
        this.dataStore = dataStore;
    }


	///Processes all potential events and returns a list of party objects.
     ///
     ///@return a list of party objects
     ///
    public List<Party> processAllParties() {
        return dataStore.getCelebrations()
                .stream()
                .map(c -> new Party(
                        c.id(),
                        c.person().firstName(),
                        null,
                        c.location(),
                        c.attendees()))
                .collect(toList());
    }

     ///Processes an event by its ID and returns the corresponding party object.
     ///
     ///@param id the ID of the event to process
     ///@return the party object
     ///@throws PartyNotFoundException if no party is found with the specified ID
     ///
    public Party processPartyById(long id) throws PartyNotFoundException {
        Optional<Celebration> potentialCelebration = dataStore.getCelebrationById(id);
        if (potentialCelebration.isPresent()) {
            Celebration celebration = potentialCelebration.get();
            int attendees = celebration.attendees();
            Cake cake = CakeCalculator.evaluateDimensions(celebration.flavor(), attendees);
            return new Party(id,
                    celebration.person().firstName(),
                    cake,
                    celebration.location(),
                    celebration.attendees());
        } else {
            throw new PartyNotFoundException("Event with id %d is not found".formatted(id));
        }
    }
}