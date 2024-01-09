package clients.review;

import catalogue.Basket;
import catalogue.BetterBasket;
import clients.Picture;
import middle.MiddleFactory;
import middle.StockReader;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the Customer view.
 * @author  Mike Smith University of Brighton
 * @version 1.0
 */

public class ReviewView implements Observer
{
  class Name                              // Names of buttons
  {
    public static final String Rev1  = "1";
    public static final String Rev2  = "2";
    public static final String Rev3  = "3";
    public static final String Rev4  = "4";
    public static final String Rev5  = "5";
    public static final String Search  = "Search";
  }

  private static final int H = 300;       // Height of window pixels
  private static final int W = 400;       // Width  of window pixels

  private final JLabel      theAction  = new JLabel();
  private final JLabel      rating  = new JLabel();
  private final JTextField  theInput   = new JTextField();
  private final JTextArea   theOutput  = new JTextArea();
  private final JScrollPane theSP      = new JScrollPane();
  private final JButton     theBt1 = new JButton( Name.Rev1 );
  private final JButton     theBt2 = new JButton( Name.Rev2 );
  private final JButton     theBt3 = new JButton( Name.Rev3 );
  private final JButton     theBt4 = new JButton( Name.Rev4 );
  private final JButton     theBt5 = new JButton( Name.Rev5 );
  private final JButton     theBtSearch = new JButton( Name.Search );

  private Picture thePicture = new Picture(80,80);
  private StockReader theStock   = null;
  private ReviewController cont= null;

  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param mf    Factor to deliver order and stock objects
   * @param x     x-cordinate of position of window on screen 
   * @param y     y-cordinate of position of window on screen  
   */
  
  public ReviewView( RootPaneContainer rpc, MiddleFactory mf, int x, int y )
  {
    try                                             // 
    {      
      theStock  = mf.makeStockReader();             // Database Access
    } catch ( Exception e )
    {
      System.out.println("Exception: " + e.getMessage() );
    }
    Container cp         = rpc.getContentPane();    // Content Pane
    Container rootWindow = (Container) rpc;         // Root Window
    cp.setLayout(null);                             // No layout manager
    rootWindow.setSize( W, H );                     // Size of Window
    rootWindow.setLocation( x, y );

    Font f = new Font("Monospaced",Font.PLAIN,12);  // Font f is
    
    theBtSearch.setBounds(  16, 100+40*0, 80, 40  );    // Check button
    theBtSearch.addActionListener(                   // Call back code
      e -> cont.doSearch( theInput.getText() ) );
    cp.add( theBtSearch );   

    theBt1.setBounds( 30, 15+60*3, 60, 30 );    // Check button
    theBt1.addActionListener(                   // Call back code
      e -> cont.doRev1( theInput.getText() ) );
    cp.add( theBt1 );                           //  Add to canvas

    theBt2.setBounds( 100, 15+60*3, 60, 30 );    // Clear button
    theBt2.addActionListener(                   // Call back code
      e -> cont.doRev2() );
    cp.add( theBt2 );     //  Add to canvas
    
    theBt3.setBounds( 170, 15+60*3, 60, 30 );    
    theBt3.addActionListener(                   //Call back code
      e -> cont.doRev3() );
    cp.add( theBt3 );                           //Add to canvas
    
    theBt4.setBounds( 240, 15+60*3, 60, 30 );    
    theBt4.addActionListener(                   //Call back code
      e -> cont.doRev4() );
    cp.add( theBt4 );     
    
    theBt5.setBounds( 310, 15+60*3, 60, 30 );    
    theBt5.addActionListener(                   //Call back code
      e -> cont.doRev5() );
    cp.add( theBt5 );     
    

    theAction.setBounds( 110, 25 , 270, 20 );       // Message area
    theAction.setText( "Please search for a product below:" );                        //  Blank
    cp.add( theAction );                            //  Add to canvas
    
    rating.setBounds( 110, 150 , 270, 20 );       // Message area
    rating.setText( "Please rate your product from 1-5:" );                        //  Blank
    cp.add( rating );                            //  Add to canvas

    theInput.setBounds( 110, 50, 270, 40 );         // Product no area
    theInput.setText("");                           // Blank
    cp.add( theInput );                             //  Add to canvas
    
 
    thePicture.setBounds( 16, 10+50*0, 80, 80 );   // Picture area
    cp.add( thePicture );                           //  Add to canvas
    thePicture.clear();
    
    rootWindow.setVisible( true );                  // Make visible);
    theInput.requestFocus();                        // Focus is here
  }

   /**
   * The controller object, used so that an interaction can be passed to the controller
   * @param c   The controller
   */

  public void setController( ReviewController c )
  {
    cont = c;
  }

  /**
   * Update the view
   * @param modelC   The observed model
   * @param arg      Specific args 
   */
   
  public void update( Observable modelC, Object arg )
  {
    ReviewModel model  = (ReviewModel) modelC;
    String        message = (String) arg;
    theAction.setText( message );
    ImageIcon image = model.getPicture();  // Image of product
    if ( image == null )
    {
      thePicture.clear();                  // Clear picture
    } else {
      thePicture.set( image );             // Display picture
    }
    theOutput.setText( model.getBasket().getDetails() );
    theInput.requestFocus();               // Focus is here
  }

}
