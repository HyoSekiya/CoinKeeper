package coinkeeper.domain.entity.expenditure;

import jakarta.validation.constraints.*;

public class ExpenditureAmount {
//    @Min(value = 0, message = "金額は0以上で入力してください")
//    @Max(value = 10000000, message = "金額は10000万以下で入力してください")
//    @NotNull(message = "金額を入力してください")
    private final int value;

    public ExpenditureAmount(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
