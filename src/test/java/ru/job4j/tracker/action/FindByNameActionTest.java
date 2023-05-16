package ru.job4j.tracker.action;

import org.junit.jupiter.api.Test;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.output.Output;
import ru.job4j.tracker.output.StubOutput;
import ru.job4j.tracker.store.MemTracker;
import ru.job4j.tracker.store.Store;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FindByNameActionTest {

    @Test
    void whenFindByName() {
        Output output = new StubOutput();
        Store store = new MemTracker();
        FindByNameAction findByNameAction = new FindByNameAction(output);
        store.add(new Item("item"));

        Input input = mock(Input.class);
        when(input.askStr(any(String.class))).thenReturn("item");

        findByNameAction.execute(input, store);
        assertEquals(store.findAll().get(0).getName(), "item");
    }
}