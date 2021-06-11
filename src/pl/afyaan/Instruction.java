package pl.afyaan;

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
