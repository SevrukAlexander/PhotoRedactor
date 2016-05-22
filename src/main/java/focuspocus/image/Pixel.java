package focuspocus.image;

public class Pixel {

  public static final int NUM_CHANNELS = 3;

  public static final int MIN_COLOR_VALUE = 0;

  public static final int MAX_COLOR_VALUE = 255;

  private int my_red;

  private int my_green;

  private int my_blue;

  public Pixel() {
    this(MIN_COLOR_VALUE, MIN_COLOR_VALUE, MIN_COLOR_VALUE);
  }

  public Pixel(final int the_red, final int the_green, final int the_blue) {
    my_red = the_red;
    my_green = the_green;
    my_blue = the_blue;
  }

  public int getRed() {
    return my_red;
  }

  public int getGreen() {
    return my_green;
  }

  public int getBlue() {
    return my_blue;
  }

  public void setRed(final int the_red) throws IllegalArgumentException {
    checkColorValue(the_red);
    my_red = the_red;
  }

  public void setGreen(final int the_green) throws IllegalArgumentException {
    checkColorValue(the_green);
    my_green = the_green;
  }

  public void setBlue(final int the_blue) throws IllegalArgumentException {
    checkColorValue(the_blue);
    my_blue = the_blue;
  }

  private void checkColorValue(final int the_value) throws IllegalArgumentException {
    if (the_value < MIN_COLOR_VALUE || the_value > MAX_COLOR_VALUE) {
      throw new IllegalArgumentException("Invalid color value: " + the_value);
    }
  }
}
