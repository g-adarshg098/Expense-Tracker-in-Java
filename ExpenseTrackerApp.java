import java.util.ArrayList;  
import java.util.List;  
import java.util.Scanner;  
import java.text.SimpleDateFormat;  
import java.util.Date;  

class Expense {  
    private String description;  
    private double amount;  
    private String date;  
    private int id; // Unique ID for the expense  
    private static int idCounter = 1; // Static counter to generate unique IDs  

    public Expense(String description, double amount) {  
        this.description = description;  
        this.amount = amount;  
        this.date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());  
        this.id = idCounter++;  
    }  

    public int getId() {  
        return id;  
    }  

    public String getDescription() {  
        return description;  
    }  

    public double getAmount() {  
        return amount;  
    }  

    public String getDate() {  
        return date;  
    }  

    @Override  
    public String toString() {  
        return String.format("%d  %s  %s  $%.2f", id, date, description, amount);  
    }  
}  

class ExpenseTracker {  
    private List<Expense> expenses;  

    public ExpenseTracker() {  
        this.expenses = new ArrayList<>();  
    }  

    public void addExpense(String description, double amount) {  
        Expense expense = new Expense(description, amount);  
        expenses.add(expense);  
        System.out.println("Expense added successfully (ID: " + expense.getId() + ")");  
    }  

    public void deleteExpense(int id) {  
        for (int i = 0; i < expenses.size(); i++) {  
            if (expenses.get(i).getId() == id) {  
                expenses.remove(i);  
                System.out.println("Expense deleted successfully");  
                return;  
            }  
        }  
        System.out.println("Expense with ID " + id + " not found.");  
    }  

    public void listExpenses() {  
        if (expenses.isEmpty()) {  
            System.out.println("No expenses recorded.");  
            return;  
        }  
        System.out.println("ID  Date       Description  Amount");  
        for (Expense expense : expenses) {  
            System.out.println(expense);  
        }  
    }  

    public void summary() {  
        double total = 0;  
        for (Expense expense : expenses) {  
            total += expense.getAmount();  
        }  
        System.out.printf("Total expenses: $%.2f%n", total);  
    }  

    public void monthlySummary(int month) {  
        double total = 0;  
        for (Expense expense : expenses) {  
            if (Integer.parseInt(expense.getDate().substring(5, 7)) == month) {  
                total += expense.getAmount();  
            }  
        }  
        System.out.printf("Total expenses for month %d: $%.2f%n", month, total);  
    }  
}  

public class ExpenseTrackerApp {  
    private static ExpenseTracker tracker = new ExpenseTracker();  

    public static void main(String[] args) {  
        Scanner scanner = new Scanner(System.in);  

        while (true) {  
            System.out.print("$ ");  
            String input = scanner.nextLine().trim();  

            if (input.startsWith("expense-tracker add")) {  
                String[] parts = input.split("--");  
                String description = "";  
                double amount = 0;  

                for (String part : parts) {  
                    part = part.trim();  
                    if (part.startsWith("description")) {  
                        description = part.split("\"")[1]; // get the text inside quotes  
                    } else if (part.startsWith("amount")) {  
                        amount = Double.parseDouble(part.split(" ")[1]);  
                    }  
                }  
                tracker.addExpense(description, amount);  

            } else if (input.startsWith("expense-tracker delete")) {  
                int id = Integer.parseInt(input.split("--id ")[1].trim());  
                tracker.deleteExpense(id);  

            } else if (input.equals("expense-tracker list")) {  
                tracker.listExpenses();  

            } else if (input.equals("expense-tracker summary")) {  
                tracker.summary();  

            } else if (input.startsWith("expense-tracker summary --month")) {  
                int month = Integer.parseInt(input.split("--month ")[1].trim());  
                tracker.monthlySummary(month);  

            } else if (input.equals("exit")) {  
                System.out.println("Exiting the application.");  
                break;  

            } else {  
                System.out.println("Invalid command. Please try again.");  
            }  
        }  

        scanner.close();  
    }  
}  