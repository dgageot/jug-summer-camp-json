package legacy;

import java.net.*;

import com.sun.net.httpserver.*;

public class InnWeb {
	public static void main(String[] args) throws Exception {
		int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));

		HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 0);

		httpServer.createContext("/", exchange -> {
			String uri = exchange.getRequestURI().toString();

			if ("/".equals(uri)) {
				byte[] response = "Hello World".getBytes();
				exchange.getResponseHeaders().add("Content-Type", "text/plain");
				exchange.sendResponseHeaders(200, response.length);
				exchange.getResponseBody().write(response);
			} else {
				exchange.sendResponseHeaders(404, 0);
			}

			exchange.close();
		});

		httpServer.start();
	}
}
