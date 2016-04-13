package focuspocus.filters;

import focuspocus.image.PixelImage;

public interface Filter
{
  void filter(PixelImage the_image);

  String getDescription();
}
