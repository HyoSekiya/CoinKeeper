package coinkeeper.api.expenditure;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
//支出編集画面クラス
public class ExpenditureForm {

    public int expenditureId;

    @Min(value = 0, message = "金額は0以上で入力してください")
    @Max(value = 10000000, message = "金額は10000万以下で入力してください")
    @NotNull(message = "金額を入力してください")
    public int expenditureAmount;

    @Size(min = 1, max = 20, message = "1文字以上20文字以下で入力してください")
    @NotBlank(message = "カテゴリーを入力してください")
    public String expenditureCategory;

    @Size(max = 20, message = "0文字以上20文字以下で入力してください")
    public String expenditureCategoryMemo;
    public ExpenditureForm() {
    }
}
