package com.hhz.study.lucene.action;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Indexer {

	public static void main(String[] args) {
		String indexDir = "";
		String dataDir = "";
		Indexer indexer = new Indexer(dataDir, new TextFilesFilter());
	}

	private IndexWriter writer;

	public Indexer(String indexDir) throws IOException {
		Directory dir = FSDirectory.open(new File(indexDir));
		IndexWriterConfig config = new IndexWriterConfig(
				Version.LUCENE_CURRENT, new StandardAnalyzer());
		writer = new IndexWriter(dir, config);
		// page 20
	}

	public Indexer(String indexDir, FileFilter filter) {

	}

	private static class TextFilesFilter implements FileFilter {

		@Override
		public boolean accept(File pathname) {
			return false;
		}

	}

}
