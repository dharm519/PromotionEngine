package com.promotion;

import com.model.Cart;
import com.model.CartItem;
import com.util.PriceList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BulkPromotion implements IPromotion{

    private List<String> appliedItems = new ArrayList<>();
    private Double promotedPrice;
    private Map<String, Boolean> availabilityCheckMap = new HashMap<>();

    public BulkPromotion(List<String> items, Double promotedPrice) {
        this.appliedItems.addAll(items);
        this.promotedPrice = promotedPrice;
    }
    @Override
    public Cart applyPromotion(Cart cart) {
        if(!isAvailable(cart)) {
            System.out.println("There is no available item in this cart.");
        }

        Cart promotedCart = new Cart(cart.getContents());
        Map<CartItem, Integer> cartContents = new HashMap<>(cart.getContents());

        for(String item: appliedItems){
            if(promotedCart.getQuantity(item)==1) {
                cartContents.remove(promotedCart.getEntryByItemName(item));
            }
            else {
                cartContents.putAll(promotedCart.adjustInventory(item,promotedCart.getQuantity(item)-1));
            }
        }
        promotedCart.setContents(cartContents);
        return promotedCart;
    }

    @Override
    public Boolean isAvailable(Cart cart) {
        for (String appliedItem : appliedItems){
            availabilityCheckMap.put(appliedItem, false);
        }

        for (Map.Entry<CartItem, Integer> kv: cart.getContents().entrySet()) {
            if (appliedItems.contains(kv.getKey().getName())) {
                availabilityCheckMap.put(kv.getKey().getName(), true);
            }
        }
         return !availabilityCheckMap.containsValue(false);
    }

    @Override
    public Double getDiscountedPrice() {
        double itemPrice = 0.0;
        for(String sku: appliedItems)
            itemPrice += PriceList.getPrice(sku);

        return itemPrice - this.promotedPrice;
    }
}
