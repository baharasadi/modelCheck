package models;

import java.util.ArrayList;
import java.util.HashMap;

public class Seller extends Person {
    private String company;
    private ArrayList<SellLog> sellHistory;
    private ArrayList<Product> productsToSell;
    private ArrayList<Sale> saleList;

    public Seller(HashMap<String, String> information) {
        super(information);
        this.company = information.get("company");
        this.sellHistory = new ArrayList<>();
        this.productsToSell = new ArrayList<>();
        this.saleList = new ArrayList<>();
    }

    public String getCompany() {
        return company;
    }

    public ArrayList<SellLog> getSellHistory() {
        return sellHistory;
    }

    public ArrayList<Product> getProductsToSell() {
        return productsToSell;
    }

    public ArrayList<Sale> getSaleList() {
        return saleList;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    void addProduct(Product newProduct) {
        productsToSell.add(newProduct);
    }

    void removeProduct(Product specificProduct) {
        productsToSell.removeIf(product -> product.equals(specificProduct));
    }

    boolean hasProductWithId(String productId) {
        for (Product product : productsToSell) {
            if (product.getProductId().equals(productId)) {
                return true;
            }
        }
        return false;
    }

    void addToSellLogs(SellLog newSellLog) {
        sellHistory.add(newSellLog);
    }

    void addSale(Sale newSale) {
        saleList.add(newSale);
    }
}