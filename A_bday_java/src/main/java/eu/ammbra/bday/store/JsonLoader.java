package eu.ammbra.bday.store;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.ammbra.bday.handlers.LocalDateTypeAdapter;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;


/// Interface for loading JSON store from a file.
///
/// @param <T> the type of store to load
////
public interface JsonLoader<T> {

	/// Loads JSON store from a file into a list of objects.
	///
	/// @param filename the name of the file to load from
	/// @param type     the type of store to load
	/// @return an unmodifiable list of loaded objects
	/// @throws IllegalArgumentException if an I/O error occurs while loading the file
	////
	default List<T> loadJson(String filename, Type type) {
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
				.create();

		try (Reader reader = new FileReader(filename)) {
			List<T> json = gson.fromJson(reader, type);
			return Collections.unmodifiableList(json);
		} catch (IOException e) {
			throw new IllegalArgumentException("Failed to load celebrations from file: " + filename, e);
		}
	}
}