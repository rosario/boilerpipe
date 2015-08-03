package de.l3s.boilerpipe.filters.lithuanian;

import de.l3s.boilerpipe.BoilerpipeFilter;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.document.TextBlock;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.labels.DefaultLabels;

/**
 * Created by martynas on 30/07/15.
 */
public final class RemoveFirstLabelFilter implements BoilerpipeFilter {

/**
 * Marks block that contains title as "boilerplate".
 **/
  public static final RemoveFirstLabelFilter
    INSTANCE_TITLE = new RemoveFirstLabelFilter(
      DefaultLabels.TITLE);

  private String label;

  public RemoveFirstLabelFilter(final String label) {
    this.label = label;
  }

  public boolean process(final TextDocument doc)
      throws BoilerpipeProcessingException {

    boolean changes = false;

    BLOCK_LOOP: for (TextBlock tb : doc.getTextBlocks()) {
      if (tb.isContent()) {

          if (tb.hasLabel(label)) {
            tb.setIsContent(false);
            changes = true;
            break;
          }

      }
    }

    return changes;
  }
}
