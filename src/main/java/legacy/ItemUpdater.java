package legacy;

public class ItemUpdater {
	private final Item item;

	public ItemUpdater(Item item) {
		this.item = item;
	}

	void updateQuality() {
		if (item.getName().equals("Sulfuras, Hand of Ragnaros")) {
			return;
		}

		item.setSellIn(item.getSellIn() - 1);

		if (item.getName().equals("Aged Brie")) {
			increaseQuality();

			if (item.getSellIn() < 0) increaseQuality();
		} else if (item.getName().equals("Backstage passes to a TAFKAL80ETC concert")) {
			increaseQuality();

			if (item.getSellIn() < 10) increaseQuality();
			if (item.getSellIn() < 5) increaseQuality();
			if (item.getSellIn() < 0) item.setQuality(0);
		} else {
			decreaseQuality();

			if (item.getSellIn() < 0) decreaseQuality();
		}
	}

	void decreaseQuality() {
		if (item.getQuality() > 0) {
			item.setQuality(item.getQuality() - 1);
		}
	}

	void increaseQuality() {
		if (item.getQuality() < 50) {
			item.setQuality(item.getQuality() + 1);
		}
	}
}