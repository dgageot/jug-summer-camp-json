package legacy;

import com.google.common.base.Joiner;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.IOException;
import java.net.InetSocketAddress;

public class InnWebServer implements HttpHandler {
  private Inn inn = new Inn();

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    String body = "";

    if ("/update".equals(exchange.getRequestURI().getPath())) {
      inn.updateQuality();
    } else {
      body = itemsToJson();

      String query = exchange.getRequestURI().getQuery();
      if (null != query) {
        String callback = query.split("[&=]")[1];
        body = callback + "(" + body + ")";
      }
    }

    byte[] response = body.getBytes();
    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length);
    exchange.getResponseBody().write(response);
    exchange.close();
  }

  private String itemsToJson() {
    return "[" + Joiner.on(",").join(inn.getItems()) + "]";
  }

  public static void main(String[] args) throws Exception {
    int port = Integer.parseInt(System.getenv("PORT"));
    HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
    server.createContext("/", new InnWebServer());
    server.start();
  }
}