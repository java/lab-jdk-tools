package eu.ammbra.bday;

import com.sun.net.httpserver.HttpServer;
import eu.ammbra.bday.handlers.PartyHandler;
import eu.ammbra.bday.store.DataStore;
import eu.ammbra.bday.operations.PartyService;

import java.io.IOException;
import java.net.InetSocketAddress;


/// Main server application for an application responsible for organizing a birthday party.
/// This server serves both static files and dynamic API endpoints.
///
public class Organizer {
	private static final int PORT = 8081;


	/// Starts the HTTP server to serve static files and dynamic store.
	///
	/// @param args Command-line arguments that may contain the path to static JSON file.
	/// @throws IOException If the server cannot start.
	///
	public static void main(String[] args) throws IOException {
		String filename = (args.length > 0) ? args[0] : "./src/main/resources/store/events.json";
		DataStore store  = new DataStore(filename);
		PartyService service = new PartyService(store);
		HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
		server.createContext("/api/organize", new PartyHandler(service));
		System.out.printf("Birthday Party Server is running at http://127.0.0.1:%d/api/organize%n", PORT);
		server.start();
	}
}