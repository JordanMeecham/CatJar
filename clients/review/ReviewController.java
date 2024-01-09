package clients.review;

import clients.review.ReviewModel;
import clients.review.ReviewView;

/**
 * The Customer Controller
 * @author M A Smith (c) June 2014
 */

public class ReviewController
{
  private ReviewModel model = null;
  private ReviewView  view  = null;
  /**
   * Constructor
   * @param model The model 
   * @param view  The view from which the interaction came
   */
  public ReviewController( ReviewModel model, ReviewView view )
  {
    this.view  = view;
    this.model = model;
  }

  /**
   * Check interaction from view
   * @param pn The product number to be checked
   */
  public void doRev1( String pn )
  {
    model.doRev1();
  }

  /**
   * Clear interaction from view
   */
  public void doRev2()
  {
    model.doRev2();
  }

  public void doRev3()
  {
    model.doRev3();
  }
  
  public void doRev4()
  {
    model.doRev4();
  }
  public void doRev5()
  {
    model.doRev5();
  }
  
  public void doSearch( String pn )
  {
    model.doSearch(pn);
  }
  
  
}

