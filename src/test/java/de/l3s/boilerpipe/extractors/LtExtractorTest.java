package de.l3s.boilerpipe.extractors;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LtExtractorTest {

    final ExtractorBase extractor = LtArticleExtractor.INSTANCE;

    private void hasNoBoilerPipe(String siteName, String inputFileNameBase) throws Exception {
        String inputHtml = "/" + inputFileNameBase + ".html";
        String shouldHaveText = "/" + inputFileNameBase + ".txt";
        assertEquals(siteName, getFileAsString(shouldHaveText), extractor.getText(getFileAsString(inputHtml)));
    }

    @Test
    public void shouldGetLtText() throws Exception{
        hasNoBoilerPipe("LRT", "lrtlt_klimatas");
        hasNoBoilerPipe("Balsas", "balsaslt_tiltas");

    }

    public String getFileAsString(String file) throws Exception {
        return IOUtils.toString(getClass().getResourceAsStream(file));
    }
}
