package playground;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;

import com.jayway.jsonpath.JsonPath;

public final class JsonPathAssert<T> {
    private final JsonPath jsonPath;
    private final Matcher<T> matcher;

    private JsonPathAssert(String expression, final Matcher<T> matcher) {
        this.jsonPath = JsonPath.compile(expression);
        this.matcher = matcher;
    }

    public static <T> JsonPathAssert<T> jsonPath(String expression, Matcher<T> matcher) {
        return new JsonPathAssert<T>(expression, matcher);
    }

    public void runAgainst(String json) {
        MatcherAssert.assertThat(evaluateExpression(json), matcher);
    }

    private T evaluateExpression(String json) {
        return jsonPath.read(json);
    }
}
