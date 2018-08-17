package com.ovictorain.tools.opml2mm;

import java.io.IOException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * .opml to .mm (version 1.0.1, FreeMind)
 *
 */
public class Opml2MmFM {
	String FName = "";
	String MMSuffix = ".mm";
	String OPMLSuffix = ".opml";

	public void convert(String filename) {
		this.FName = filename;

		try {
			Document document = Utils.parseWithFeedline(FName + OPMLSuffix);
			Document opmlDocument = navigate(document);

			Utils.write(opmlDocument, FName + MMSuffix);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据 OPML Document 生成 MM Document
	 * 
	 * @param document
	 * @return
	 */
	public Document navigate(Document document) {
		Element root = document.getRootElement();

		Document mmDocument = DocumentHelper.createDocument();
		Element mmRoot = mmDocument.addElement("map").addAttribute("version", "1.0.1");
		Element nodeRoot = mmRoot.addElement("node");

		String rootName = root.elementIterator("head").next().elementIterator("title").next().getText();
		Element body = root.elementIterator("body").next();
		nodeRoot.addAttribute("TEXT", rootName);

		// iterate through child elements of the body's children.
		// Can't use "nodeRoot.add(navigate(body));", because it'll create a empty
		// <node>.
		for (Iterator<Element> it = body.elementIterator(); it.hasNext();) {
			Element element = it.next();
			nodeRoot.add(navigate(element));
		}

		return mmDocument;
	}

	/**
	 * 遍历 OPML Element 生成 MM Element
	 * 
	 * @param opmlRoot
	 * @return
	 */
	public Element navigate(Element opmlRoot) {
		Element mmRoot = DocumentHelper.createElement("node");

		String text = opmlRoot.attributeValue("text");
		String _note = opmlRoot.attributeValue("_note");
		if (text != null && !text.trim().equals("")) {
			mmRoot.addAttribute("TEXT", text);
		}
		if (_note != null && !_note.trim().equals("")) {
			mmRoot.add(getRichcontent(_note));
		}

		// Iterate through child elements of root
		for (Iterator<Element> it = opmlRoot.elementIterator(); it.hasNext();) {
			Element element = it.next();

			mmRoot.add(navigate(element));
		}

		return mmRoot;
	}

	/**
	 * Create richcontent label used in FreeMind&FreePlane (version 1.0.1)
	 * 
	 * @param content
	 * @return
	 */
	@SuppressWarnings("unused")
	public Element getRichcontent(String content) {
		Element root = DocumentHelper.createElement("richcontent").addAttribute("TYPE", "NOTE");

		Element html = root.addElement("html");
		Element head = html.addElement("head");
		Element body = html.addElement("body");

		// 根据换行符 split 内容，每行对应一个 <p>
		String[] lines = content.split(Utils.CUSTOM_FEEDLINE);
		for (String line : lines) {
			body.addElement("p").addText(line);
		}

		return root;
	}
}
