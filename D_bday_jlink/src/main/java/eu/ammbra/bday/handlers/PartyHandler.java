package eu.ammbra.bday.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import eu.ammbra.bday.details.Party;
import eu.ammbra.bday.operations.PartyNotFoundException;
import eu.ammbra.bday.operations.PartyService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;


///Handles HTTP requests for party-related store.
///
public class PartyHandler implements HttpHandler {

	private final PartyService service;


	///Constructs a new PartyHandler instance with the specified service.
	///
	///@param service the party service instance
	///
	public PartyHandler(PartyService service) {
		this.service = service;
	}


	///Handles an HTTP request for party-related store.
	///
	/// @param exchange the HTTP exchange object
	 /// @throws IOException if an I/O error occurs
	///
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
			StringBuilder result = new StringBuilder();
			int statusCode = 200;

			String path = exchange.getRequestURI().getPath();

			String[] segments = path.split("/");

			switch (segments.length) {
				case int _ when (segments.length < 3 || !segments[2].equals("organize")) -> {
					result.append("Invalid endpoint");
					statusCode = 404;
				}
				case 3 -> {
					List<Party> parties = service.processAllParties();
					result.append(new Gson().toJson(parties));
				}
				case int _ -> {
					try {
						Party party = service.processPartyById(Long.parseLong(segments[3]));
						result.append(new Gson().toJson(party));
					} catch (PartyNotFoundException e) {
						result.append(e.getMessage());
					}
				}
			}

			exchange.getResponseHeaders().set("Content-Type", "application/json");
			String response = result.toString();
			exchange.sendResponseHeaders(statusCode, response.getBytes().length);

			try (OutputStream os = exchange.getResponseBody()) {
				os.write(response.getBytes(StandardCharsets.UTF_8));
			}
		} else {
			exchange.sendResponseHeaders(405, -1);
		}
	}
}