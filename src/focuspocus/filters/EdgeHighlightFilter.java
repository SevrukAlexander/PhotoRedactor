package focuspocus.filters;

public class EdgeHighlightFilter extends AbstractWeightedFilter
{
  private static final int[][] WEIGHTS = {{-1, -1, -1}, {-1, 9, -1}, {-1, -1, -1}};

  public EdgeHighlightFilter()
  {
    super("Edge Highlight", WEIGHTS);
  }

}
