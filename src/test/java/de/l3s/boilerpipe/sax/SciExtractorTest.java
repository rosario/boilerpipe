package de.l3s.boilerpipe.sax;

import de.l3s.boilerpipe.BoilerpipeExtractor;
import de.l3s.boilerpipe.extractors.*;
import junit.framework.Assert;
import org.junit.Test;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by martynas on 13/11/14.
 */
public class SciExtractorTest {
//    @Test
//    public void shouldGetSciText1() throws IOException, URISyntaxException {
//        URL url = new URL("http://www.genetics.org/content/122/1/19.short");
//        InputSource is = new InputSource();
//        is.setEncoding("UTF-8");
//        is.setByteStream(url.openStream());
//        try {
//            is.setByteStream(url.openStream());
//            final SciArticleExtractor ae = SciArticleExtractor.INSTANCE;
//            String text = ae.getText(is);
//            Assert.assertEquals("Results differ", text, Texts.EN1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void shouldGetSciText2() throws IOException, URISyntaxException {
//        URL url = new URL("http://www.nature.com/nature/journal/v403/n6770/abs/403623a0.html");
//        InputSource is = new InputSource();
//        is.setEncoding("UTF-8");
//        is.setByteStream(url.openStream());
//        try {
//            is.setByteStream(url.openStream());
//            final SciArticleExtractor ae = SciArticleExtractor.INSTANCE;
//            String text = ae.getText(is);
//            Assert.assertEquals("Results differ", text, Texts.EN2);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    @Test
//    public void shouldGetSciText3() throws IOException, URISyntaxException {
//        URL url = new URL("http://www.molbiolcell.org/content/9/12/3273.short");
//        InputSource is = new InputSource();
//        is.setEncoding("UTF-8");
//        is.setByteStream(url.openStream());
//        try {
//            is.setByteStream(url.openStream());
//            final SciArticleExtractor ae = SciArticleExtractor.INSTANCE;
//            String text = ae.getText(is);
//            Assert.assertEquals("Results differ", text, Texts.EN3);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    @Test
//    public void shouldGetSciText4() throws IOException, URISyntaxException {
//        URL url = new URL("http://onlinelibrary.wiley.com/doi/10.1002/yea.320101310/abstract;jsessionid=35EF516C4A11F5347489FE8EC5A79681.f02t04");
//        InputSource is = new InputSource();
//        is.setEncoding("UTF-8");
//        is.setByteStream(url.openStream());
//        try {
//            is.setByteStream(url.openStream());
//            final SciArticleExtractor ae = SciArticleExtractor.INSTANCE;
//            String text = ae.getText(is);
//            Assert.assertEquals("Results differ", text, Texts.EN4);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    @Test
//    public void shouldGetSciText5() throws IOException, URISyntaxException {
//        URL url = new URL("http://www.sciencedirect.com/science/article/pii/0378111987902320");
//        try {
//            final SciArticleExtractor ae = SciArticleExtractor.INSTANCE;
//            String text = ae.getText(getFileAsString("/en5.html"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void shouldGetSciText6() throws IOException, URISyntaxException {
////        http://www.scielo.br/scielo.php?script=sci_arttext&pid=S1517-83822010000200030
//        try {
//            final SciArticleExtractor ae = SciArticleExtractor.INSTANCE;
//            String text = ae.getText(getFileAsString("/en7.html"));
//            System.out.println(text);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void shouldGetSciText7() throws IOException, URISyntaxException {
////        http://journal.frontiersin.org/Journal/10.3389/fmicb.2013.00166/full
//        try {
//            final SciArticleExtractor ae = SciArticleExtractor.INSTANCE;
//            String text = ae.getText(getFileAsString("/en7.html"));
//            System.out.println(text);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void shouldGetSciText8() throws IOException, URISyntaxException {
////        http://www.scielo.br/scielo.php?script=sci_arttext&pid=S0001-37141999000300012
//        try {
//            final SciArticleExtractor ae = SciArticleExtractor.INSTANCE;
//            String text = ae.getText(getFileAsString("/en8.html"));
//            System.out.println(text);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void shouldGetSciText9() throws IOException, URISyntaxException {
////        http://www.revistas.unal.edu.co/index.php/biotecnologia/article/view/15540
//        try {
//            final SciArticleExtractor ae = SciArticleExtractor.INSTANCE;
//            String text = ae.getText(getFileAsString("/en9.html"));
//            System.out.println(text);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void shouldGetSciText10() throws IOException, URISyntaxException {
////        http://www.microbialcellfactories.com/content/5/1/20
//        try {
//            final SciArticleExtractor ae = SciArticleExtractor.INSTANCE;
//            String text = ae.getText(getFileAsString("/en10.html"));
//            System.out.println(text);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void shouldGetSciText10() throws IOException, URISyntaxException {
        try {
            final SciArticleExtractor ae = SciArticleExtractor.INSTANCE;
            String text = ae.getText(getFileAsString("/en11.html"));
            System.out.println(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFileAsString(String file) {
        InputStream stream = getClass().getResourceAsStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
