package com.hhz.study.lucene;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class InMemoryMonolithDemo {

	public static void main(String[] args) throws IOException, ParseException {
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
		Directory dir = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig(
				Version.LUCENE_CURRENT, analyzer);
		IndexWriter iWriter = new IndexWriter(dir, config);
		Document doc = new Document();
		String name = "This is the text to be indexed.";
		doc.add(new Field("name", name, TextField.TYPE_STORED));
		iWriter.addDocument(doc);
		iWriter.close();

		DirectoryReader dReader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(dReader);
		QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, "name",
				analyzer);
		Query query = parser.parse("text");
		ScoreDoc[] hits = searcher.search(query, null, 1000).scoreDocs;
		for (int i = 0; i < hits.length; i++) {
			Document hitDoc = searcher.doc(hits[i].doc);
			System.out.println(hitDoc.get("name"));
		}
		dReader.close();
		dir.close();
	}

}
