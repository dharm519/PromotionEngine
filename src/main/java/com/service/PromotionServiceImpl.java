package com.service;

import com.model.Cart;
import com.model.CartItem;
import com.promotion.IPromotion;
import com.util.PriceList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PromotionServiceImpl implements IPromotionService{
    @Override
    public Double getRawPrice(Cart cart) {
        Double firstPrice = 0.0;

        for (Map.Entry<CartItem, Integer> entry: cart.getContents().entrySet()) {
            firstPrice = firstPrice + entry.getValue() * PriceList.getPrice(entry.getKey().getName());
        }
        return firstPrice;
    }

    @Override
    public Double getPromotedPrice(Cart cart, List<IPromotion> promotions) {
        Double rawPrice = getRawPrice(cart);
        return applyPromotions(cart, promotions, rawPrice);
    }

    private Double applyPromotions(Cart cart, List<IPromotion> promotions, Double checkoutPrice) {
        IPromotion selectedPromotion = null;
        double discountedPrice = 0.0;

        // First, get available promotions over the cart.
        List<IPromotion> availablePromotions = new ArrayList<>();
        for (IPromotion promotion: promotions) {
            if (promotion.isAvailable(cart)) {
                availablePromotions.add(promotion);
            }
        }

        // No available promotion on the cart, so return with the raw price and exit condition for recursion.
        if (availablePromotions.isEmpty()) {
            return checkoutPrice;
        }

        // Find the promotion to apply on cart and the discounted price with it over the cart.
        for (IPromotion promotion: availablePromotions) {
            double promotionDiscountedPrice = promotion.getDiscountedPrice();
            if (promotionDiscountedPrice > discountedPrice) {
                discountedPrice = promotionDiscountedPrice;
                selectedPromotion = promotion;
            }
        }

        // Adjust the items in the cart after promotions are applied
        Cart promotedCart = new Cart();
        if (selectedPromotion != null) {
            promotedCart = selectedPromotion.applyPromotion(cart);
        }
        // Subtract the discounted price from the cart price since promotion was applied.
        checkoutPrice = checkoutPrice - discountedPrice;
        return applyPromotions(promotedCart, availablePromotions, checkoutPrice);
    }
}
