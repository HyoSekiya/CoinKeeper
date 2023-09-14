package coinkeeper.domain.entity.expenditure;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ExpenditureCategory {
//    @Size(min = 1, max = 20, message = "1文字以上20文字以下で入力してください")
//    @NotBlank(message = "カテゴリーを入力してください")
    private final String value;

    public ExpenditureCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
