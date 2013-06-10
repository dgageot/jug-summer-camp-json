package legacy;

import static org.fest.assertions.Assertions.*;

import org.junit.*;

public class InnTest {
  Inn inn = new Inn();

  @Test
  public void should_write_tests() {
    assertThat(inn.getItems()).onProperty("name").containsExactly("+5 Dexterity Vest", "Aged Brie", "Elixir of the Mongoose", "Sulfuras, Hand of Ragnaros", "Backstage passes to a TAFKAL80ETC concert", "Conjured Mana Cake");
    assertThat(inn.getItems()).onProperty("quality").containsExactly(20, 0, 7, 80, 20, 6);
    assertThat(inn.getItems()).onProperty("sellIn").containsExactly(10, 2, 5, 0, 15, 3);
  }

  @Test
  public void should_write_tests2() {
    inn.updateQuality();

    assertThat(inn.getItems()).onProperty("quality").containsExactly(19, 1, 6, 80, 21, 5);
    assertThat(inn.getItems()).onProperty("sellIn").containsExactly(9, 1, 4, 0, 14, 2);
  }
}

