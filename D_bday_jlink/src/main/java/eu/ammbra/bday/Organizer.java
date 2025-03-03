package eu.ammbra.bday;

import com.sun.net.httpserver.HttpServer;
import eu.ammbra.bday.handlers.PartyHandler;
import eu.ammbra.bday.store.DataStore;
import eu.ammbra.bday.operations.PartyService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;


/// Main server application for an application responsible for organizing a birthday party.
/// This server serves both static files and dynamic API endpoints.
///
public class Organizer {
	private static final int PORT = 8081;
	private static HttpServer server;

	/// Starts the HTTP server to serve static files and dynamic store.
	///
	/// @param args Command-line arguments that may contain the path to static JSON file.
	 /// @throws IOException If the server cannot start.
	///
	public static void main(String[] args) throws IOException, URISyntaxException {
		String filename = (args.length > 0) ? args[0] : "./src/main/resources/store/events.json";
		int port = (args.length > 1) ? Integer.parseInt(args[1]) : PORT;

		DataStore store  = new DataStore(filename);
		PartyService service = new PartyService(store);

		server = HttpServer.create(new InetSocketAddress(port), 0);
		server.createContext("/api/organize", new PartyHandler(service));
		System.out.printf("Birthday Party Server is running at http://127.0.0.1:%d%n/api/organize", port);
		server.start();
	}

	public static void stop() {
		if (server != null) {
			server.stop(0);
		}
	}
}