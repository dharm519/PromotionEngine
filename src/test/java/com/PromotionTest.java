package com;
import com.model.Cart;
import com.model.CartItem;
import com.promotion.BulkPromotion;
import com.promotion.IPromotion;
import com.promotion.SingleItemGroupPromotion;
import com.service.IPromotionService;
import com.service.PromotionServiceImpl;
import com.util.PromotionUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PromotionTest {

    private static IPromotionService promotionService;
    private static List<IPromotion> promotions;
    private static Cart cart;

    @BeforeAll
    public static void setup() {
        promotionService = new PromotionServiceImpl();
        promotions = PromotionUtil.setupPromotions();
        cart = new Cart();
    }

    @AfterAll
    public static void teardown() {
        cart.getContents().clear();
    }

    @Test
    public void testIsAvailableMethodOnPromotionsTrue() {
        IPromotion promotion = new SingleItemGroupPromotion("A", 2, 90.0);
        Map<CartItem, Integer> testContents = new HashMap<>();
        testContents.put(new CartItem("A"), 3);
        cart.setContents(testContents);
        assertTrue(promotion.isAvailable(cart), "This cart should contain items for this promotion.");
    }

    @Test
    public void testIsAvailableMethodOnPromotionsFalse() {
        IPromotion promotion = new SingleItemGroupPromotion("A", 2, 90.0);
        Map<CartItem, Integer> testContents = new HashMap<>();
        testContents.put(new CartItem("B"), 3);
        cart.setContents(testContents);
        assertFalse(promotion.isAvailable(cart), "This cart should not contain items for this promotion.");
    }

    @Test
    public void testIsAvailableMethodOnBundledPromotionsTrue() {
        IPromotion promotion = new BulkPromotion(Arrays.asList("A", "B"), 30.0);
        Map<CartItem, Integer> testContents = new HashMap<>();
        testContents.put(new CartItem("A"), 3);
        testContents.put(new CartItem("B"), 1);
        cart.setContents(testContents);
        assertTrue(promotion.isAvailable(cart), "This cart should contain items for this promotion.");
    }

    @Test
    public void testIsAvailableMethodOnBundledPromotionsFalse() {
        IPromotion promotion = new BulkPromotion(Arrays.asList("A", "B"), 30.0);
        Map<CartItem, Integer> testContents = new HashMap<>();
        testContents.put(new CartItem("A"), 3);
        testContents.put(new CartItem("C"), 1);
        cart.setContents(testContents);
        assertFalse(promotion.isAvailable(cart), "This cart should not contain items for this promotion.");
    }
}
