package com.example.tuwaiqcapstone1.Service;

import com.example.tuwaiqcapstone1.Model.Merchant;
import com.example.tuwaiqcapstone1.Model.MerchantStock;
import com.example.tuwaiqcapstone1.Model.Product;
import com.example.tuwaiqcapstone1.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

}