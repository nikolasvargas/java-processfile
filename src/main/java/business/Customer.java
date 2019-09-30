package business;

public class Customer {
    private String name;
    private String cnpj;
    private String businessArea;

    public String getName() {
        return name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    public Customer(String name, String cnpj, String businessArea) {
        this.name = name;
        this.cnpj = cnpj;
        this.businessArea = businessArea;
    }

    public static Customer toArray(String[] row) {
        String businessArea = row[3], name = row[2], cnpj = row[1];
        return new Customer(name, cnpj, businessArea);
    }
}
