package legacy;

import com.google.common.base.Joiner;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class InnWebServer implements HttpHandler {
  private final Inn inn = new Inn();

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    byte[] body = new byte[0];

    if ("/update".equals(exchange.getRequestURI().getPath())) {
      inn.updateQuality();
    } else {
      body = getItemsAsJson().getBytes();
    }

    exchange.sendResponseHeaders(200, body.length);
    exchange.getResponseBody().write(body);
    exchange.close();
  }

  private String getItemsAsJson() {
    return Joiner.on(',').join(inn.getItems());
  }

  public static void main(String[] args) throws IOException {
    int port = Integer.parseInt(System.getenv("PORT"));

    HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
    server.createContext("/", new InnWebServer());
    server.start();
  }
}