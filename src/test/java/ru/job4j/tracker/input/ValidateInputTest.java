package ru.job4j.tracker.input;

import org.junit.jupiter.api.Test;
import ru.job4j.tracker.output.Output;
import ru.job4j.tracker.output.StubOutput;

import static org.junit.jupiter.api.Assertions.*;

class ValidateInputTest {

    @Test
    void whenInvalidInput() {
        Output out = new StubOutput();
        Input in = new StubInput(
                new String[] {"one", "1"}
        );
        ValidateInput input = new ValidateInput(out, in);
        int select = input.askInt("Enter menu: ");
        assertEquals(select, 1);
    }

    @Test
    void whenInputNegativeNumb() {
        Output out = new StubOutput();
        Input in = new StubInput(
                new String[] {"-1", "3"}
        );
        ValidateInput input = new ValidateInput(out, in);
        int select = input.askInt("Enter menu: ");
        assertEquals(select, -1);
    }
}