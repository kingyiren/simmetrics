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
package org.simmetrics.example;

import java.util.Arrays;

import org.simmetrics.StringMetricBuilder;
import org.simmetrics.StringMetric;
import org.simmetrics.StringMetrics;
import org.simmetrics.metrics.CosineSimilarity;
import org.simmetrics.simplifiers.CaseSimplifier;
import org.simmetrics.tokenizers.QGram2Tokenizer;

public class BatchExample {

	public static void main(String[] args) {

		String[] names = new String[] { "Maryjo Murff", "Page Desmarais",
				"Alayna Sawin", "Charmain Scoggin", "Sanora Larkey" };
		String name = "Gearldine Desanti";

		StringMetric metric = new StringMetricBuilder()
				.setMetric(new CosineSimilarity())
				.setSimplifier(new CaseSimplifier.Lower())
				.setTokenizer(new QGram2Tokenizer())
				.build();

		float[] scores = StringMetrics.compare(metric, name, names);

		System.out.println(String.format(
				"Comparing %s to %s using %s gives: %s",
				Arrays.toString(names), name, metric, Arrays.toString(scores)));

	}
}
