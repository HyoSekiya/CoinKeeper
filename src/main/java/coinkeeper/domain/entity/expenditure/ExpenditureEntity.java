package coinkeeper.domain.entity.expenditure;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ExpenditureEntity {
    //    private ExpenditureId expenditureId;
    private int expenditureId;
    //    private ExpenditureAmount expenditureAmount;
    private int expenditureAmount;
    //    private ExpenditureCategory expenditureCategory;
    private String expenditureCategory;
    //    private ExpenditureCategoryMemo expenditureCategoryMemo;
    private String expenditureCategoryMemo;
    //    private LocalDateTime date = LocalDateTime.now();
    private LocalDateTime date;


    public ExpenditureEntity() {
    }
}

