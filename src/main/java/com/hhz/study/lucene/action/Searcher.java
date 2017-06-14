package com.hhz.study.lucene.action;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public class Searcher {
	public static void main(String[] args) throws IOException, ParseException {
        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get("searcher")));
        IndexSearcher searcher = new IndexSearcher(reader);
//		QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, "name",
//				analyzer);
//		Query query = parser.parse("text");
        Analyzer analyzer = new StandardAnalyzer();
        QueryParser parser = new QueryParser("age", analyzer);
        Query query = parser.parse("age:20");
        TopDocs tfd = searcher.search(query, 1);
        ScoreDoc[] hits = tfd.scoreDocs;
        for (int i = 0; i < hits.length; i++) {
            Document hitDoc = searcher.doc(hits[i].doc);
            System.out.println(hitDoc.get("name"));
            System.out.println(hitDoc.getFields());
        }
        reader.close();
    }

}
