package legacy;

class ItemUpdater {
  final Item item;

  ItemUpdater(Item item) {
    this.item = item;
  }

  void updateQuality() {
    if ("Sulfuras, Hand of Ragnaros".equals(item.getName())) {
      return;
    }

    decreaseSellIm();

    if ("Aged Brie".equals(item.getName())) {
      increaseQuality();
      if (item.getSellIn() < 0) increaseQuality();
    } else if ("Backstage passes to a TAFKAL80ETC concert".equals(item.getName())) {
      increaseQuality();
      if (item.getSellIn() < 10) increaseQuality();
      if (item.getSellIn() < 5) increaseQuality();
      if (item.getSellIn() < 0) item.setQuality(0);
    } else {
      decreaseQuality();
      if (item.getSellIn() < 0) decreaseQuality();
    }
  }

  void decreaseSellIm() {
    item.setSellIn(item.getSellIn() - 1);
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