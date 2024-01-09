package clients.review;

import clients.review.ReviewController;
import clients.review.ReviewModel;
import clients.review.ReviewView;
import middle.MiddleFactory;
import middle.Names;
import middle.RemoteMiddleFactory;

import java.awt.Color;

import javax.swing.*;

/**
 * The standalone Customer Client
 * @author  Mike Smith University of Brighton
 * @version 2.0
 */
public class ReviewClient
{
  public static void main (String args[])
  {
    String stockURL = args.length < 1         // URL of stock R
                    ? Names.STOCK_R           //  default  location
                    : args[0];                //  supplied location
    
    RemoteMiddleFactory mrf = new RemoteMiddleFactory();
    mrf.setStockRInfo( stockURL );
    displayGUI(mrf);                          // Create GUI
  }
   
  private static void displayGUI(MiddleFactory mf)
  {
    JFrame  window = new JFrame();     
    window.setTitle( "Review Client (MVC RMI)" );
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    ReviewModel model = new ReviewModel(mf);
    ReviewView  view  = new ReviewView( window, mf, 0, 0 );
    ReviewController cont  = new ReviewController( model, view );
    view.setController( cont );

    model.addObserver( view );       // Add observer to the model
    window.setVisible(true);         // Display Screen
  }
}
