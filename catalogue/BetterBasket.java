package catalogue;

import java.io.Serializable;
import java.util.Collections;
import java.util.Currency;
import java.util.Formatter;
import java.util.Locale;

public class BetterBasket extends Basket implements Serializable
{
	private static final long serialVersionUID = 1L;
	
 @Override
	public boolean add (Product product1) {
	for (Product product2: this) {
	if (product1.getProductNum().equals (product2.getProductNum())) { 
	product2.setQuantity(product2.getQuantity()+product1.getQuantity());
	return(true);
	}}
	super.add(product1);
	return(true);
 }}
