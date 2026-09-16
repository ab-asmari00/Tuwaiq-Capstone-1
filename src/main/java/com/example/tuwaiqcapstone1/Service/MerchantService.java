package com.example.tuwaiqcapstone1.Service;

import com.example.tuwaiqcapstone1.Model.Merchant;
import com.example.tuwaiqcapstone1.Model.MerchantStock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantStockService merchantStockService;


    private ArrayList<Merchant> merchants = new ArrayList<>();

    public ArrayList<Merchant> getMerchants() {
        return merchants;
    }

    public void addMerchant(Merchant merchant) {
        merchants.add(merchant);
    }

    public boolean updateMerchant(String id, Merchant merchant) {

        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId().equals(id)) {
                merchants.set(i, merchant);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchant(String id) {

        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId().equals(id)) {
                merchants.remove(i);
                return true;
            }
        }
        return false;
    }

    public ArrayList<MerchantStock> getMerchantStocks(String merchantId) {

        ArrayList<MerchantStock> result = new ArrayList<>();

        for (MerchantStock ms : merchantStockService.getMerchantStocks()) {
            if (ms.getMerchantId().equals(merchantId)) {
                result.add(ms);
            }
        }

        return result;
    }
}
