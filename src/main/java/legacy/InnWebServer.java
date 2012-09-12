package legacy;

import com.google.common.base.Function;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;

import static net.gageot.listmaker.ListMaker.with;

public class InnWebServer implements HttpHandler {
  private final Inn inn = new Inn();

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    URI uri = exchange.getRequestURI();
    String path = uri.getPath();

    handle(exchange, path);
  }

  private void handle(HttpExchange exchange, String path) throws IOException {
    String body = "";

    if ("/update".equals(path)) {
      inn.updateQuality();
    } else {
      body = with(inn.getItems()).to(TO_JSON).join(",");
    }

    byte[] response = body.getBytes();
    exchange.sendResponseHeaders(200, response.length);
    exchange.getResponseBody().write(response);
    exchange.close();
  }

  private static Function<Item, String> TO_JSON = new Function<Item, String>() {
    @Override
    public String apply(@Nullable Item item) {
      return String.format("{\"name\": \"%s\", \"quality\": %d, \"sellIn\": %d}", item.getName(), item.getQuality(), item.getSellIn());
    }
  };

  public static void main(String[] args) throws IOException {
    int port = Integer.parseInt(System.getenv("PORT"));

    HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
    server.createContext("/", new InnWebServer());
    server.start();
  }
}