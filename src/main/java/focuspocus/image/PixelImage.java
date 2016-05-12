package focuspocus.image;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class PixelImage extends BufferedImage {

  private static final String ARRAY_ERROR = "Array size is invalid.";

  private PixelImage(final int the_width, final int the_height, final int the_type) {
    super(the_width, the_height, the_type);
  }

  public static PixelImage load(final File the_file) throws IOException {
    final BufferedImage buf = ImageIO.read(the_file);

    if (buf == null) {
      throw new IOException("File did not contain a valid image: " + the_file);
    }
    
    int width = buf.getWidth();
    int height = buf.getHeight();
    
    if (width > 1200) {
        width = 1200;
        height = height*(1200/width);
    }
    
    if (height > 600) {
        height = 600;
        width = width*(600/height);
    }

    final PixelImage image =
        new PixelImage(width, height, BufferedImage.TYPE_INT_RGB);
    
    /*int scale = 2;
    int w = width/scale;
    int h = height/scale;*/
    
    final Graphics g = image.getGraphics();
    g.drawImage(buf, 0, 0, (int) width, (int) height, null);
    return image;
  }

  public void save(final File the_file) throws IOException {
    ImageIO.write(this, "png", the_file);
  }

  public Pixel[][] getPixelData() {
    final Raster r = getRaster();
    final Pixel[][] data = new Pixel[r.getHeight()][r.getWidth()];
    int[] samples = new int[Pixel.NUM_CHANNELS];

    for (int row = 0; row < r.getHeight(); row++) {
      for (int col = 0; col < r.getWidth(); col++) {
        samples = r.getPixel(col, row, samples);
        final Pixel new_pixel = new Pixel(samples[0], samples[1], samples[2]);
        data[row][col] = new_pixel;
      }
    }

    return data;
  }

  public void setPixelData(final Pixel[][] the_data) throws IllegalArgumentException {
    final int[] pixel_values = new int[Pixel.NUM_CHANNELS];
    final WritableRaster wr = getRaster();

    if (the_data == null || the_data.length != wr.getHeight()) {
      throw new IllegalArgumentException(ARRAY_ERROR);
    } else if (the_data[0].length != wr.getWidth()) {
      for (int i = 0; i < the_data.length; i++) {
        if (the_data[i] == null || the_data[i].length != wr.getWidth()) {
          throw new IllegalArgumentException(ARRAY_ERROR);
        }
      }
    }

    for (int row = 0; row < wr.getHeight(); row++) {
      for (int col = 0; col < wr.getWidth(); col++) {
        pixel_values[0] = the_data[row][col].getRed();
        pixel_values[1] = the_data[row][col].getGreen();
        pixel_values[2] = the_data[row][col].getBlue();
        wr.setPixel(col, row, pixel_values);
      }
    }
  }
}
