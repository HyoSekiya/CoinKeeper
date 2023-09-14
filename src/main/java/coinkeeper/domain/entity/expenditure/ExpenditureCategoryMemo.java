package coinkeeper.domain.entity.expenditure;

import jakarta.validation.constraints.Size;

public class ExpenditureCategoryMemo {
//    @Size(max = 20, message = "0文字以上20文字以下で入力してください")
    private final String value;

    public ExpenditureCategoryMemo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
