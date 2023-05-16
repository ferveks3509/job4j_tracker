package ru.job4j.tracker.action;

import org.junit.jupiter.api.Test;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.output.Output;
import ru.job4j.tracker.output.StubOutput;
import ru.job4j.tracker.store.MemTracker;
import ru.job4j.tracker.store.Store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EditActionTest {

    @Test
    void whenReplaceItem() {
        Output out = new StubOutput();
        Store tracker = new MemTracker();
        tracker.add(new Item("new Item"));
        String replacedName = "Edit item";
        EditAction rep = new EditAction(out);

        Input input = mock(Input.class);
        when(input.askInt(any(String.class))).thenReturn(0);
        when(input.askStr(any(String.class))).thenReturn(replacedName);

        rep.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(out.toString()).isEqualTo("=== Edit item ===" + ln + "Заявка изменена успешно" + ln);
        assertThat(tracker.findAll().get(0).getName()).isEqualTo(replacedName);
    }
}