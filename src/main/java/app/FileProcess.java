package app;

import business.Customer;
import business.Sale;
import business.Salesman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;

public class FileProcess {
    private File currentFile;
    private HashSet<Customer> customerList = new HashSet<>();
    private HashSet<Salesman> salesmanList = new HashSet<>();
    private HashSet<Sale> saleList = new HashSet<>();
    private Sale mostExpensiveSale = null;
    private Salesman worstSalesman = null;

    public FileProcess(File currentFile){
        this.currentFile = currentFile;
    }

    public void process() {
        try {
            Scanner scanner = new Scanner(this.currentFile);

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] parsedLine = line.split("ç");

                boolean hasLinesToParse = parsedLine.length > 0;

                if (hasLinesToParse){
                    if (parsedLine[0].equals("001")) {
                        boolean SalesmanData = parsedLine.length == 4;

                        if (SalesmanData){
                            Salesman salesman = Salesman.toArray(parsedLine);
                            salesmanList.add(salesman);
                        }

                    } else if (parsedLine[0].equals("002")) {
                        boolean customerData = parsedLine.length == 4;

                        if (customerData){
                            Customer customer = Customer.toArray(parsedLine);
                            customerList.add(customer);
                        }

                    } else if (parsedLine[0].equals("003")) {
                        boolean salesData = parsedLine.length == 4;

                        if (salesData){
                            Sale sale = Sale.toArray(parsedLine);
                            sale.setSalesman(this.getByName(parsedLine[3]));
                            saleList.add(sale);
                        }
                    }
                }
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void summarize() {
        boolean saleListIsNotEmpty = this.saleList.size() > 0;
        if (saleListIsNotEmpty) {
            mostExpensiveSale = this.saleList.iterator().next();

            for (Sale sale : this.saleList) {
                if (mostExpensiveSale.getTotal() > sale.getTotal()) {
                    mostExpensiveSale = sale;
                }
                sale.getSalesman().setTotalSales(sale.getTotal());
            }

            worstSalesman = this.salesmanList.iterator().next();

            for(Salesman salesman : this.salesmanList){
                if (worstSalesman.getTotalSales() < salesman.getTotalSales()) {
                    worstSalesman = salesman;
                }
            }
        }
    }

    public void moveProcessedFile(File pathTo) {
        System.out.println("Moving file to: " + pathTo.getName());
        this.currentFile.renameTo(new File(pathTo.getAbsolutePath()+"//"+this.currentFile.getName()));
        System.out.println("File processed: " + this.currentFile.getName());
    }

    public void createOutputFile(File pathTo){
        String currentFileName = this.currentFile.getName();
        int lastIndex = currentFileName.lastIndexOf(".dat");
        String outputFileName = pathTo.getAbsolutePath() + "//" + currentFileName.substring(0, lastIndex) + ".done.dat";
        File outputFile = new File(outputFileName);
        try {
            PrintWriter output = new PrintWriter(outputFile);
            this.summarize();
            output.println("Amount of clients: " + this.customerList.size());
            output.println("Amount of salesman: " + this.salesmanList.size());
            output.println("ID of Most Expensive Sale: " + this.mostExpensiveSale.getId());
            output.println("Worst Salesman Ever: " + this.worstSalesman.getName());
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Salesman getByName(String string) {
        for (Salesman salesman : this.salesmanList){
            if (salesman.getName().equals(string)){
                return salesman;
            }
        }
        return null;
    }
}
