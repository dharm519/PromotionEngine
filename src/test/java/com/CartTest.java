package com;

import com.model.Cart;
import com.model.CartItem;
import com.util.PriceList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class CartTest {
    private static Cart cart;

    @BeforeAll
    public static void addItemsToCart() {
        cart = new Cart();
        Map<CartItem, Integer> testItems = new HashMap<>();
        testItems.put(new CartItem("A", 50), 1);
        testItems.put(new CartItem("B", 30), 1);
        testItems.put(new CartItem("C", 20), 1);
        testItems.put(new CartItem("D", 15), 1);
        cart.setContents(testItems);
    }

    @AfterAll
    public static void teardown() {
        cart.getContents().clear();
    }

    @Test
    public void testCartWithValidProductItems() {
        Double price = PriceList.getPrice("A");
        System.out.println(price);
        Assertions.assertNotNull(price, "Product invalid");
    }

    @Test
    public void  testCartWithInvalidProductItem() {
        Double price = PriceList.getPrice("F");
        Assertions.assertNull(price, "Please add an invalid product to pass this test.");
    }
}
