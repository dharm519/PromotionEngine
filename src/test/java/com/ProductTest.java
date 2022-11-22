package com;
import com.model.CartItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductTest {
    @Test
    public void testProductCreation() {
        CartItem product = new CartItem("A", 50);
        Assertions.assertEquals(50, product.getPrice());
        Assertions.assertEquals("A", product.getName());
        Assertions.assertNotNull(product, "Unable to create a product instance.");
    }
}
