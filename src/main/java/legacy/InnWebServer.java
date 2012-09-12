package legacy;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.functions.Mapper;

public class InnWebServer implements HttpHandler {
  final Inn inn = new Inn();

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    URI uri = exchange.getRequestURI();
    String query = uri.getQuery();
    String body = "";

    if ("/update".equals(uri.getPath())) {
      inn.updateQuality();
    } else if (query != null) {
      String callback = query.split("[=&]")[1];
      body = getJsonp(callback);
    } else {
      body = getJson();
    }

    byte[] response = body.getBytes();
    exchange.sendResponseHeaders(200, response.length);
    exchange.getResponseBody().write(response);
    exchange.close();
  }

  String getJson() {
    return String.join(",", inn.getItems().map(toJson()));
  }

  String getJsonp(String callback) {
    return callback + "(" + getJson() + ")";
  }

  static Mapper<Item, String> toJson() {
    return item -> String.format("{\"name\":\"%s\", \"quality\":%d, \"sellIn\":%d}", item.getName(), item.getQuality(), item.getSellIn());
  }

  public static void main(String[] args) throws IOException {
    int port = Integer.parseInt(System.getenv("PORT"));

    HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
    server.createContext("/", new InnWebServer());
    server.start();
  }
}