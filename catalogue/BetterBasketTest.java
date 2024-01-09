package catalogue;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BetterBasketTest {

	@Test
	void testMergeAddProduct() {
	BetterBasket testBasket = new BetterBasket ();
	
	Product productl = new Product ("0001", "Toaster", 10.00, 1); 
	Product product2 = new Product ("0001", "Toaster", 10.00, 1); 
	Product product3 = new Product ("0003", "Watch", 10.00, 1); 

	
	testBasket.add(productl);
	testBasket.add(product2);
	assertEquals(1, testBasket.size(), "Wrong size after merge");
	assertEquals(2, testBasket.get(0).getQuantity(), "Wrong quantity after merge");
	
	// Test that product 3 doesn't get merged with the others
	testBasket.add(product3);
	assertEquals(2, testBasket.size(), "Wrong size after non-merge");
	

	}}