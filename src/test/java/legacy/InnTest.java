package legacy;

import static org.assertj.core.api.Assertions.*;

import java.util.*;

import org.junit.*;

public class InnTest {
	@Test
	public void list_items() {
		Inn inn = new Inn();
		List<Item> items = inn.getItems();

		assertThat(items).extracting("name").containsExactly("+5 Dexterity Vest", "Aged Brie", "Elixir of the Mongoose", "Sulfuras, Hand of Ragnaros", "Backstage passes to a TAFKAL80ETC concert", "Conjured Mana Cake");
		assertThat(items).extracting("sellIn").containsExactly(10, 2, 5, 0, 15, 3);
		assertThat(items).extracting("quality").containsExactly(20, 0, 7, 80, 20, 6);
	}

	@Test
	public void update_items() {
		Inn inn = new Inn();
		inn.updateQuality();
		List<Item> items = inn.getItems();

		assertThat(items).extracting("name").containsExactly("+5 Dexterity Vest", "Aged Brie", "Elixir of the Mongoose", "Sulfuras, Hand of Ragnaros", "Backstage passes to a TAFKAL80ETC concert", "Conjured Mana Cake");
		assertThat(items).extracting("sellIn").containsExactly(9, 1, 4, 0, 14, 2);
		assertThat(items).extracting("quality").containsExactly(19, 1, 6, 80, 21, 5);
	}

	@Test
	public void compare_to_legacy_code() {
		Inn inn = new Inn();
		LegacyInn legacyInn = new LegacyInn();

		List<Item> items = inn.getItems();
		List<Item> legacyItems = legacyInn.getItems();

		assertThat(items).hasSize(legacyItems.size());

		for (int day = 0; day < 1000; day++) {
			for (int i = 0; i < items.size(); i++) {
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

