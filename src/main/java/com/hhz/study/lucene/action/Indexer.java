package com.hhz.study.lucene.action;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Paths;

public class Indexer {
	/**
	 * 给data目录建索引，建好后放在index目录中 lucene实战p20
	 */
	public static void main(String[] args) throws Exception {
        Directory dir = FSDirectory.open(Paths.get("searcher"));
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter iWriter = new IndexWriter(dir, config);
        Document doc = new Document();
        String name = "This is the text to be indexed.";
        doc.add(new StringField("name", name, Field.Store.YES));
        doc.add(new StringField("age", "20", Field.Store.YES));
        doc.add(new IntPoint("grade", 4));
        iWriter.addDocument(doc);
        iWriter.commit();
        iWriter.close();
        dir.close();
	}
}
