package ru.job4j.tracker.action;

import org.junit.jupiter.api.Test;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.output.Output;
import ru.job4j.tracker.output.StubOutput;
import ru.job4j.tracker.store.MemTracker;
import ru.job4j.tracker.store.Store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FindByIdActionTest {

    @Test
    void whenFindByIdZero() {
        Output output = new StubOutput();
        Store store = new MemTracker();
        FindByIdAction findByIdAction = new FindByIdAction(output);
        store.add(new Item("first Item"));
        store.add(new Item("second Item"));

        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(1);

        findByIdAction.execute(input, store);

        assertEquals(store.findById(1).getName(), "second Item");
    }
}