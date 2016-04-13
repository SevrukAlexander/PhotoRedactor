package focuspocus.filters;

public class SharpenFilter extends AbstractWeightedFilter
{
  private static final int[][] WEIGHTS = {{-1, -2, -1}, {-2, 28, -2}, {-1, -2, -1}};

  public SharpenFilter()
  {
    super("Sharpen", WEIGHTS);
  }
}
