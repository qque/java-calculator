/*
 *  Generic type for output to handle double, int, boolean, etc.
 */

package calculator.logic;

public class Output {
    private final Object value;
    private final Class<?> type;

    public Output(Object value, Class<?> type) {
        this.value = value;
        this.type = type;
    }

    public Object value() { return value; }
    public Class<?> type() { return type; }

    @Override
    public String toString() {
        return value == null ? "void" : value.toString();
    }
}
