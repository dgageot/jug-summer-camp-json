package legacy;

import java.io.*;
import java.net.*;
import java.nio.file.*;

import com.google.gson.*;
import com.sun.net.httpserver.*;

public class InnWeb {
	public static void main(String[] args) throws IOException {
		int port = Integer.parseInt(System.getProperty("app.port", "8080"));
		System.out.println(port);

		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		server.createContext("/", (HttpExchange exchange) -> {
			byte[] response = null;

			String uri = exchange.getRequestURI().toString();
			switch (uri) {
				case "/": {
					response = Files.readAllBytes(Paths.get("app", "index.html"));
					exchange.getResponseHeaders().add("Content-Type", "text/html");
					break;
				}
				case "/items": {
					String body = new Gson().toJson(new Inn().getItems());
					response = body.getBytes();
					exchange.getResponseHeaders().add("Content-Type", "application/json");
					break;
				}
			}

			if (response != null) {
				exchange.sendResponseHeaders(200, response.length);
				exchange.getResponseBody().write(response);
			} else {
				exchange.sendResponseHeaders(404, 0);
			}
			exchange.close();
		});
		server.start();
	}
}
