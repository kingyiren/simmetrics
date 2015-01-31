/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance Metrics,
 * e.g. Levenshtein Distance, that provide float based similarity measures
 * between String Data. All metrics return consistent measures rather than
 * unbounded similarity scores.
 * 
 * Copyright (C) 2014 SimMetrics authors
 * 
 * This file is part of SimMetrics. SimMetrics is free software: you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * SimMetrics is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */
package org.simmetrics;

import java.util.List;

import org.simmetrics.metrics.BlockDistance;
import org.simmetrics.metrics.CosineSimilarity;
import org.simmetrics.metrics.DiceSimilarity;
import org.simmetrics.metrics.EuclideanDistance;
import org.simmetrics.metrics.JaccardSimilarity;
import org.simmetrics.metrics.Jaro;
import org.simmetrics.metrics.JaroWinkler;
import org.simmetrics.metrics.Levenshtein;
import org.simmetrics.metrics.MatchingCoefficient;
import org.simmetrics.metrics.MongeElkan;
import org.simmetrics.metrics.NeedlemanWunch;
import org.simmetrics.metrics.OverlapCoefficient;
import org.simmetrics.metrics.QGramsDistance;
import org.simmetrics.metrics.SimonWhite;
import org.simmetrics.metrics.SmithWaterman;
import org.simmetrics.metrics.SmithWatermanGotoh;
import org.simmetrics.metrics.SmithWatermanGotohWindowedAffine;
import org.simmetrics.simplifiers.SoundexSimplifier;
import org.simmetrics.tokenizers.QGram2Tokenizer;
import org.simmetrics.tokenizers.QGram3ExtendedTokenizer;
import org.simmetrics.tokenizers.QGramTokenizer;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.WhitespaceTokenizer;
import org.simmetrics.tokenizers.WordQGramTokenizer;

/**
 * This class consists exclusively of static methods that apply a metric to
 * lists and arrays of strings.
 * 
 * 
 * @author mpkorstanje
 *
 */
public abstract class StringMetrics {

	/**
	 * Applies a metric to a string c and a list of strings. Returns an array
	 * with the similarity value for c and each string in the list.
	 * 
	 * @param metric
	 *            to compare c with each each value in the list
	 * @param c
	 *            string to compare the list against
	 * @param strings
	 *            to compare c against
	 * @return an array with the similarity value for c and each string in the
	 *         list
	 */
	public static final float[] compare(StringMetric metric, final String c,
			final List<String> strings) {

		final float[] results = new float[strings.size()];

		// Iterate because List.get() may not be efficient (e.g. LinkedList).
		int i = 0;
		for (String s : strings) {
			results[i++] = metric.compare(c, s);
		}

		return results;
	}

	/**
	 * Applies a metric to a string c and a list of strings. Returns an array
	 * with the similarity value for c and each string in the list.
	 * 
	 * @param metric
	 *            to compare c with each each value in the list
	 * @param c
	 *            string to compare the list against
	 * @param strings
	 *            to compare c against
	 * @return an array with the similarity value for c and each string in the
	 *         list
	 */
	public static final float[] compare(StringMetric metric, final String c,
			final String... strings) {

		final float[] results = new float[strings.length];
		for (int i = 0; i < strings.length; i++) {
			// perform similarity test
			results[i] = metric.compare(c, strings[i]);
		}

		return results;
	}

	/**
	 * Applies a metric to each pair of a[n] and b[n]. Returns an array where
	 * result[n] contains the similarity between a[n] and b[n].
	 * 
	 * @param metric
	 *            to compare each element in a and b
	 * @param a
	 *            array of string to compare
	 * @param b
	 *            array of string to compare
	 * @throws IllegalArgumentException
	 *             when a and b are of a different size
	 * @return a list of similarity values for each pair a[n] b[n].
	 */
	public static final float[] compareArrays(StringMetric metric,
			final String[] a, final String[] b) {

		if (a.length != b.length) {
			throw new IllegalArgumentException("arrays must have the same size");
		}

		final float[] results = new float[a.length];

		for (int i = 0; i < a.length; i++) {
			results[i] = metric.compare(a[i], b[i]);
		}
		return results;
	}

