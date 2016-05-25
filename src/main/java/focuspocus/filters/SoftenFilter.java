package focuspocus.filters;

public class SoftenFilter extends AbstractWeightedFilter
{
  private static final int[][] WEIGHTS = {{1, 2, 1}, {2, 4, 2}, {1, 2, 1}};

  public SoftenFilter()
  {
    super("Размытие", WEIGHTS);
  }

}
