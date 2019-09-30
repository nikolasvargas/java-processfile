package business;

public class Sale {
    private String id;
    private Salesman salesman;
    private double total;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Salesman getSalesman() {
        return salesman;
    }

    public void setSalesman(Salesman salesman) {
        this.salesman = salesman;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public static Sale toArray(String[] row) {
        Sale sale = new Sale();
        sale.setId(row[1]);

        // Parse and calculate Sale value
        double totalSale = 0;
        String[] strItems = row[2].replace("[", "").replace("]", "").split(",");
        if (strItems.length > 0) {
            for(String strItem : strItems) {
                String[] itemSplit = strItem.split("-");
                if (itemSplit.length == 3) {
                    double totalItem = Double.parseDouble(itemSplit[1]) * Double.parseDouble(itemSplit[2]);
                    totalSale += totalItem;
                }
            }
        }

        sale.setTotal(totalSale);
        return sale;
    }
}
