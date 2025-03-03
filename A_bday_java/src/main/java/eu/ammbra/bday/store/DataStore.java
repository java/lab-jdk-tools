package eu.ammbra.bday.store;

import com.google.gson.reflect.TypeToken;
import eu.ammbra.bday.details.Celebration;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;


///Stores and manages celebration store loaded from a JSON file.
///
public class DataStore implements JsonLoader<Celebration> {


	///List of celebrations loaded from the JSON file.
	///
	private final List<Celebration> celebrations;


	///Constructs a new DataStore instance and loads celebrations from the specified file.
	///
	///@param filename the name of the JSON file to load from
	///
	public DataStore(String filename) {
		Type type = new TypeToken<List<Celebration>>() {}.getType();
		this.celebrations = this.loadJson(filename, type);
	}

	///Returns the list of celebrations.
	///
	///@return the list of celebrations
	///
	public List<Celebration> getCelebrations() {
		return celebrations;
	}

	///Finds a celebration by its ID.
	///
	///@param id the ID of the celebration to find
	///@return an optional containing the celebration if found, otherwise an empty optional
	///
	public Optional<Celebration> getCelebrationById(long id) {
		return celebrations.stream()
				.filter(event -> event.id() == id)
				.findFirst();
	}
}