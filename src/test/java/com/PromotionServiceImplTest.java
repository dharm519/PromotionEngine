package com;

import com.model.Cart;
import com.model.CartItem;
import com.promotion.IPromotion;
import com.service.IPromotionService;
import com.service.PromotionServiceImpl;
import com.util.PromotionUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class PromotionServiceImplTest {

    private static IPromotionService promotionService;
    private static List<IPromotion> promotions;
    private static Cart cart;
    private static CartItem productA;
    private static CartItem productB;
    private static CartItem productC;
    private static CartItem productD;

    @BeforeAll
    public static void setup() {
        promotionService = new PromotionServiceImpl();
        promotions = PromotionUtil.setupPromotions();
        cart = new Cart();
        productA = new CartItem("A");
        productB = new CartItem("B");
        productC = new CartItem("C");
        productD = new CartItem("D");
    }

    @AfterAll
    public static void teardown() {
        cart.getContents().clear();
    }

    @Test
    public void testBundlePromotionAppliedOnCart() {
        Map<CartItem, Integer> testContents = new HashMap<>();
        testContents.put(productA, 3);
        testContents.put(productB, 5);
        testContents.put(productC, 1);
        testContents.put(productD, 1);
        cart.setContents(testContents);
        Double checkoutPrice = promotionService.getPromotedPrice(cart, promotions);
        assertEquals(280.0, checkoutPrice);
    }

    @Test
    public void testCartWithNoAvailablePromotion() {
        Map<CartItem, Integer> testContents = new HashMap<>();
        testContents.put(productA, 1);
        testContents.put(productB, 1);
        testContents.put(productC, 1);
        cart.setContents(testContents);
        Double checkoutPrice = promotionService.getPromotedPrice(cart, promotions);
        assertEquals(100.0, checkoutPrice);
    }

    @Test
    public void testSingleProductGroupingPromotionAppliedOnCart() {
        Map<CartItem, Integer> testContents = new HashMap<>();
        testContents.put(productA, 5);
        testContents.put(productB, 5);
        testContents.put(productC, 1);
        cart.setContents(testContents);
        Double checkoutPrice = promotionService.getPromotedPrice(cart, promotions);
        assertEquals(370.0, checkoutPrice);
    }

    @Test
    public void testBundlePromotionAppliedOnCartTwoTimes() {
        Map<CartItem, Integer> testContents = new HashMap<>();
        testContents.put(productC, 2);
        testContents.put(productD, 2);
        cart.setContents(testContents);
        Double checkoutPrice = promotionService.getPromotedPrice(cart, promotions);
        assertEquals(60.0, checkoutPrice);
    }

    @Test
    public void testSingleProductGroupingPromotionAppliedOnCartTwoTimes() {
        Map<CartItem, Integer> testContents = new HashMap<>();
        testContents.put(productA, 6);
        cart.setContents(testContents);
        Double checkoutPrice = promotionService.getPromotedPrice(cart, promotions);
        assertEquals(260.0, checkoutPrice);
    }

    @Test
    public void testSingleProductGroupingPromotionAppliedOnCartTwoTimesAndOneWithoutPromotion() {
        Map<CartItem, Integer> testContents = new HashMap<>();
        testContents.put(productA, 7);
        cart.setContents(testContents);
        Double checkoutPrice = promotionService.getPromotedPrice(cart, promotions);
        assertEquals(310.0, checkoutPrice);
    }
}
