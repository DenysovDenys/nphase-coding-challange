package com.nphase.service;

import com.nphase.entity.Category;
import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCartService {

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        return shoppingCart.getProducts()
                .stream()
                .map(product -> product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    //task2
    public BigDecimal calculateTotalPriceWithDiscount(ShoppingCart shoppingCart) {
        return shoppingCart.getProducts()
                .stream()
                .map(product -> {
                    if (product.getQuantity() > 3) {
                        return product.getPricePerUnit()
                                .multiply(BigDecimal.valueOf(product.getQuantity()))
                                .multiply(product.getCategory().getDiscount());
                    } else {
                        return product.getPricePerUnit()
                                .multiply(BigDecimal.valueOf(product.getQuantity()));
                    }
                })
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    //task3 and 4
    public BigDecimal calculateTotalPriceWithDiscountByCategory(ShoppingCart shoppingCart) {

        Map<Category, Integer> productQuantityByCategory = new HashMap<>();
        for (Product product : shoppingCart.getProducts()) {
            Category category = product.getCategory();
            int count = productQuantityByCategory.getOrDefault(category, 0);
            productQuantityByCategory.put(category, count + product.getQuantity());
        }

        return shoppingCart.getProducts()
                .stream()
                .map(product -> {
                    if (productQuantityByCategory.get(product.getCategory()) > product.getCategory().getCountForDiscount()) {
                        return product.getPricePerUnit()
                                .multiply(BigDecimal.valueOf(product.getQuantity()))
                                .multiply(product.getCategory().getDiscount());
                    } else {
                        return product.getPricePerUnit()
                                .multiply(BigDecimal.valueOf(product.getQuantity()));
                    }
                })
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
