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
package de.l3s.boilerpipe.filters.lith;

import de.l3s.boilerpipe.BoilerpipeFilter;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.document.TextBlock;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.labels.DefaultLabels;

/**
 * Finds blocks which are potentially indicating the end of an article text and
 * marks them with {@link de.l3s.boilerpipe.labels.DefaultLabels#INDICATES_END_OF_TEXT}. This can be used
 * in conjunction with a downstream {@link de.l3s.boilerpipe.filters.english.IgnoreBlocksAfterContentFilter}.
 *
 * @author Christian Kohlschütter
 * @see de.l3s.boilerpipe.filters.english.IgnoreBlocksAfterContentFilter
 */
public class TerminatingBlocksFinder implements BoilerpipeFilter {
	public static final TerminatingBlocksFinder INSTANCE = new TerminatingBlocksFinder();

	/**
	 * Returns the singleton instance for TerminatingBlocksFinder.
	 */
	public static TerminatingBlocksFinder getInstance() {
		return INSTANCE;
	}

	// public static long timeSpent = 0;

	public boolean process(TextDocument doc)
			throws BoilerpipeProcessingException {
		boolean changes = false;

		// long t = System.currentTimeMillis();

		for (TextBlock tb : doc.getTextBlocks()) {
			final int numWords = tb.getNumWords();
			if (numWords < 32) {
				final String text = tb.getText().trim();
				final int len = text.length();
				if (len >= 8) {
					final String textLC = text.toLowerCase();
					if (textLC.startsWith("komentarai")
							|| startsWithNumber(textLC, len, " komentarų", " komentarai",
							" atsiliepimų", " atsiliepimai",
							" griežtai draudžiama delfi")
							|| textLC.startsWith("komentaras")
							|| textLC.startsWith("komentuoti")
							|| textLC.startsWith("komentuok")
							|| textLC.startsWith("parašyk komentarą")
							|| textLC.startsWith("skaityti komentarus")
							|| textLC.startsWith("dalinkis")
							|| textLC.startsWith("pasidalink")
							|| textLC.startsWith("rašyti komentarą")
							|| textLC.startsWith("griežtai draudžiama delfi")
							|| textLC.startsWith("naujienų agentūros bns informaciją")
							|| textLC.startsWith("visas tinklalapyje vz.lt skelbiamas")
							|| textLC
									.equals("thanks for your comments - this feedback is now closed")) {
						tb.addLabel(DefaultLabels.INDICATES_END_OF_TEXT);
						changes = true;
					}
				} else if(tb.getLinkDensity() == 1.0) {
					if(text.equals("Komentarai")) {
						tb.addLabel(DefaultLabels.INDICATES_END_OF_TEXT);
					}
				}
			} else if (numWords < 60) {
				final String text = tb.getText().trim();
				final int len = text.length();
				final String textLC = text.toLowerCase();
				if (textLC.startsWith("griežtai draudžiama delfi")
						|| textLC.startsWith("naujienų agentūros bns informaciją")
						|| textLC.startsWith("visas tinklalapyje vz.lt skelbiamas")
						|| textLC
						.equals("thanks for your comments - this feedback is now closed")) {
					tb.addLabel(DefaultLabels.INDICATES_END_OF_TEXT);
					changes = true;
				}
			}
		}

		// timeSpent += System.currentTimeMillis() - t;

		return changes;
	}

	/**
	 * Checks whether the given text t starts with a sequence of digits,
	 * followed by one of the given strings.
	 * 
	 * @param t
	 *            The text to examine
	 * @param len
	 *            The length of the text to examine
	 * @param str
	 *            Any strings that may follow the digits.
	 * @return true if at least one combination matches
	 */
	private static boolean startsWithNumber(final String t, final int len,
			final String... str) {
		int j = 0;
		while (j < len && isDigit(t.charAt(j))) {
			j++;
		}
		if (j != 0) {
			for (String s : str) {
				if (t.startsWith(s, j)) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean isDigit(final char c) {
		return c >= '0' && c <= '9';
	}

}
