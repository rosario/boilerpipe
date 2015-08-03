package de.l3s.boilerpipe.filters.lithuanian;


    import java.util.regex.Pattern;

    import de.l3s.boilerpipe.BoilerpipeFilter;
    import de.l3s.boilerpipe.BoilerpipeProcessingException;
    import de.l3s.boilerpipe.document.TextBlock;
    import de.l3s.boilerpipe.document.TextDocument;
    import de.l3s.boilerpipe.labels.DefaultLabels;

public class ArticleMetadataFilter implements BoilerpipeFilter {
  private static final Pattern[] PATTERNS_SHORT = new Pattern[] {
      Pattern
          .compile(
              "^[0-9 \\,\\./]*\\b(Sausis|Vasaris|Kovas|Balandis|Gegužė|Birželis|Liepa|Rugpjūtis|Rugsėjis|Spalis|Lapkritis|Gruodis|Sausio|Vasario|Kovo|Balandžio|Gegužės|Birželio|Liepos|Rugpjūčio|Rugsėjo|Spalio|Lapkričio|Gruodžio)?\\b[0-9 \\,\\:apm\\./]*([CPSDMGET]{2,3})?$"),
      Pattern.compile("^[Pp]arengė "),
      Pattern.compile("^[Bb]y "),
      Pattern.compile("^[Nn]r"),
      Pattern.compile("^\\([0-9]{1,3}\\)"),
      Pattern.compile("^((?:19|20)\\d\\d)[- ](0?[1-9]|1[012])[- ]([12][0-9]|3[01]|0?[1-9])"),
      Pattern.compile("^[A-ZĄČĘĖĮŠŲŪŽ]{1}\\p{L}{1,30} [A-ZĄČĘĖĮŠŲŪ]{1}\\p{L}{1,30}"), // \\p{L} is a Unicode Character Property that matches any kind of letter from any language
//      Pattern.compile("^[A-ZĄČĘĖĮŠŲŪŽ]{1}\\p{L}{0,30}[.]{0,1} [A-ZĄČĘĖĮŠŲŪ]{1}\\p{L}{1,30} nuotr."),
//      Pattern.compile("^[A-ZĄČĘĖĮŠŲŪŽ]{1}\\p{L}{0,30}[.]{0,1} [A-ZĄČĘĖĮŠŲŪ]{1}\\p{L}{1,30} \\([A-Za-z]{1,30}\\) nuotr."),
      Pattern.compile(".*?inf.$"),
      Pattern.compile(".*?nuotr.$"),
      Pattern.compile("^(?:\\xA9|&copy;)")
  };


  public static final ArticleMetadataFilter INSTANCE = new ArticleMetadataFilter();

  private ArticleMetadataFilter() {
  }

  public boolean process(TextDocument doc)
      throws BoilerpipeProcessingException {
    boolean changed = false;
    for (TextBlock tb : doc.getTextBlocks()) {
      if (tb.getNumWords() > 10) {
        continue;
      }
      final String text = tb.getText();
      for (Pattern p : PATTERNS_SHORT) {
        if (p.matcher(text).find()) {
          changed = true;
          tb.setIsContent(false);
          tb.addLabel(DefaultLabels.ARTICLE_METADATA);
        }
      }
    }
    return changed;
  }

}
