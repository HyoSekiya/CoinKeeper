package coinkeeper.domain.entity.income;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;

import java.time.LocalDateTime;



@Entity
@NamedQuery(name = "IncomeEntity.findIncomeById", query = "SELECT i FROM IncomeEntity i WHERE i.id = :id")
public class IncomeEntity {

    @Id
    private int id;
    private int amount;
    private String category;
    private String categoryMemo;
    private LocalDateTime date;


    public IncomeEntity() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryMemo() {
        return categoryMemo;
    }

    public void setCategoryMemo(String categoryMemo) {
        this.categoryMemo = categoryMemo;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

