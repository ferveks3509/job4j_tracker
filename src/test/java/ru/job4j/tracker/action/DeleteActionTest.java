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

class DeleteActionTest {

    @Test
    void whenDeleteItem() {
        Store store = new MemTracker();
        Output output = new StubOutput();
        DeleteAction deleteAction = new DeleteAction(output);
        store.add(new Item("name"));

        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(0);

        assertTrue(deleteAction.execute(input, store));
        assertEquals(store.findAll().size(), 0);
    }
}