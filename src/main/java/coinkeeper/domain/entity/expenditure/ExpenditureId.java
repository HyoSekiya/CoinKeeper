package coinkeeper.domain.entity.expenditure;

import jakarta.validation.constraints.Min;

public class ExpenditureId {

    private final int value;
    public ExpenditureId(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


}