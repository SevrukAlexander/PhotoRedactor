package focuspocus.filters;

import focuspocus.image.Pixel;
import focuspocus.image.PixelImage;

public class FlipVerticalFilter extends AbstractFilter
{
  public FlipVerticalFilter()
  {
    super("Отразить по вертикали");
  }

  public void filter(final PixelImage the_image)
  {
    final Pixel[][] data = the_image.getPixelData();
    for (int row = 0; row < the_image.getHeight() / 2; row++)
    {
      for (int col = 0; col < the_image.getWidth(); col++)
      {
        swap(data, row, col, the_image.getHeight() - row - 1, col);
      }
    }
    the_image.setPixelData(data);
  }
}
