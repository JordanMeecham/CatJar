package clients.review;
import catalogue.Basket;
import catalogue.Product;
import debug.DEBUG;
import middle.MiddleFactory;
import middle.OrderProcessing;
import middle.StockException;
import middle.StockReader;
import java.sql.*;
import javax.swing.*;
import clients.Setup;
import com.mysql.cj.x.protobuf.MysqlxCrud.Insert;

import java.util.HashMap;
import java.sql.*;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;


/**
 * Implements the Model of the customer client
 * @author  Mike Smith University of Brighton
 * @version 1.0
 */
public class ReviewModel extends Observable
{
  private Product     theProduct = null;          // Current product
  private Basket      theBasket  = null;          // Bought items 
  public String      pn = "";                    // Product being processed
  public int review1 = 1;

  private StockReader     theStock     = null;
  private OrderProcessing theOrder     = null;
  private ImageIcon       thePic       = null;

  /*
   * Construct the model of the Customer
   * @param mf The factory to create the connection objects
   */
  public ReviewModel(MiddleFactory mf)
  {
    try                                          // 
    {  
      theStock = mf.makeStockReader();           // Database access
    } catch ( Exception e )
    {
      DEBUG.error("CustomerModel.constructor\n" +
                  "Database not created?\n%s\n", e.getMessage() );
    }


    theBasket = makeBasket();                    // Initial Basket
  }
  
  /**
   * return the Basket of products
   * @return the basket of products
   */
  public Basket getBasket()
  {
    return theBasket;
  }

  /**
   * Check if the product is in Stock
   * @param productNum The product number
   */
  public void doRev1()
  {
	  
  }
  
  public void doRev2() {
	  
  }
  public void doRev3() {
	  
  }
 public void doRev4() {
	  
  }
 public void doRev5() {
	  
 }
 public void doSearch(String productNum) {
	    String theAction = "";
	    pn  = productNum.trim();                    // Product no.
	  
	    /** This is a map I created with the product names and numbers, it will be searched for a match when the user 
	    inputs and will help to match the product number to the name, learned how to create a map from, a copy of the one I created in customer client <a href="https://www.youtube.com/watch?v=H62Jfv1DJlU">
	    https://www.youtube.com/watch?v=H62Jfv1DJlU</a> */  
	    
	    
	    Map<String, String> productMap = new HashMap <String, String>(); //creates map
	    
	    
	    //Lines below add to map 
	    productMap.put( "0001", "40 inch LED HD TV");
	    productMap.put( "0002", "DAB Radio");
	    productMap.put( "0003", "Toaster");
	    productMap.put( "0004", "Watch");
	    productMap.put( "0005", "Digital Camera");
	    productMap.put( "0006", "MP3 Player");
	    productMap.put( "0007", "32Gb USB2 drive"); 
	    
	    
	    /** If statement that runs if the user input is contained in the map, a copy of the statement I created in customer client, learned how to 
	    iterate through a map from <a href="https://www.geeksforgeeks.org/iterate-map-java/">
	    https://www.geeksforgeeks.org/iterate-map-java/</a> */
	    
	  try
	   {
		   if(productMap.containsKey(pn) || productMap.containsValue(pn)) //Check if the map contains pn
		      {
		       
		       for (Entry<String, String> entry : productMap.entrySet()) { //Iterate through the map entries to look for match
		          if(entry.getValue().contains(pn)){ 
		           pn = entry.getKey(); 
		       break;
		            }
		          }
		         
		       
			         
	    Product pr = theStock.getDetails( pn ); //  Product
	        { 
	          theAction =                           //   Display 
	            String.format( "%s : %7.2f (%2d) ", //
	              pr.getDescription(),              //    description
	              pr.getPrice(),                    //    price
	              pr.getQuantity() );               //    quantity

	          thePic = theStock.getImage( pn );     //    product
	        }}
			  else {                                  // F
	        theAction =                             //  Inform Unknown
	          "Unknown product number " + pn;       //  product number
			  }
		   }
			 catch( StockException e )
	    {
	      DEBUG.error("CustomerClient.doCheck()\n%s",
	      e.getMessage() );
	    }
	    setChanged(); notifyObservers(theAction);
	  }
	      
	  /**
	   * Clear the products from the basket
	   */
	  public void doClear()
	  {
	    String theAction = "";
	    theBasket.clear();                        // Clear s. list
	    theAction = "Enter Product Number";       // Set display
	    thePic = null;                            // No picture
	    setChanged(); notifyObservers(theAction);
	  
 }
  
  public ImageIcon getPicture()
  {
    return thePic;
  }
  
  /**
   * ask for update of view callled at start
   */
  private void askForUpdate()
  {
    setChanged(); notifyObservers("START only"); // Notify
  }

  /**
   * Make a new Basket
   * @return an instance of a new Basket
   */
  protected Basket makeBasket()
  {
    return new Basket();
  }
}
