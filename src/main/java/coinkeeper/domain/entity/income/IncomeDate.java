package coinkeeper.domain.entity.income;

import java.util.Date;

public class IncomeDate {

    private final Date date;
    public IncomeDate (Date date) {
        this.date = date;
    }

    public Date getValue() {
        return date;
    }
}
