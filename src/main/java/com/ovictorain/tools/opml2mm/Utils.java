package com.ovictorain.tools.opml2mm;

import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Utils {

	protected final static String DIRECTORY = "files/";

	/**
	 * Read file to document
	 * 
	 * @param filename
	 *            文件名称
	 * @return
	 * @throws DocumentException
	 */
	public static Document parse(String filename) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(DIRECTORY + filename);

		return document;
	}

	/**
	 * Write document to file
	 * 
	 * @param document
	 * @throws IOException
	 */
	public static void write(Document document, String filename) throws IOException {
		try (FileWriter fileWriter = new FileWriter(Utils.DIRECTORY + filename)) {
			XMLWriter writer = new XMLWriter(fileWriter);

			// Pretty print the document to file
			OutputFormat format = OutputFormat.createPrettyPrint();

			writer = new XMLWriter(System.out, format);
			writer.write(document);
			writer = new XMLWriter(fileWriter, format);
			writer.write(document);
			writer.close();
		}
	}
}
