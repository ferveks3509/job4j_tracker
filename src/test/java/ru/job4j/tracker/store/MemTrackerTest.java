package ru.job4j.tracker.store;

import org.junit.jupiter.api.Test;
import ru.job4j.tracker.model.Item;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemTrackerTest {

    @Test
    void whenFindById() {
        MemTracker memTracker = new MemTracker();
        Item item = new Item("name");
        memTracker.add(item);
        Item rsl = memTracker.findById(0);
        assertEquals(rsl.getName(), "name");
    }

    @Test
    void whenFindAllTest() {
        MemTracker memTracker = new MemTracker();
        Item item = new Item("name");
        Item item1 = new Item("name1");
        memTracker.add(item);
        memTracker.add(item1);
        List<Item> rsl = memTracker.findAll();
        assertEquals(rsl.get(0).getName(), "name");
        assertEquals(rsl.get(1).getName(), "name1");
    }

    @Test
    void whenFindByName() {
        MemTracker memTracker = new MemTracker();
        Item item = new Item("name");
        Item item1 = new Item("name1");
        memTracker.add(item);
        memTracker.add(item1);
        List<Item> rsl = memTracker.findByName("name");
        String expected = "name";
        assertEquals(expected, rsl.get(0).getName());
    }

    @Test
    void findByName() {
        MemTracker memTracker = new MemTracker();
        Item first = new Item("First");
        Item second = new Item("Second");
        memTracker.add(first);
        memTracker.add(second);
        memTracker.add(new Item("First"));
        memTracker.add(new Item("Second"));
        memTracker.add(new Item("First"));
        List<Item> result = memTracker.findByName(second.getName());
        assertThat(result.get(0).getName()).isEqualTo(second.getName());
    }

    @Test
    void whenReplaceItemSuccess() {
        MemTracker memTracker = new MemTracker();
        Item first = new Item("first");
        memTracker.add(first);
        Item second = new Item("second");
        boolean rsl = memTracker.replace(0, second);
        assertTrue(rsl);
    }

    @Test
    void whenReplaceNotSuccess() {
        MemTracker memTracker = new MemTracker();
        Item item = new Item("Bug");
        memTracker.add(item);
        Item updateItem = new Item("Bug with description");
        boolean result = memTracker.replace(1000, updateItem);
        assertThat(memTracker.findById(item.getId()).getName()).isEqualTo("Bug");
        assertThat(result).isFalse();
    }

    @Test
    void whenDeleteTrue() {
        MemTracker memTracker = new MemTracker();
        Item item = new Item("Bug");
        memTracker.add(item);
        int id = item.getId();
        memTracker.delete(id);
        assertNull(memTracker.findById(id));
    }

    @Test
    void whenDeleteFalse() {
        MemTracker memTracker = new MemTracker();
        Item item = new Item("Bug");
        memTracker.add(item);
        boolean result = memTracker.delete(1000);
        assertThat(memTracker.findById(item.getId()).getName()).isEqualTo("Bug");
        assertThat(result).isFalse();
    }
}