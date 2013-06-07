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

		HttpServer
				.create(new InetSocketAddress(port), 0)
				.createContext("/", new HttpHandler() {
					@Override
					public void handle(HttpExchange exchange) throws IOException {
						String uri = exchange.getRequestURI().toString();

						switch (uri) {
							case "/items": {
								byte[] response = new Gson().toJson(new Inn().getItems()).getBytes();
								exchange.getResponseHeaders().add("Content-Type", "application/json");
								exchange.sendResponseHeaders(200, response.length);
								exchange.getResponseBody().write(response);
								break;
							}
							case "/": {
								byte[] response = Files.readAllBytes(Paths.get("app", "index.html"));
								exchange.getResponseHeaders().add("Content-Type", "text/html");
								exchange.sendResponseHeaders(200, response.length);
								exchange.getResponseBody().write(response);
								break;
							}
							default:
								exchange.sendResponseHeaders(404, 0);
								break;
						}

						exchange.close();
					}
				})
				.getServer()
				.start();
	}
}
