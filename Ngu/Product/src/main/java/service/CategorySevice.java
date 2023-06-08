package service;

import model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategorySevice {
    private final List<Category> categories;
    private static CategorySevice categorySevice;

    public CategorySevice() {
        categories = new ArrayList<>();
        categories.add(new Category(1L,"Bánh"));
        categories.add(new Category(2L,"Bim Bim"));
        categories.add(new Category(3L,"Nước ngọt"));
    }
    public static CategorySevice getInstance() {
        if (categorySevice == null) {
            categorySevice = new CategorySevice();
        }
        return categorySevice;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public Category getById(Long id) {
        for (Category category : categories) {
            if (category.getId().equals(id)) {
                return category;
            }
        }
        return null;
    }

    public void deleteById(Long id) {
        Category category = getById(id);
        if (category != null) {
            categories.remove(category);
        }
    }
}
