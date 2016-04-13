package focuspocus.filters;

import focuspocus.image.PixelImage;

public abstract class AbstractWeightedFilter extends AbstractFilter
{
  private final int[][] my_weights;

  public AbstractWeightedFilter(final String the_description, final int[][] the_weights)
  {
    super(the_description);
    my_weights = the_weights.clone();
  }

  public void filter(final PixelImage the_image)
  {
    weight(the_image, my_weights);
  }

}
