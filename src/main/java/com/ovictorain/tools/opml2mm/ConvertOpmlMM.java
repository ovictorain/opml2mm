package com.ovictorain.tools.opml2mm;

public class ConvertOpmlMM {

	public static void main(String[] args) {
		String filename = args[0];
		if (filename == null || "".equals(filename) || filename.lastIndexOf('.') == -1) {
			System.out.println("请输入文件名称并以 .mm 或 .opml 结尾，本工具将自动生成对应文件.");
			return;
		}

		int separatorIndex = filename.lastIndexOf('.');
		String fname = filename.substring(0, separatorIndex);
		String suffix = filename.substring(separatorIndex + 1, filename.length());
		// 调用转换方法. Call Convert Method
		if ("mm".equals(suffix)) {
			new Mm2OpmlFM().convert(fname);
		} else if ("opml".equals(suffix)) {
			new Opml2MmFM().convert(fname);
		}
		
		System.out.println("Done.");
	}
}
