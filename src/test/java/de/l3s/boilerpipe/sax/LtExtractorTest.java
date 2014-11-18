package de.l3s.boilerpipe.sax;

import de.l3s.boilerpipe.BoilerpipeExtractor;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.extractors.LtArticleExtractor;
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
public class LtExtractorTest {
    @Test
    public void shouldGetLtText() throws IOException, URISyntaxException {
        URL url = new URL("http://www.balsas.lt/naujiena/814860/parlamento-gynejas-zaliojo-tilto-skulpturas-papuose-grandinemis");
        url = new URL("http://www.balsas.lt/naujiena/814867/ukraina-paskelbe-kam-ruosis-artimiausiu-metu?utm_source=kitiRubrikosStr&utm_medium=susijeEuropa&utm_campaign=naujiena");
        url = new URL("http://iq.lt/pasaulis/rygos-randas");
        url = new URL("http://www.15min.lt/naujiena/aktualu/pasaulis/kremlius-per-gruzija-siekia-vidurine-azija-ir-kinija-atkirsti-nuo-europos-57-466666?cf=df");
        url = new URL("http://www.bernardinai.lt/straipsnis/2014-11-15-mykolas-drunga-ar-darbdaviu-ir-darbininku-atstovai-susitars/124229");
        url = new URL("http://www.lrt.lt/naujienos/lietuvoje/2/78585/klimato_pokyciai_briedziams_nerupi_dirvozemiui_kenkia");
        url = new URL("http://www.alfa.lt/straipsnis/1215053/nuosprendis-narkotiku-byloje-kaltinamam-taraskeviciui-astuoneri-su-puse-metu-nelaisves");
        url = new URL("http://www.bns.lt/topic/941/news/46650709/");
        url = new URL("http://www.delfi.lt/news/daily/crime/klaipedoje-ieskoma-dingusios-studentes-sukuojami-miskai-pakeltas-sraigtasparnis.d?id=66427936");
        url = new URL("http://vz.lt/article/2014/11/18/laisves-premija-skirta-lenku-disidentui");
        InputSource is = new InputSource();
        is.setEncoding("UTF-8");
        is.setByteStream(url.openStream());

        try {
            final BoilerpipeExtractor extractor = CommonExtractors.ARTICLE_EXTRACTOR;

            final LtArticleExtractor ie = LtArticleExtractor.INSTANCE;

//          String text = ie.getText(getFileAsString("/lrtlt_klimatas.html"));

            String text = ie.getText(is);

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
