package legacy;

import com.google.common.base.Joiner;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Inn {
  private List<Item> items;

  public Inn() {
    items = new ArrayList<Item>();
    items.add(new Item("+5 Dexterity Vest", 10, 20));
    items.add(new Item("Aged Brie", 2, 0));
    items.add(new Item("Elixir of the Mongoose", 5, 7));
    items.add(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
    items.add(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
    items.add(new Item("Conjured Mana Cake", 3, 6));
  }

  public List<Item> getItems() {
    return items;
  }

  public void updateQuality() {
    for (Item item : items) {
      updateQuality(item);
    }
  }

  private void updateQuality(Item item) {
    if ("Sulfuras, Hand of Ragnaros".equals(item.getName())) {
      return;
    }

    item.setSellIn(item.getSellIn() - 1);

    if ("Aged Brie".equals(item.getName())) {
      increaseQuality(item);

      if (item.getSellIn() < 0) increaseQuality(item);
    } else if ("Backstage passes to a TAFKAL80ETC concert".equals(item.getName())) {
      increaseQuality(item);

      if (item.getSellIn() < 10) increaseQuality(item);
      if (item.getSellIn() < 5) increaseQuality(item);
      if (item.getSellIn() < 0) item.setQuality(0);
    } else {
      decreaseQuality(item);

      if (item.getSellIn() < 0) decreaseQuality(item);
    }
  }

  private void decreaseQuality(Item item) {
    if (item.getQuality() > 0) {
      item.setQuality(item.getQuality() - 1);
    }
  }

  private void increaseQuality(Item item) {
    if (item.getQuality() < 50) {
      item.setQuality(item.getQuality() + 1);
    }
  }

  public static void main(String[] args) throws Exception {
    int port = parseInt(System.getenv("PORT"));

    HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
    server.createContext("/", new HttpHandler() {
      @Override
      public void handle(HttpExchange exchange) throws IOException {
        List<Item> items = new Inn().getItems();

        String body = "[" + Joiner.on(",").join(items) + "]";

        String query = exchange.getRequestURI().getQuery();
        if (null != query) {
          String callback = query.split("[&=]")[1];
          body = callback + "(" + body + ")";
        }

        byte[] response = body.getBytes();
        exchange.sendResponseHeaders(200, response.length);
        exchange.getResponseBody().write(response);
        exchange.close();
      }
    });
    server.start();

  }
}
