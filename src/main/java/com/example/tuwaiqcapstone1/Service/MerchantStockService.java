package com.example.tuwaiqcapstone1.Service;

import com.example.tuwaiqcapstone1.Model.MerchantStock;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantStockService {

    private ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    public ArrayList<MerchantStock> getMerchantStocks() {
        return merchantStocks;
    }

    public void addMerchantStock(MerchantStock merchantStock) {
        merchantStocks.add(merchantStock);
    }

    public boolean updateMerchantStock(String id, MerchantStock merchantStock) {

        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)) {
                merchantStocks.set(i, merchantStock);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchantStock(String id) {

        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)) {
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean addStock(String productId, String merchantId, int stock) {

        for (MerchantStock merchantStock : merchantStocks) {
            if (merchantStock.getProductId().equals(productId)
                && merchantStock.getMerchantId().equals(merchantId)){
                int newStock = merchantStock.getStock() + stock;
                merchantStock.setStock(newStock);
                return true;
            }
        }
        return false;
    }

}