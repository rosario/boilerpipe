package de.l3s.boilerpipe.extractors;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class LtExtractorTest {

  final ExtractorBase extractor = LtArticleExtractor.INSTANCE;
  final CanolaExtractor cExtractor = CanolaExtractor.INSTANCE;

  private final static int LIMIT_SITES = 200;
  private final static float MATCH_PERCENTILE = 99.2f;
  private int atLeastSubsetMatched = 0;

  private float hasNoBoilerPipe(String siteName, String inputFileNameBase) throws Exception {
    System.out.println(inputFileNameBase);
    String inputHtml = "/" + inputFileNameBase + ".html";
    String shouldHaveText = "/" + inputFileNameBase + ".txt";
    String shouldHaveString = getFileAsString(shouldHaveText);

    String extractedString = extractor.getText(getFileAsString(inputHtml));

    PrintWriter out = new PrintWriter("/tmp/" + inputFileNameBase + ".ext.txt", "UTF-8");
    out.println(extractedString);
    out.close();

    shouldHaveString = shouldHaveString.replaceAll("\\s+", " ");

    extractedString = extractedString.replaceAll("\\u00AD", "");
    extractedString = extractedString.replaceAll("\\s+", " ");

    ArrayList<String> lcs = longestCommonSubsequence(shouldHaveString, extractedString);
    int matchedWords = lcs.size();

    int extractedWords = extractedString.split(" ").length;
    int shouldHaveWords = shouldHaveString.split(" ").length;

    float similarityPercentile = 0;
    if (extractedWords > shouldHaveWords) {
      similarityPercentile = (matchedWords * 100.0f) / extractedWords;
    } else {
      similarityPercentile = (matchedWords * 100.0f) / shouldHaveWords;
      atLeastSubsetMatched++;
    }

    System.out.println(siteName + "\t " + similarityPercentile + "% similarity");

    //assertEquals(siteName, shouldHaveText, shouldHaveString);
    return similarityPercentile;
  }

  @Test
  public void shouldGetLtText() throws Exception {
    URL url = getClass().getResource("/");

    File[] files = new File(url.toURI()).listFiles(new FilenameFilter() {
      public boolean accept(File dir, String name) {
        return name.toLowerCase().endsWith(".txt");
      }
    });

    // run boilerpipe for each resource file
    float total = 0;
    int checkedNum = 0;
    int matched = 0;
    atLeastSubsetMatched = 0;

    for (File file : files) {
      if (checkedNum < LIMIT_SITES) {
        String fname = FilenameUtils.removeExtension(file.getName());
        float similarity = hasNoBoilerPipe(fname, fname);
        total += similarity;
        if (similarity > MATCH_PERCENTILE) {
          matched++;
        }
        checkedNum++;
      }
    }

    float avg = total / checkedNum;
    System.out.println(
        "Avg sim %: " + avg + ", exact/short: " + matched + "/" + atLeastSubsetMatched + " (out of "
        + checkedNum + ")");
    assertTrue("Average Boilerpipe efficiency", avg > 0.5);
  }

  public String getFileAsString(String file) throws Exception {
    return IOUtils.toString(getClass().getResourceAsStream(file));
  }

  /**
   * Finds a list of longest common subsequences (lcs) of given two texts.
   *
   * @return - longest common subsequence list
   */
  private ArrayList<String> longestCommonSubsequence(String text1, String text2) {
    String[] text1Words = text1.split(" ");
    String[] text2Words = text2.split(" ");
    int text1WordCount = text1Words.length;
    int text2WordCount = text2Words.length;

    int[][] solutionMatrix = new int[text1WordCount + 1][text2WordCount + 1];

    for (int i = text1WordCount - 1; i >= 0; i--) {
      for (int j = text2WordCount - 1; j >= 0; j--) {
        if (text1Words[i].equals(text2Words[j])) {
          solutionMatrix[i][j] = solutionMatrix[i + 1][j + 1] + 1;
        } else {
          solutionMatrix[i][j] = Math.max(solutionMatrix[i + 1][j],
                                          solutionMatrix[i][j + 1]);
        }
      }
    }

    int i = 0, j = 0;
    ArrayList<String> lcsResultList = new ArrayList<String>();
    while (i < text1WordCount && j < text2WordCount) {
      if (text1Words[i].equals(text2Words[j])) {
        lcsResultList.add(text2Words[j]);
        i++;
        j++;
      } else if (solutionMatrix[i + 1][j] >= solutionMatrix[i][j + 1]) {
        i++;
      } else {
        j++;
      }
    }
    return lcsResultList;
  }

  private boolean equalsIgnoreWhitespace(String a, String b) {
    return a.replaceAll("\\s+", "").equalsIgnoreCase(b.replaceAll("\\s+", ""));
  }

}
