package legacy;

import java.net.*;

import com.sun.net.httpserver.*;

public class InnWeb {
	public static void main(String[] args) throws Exception {
		int port = Integer.parseInt(System.getProperty("app.port", "8080"));

		HttpServer.
				create(new InetSocketAddress(port), 0).
				createContext("/", (HttpExchange exchange) -> {
					byte[] body = null;

					String uri = exchange.getRequestURI().toString();
					switch (uri) {
						case "/": {
							body = "Hello World".getBytes();
							exchange.getResponseHeaders().add("Content-Type", "text/html");
							break;
						}
					}

					if (body != null) {
						exchange.sendResponseHeaders(200, body.length);
						exchange.getResponseBody().write(body);
					} else {
						exchange.sendResponseHeaders(404, 0);
					}
					exchange.close();
				}).
				getServer().
				start();

	}
}
