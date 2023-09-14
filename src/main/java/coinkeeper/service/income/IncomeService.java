package coinkeeper.service.income;

import coinkeeper.api.income.MonthlyIncomeSummaryForm;
import coinkeeper.domain.entity.income.IncomeEntity;

import java.util.List;
import java.util.Optional;

public interface IncomeService {

    void insertIncome(IncomeEntity incomeEntity);

    void updateIncome(IncomeEntity incomeEntity);

    void deleteIncome(int id);

    List<IncomeEntity> getIncome();

    Optional<IncomeEntity> getIncomeOptional(int id);

    List<MonthlyIncomeSummaryForm> getMonthlyIncomeSummary();
}
