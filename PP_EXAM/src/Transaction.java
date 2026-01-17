public class Transaction
{
    String date;
    String description;
    double amount;
    String type;

    public Transaction(String date, String description, double amount, String type)
    {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "Дата: " + date + ", Описание: " + description + ", Сумма: " + amount + ", Тип: " + type;
    }
}
