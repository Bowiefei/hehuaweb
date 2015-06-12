package com.hehuaweb.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Page number generator.
 * 
 * @author xueqiangmi
 * @since Jun 29, 2013
 */
public final class PageNumberUtils {
  public static final int PAGE_SIZE = 20;

  /**
   * @param currentPageNumber
   *          current page number.
   * @param count
   *          the amount of the data item.
   * @return page number and the last page number.
   */
  public static Pair<List<Integer>, Integer> generate(int currentPageNumber, long count) {
    return generate(currentPageNumber, count, PAGE_SIZE);
  }

  /**
   * @param currentPageNumber
   *          current page number.
   * @param count
   *          the amount of the data item.
   * @param size
   *          page size
   * @return page number and the last page number.
   */
  public static Pair<List<Integer>, Integer> generate(int currentPageNumber, long count, int size) {
    int last = (int) (count / size + (count % size > 0 ? 1 : 0));
    int start = currentPageNumber > 3 ? currentPageNumber - 3 : 1;
    int end = currentPageNumber + 3 < last ? currentPageNumber + 3 : last;

    List<Integer> pageNumbers = new ArrayList<Integer>();
    for (int i = start; i <= end; i++) {
      pageNumbers.add(i);
    }
    return new Pair<List<Integer>, Integer>(pageNumbers, last);
  }

  public static List<Integer> generates(int pageNumber, int last) {
    int start = pageNumber > 3 ? pageNumber - 3 : 1;
    int end = pageNumber + 3 < last ? pageNumber + 3 : last;

    List<Integer> pageNumbers = new ArrayList<Integer>();
    for (int i = start; i <= end; i++) {
      pageNumbers.add(i);
    }
    return pageNumbers;
  }
}
