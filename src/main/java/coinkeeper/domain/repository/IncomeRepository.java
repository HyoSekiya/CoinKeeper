package coinkeeper.domain.repository;

import coinkeeper.api.income.MonthlyIncomeSummaryForm;
import coinkeeper.domain.entity.income.IncomeEntity;

import java.util.List;
import java.util.Optional;


public interface IncomeRepository{

    void insertIncome(IncomeEntity incomeEntity);

    int updateIncome(IncomeEntity incomeEntity);

    int deleteIncome(int id);
    List<IncomeEntity> getIncome();

    Optional<IncomeEntity> findById(int id);

    List<MonthlyIncomeSummaryForm> getMonthlyIncomeSummaries();
}
