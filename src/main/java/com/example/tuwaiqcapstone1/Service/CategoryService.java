package com.example.tuwaiqcapstone1.Service;

import com.example.tuwaiqcapstone1.Model.Category;
import com.example.tuwaiqcapstone1.Model.Product;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final ProductService productService;

    private ArrayList<Category> categories = new ArrayList<>();

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public boolean updateCategory(String id, Category category){

        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(id)) {
                categories.set(i, category);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCategory(String id) {

        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(id)) {
                categories.remove(i);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Product> GetCategoryProducts(String id) {

        ArrayList<Product> result = new ArrayList<>();

        for (Product p : productService.getProducts()) {
            if (p.getCategoryId().equals(id)) {
                result.add(p);
            }
        }

        return result;
    }

}
