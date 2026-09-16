package com.example.tuwaiqcapstone1.Service;

import com.example.tuwaiqcapstone1.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {

    private ArrayList<Product> products = new ArrayList<>();

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public boolean updateProduct(String id, Product product) {

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.set(i, product);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProduct(String id) {

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.remove(i);
                return true;
            }
        }
        return false;
    }

    public int discount(String productId, double percentage) {

        Product product = null;
        for (Product p : products) {
            if (p.getId().equals(productId)) {
                product = p;
                break;
            }
        }
        if (product == null) {
            return 1; // ProductId was not found
        }

        if (percentage <= 0 || percentage >= 100) {
            return 2; // Invalid discount percentage
        }

        double newPrice = product.getPrice() * (1 - percentage / 100.0);
        product.setPrice(newPrice);

        return 0; // Discount applied successfully
    }

}