	public static StringMetric blockDistance() {
		return blockDistance(new WhitespaceTokenizer());
	}

	public static StringMetric blockDistance(Tokenizer tokenizer) {
		return new StringMetricBuilder().setMetric(new BlockDistance())
				.setTokenizer(tokenizer).build();
	}

	public static StringMetric cosineSimilarity() {
		return cosineSimilarity(new WhitespaceTokenizer());
	}

	public static StringMetric cosineSimilarity(Tokenizer tokenizer) {
		return new StringMetricBuilder().setMetric(new CosineSimilarity())
				.setTokenizer(tokenizer).build();
	}

	public static StringMetric diceSimilarity() {
		return diceSimilarity(new WhitespaceTokenizer());
	}

	public static StringMetric diceSimilarity(Tokenizer tokenizer) {
		return new StringMetricBuilder().setMetric(new DiceSimilarity())
				.setTokenizer(tokenizer).build();
	}

	public static StringMetric euclideanDistance() {
		return euclideanDistance(new WhitespaceTokenizer());
	}

	public static StringMetric euclideanDistance(Tokenizer tokenizer) {
		return new StringMetricBuilder().setMetric(new EuclideanDistance())
				.setTokenizer(tokenizer).build();
	}

	public static StringMetric jaccardSimilarity() {
		return jaccardSimilarity(new WhitespaceTokenizer());
	}

	public static StringMetric jaccardSimilarity(Tokenizer tokenizer) {
		return new StringMetricBuilder().setMetric(new JaccardSimilarity())
				.setTokenizer(tokenizer).build();
	}

	public static StringMetric jaro() {
		return new Jaro();
	}

	public static StringMetric jaroWinkler() {
		return new JaroWinkler();
	}

	public static StringMetric levenshtein() {
		return new Levenshtein();
	}

	public static StringMetric matchingCoefficient() {
		return matchingCoefficient(new WhitespaceTokenizer());
	}

	public static StringMetric matchingCoefficient(Tokenizer tokenizer) {
		return new StringMetricBuilder().setMetric(new MatchingCoefficient())
				.setTokenizer(tokenizer).build();
	}

	public static StringMetric mongeElkan() {
		return mongeElkan(new WhitespaceTokenizer());
	}

	public static StringMetric mongeElkan(Tokenizer tokenizer) {
		return new StringMetricBuilder()
				.setMetric(new MongeElkan(new SmithWatermanGotoh()))
				.setTokenizer(tokenizer).build();
	}

	public static StringMetric needlemanWunch() {
		return new NeedlemanWunch();
	}

	public static StringMetric overlapCoefficient() {
		return overlapCoefficient(new WhitespaceTokenizer());
	}

	public static StringMetric overlapCoefficient(Tokenizer tokenizer) {
		return new StringMetricBuilder().setMetric(new OverlapCoefficient())
				.setTokenizer(tokenizer).build();
	}

	public static StringMetric qGramsDistance() {
		return qGramsDistance(new QGram3ExtendedTokenizer());
	}

	public static StringMetric qGramsDistance(QGramTokenizer tokenizer) {
		return new StringMetricBuilder().setMetric(new QGramsDistance())
				.setTokenizer(tokenizer).build();
	}

	public static StringMetric simonWhite() {
		return simonWhite(new QGram2Tokenizer());
	}

	public static StringMetric simonWhite(QGramTokenizer tokenizer) {
		return new StringMetricBuilder().setMetric(new SimonWhite())
				.setTokenizer(new WordQGramTokenizer(tokenizer)).build();
	}

	public static SmithWaterman smithWaterman() {
		return new SmithWaterman();
	}

	public static SmithWatermanGotoh smithWatermanGotoh() {
		return new SmithWatermanGotoh();
	}

	public static SmithWatermanGotohWindowedAffine smithWatermanGotohWindowedAffine() {
		return new SmithWatermanGotohWindowedAffine();
	}

	public static StringMetric soundex() {
		return new StringMetricBuilder().setMetric(new JaroWinkler())
				.setSimplifier(new SoundexSimplifier()).build();
	}
}
