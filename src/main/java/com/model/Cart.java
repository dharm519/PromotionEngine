package com.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map<CartItem, Integer> contents;

    public Cart() {
    }

    public Cart(Map<CartItem, Integer> contents) {
        this.contents = contents;
    }

    public Map<CartItem, Integer> getContents() {
        return contents;
    }

    public void setContents(Map<CartItem, Integer> contents) {
        this.contents = contents;
    }

    public Integer getQuantity(String itemName){
        for (Map.Entry<CartItem, Integer> kv: this.contents.entrySet()) {
            if (kv.getKey().getName().equalsIgnoreCase(itemName)) {
                return kv.getValue();
            }
        }
        return 0;
    }

    public Map<CartItem, Integer> removeItem(String itemToRemove) {
        CartItem productToRemove = null;
        Map<CartItem, Integer> temp = new HashMap<>();
        temp.putAll(this.contents);

        for (Map.Entry<CartItem, Integer> kv: temp.entrySet()) {
            if (kv.getKey().getName().equalsIgnoreCase(itemToRemove)) {
                productToRemove = kv.getKey();
            }
        }
        if (itemToRemove != null) {
            temp.remove(productToRemove);
        }
        return temp;
    }

    public Map<CartItem, Integer> adjustInventory(String itemName, Integer quantity) {
        CartItem productOut = null;
        Map<CartItem, Integer> temp = new HashMap<>();
        temp.putAll(this.contents);

        for (Map.Entry<CartItem, Integer> kv: temp.entrySet()) {
            if (kv.getKey().getName().equalsIgnoreCase(itemName)) {
                productOut = kv.getKey();
            }
        }
        if (null != productOut) {
            temp.put(productOut, quantity);
        }
        return temp;
    }

    /**
     * Returns product entry from the cart by product name, if it does exist
     * @param item
     * @return
     */
    public CartItem getEntryByItemName(String item) {
        for (Map.Entry<CartItem, Integer> kv: contents.entrySet()) {
            if (kv.getKey().getName().equalsIgnoreCase(item)) {
                return kv.getKey();
            }
        }
        return null;
    }

}
