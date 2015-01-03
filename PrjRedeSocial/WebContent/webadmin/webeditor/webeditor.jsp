<%@ include file="language.jsp" %>
<%!

class WebEditor {
	private String out = "";
		
	WebEditor(String name, String content, String options) {
		out = "";
		out += "<script type=\"text/javascript\">" + "\r\n";
		out += "<!--" + "\r\n";
		if ((options != null) && (! options.equals(""))) {
			out += "WebEditor('" + name + "', '" + WebEditorContent(content) + "', " + options + ");" + "\r\n";
		} else {
			out += "WebEditor('" + name + "', '" + WebEditorContent(content) + "');" + "\r\n";
		}
		out += "// -->" + "\r\n";
		out += "</script>" + "\r\n";
	}
		
	public String WebEditorContent(String content) {
		if (content == null) return "";
		return content.replaceAll("\\\\", "\\\\\\\\").replaceAll("</", "<\\\\/").replaceAll("'", "\\\\'").replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n").replaceAll("\t", "\\\\t").replaceAll("script", "scr'+'ipt").replaceAll("SCRIPT", "SCR'+'IPT");
	}
		
	public String toString() {
		return "" + out;
	}

}

%>