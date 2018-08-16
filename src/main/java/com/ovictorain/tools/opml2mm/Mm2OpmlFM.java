package com.ovictorain.tools.opml2mm;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * .opmll to .mm (version 1.0.1, FreeMind)
 *
 */
public class Mm2OpmlFM {
	private final String DIRECTORY = "files/";

	public static void main(String[] args) {
		Mm2OpmlFM self = new Mm2OpmlFM();
		String fromFILE = "testFM.mm";

		try {
			Document document = self.parse(fromFILE);

			Document mmDocument = self.navigate(document);

			self.write(mmDocument);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read file to document
	 * 
	 * @param filename
	 *            文件名称
	 * @return
	 * @throws DocumentException
	 */
	public Document parse(String filename) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(DIRECTORY + filename);

		return document;
	}

	/**
	 * 根据 MM Document 生成 OPML Document
	 * 
	 * @param document
	 * @return
	 */
	public Document navigate(Document document) {
		Element root = document.getRootElement();

		String rootName = root.elementIterator("node").next().attributeValue("TEXT");

		Document opmlDocument = DocumentHelper.createDocument();
		Element mmRoot = opmlDocument.addElement("opml").addAttribute("version", "1.0");
		Element head = mmRoot.addElement("head");
		head.addElement("title").addText(rootName);

		Element body = mmRoot.addElement("body");

		// iterate through child elements of the first node's children
		for (Iterator<Element> it = root.elementIterator("node").next().elementIterator(); it.hasNext();) {
			Element element = it.next();
			Element opmlElement = navigate(element);
			body.add(opmlElement);
		}

		return opmlDocument;
	}

	/**
	 * 遍历 MM Element 生成 OPML Element
	 * 
	 * @param mmRoot
	 * @return
	 */
	public Element navigate(Element mmRoot) {
		Element opmlRoot = DocumentHelper.createElement("outline");

		String text = mmRoot.attributeValue("TEXT");
		if (text != null && !text.trim().equals("")) {
			opmlRoot.addAttribute("text", text);
		}

		Iterator<Element> richcontents = mmRoot.elementIterator("richcontent");
		if (richcontents.hasNext()) {
			String note = getNote(richcontents.next());
			opmlRoot.addAttribute("_note", note);
		}

		// Iterate through <node> child elements of root
		for (Iterator<Element> it = mmRoot.elementIterator("node"); it.hasNext();) {
			Element element = it.next();

			opmlRoot.add(navigate(element));
		}

		return opmlRoot;
	}

	/**
	 * Write document to file
	 * 
	 * @param document
	 * @throws IOException
	 */
	public void write(Document document) throws IOException {
		try (FileWriter fileWriter = new FileWriter(DIRECTORY + "output.opml")) {
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

	/**
	 * Create richcontent label used in FreeMind&FreePlane (version 1.0.1)
	 * 
	 * @param content
	 * @return
	 */
	public String getNote(Element richcontent) {
		Element body = richcontent.element("html").element("body");
		String note = "";

		for (Iterator<Element> it = body.elementIterator("p"); it.hasNext();) {
			Element element = it.next();

			note += "  " + element.getTextTrim() + "\n\r";
		}
		note = note.length() > 3 ? note.substring(0, note.length() - 2) : note;
		return note;
	}
}
