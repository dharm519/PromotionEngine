package com.util;

import com.promotion.BulkPromotion;
import com.promotion.IPromotion;
import com.promotion.SingleItemGroupPromotion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PromotionUtil {

    public static List<IPromotion> setupPromotions() {
        List<IPromotion> promotions = new ArrayList<>();
        BulkPromotion bulkPromotion = new BulkPromotion(Arrays.asList("C", "D"), 30.0);
        SingleItemGroupPromotion singleItemGroupPromotionA = new SingleItemGroupPromotion("A", 3, 130.0);
        SingleItemGroupPromotion singleItemGroupPromotionB = new SingleItemGroupPromotion("B",2, 45.0);
        promotions.add(bulkPromotion);
        promotions.add(singleItemGroupPromotionA);
        promotions.add(singleItemGroupPromotionB);
        return promotions;
    }
}
