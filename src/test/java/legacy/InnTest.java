package legacy;

import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class InnTest {
  Inn inn = new Inn();

  @Test
  public void should_list_items() {
    assertThat(inn.getItems()).onProperty("name").containsExactly("+5 Dexterity Vest", "Aged Brie", "Elixir of the Mongoose", "Sulfuras, Hand of Ragnaros", "Backstage passes to a TAFKAL80ETC concert", "Conjured Mana Cake");
    assertThat(inn.getItems()).onProperty("quality").containsExactly(20, 0, 7, 80, 20, 6);
    assertThat(inn.getItems()).onProperty("sellIn").containsExactly(10, 2, 5, 0, 15, 3);
  }

  @Test
  public void should_update_items() {
    inn.updateQuality();

    assertThat(inn.getItems()).onProperty("name").containsExactly("+5 Dexterity Vest", "Aged Brie", "Elixir of the Mongoose", "Sulfuras, Hand of Ragnaros", "Backstage passes to a TAFKAL80ETC concert", "Conjured Mana Cake");
    assertThat(inn.getItems()).onProperty("quality").containsExactly(19, 1, 6, 80, 21, 5);
    assertThat(inn.getItems()).onProperty("sellIn").containsExactly(9, 1, 4, 0, 14, 2);
  }

  @Test
  public void should_update_items_twice() {
    inn.updateQuality();
    inn.updateQuality();

    assertThat(inn.getItems()).onProperty("quality").containsExactly(18, 2, 5, 80, 22, 4);
    assertThat(inn.getItems()).onProperty("sellIn").containsExactly(8, 0, 3, 0, 13, 1);
  }

  @Test
  public void should_update_a_lot() {
    for (int day = 0; day < 100; day++) {
      inn.updateQuality();
    }

    assertThat(inn.getItems()).onProperty("quality").containsExactly(0, 50, 0, 80, 0, 0);
    assertThat(inn.getItems()).onProperty("sellIn").containsExactly(-90, -98, -95, 0, -85, -97);
  }

  @Test
  public void should_test_against_legacy_code() {
    LegacyInn legacyInn = new LegacyInn();

    for (int day = 0; day < 100; day++) {
      List<Item> items = inn.getItems();
      List<Item> legacyItems = legacyInn.getItems();

      for (int i = 0; i < legacyItems.size(); i++) {
        Item item = items.get(i);
        Item legacyItem = legacyItems.get(i);

        assertThat(item.getName()).isEqualTo(legacyItem.getName());
        assertThat(item.getQuality()).isEqualTo(legacyItem.getQuality());
        assertThat(item.getSellIn()).isEqualTo(legacyItem.getSellIn());
      }

      inn.updateQuality();
      legacyInn.updateQuality();
    }
  }

}


