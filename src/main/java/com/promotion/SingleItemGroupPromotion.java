package com.promotion;

import com.model.Cart;
import com.model.CartItem;

import java.util.HashMap;
import java.util.Map;

public class SingleItemGroupPromotion implements IPromotion{
    private String appliedItem;
    private Integer quota;
    private Double promotedPrice;

    public SingleItemGroupPromotion(String appliedItem, Integer quota, Double promotedPrice) {
        this.appliedItem = appliedItem;
        this.quota = quota;
        this.promotedPrice = promotedPrice;
    }
    @Override
    public Cart applyPromotion(Cart cart) {
        if(cart.getContents().isEmpty())
            return cart;

        if(!isAvailable(cart)) {
            System.out.println("Not available.");
        }

        Cart promotedCart = new Cart(cart.getContents());

        int cartQuantity = promotedCart.getQuantity(appliedItem);
        Map<CartItem, Integer> updatedContents = new HashMap<>();

        if(cartQuantity - quota == 0) {
            updatedContents.putAll(promotedCart.removeItem(appliedItem));
        }
        else {
            updatedContents.putAll(promotedCart.adjustInventory(appliedItem, cartQuantity - quota));
        }

        promotedCart.setContents(updatedContents);
        return promotedCart;
    }

    /**
     * Returns true if the cart contains the specific item meeting the quota quantity for this promotion
     * @param cart
     * @return
     */
    @Override
    public Boolean isAvailable(Cart cart) {
        for(Map.Entry<CartItem, Integer> kv: cart.getContents().entrySet()){
            if(kv.getKey().getName().equalsIgnoreCase(appliedItem) && kv.getValue() >= quota)
                return true;
        }
        return false;
    }

    @Override
    public Double getDiscountedPrice() {
        return null;
    }
}
