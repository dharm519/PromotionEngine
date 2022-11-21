package com.service;

import com.model.Cart;
import com.promotion.IPromotion;

import java.util.List;

public interface IPromotionService {

    /**
     * Returns the checkout total before applying the promotions.
     * @param cart
     * @return
     */
    public Double getRawPrice(Cart cart);

    /**
     * Returns the price after promotions are applied.
     * @param cart
     * @param promotions
     * @return
     */
    public Double getPromotedPrice(Cart cart, List<IPromotion> promotions);
}
