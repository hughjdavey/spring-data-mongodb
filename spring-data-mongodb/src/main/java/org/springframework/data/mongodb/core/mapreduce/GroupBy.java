/*
 * Copyright 2010-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.mongodb.core.mapreduce;

import java.util.Optional;

import org.bson.Document;
import org.springframework.data.mongodb.core.Collation;

/**
 * Collects the parameters required to perform a group operation on a collection. The query condition and the input
 * collection are specified on the group method as method arguments to be consistent with other operations, e.g.
 * map-reduce.
 * 
 * @author Mark Pollack
 * @author Christoph Strobl
 */
public class GroupBy {

	private Document dboKeys;
	private String keyFunction;
	private String initial;
	private Document initialDocument;
	private String reduce;
	private String finalize;
	private Optional<Collation> collation = Optional.empty();

	public GroupBy(String... keys) {
		Document document = new Document();
		for (String key : keys) {
			document.put(key, 1);
		}
		dboKeys = document;
	}

	// NOTE GroupByCommand does not handle keyfunction.

	public GroupBy(String key, boolean isKeyFunction) {
		Document document = new Document();
		if (isKeyFunction) {
			keyFunction = key;
		} else {
			document.put(key, 1);
			dboKeys = document;
		}
	}

	public static GroupBy keyFunction(String key) {
		return new GroupBy(key, true);
	}

	public static GroupBy key(String... keys) {
		return new GroupBy(keys);
	}

	public GroupBy initialDocument(String initialDocument) {
		initial = initialDocument;
		return this;
	}

	public GroupBy initialDocument(Document initialDocument) {
		this.initialDocument = initialDocument;
		return this;
	}

	public GroupBy reduceFunction(String reduceFunction) {
		reduce = reduceFunction;
		return this;
	}

	public GroupBy finalizeFunction(String finalizeFunction) {
		finalize = finalizeFunction;
		return this;
	}

	public GroupBy collation(Collation collation) {

		this.collation = Optional.ofNullable(collation);
		return this;
	}

	public Document getGroupByObject() {
		// return new GroupCommand(dbCollection, dboKeys, condition, initial, reduce, finalize);
		Document document = new Document();
		if (dboKeys != null) {
			document.put("key", dboKeys);
		}
		if (keyFunction != null) {
			document.put("$keyf", keyFunction);
		}

		document.put("$reduce", reduce);

		document.put("initial", initialDocument);
		if (initial != null) {
			document.put("initial", initial);
		}
		if (finalize != null) {
			document.put("finalize", finalize);
		}
		collation.ifPresent(val -> document.append("collation", val.toDocument()));

		return document;
	}

}
