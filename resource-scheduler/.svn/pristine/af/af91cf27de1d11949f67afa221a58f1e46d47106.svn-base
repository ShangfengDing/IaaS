/**
 * File: TemplateConf.java
 * Author: weed
 * Create Time: 2013-5-7
 */
package appcloud.resourcescheduler.stub.builder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author weed
 *
 */
public class TemplateConf {
	public enum PatternType {
		UPDATETIME, PACKAGE, IMPORTS, INTERFACE, CLASS, METHODS
	};

	private List<PatternType> __marks = new ArrayList<PatternType>();
	public List<String> __templateParts = new ArrayList<String>();
	private String __fileName;

	public TemplateConf(String fileName) throws IOException {
		super();
		this.__fileName = fileName;
		parseTemplate(__marks, __templateParts, _getTemplate(__fileName));
	}

	public String composing(Map<PatternType, String> contents) {
		StringBuffer content = new StringBuffer();
		int len = __marks.size();
		content.append(__templateParts.get(0));
		for (int i = 0; i < len; i++) {
			content.append(contents.get(__marks.get(i)));
			content.append(__templateParts.get(i + 1));
		}
		return content.toString();
	}

	protected String _getTemplate(String templateFile) throws IOException {
		BufferedReader brConf = new BufferedReader(new InputStreamReader(
				TemplateConf.class.getClassLoader().getResourceAsStream(
						templateFile)));

		String tempConf = null;
		StringBuilder sbReadConf = new StringBuilder();
		tempConf = brConf.readLine();
		while (tempConf != null) {
			sbReadConf.append(tempConf).append("\n");
			tempConf = brConf.readLine();
		}
		brConf.close();

		return sbReadConf.toString();
	}

	protected void parseTemplate(List<PatternType> marks,
			List<String> templateParts, String templateConf) {
		int pos = -1;
		int pre_pos = 0;
		while (true) {
			pos = templateConf.indexOf("###", pre_pos);
			if (pos == -1) {
				break;
			}
			templateParts.add(templateConf.substring(pre_pos, pos));
			if (templateConf.startsWith("###UPDATETIME###", pos)) {
				marks.add(PatternType.UPDATETIME);
				pre_pos = pos + 16;
			} else if (templateConf.startsWith("###PACKAGE###", pos)) {
				marks.add(PatternType.PACKAGE);
				pre_pos = pos + 13;
			} else if (templateConf.startsWith("###IMPORTS###", pos)) {
				marks.add(PatternType.IMPORTS);
				pre_pos = pos + 13;
			} else if (templateConf.startsWith("###INTERFACE###", pos)) {
				marks.add(PatternType.INTERFACE);
				pre_pos = pos + 15;
			} else if (templateConf.startsWith("###CLASS###", pos)) {
				marks.add(PatternType.CLASS);
				pre_pos = pos + 11;
			} else if (templateConf.startsWith("###METHODS###", pos)) {
				marks.add(PatternType.METHODS);
				pre_pos = pos + 13;
			} else {
				pre_pos = pos + 3;
			}

		}
		templateParts.add(templateConf.substring(pre_pos));
	}
}
