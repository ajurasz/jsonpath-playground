package playground.exercises;

import static playground.JsonLoader.json;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

import static playground.JsonPathAssert.jsonPath;

public class SampleTest {

    @Test
    public void should_pass() {
        jsonPath("$[0].index", is(0)).testWith(json());
        jsonPath("$[1].index", is(1)).testWith(json());
        jsonPath("$[2].index", is(2)).testWith(json());
    }
}
