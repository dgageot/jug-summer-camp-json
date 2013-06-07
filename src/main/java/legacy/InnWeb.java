package legacy;

import java.io.*;
import java.net.*;

import com.google.common.base.*;
import com.sun.net.httpserver.*;

public class InnWeb {
	public static void main(String[] args) throws Exception {
		int port = Integer.parseInt(Objects.firstNonNull(System.getenv("PORT"), "8080"));

		HttpServer
				.create(new InetSocketAddress(port), 0)
				.createContext("/", new HttpHandler() {
					@Override
					public void handle(HttpExchange exchange) throws IOException {
						String uri = exchange.getRequestURI().toString();

						if ("/".equals(uri)) {
							String body = "Hello World";
							byte[] response = body.getBytes();
							exchange.getResponseHeaders().add("Content-Type", "text/plain");
							exchange.sendResponseHeaders(200, response.length);
							exchange.getResponseBody().write(response);
						} else {
							exchange.sendResponseHeaders(404, 0);
						}

						exchange.close();
					}
				})
				.getServer()
				.start();
	}
}
