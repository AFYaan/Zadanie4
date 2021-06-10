package pl.afyaan;

/**
 * @author AFYaan
 * @created 10.06.2021
 * @project Zadanie4
 */
public class Instruction {
    private Type type;
    private Object value;

    public Instruction(Type type, Object value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
}
