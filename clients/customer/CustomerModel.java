package clients.customer;
import catalogue.Basket;
import catalogue.Product;
import debug.DEBUG;
import middle.MiddleFactory;
import middle.OrderProcessing;
import middle.StockException;
import middle.StockReader;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;

/**
 * Implements the Model of the customer client
 * @author  Mike Smith University of Brighton
 * @version 1.0
 */
public class CustomerModel extends Observable
{
  private Product     theProduct = null;          // Current product
  private Basket      theBasket  = null;          // Bought items 
  public String      pn = "";                    // Product being processed 

  private StockReader     theStock     = null;
  private OrderProcessing theOrder     = null;
  private ImageIcon       thePic       = null;

  /*
   * Construct the model of the Customer
   * @param mf The factory to create the connection objects
   */
  public CustomerModel(MiddleFactory mf)
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
  public void doCheck(String productNum )
  {
    theBasket.clear();                          // Clear s. list
    String theAction = "";
    pn  = productNum.trim();                    // Product no.
    int    amount  = 1;                         //  & quantity
  
    
    /** This is a map I created with the product names and numbers, it will be searched for a match when the user 
   inputs and will help to match the product number to the name, learned how to create a map from <a href="https://www.youtube.com/watch?v=H62Jfv1DJlU">
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
    
  
    
    /** If statement that runs if the user input is contained in the map, learned how to 
    iterate through a map from <a href="https://www.geeksforgeeks.org/iterate-map-java/">
    https://www.geeksforgeeks.org/iterate-map-java/</a> */
  try
   {
	   if(productMap.containsKey(pn) || productMap.containsValue(pn)) //Check if the map contains pn
	      {
	       
	       for (Entry<String, String> entry : productMap.entrySet()) { //Iterate through the map entries to look for match
	          if(entry.getValue().contains(pn)){ //if match found
	          pn = entry.getKey(); //product number is mapped to product name
	       break;
	            }
	          }
	         
	       
		         
    Product pr = theStock.getDetails( pn ); //  Product
        if ( pr.getQuantity() >= amount )     //  In stock?
        { 
          theAction =                           //   Display 
            String.format( "%s : %7.2f (%2d) ", //
              pr.getDescription(),              //    description
              pr.getPrice(),                    //    price
              pr.getQuantity() );               //    quantity
          pr.setQuantity( amount );             //   Require 1
          theBasket.add( pr );                  //   Add to basket
          thePic = theStock.getImage( pn );     //    product
        
        } else {                                //  F
          theAction =                           //   Inform
            pr.getDescription() +               //    product not
            " not in stock" ;                   //    in stock
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
  
 
  public void doCatalogue()
  /** creates a JFrame table that acts as a catalogue and is shownn when the catalogue button is pressed, learned how to create a JTable from
 <a href="https://www.developer.com/java/java-tables/#:~:text=How%20to%20Create%20a%20Table,new%20JTable%20(row%2C%20column)%3B">
 https://www.developer.com/java/java-tables/#:~:text=How%20to%20Create%20a%20Table,new%20JTable%20(row%2C%20column)%3B</a> */

  {
	    JFrame catalogueFrame = new JFrame(); //creates new JFrame
	    
	      String[] headingNames = {"Product Number", "Description", "Price"}; //Column names
	 
	      Object[][] products = { //adds the product details to an object called products
	       {"Product Number", "Description", "Price"},
	       {"0001", "40 inch LED HD TV", "269.00"},
	       {"0002", "DAB Radio", "29.99"},
	       {"0003", "Toaster", "19.99"},
	       {"0004", "Watch", "29.99"},
	       {"0005", "Digital Camera", "89.99"},
	       {"0006", "MP3 Player", "7.99"},
	       {"0007", "32Gb USB2 drive", "6.99"},
	   };
	    JTable catalogue = new JTable(products, headingNames); //creates a JTable from the column names and products object I created
	 
	    catalogueFrame.add(catalogue); //Adds my JTable to the frame
	    catalogueFrame.setSize(400,300); //determines size, same size as other clients
	    catalogueFrame.setLocationRelativeTo(null); //Makes sure isn't positioned over customer client  
	    catalogueFrame.setVisible(true); //Makes the frame visable
	    
	   }
  
  /**
   * Return a picture of the product
   * @return An instance of an ImageIcon
   */ 
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

