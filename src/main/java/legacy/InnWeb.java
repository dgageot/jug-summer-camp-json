package legacy;

import net.codestory.http.*;

public class InnWeb {
	public static void main(String[] args) {
		new WebServer().configure(routes -> {
			routes.get("/items", () -> new Inn().getItems());
		}).start(8080);
	}
}
