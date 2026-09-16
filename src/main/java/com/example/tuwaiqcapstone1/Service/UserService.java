package com.example.tuwaiqcapstone1.Service;

import com.example.tuwaiqcapstone1.Model.Merchant;
import com.example.tuwaiqcapstone1.Model.MerchantStock;
import com.example.tuwaiqcapstone1.Model.Product;
import com.example.tuwaiqcapstone1.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final MerchantService merchantService;
    private final ProductService productService;
    private final MerchantStockService merchantStockService;

    ArrayList<User> users = new ArrayList<>();

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public boolean updateUser(String id, User user) {

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.set(i, user);
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(String id) {

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    public int buyProduct(String userId, String productId, String merchantId) {

        User user = null;
        for (User u : users) {
            if (u.getId().equals(userId)) {
                user = u;
                break;
            }
        }
        if (user == null) {
            return 1; // UserId not found
        }

        Product product = null;
        for (Product p : productService.getProducts()) {
            if (product.getId().equals(productId)) {
                product = p;
                break;
            }
        }
        if (product == null) {
            return 2; // ProductId was not found
        }

        Merchant merchant = null;
        for (Merchant m : merchantService.getMerchants()) {
            if (m.getId().equals(merchantId)) {
                merchant = m;
                break;
            }
        }
        if (merchant == null) {
            return 3; // MerchantId was not found
        }

        MerchantStock stock = null;
        for (MerchantStock ms : merchantStockService.getMerchantStocks()) {
            if (ms.getProductId().equals(productId) &&
                ms.getMerchantId().equals(merchantId)) {
                stock = ms;
                break;
            }
        }

        if (stock.getStock() < 1) {
            return 4; // wanted Product is out of stock
        }

        if (user.getBalance() < product.getPrice()) {
            return 5; // Balance is insufficient to buy this product
        }

        stock.setStock(stock.getStock() - 1);
        user.setBalance(user.getBalance() - product.getPrice());

        return 0; // Purchase done successfully
    }

    public int addBalance(String userId, double amount) {

        User user = null;
        for (User u : users) {
            if (u.getId().equals(userId)) {
                user = u;
                break;
            }
        }
        if (user == null) {
            return 1; // UserId was not found
        }

        if (amount <= 0) {
            return 2; // Invalid amount
        }

        user.setBalance(user.getBalance() + amount);

        return 0; // Balance added successfully
    }

    public int addMerchant(String userId, String merchantId, String merchantName) {

        User user = null;
        for (User u : users) {
            if (u.getId().equals(userId)) {
                user = u;
                break;
            }
        }
        if (user == null) {
            return 1; // UserId was not found
        }

        if (!user.getRole().equals("Admin")) {
            return 2; // User is not an Admin
        }

        Merchant merchant = new Merchant(merchantId, merchantName);

        merchantService.addMerchant(merchant);

        return 0; // Merchant added successfully
    }

    public int groupBuy(String merchantStockId, ArrayList<Map<String, Object>> buyers) {

        if (merchantStockId == null || !merchantStockId.startsWith("MS-")) {
            return 1; // MerchantStock Id must start with MS
        }

        if (buyers == null || buyers.isEmpty()) {
            return 2; //Group buy requires at least one buyer.
        }

        MerchantStock merchantStock = null;
        for (MerchantStock ms : merchantStockService.getMerchantStocks()) {
            if (ms.getId().equals(merchantStockId)) {
                merchantStock = ms;
                break;
            }
        }

        if (merchantStock == null) {
            return 3; // Not merchant stock was found
        }

        Product product = null;
        for (Product p : productService.getProducts()) {
            if (p.getId().equals(merchantStock.getProductId())) {
                product = p;
                break;
            }
        }

        if (product == null) {
            return 4; // No product was found
        }

        int totalQty = 0;
        ArrayList<Integer> parsedQuantities = new ArrayList<>();

        for (Map<String, Object> buyerData : buyers) {
            if (buyerData.get("quantity") == null) {
                return 5; // Quantity field is required for all buyers
            }
            try {
                int qty = (int) Math.round(Double.parseDouble(buyerData.get("quantity").toString()));
                if (qty < 1) {
                    return 6; // Quantity must be greater than zero.
                }
                parsedQuantities.add(qty);
                totalQty += qty;
            } catch (NumberFormatException e) {
                return 7; // Quantity must be a valid number
            }
        }

        if (merchantStock.getStock() < totalQty) {
            return 8; // Insufficient merchant stock for the demanded quantity
        }

        double discountRate = 0.0;

        if (totalQty >= 50) {
            discountRate = 0.25;
        } else if (totalQty >= 20) {
            discountRate = 0.15;
        } else if (totalQty >= 5) {
            discountRate = 0.05;
        }

        double discountedUnitPrice = product.getPrice() * (1 - discountRate);

        ArrayList<User> validatedUsers = new ArrayList<>();
        ArrayList<Double> userCosts = new ArrayList<>();

        for (int i = 0; i < buyers.size(); i++) {
            Map<String, Object> buyerData = buyers.get(i);
            Object userIdObj = buyerData.get("userId");

            if (userIdObj == null || !userIdObj.toString().startsWith("U-")) {
                return 9; // User ID is missing or must start with U-
            }

            String userId = userIdObj.toString();
            User user = null;
            for (User u : users) {
                if (u.getId().equals(userId)) {
                    user = u;
                    break;
                }
            }

            if (user == null) {
                return 10; // User not found
            }

            double cost = discountedUnitPrice * parsedQuantities.get(i);
            if (user.getBalance() < cost) {
                return 11; // User has insufficient balance
            }

            validatedUsers.add(user);
            userCosts.add(cost);
        }

        for (int i = 0; i < validatedUsers.size(); i++) {
            User u = validatedUsers.get(i);
            u.setBalance(u.getBalance() - userCosts.get(i));
        }

        merchantStock.setStock(merchantStock.getStock() - totalQty);

        return 0; // Success
    }

}