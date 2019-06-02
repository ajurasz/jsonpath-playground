package playground.exercises;

import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.nullValue;
import static playground.JsonLoader.json;
import static playground.JsonPathAssert.jsonPath;

import org.junit.Test;

public class SampleTest {

    private String json = json();

    @Test
    public void should_pass() {
        jsonPath("$._id", is("5cdaff5fce90bc3ddd98475b")).runAgainst(json);
    }

    @Test
    public void should_verify_root_property() {
        jsonPath("$.guid", is("57cc1cbb-52ba-49ff-af66-4b9dde85a207")).runAgainst(json);
        jsonPath("$.isActive", isA(Boolean.class)).runAgainst(json);
        jsonPath("$.age", instanceOf(Number.class)).runAgainst(json);
        jsonPath("$.nonExisting", nullValue());
    }

    @Test
    public void should_verify_nested_property() {
        jsonPath("$.name.first", is("Opal")).runAgainst(json);
        jsonPath("$..last", hasItem("Lyons")).runAgainst(json);
        jsonPath("$.name..first", everyItem(is("Opal"))).runAgainst(json);
    }

    @Test
    public void should_verify_property_using_bracket_notation() {
        jsonPath("$['guid']", is("57cc1cbb-52ba-49ff-af66-4b9dde85a207")).runAgainst(json);
        jsonPath("$['isActive']", isA(Boolean.class)).runAgainst(json);
        jsonPath("$['name']['first']", is("Opal")).runAgainst(json);
    }

    @Test
    public void should_verify_property_through_deep_scan() {
        jsonPath("$..f", hasItems("end", "other-end")).runAgainst(json);
        jsonPath("$.a..f", hasItems("end")).runAgainst(json);
        jsonPath("$..eyeColor", hasItems("blue", "blue", "brown", "black")).runAgainst(json);
        jsonPath("$.data..eyeColor", hasItems("blue", "brown", "black")).runAgainst(json);
    }

    @Test
    public void should_verify_json_path_functions() {
        jsonPath("$.range.avg()", is(4.5)).runAgainst(json);
        jsonPath("$.range.min()", is(0.0)).runAgainst(json);
        jsonPath("$.range.max()", is(9.0)).runAgainst(json);
        jsonPath("$.range.stddev()", is(2.8722813232690143)).runAgainst(json);
        jsonPath("$.range.length()", is(10)).runAgainst(json);
    }

    @Test
    public void should_find_friends_with_id_greater_than_or_equal_1() {
        jsonPath("$.friends[?(@.id >= 1)].name", hasItems("Lucinda Goff", "Ella Day")).runAgainst(json);
    }

    @Test
    public void should_find_friends_with_id_greater_than_0_and_less_than_2() {
        jsonPath("$.friends[?(@.id > 0 && @.id < 2)].name", hasItems("Lucinda Goff")).runAgainst(json);
    }

    @Test
    public void should_find_friends_with_name_containing_la() {
        jsonPath("$.friends[?(@.name =~ /^.*la.*$/i)].id", hasItems(0, 2)).runAgainst(json);
    }

    @Test
    public void should_find_data_with_person_eye_color_in() {
        jsonPath("$.data[?(@.eyeColor in ['blue', 'black'])].index", hasItems(0, 2)).runAgainst(json);
    }

    @Test
    public void should_verify_array_length() {
        jsonPath("$.data.length()", is(3)).runAgainst(json);
    }

    @Test
    public void should_verify_array_item_property() {
        jsonPath("$.data[0]._id", is("5cdaff5f09f1dcee0101c3a0")).runAgainst(json);
        jsonPath("$.data[0].tags.length()", is(5)).runAgainst(json);
        jsonPath("$.friends[*].age", hasItems(31, 27, 17)).runAgainst(json);
    }

    @Test
    public void should_verify_every_item_in_collection() {
        jsonPath("$.data[*].age", everyItem(is(33))).runAgainst(json);
    }

    @Test
    public void should_get_last_item_from_array() {
        jsonPath("$.data[-1:].index", hasItems(2)).runAgainst(json);
    }

    @Test
    public void should_verify_array_slicing() {
        jsonPath("$.data[1:2].index", hasItems(1)).runAgainst(json);
        jsonPath("$.data[1:3].index", hasItems(1, 2)).runAgainst(json);
        jsonPath("$.data[:3].index", hasItems(0, 1, 2)).runAgainst(json);
        jsonPath("$.friends[:3].age", hasItems(31, 27)).runAgainst(json);
        jsonPath("$.friends[1:2].age", hasItems(27)).runAgainst(json);
        jsonPath("$.friends[:2].age", hasItems(31, 27)).runAgainst(json);
        jsonPath("$.friends[-2:].age", hasItems(17, 27)).runAgainst(json);
    }
}
