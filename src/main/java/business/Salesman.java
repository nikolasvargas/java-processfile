package business;

public class Salesman {
    private String name;
    private String cpf;
    private double salary;
    private double totalSales = 0d;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales += totalSales;
    }

    public Salesman(String name, String cpf, double salary) {
        this.name = name;
        this.cpf = cpf;
        this.salary = salary;
    }

    public static Salesman toArray(String[] row) {
        String cpf = row[1], name = row[2];
        double salary = Double.parseDouble(row[3]);
        return new Salesman(name, cpf, salary);
    }
}
