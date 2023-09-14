package coinkeeper.service.expenditure;

import coinkeeper.api.expenditure.MonthlyExpenditureSummaryForm;
import coinkeeper.domain.entity.expenditure.ExpenditureEntity;
import coinkeeper.domain.entity.expenditure.ExpenditureId;

import java.util.List;
import java.util.Optional;

public interface ExpenditureService {

    void insertExpenditure(ExpenditureEntity expenditureEntity);

    void updateExpenditure(ExpenditureEntity incomeEntity);

    void deleteExpenditure(int expenditureId);

    List<ExpenditureEntity> getExpenditure();

    Optional<ExpenditureEntity> getExpenditureOptional(int expenditureId);

    List<MonthlyExpenditureSummaryForm> getMonthlyExpenditureSummary();
}
