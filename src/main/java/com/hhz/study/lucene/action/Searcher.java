package com.hhz.study.lucene.action;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.google.common.base.Stopwatch;
import com.hhz.study.util.Configuration;
import com.hhz.study.util.Console;

public class Searcher {
	public static void main(String[] args) throws IOException, ParseException {
		String indexDir = Configuration.getProperty("lucene.indexDir");
		String q = "solr";
		search(indexDir, q);
	}

	public static void search(String indexDir, String q) throws IOException,
			ParseException {
		Directory dir = FSDirectory.open(new File(indexDir));
		IndexSearcher is = new IndexSearcher(DirectoryReader.open(dir));
		QueryParser parser = new QueryParser(Version.LUCENE_CURRENT,
				"contents", new StandardAnalyzer(Version.LUCENE_CURRENT));
		Query query = parser.parse(q);
		Stopwatch sw = Stopwatch.createStarted();
		TopDocs hits = is.search(query, 10);
		sw.stop();
		Console.writeTime("命中" + hits.totalHits, sw);
		for (ScoreDoc scoreDoc : hits.scoreDocs) {
			Document doc = is.doc(scoreDoc.doc);
			System.out.println(doc.get("fullpath"));
		}
	}
}
