package coinkeeper.domain.monthlyincome;

import coinkeeper.domain.entity.income.Amount;

import java.time.LocalDate;

public class MonthlyIncome {
    private final LocalDate date;
    private final Amount amount;

    public MonthlyIncome(LocalDate date, Amount amount) {
        this.date = date;
        this.amount = amount;
    }

    public Amount calculateMonthlyIncome () {
        int sum = 0;

        return new Amount(sum);
    }
}