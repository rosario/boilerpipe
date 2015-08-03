/**
 * boilerpipe
 *
 * Copyright (c) 2009 Christian Kohlschütter
 *
 * The author licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.l3s.boilerpipe.extractors;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.filters.english.DensityRulesClassifier;
import de.l3s.boilerpipe.filters.english.IgnoreBlocksAfterContentFilter;
import de.l3s.boilerpipe.filters.english.MinFulltextWordsFilter;
import de.l3s.boilerpipe.filters.english.NumWordsRulesClassifier;
import de.l3s.boilerpipe.filters.lithuanian.*;
import de.l3s.boilerpipe.filters.heuristics.*;
import de.l3s.boilerpipe.filters.lithuanian.ArticleMetadataFilter;
import de.l3s.boilerpipe.filters.simple.BoilerplateBlockFilter;
import de.l3s.boilerpipe.filters.simple.LabelToBoilerplateFilter;
import de.l3s.boilerpipe.labels.DefaultLabels;

/**
 * A full-text extractor which is tuned towards news articles. In this scenario
 * it achieves higher accuracy than {@link de.l3s.boilerpipe.extractors.DefaultExtractor}.
 *
 * @author Christian Kohlschütter
 */
public final class LtArticleExtractor extends ExtractorBase {
    public static final LtArticleExtractor INSTANCE = new LtArticleExtractor();

    /**
     * Returns the singleton instance for {@link de.l3s.boilerpipe.extractors.LtArticleExtractor}.
     */
    public static LtArticleExtractor getInstance() {
        return INSTANCE;
    }
    
    public boolean process(TextDocument doc)
            throws BoilerpipeProcessingException {
        return

        TerminatingBlocksFinder.INSTANCE.process(doc)
        | new DocumentTitleMatchClassifier(doc.getTitle()).process(doc)
        | NumWordsRulesClassifier.INSTANCE.process(doc)
        | ArticleMetadataFilter.INSTANCE.process(doc)
        | IgnoreBlocksAfterContentFilter.DEFAULT_INSTANCE.process(doc) // gets rid of pot-end-of-content stuff
        | TrailingHeadlineToBoilerplateFilter.INSTANCE.process(doc) // MSA: experiment with throwing away

        | RemoveFirstLabelFilter.INSTANCE_TITLE.process(doc)

        | ExpandTitleToContentFilter.INSTANCE_SKIP_TITLE.process(doc)

        | BlockProximityFusion.MAX_DISTANCE_1_CONTENT_ONLY_SAME_TAGLEVEL.process(doc)

        | BoilerplateBlockFilter.INSTANCE.process(doc)

        | KeepLargestBlockFilter.INSTANCE_EXPAND_TO_SAME_TAGLEVEL_MIN_WORDS.process(doc)

        | LargeBlockSameTagLevelToContentFilter.INSTANCE.process(doc)

        | ListAtEndFilter.INSTANCE.process(doc)
        ;

    }
}
