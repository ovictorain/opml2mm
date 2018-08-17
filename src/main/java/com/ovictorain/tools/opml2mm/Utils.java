package com.ovictorain.tools.opml2mm;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Utils {

	protected final static String DIRECTORY = "files/";
	protected final static String MINDLY_OPML_FEEDLINE_REGEX = "&#xA;";
	protected final static String CUSTOM_FEEDLINE = "NEWFL";

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
	 * Read file to document
	 * 
	 * @param filename
	 *            文件名称
	 * @return
	 * @throws DocumentException
	 */
	public static Document parseWithFeedline(String filename) throws DocumentException {
		String text = readMindlyOpml(DIRECTORY + filename);
		Document document = DocumentHelper.parseText(text);

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
			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter writer = new XMLWriter(fileWriter, format);

			// Pretty print the document to file
			writer.write(document);

			// Pretty print the document to System.out
			// writer = new XMLWriter(System.out, format);
			// writer.write(document);

			writer.close();
		}
	}

	/**
	 * read file, and replace "\n" "\r" and "&#xA;" to {@code CUSTOM_FEEDLINE},
	 * because dom4j and w3c don't recommend linefeed in attrubute. Doesn't handle
	 * "\n" in attribute yet.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static String readMindlyOpml(String filename) {
		String text = "";
		try (FileReader reader = new FileReader(filename)) {
			int length = 0;
			char[] buffer = new char[1024];

			// 读取数组中的内容
			while ((length = reader.read(buffer)) != -1) {
				// boolean findLinefeed = false;
				// mindly 的 attribute 将换行符输出为 "&#xA;"， 故可进行替换
				// for (int i = 0; i < length - 3; i++) {
				// if (buffer[i] == '&' && buffer[i + 1] == '#' && buffer[i + 2] == 'x' &&
				// buffer[i + 3] == 'A'
				// && buffer[i + 4] == ';') {
				// Arrays.fill(array, i, i+5, 'L');
				// }
				// }

				text += new String(buffer, 0, length);
			}

			text = text.replaceAll(MINDLY_OPML_FEEDLINE_REGEX, CUSTOM_FEEDLINE);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return text;
	}
}
