package focuspocus.filters;

import focuspocus.image.PixelImage;

public class GrayscaleFilter extends AbstractFilter
{
  private static final int MASK = 0xff;

  private static final int ALPHA_OFFSET = 24;

  private static final int RED_OFFSET = 16;

  private static final int GREEN_OFFSET = 8;

  private static final int NUM_COLORS = 3;

  public GrayscaleFilter()
  {
    super("Grayscale");
  }

  public void filter(final PixelImage the_image)
  {
    final int w = the_image.getWidth(null);
    final int h = the_image.getHeight(null);
    for (int i = 0; i < w; i++)
    {
      for (int j = 0; j < h; j++)
      {
        final int p = the_image.getRGB(i, j);
        final int a = (((p >> RED_OFFSET) & MASK) + ((p >> GREEN_OFFSET) & MASK)
                      + (p & MASK)) / NUM_COLORS;
        the_image.setRGB(i, j, (MASK << ALPHA_OFFSET) | (a << RED_OFFSET)
                         | (a << GREEN_OFFSET) | a);
      }
    }
  }
}
