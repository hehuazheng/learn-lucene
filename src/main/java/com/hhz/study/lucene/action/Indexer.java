package com.hhz.study.lucene.action;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.hhz.study.util.Configuration;

public class Indexer {
	/**
	 * 给data目录建索引，建好后放在index目录中 lucene实战p20
	 */
	public static void main(String[] args) throws Exception {
		String indexDir = Configuration.getProperty("lucene.indexDir");
		String dataDir = Configuration.getProperty("lucene.dataDir");
		Indexer indexer = new Indexer(indexDir);
		int numIndexed = indexer.index(dataDir, new TextFilesFilter());
		indexer.close();
		System.out.println("indexing " + numIndexed);
	}

	private IndexWriter writer;

	public Indexer(String indexDir) throws IOException {
		Directory dir = FSDirectory.open(new File(indexDir));
		IndexWriterConfig config = new IndexWriterConfig(
				Version.LUCENE_CURRENT, new StandardAnalyzer());
		writer = new IndexWriter(dir, config);
	}

	public void close() throws IOException {
		writer.close();
	}

	public int index(String dataDir, FileFilter filter) throws Exception {
		File[] files = new File(dataDir).listFiles();
		for (File f : files) {
			if (!f.isDirectory() && !f.isHidden() && f.exists() && f.canRead()
					&& (filter == null || filter.accept(f))) {
				indexFile(f);
			}
		}
		return writer.numDocs();
	}

	private static class TextFilesFilter implements FileFilter {

		@Override
		public boolean accept(File path) {
			return path.getName().toLowerCase().endsWith(".txt");
		}
	}

	private Document getDocument(File f) throws Exception {
		Document doc = new Document();
		doc.add(new Field("contents", new FileReader(f)));
		doc.add(new Field("filename", f.getName(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field("fullpath", f.getCanonicalPath(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		return doc;
	}

	private void indexFile(File f) throws Exception {
		System.out.println("indexing " + f.getCanonicalPath());
		Document doc = getDocument(f);
		writer.addDocument(doc);
	}
}
