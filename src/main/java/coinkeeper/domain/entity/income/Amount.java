package coinkeeper.domain.entity.income;

public class Amount {
    private final int value;
    public Amount(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}