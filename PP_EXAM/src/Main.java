import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class Main
{
    public static void saveToFile(Transaction transaction)
    {
        try (FileWriter writer = new FileWriter("input.txt", true))
        {
            writer.write(transaction.date + ";" + transaction.description + ";" +transaction.amount + ";" + transaction.type + "\n");
        } catch (IOException e)
        {
            System.out.println("Ошибка при записи в файл");
        }
    }

    public static void loadFromFile(ArrayList<Transaction> transactions)
    {
        File file = new File("input.txt");
        if (!file.exists())
        {
            return;
        }
        try (Scanner fileScanner = new Scanner(file))
        {
            while (fileScanner.hasNextLine())
            {
                String[] parts = fileScanner.nextLine().split(";");
                if (parts.length == 4)
                {
                    transactions.add(new Transaction(parts[0], parts[1], Double.parseDouble(parts[2]), parts[3]
                    ));
                }
            }
        } catch (Exception e)
        {
            System.out.println("Ошибка чтения файла");
        }
    }

    public static boolean isValidDate(String date)
    {
        if (!date.matches("\\d{2}\\.\\d{2}\\.\\d{4}"))
        {
            return false;
        }
        String[] parts = date.split("\\.");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        return day >= 1 && day <= 31 && month >= 1 && month <= 12;
    }

    public static boolean isValidType(String type)
    {
        return type.equalsIgnoreCase("Доход") || type.equalsIgnoreCase("Расход");
    }

    public static void main(String[] args)
    {
        ArrayList<Transaction> transactions = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        loadFromFile(transactions);

        while (true) {
            System.out.println();
            System.out.println("      (Меню)     ");
            System.out.println("1.Добавить транзакцию");
            System.out.println("2.Показать все транзакции");
            System.out.println("3.Рассчитать и вывести текущий баланс");
            System.out.println("4.Показать все транзакции определенного типа");
            System.out.println("5.Выход");
            System.out.print("Выберите пункт: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice)
            {
                case 1:
                    String date;
                    do {
                        System.out.print("Введите дату: ");
                        date = scanner.nextLine();
                        if (!isValidDate(date))
                        {
                            System.out.println("некорректная дата");
                        }
                    } while (!isValidDate(date));

                    System.out.print("Описание транзакции: ");
                    String description = scanner.nextLine();

                    System.out.print("Сумма транзакции: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();

                    String type;
                    do {
                        System.out.print("Введите тип Доход или Расход: ");
                        type = scanner.nextLine();
                        if (!isValidType(type)) {
                            System.out.println("Тип должен быть 'Доход' или 'Расход'");
                        }
                    } while (!isValidType(type));

                    Transaction transaction = new Transaction(date, description, amount, type);

                    transactions.add(transaction);
                    saveToFile(transaction);

                    System.out.println("Транзакция добавлена");
                    break;

                case 2:
                    if (transactions.isEmpty())
                    {
                        System.out.println("список транзакций пуст.");
                    } else
                    {
                        for (Transaction t : transactions)
                        {
                            System.out.println(t);
                        }
                    }
                    break;

                case 3:
                    double balance = 0;
                    for (Transaction t : transactions)
                    {
                        if (t.type.equalsIgnoreCase("Доход"))
                        {
                            balance = balance + t.amount;
                        } else
                        {
                            balance = balance - t.amount;
                        }
                    }
                    System.out.println("Текущий баланс: " + balance);
                    break;

                case 4:
                    System.out.print("Введите тип (Доход/Расход): ");
                    String filterType = scanner.nextLine();

                    boolean found = false;
                    for (Transaction t : transactions)
                    {
                        if (t.type.equalsIgnoreCase(filterType))
                        {
                            System.out.println(t);
                            found = true;
                        }
                    }
                    if (!found)
                    {
                        System.out.println("Таких транзакцией нету");
                    }
                    break;

                case 5:
                    System.out.println("Выход ");
                    scanner.close();
                    return;

                default:
                    System.out.println("введите число от 1 до 5");
            }
        }
    }
}
