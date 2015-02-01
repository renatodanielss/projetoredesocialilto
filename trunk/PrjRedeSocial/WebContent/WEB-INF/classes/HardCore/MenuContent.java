package HardCore;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MenuContent {
	public static int html = 0;
	public static int dtree = 1;
	public static int ajaxdtree = 2;
	public static int jstree = 3;
	public static int outputformat = jstree;



	public static String ProductsTypes(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("contentMenu.add(menuitem++,menuitem_content_ecommerce_products_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=-&status= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("contentMenu.add(menuitem++,menuitem_content_ecommerce_products_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=-&status= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_content_ecommerce_products_types_none\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=-&status= &status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select * from producttypes order by producttype";
		Producttype producttypes = new Producttype();
		producttypes.records(db, SQL);
		while (producttypes.records(db, "")) {
			if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || producttypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
				String producttype = producttypes.getProducttype();
				if (outputformat == dtree) {
					output.append("contentMenu.add(menuitem++,menuitem_content_ecommerce_products_types,'" + producttype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(producttype) + "&status= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else if (outputformat == ajaxdtree) {
					output.append("contentMenu.add(menuitem++,menuitem_content_ecommerce_products_types,'" + producttype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(producttype) + "&status= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else {
					output.append("<li id=\"menuitem_content_ecommerce_products_types_" + producttype + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(producttype) + "&status= &status= &version= &" + Math.random() + "\">" + producttype + "</a></li>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String ProductsGroups(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("contentMenu.add(menuitem++,menuitem_content_ecommerce_products_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("contentMenu.add(menuitem++,menuitem_content_ecommerce_products_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_content_ecommerce_products_groups_none\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select * from productgroups order by productgroup";
		Productgroup productgroups = new Productgroup();
		productgroups.records(db, SQL);
		while (productgroups.records(db, "")) {
			if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || productgroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
				String productgroup = productgroups.getProductgroup();
				if (outputformat == dtree) {
					output.append("contentMenu.add(menuitem++,menuitem_content_ecommerce_products_groups,'" + productgroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup=" + URLEncoder.encode(productgroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else if (outputformat == ajaxdtree) {
					output.append("contentMenu.add(menuitem++,menuitem_content_ecommerce_products_groups,'" + productgroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup=" + URLEncoder.encode(productgroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else {
					output.append("<li id=\"menuitem_content_ecommerce_products_groups_" + productgroup + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup=" + URLEncoder.encode(productgroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + productgroup + "</a></li>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String ProductsVersions(Text text, DB db) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("contentMenu.add(menuitem++,menuitem_content_ecommerce_products_versions,'" + text.display("master") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("contentMenu.add(menuitem++,menuitem_content_ecommerce_products_versions,'" + text.display("master") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_content_ecommerce_products_versions_none\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "\">" + text.display("master") + "</a></li>" + "\r\n");
		}
		String SQL = "select distinct version from versions order by version";
		Version versions = new Version();
		versions.records(db, SQL);
		while (versions.records(db, "")) {
			String version = versions.getVersion();
			if (outputformat == dtree) {
				output.append("contentMenu.add(menuitem++,menuitem_content_ecommerce_products_versions,'" + version.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("contentMenu.add(menuitem++,menuitem_content_ecommerce_products_versions,'" + version.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_content_ecommerce_products_versions_" + version + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "\">" + version + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String PagesTypes(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("contentMenu.add(menuitem++,menuitem_content_pages_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("contentMenu.add(menuitem++,menuitem_content_pages_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_content_pages_types_none\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select * from contenttypes order by contenttype";
		Contenttype contenttypes = new Contenttype();
		contenttypes.records(db, SQL);
		if (false) {
			while (contenttypes.records(db, "")) {
				String contenttype = contenttypes.getContenttype();
				if (outputformat == dtree) {
					output.append("contentMenu.add(menuitem++,menuitem_content_pages_types,'" + contenttype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else if (outputformat == ajaxdtree) {
					output.append("contentMenu.add(menuitem++,menuitem_content_pages_types,'" + contenttype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else {
					output.append("<li id=\"menuitem_content_pages_types_" + contenttype + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &" + Math.random() + "\">" + contenttype + "</a></li>" + "\r\n");
				}
			}
		} else {
			while (contenttypes.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contenttypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contenttype = contenttypes.getContenttype();
					if (outputformat == dtree) {
						output.append("contentMenu.pid[menuitem] = menuitem_content_pages_types;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.clearCookie();contentMenu.select(\\'contentMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &" + Math.random() + "\\')\"><img alt=\"\" id=\"contentMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"contentMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.clearCookie();contentMenu.select(\\'contentMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &" + Math.random() + "\\')\">" + contenttype.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("contentMenu.pid[menuitem] = menuitem_content_pages_types;" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_content_pages_types_" + contenttype + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &" + Math.random() + "\">" + contenttype + "</a></li>" + "\r\n");
					}
				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("contentMenu.selectURLsubstring(contentMenu.getCookie('select' + 'contentMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("contentMenu.selectURLsubstring(contentMenu.getCookie('select' + 'contentMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String ajaxdtreePagesTypes(Text text, DB db, Configuration config, Session session, String selected) {
		StringBuffer output = new StringBuffer();
		String SQL = "select * from contenttypes order by contenttype";
		Contenttype contenttypes = new Contenttype();
		contenttypes.records(db, SQL);
		if (false) {
			while (contenttypes.records(db, "")) {
				String contenttype = contenttypes.getContenttype();
			}
		} else {
			while (contenttypes.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contenttypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contenttype = contenttypes.getContenttype();
					String classname = "node";
					if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype).replaceAll("\\+", " ") + "&status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=" + contenttype + "&status= &version= &")) {
						classname = "nodeSel";
					}
					output.append("<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.clearCookie();contentMenu.select('contentMenu','+menuitem+','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &')\"><img alt=\"\" id=\"contentMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"contentMenuLink'+menuitem+'\" class=\"" + classname + "\" href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.clearCookie();contentMenu.select('contentMenu','+menuitem+','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &')\">" + contenttype + "</a><br></nobr>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String PagesGroups(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("contentMenu.add(menuitem++,menuitem_content_pages_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("contentMenu.add(menuitem++,menuitem_content_pages_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_content_pages_groups_none\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select * from contentgroups order by contentgroup";
		Contentgroup contentgroups = new Contentgroup();
 		contentgroups.records(db, SQL);
		if (false) {
			while (contentgroups.records(db, "")) {
				String contentgroup = contentgroups.getContentgroup();
				if (outputformat == dtree) {
					output.append("contentMenu.add(menuitem++,menuitem_content_pages_groups,'" + contentgroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else if (outputformat == ajaxdtree) {
					output.append("contentMenu.add(menuitem++,menuitem_content_pages_groups,'" + contentgroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else {
					output.append("<li id=\"menuitem_content_pages_groups_" + contentgroup + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + contentgroup + "</a></li>" + "\r\n");
				}
			}
		} else {
			while (contentgroups.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contentgroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contentgroup = contentgroups.getContentgroup();
					if (outputformat == dtree) {
						output.append("contentMenu.pid[menuitem] = menuitem_content_pages_groups;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.clearCookie();contentMenu.select(\\'contentMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &" + Math.random() + "\\')\"><img alt=\"\" id=\"contentMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"contentMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.clearCookie();contentMenu.select(\\'contentMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &" + Math.random() + "\\')\">" + contentgroup.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("contentMenu.pid[menuitem] = menuitem_content_pages_groups;" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_content_pages_groups_" + contentgroup + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + contentgroup + "</a></li>" + "\r\n");
					}
				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("contentMenu.selectURLsubstring(contentMenu.getCookie('select' + 'contentMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("contentMenu.selectURLsubstring(contentMenu.getCookie('select' + 'contentMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String ajaxdtreePagesGroups(Text text, DB db, Configuration config, Session session, String selected) {
		StringBuffer output = new StringBuffer();
		String SQL = "select * from contentgroups order by contentgroup";
		Contentgroup contentgroups = new Contentgroup();
		contentgroups.records(db, SQL);
		if (false) {
			while (contentgroups.records(db, "")) {
				String contentgroup = contentgroups.getContentgroup();
			}
		} else {
			while (contentgroups.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contentgroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contentgroup = contentgroups.getContentgroup();
					String classname = "node";
					if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup).replaceAll("\\+", " ") + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup=" + contentgroup + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					}
					output.append("<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.clearCookie();contentMenu.select('contentMenu','+menuitem+','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &')\"><img alt=\"\" id=\"contentMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"contentMenuLink'+menuitem+'\" class=\"" + classname + "\" href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.clearCookie();contentMenu.select('contentMenu','+menuitem+','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &')\">" + contentgroup + "</a><br></nobr>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String PagesVersions(Text text, DB db) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("contentMenu.add(menuitem++,menuitem_content_pages_versions,'" + text.display("master") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("contentMenu.add(menuitem++,menuitem_content_pages_versions,'" + text.display("master") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_content_pages_versions_none\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "\">" + text.display("master") + "</a></li>" + "\r\n");
		}
		String SQL = "select distinct version from versions order by version";
		Version versions = new Version();
		versions.records(db, SQL);
		while (versions.records(db, "")) {
			String version = versions.getVersion();
			if (outputformat == dtree) {
				output.append("contentMenu.add(menuitem++,menuitem_content_pages_versions,'" + version.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("contentMenu.add(menuitem++,menuitem_content_pages_versions,'" + version.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_content_pages_versions_" + version + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "\">" + version + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String PagesWorkflows(Text text, DB db) {
		StringBuffer output = new StringBuffer();
		String SQL = "select distinct tostate from workflow where " + db.is_not_blank("tostate") + " order by tostate";
		Workflow workflows = new Workflow(text);
		workflows.records(db, SQL);
		while (workflows.records(db, "")) {
			String status = workflows.getToState();
			if (outputformat == dtree) {
				output.append("contentMenu.add(menuitem++,menuitem_content_pages_status,'" + status.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype= &status=" + URLEncoder.encode(status) + "&version= &" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("contentMenu.add(menuitem++,menuitem_content_pages_status,'" + status.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype= &status=" + URLEncoder.encode(status) + "&version= &" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_content_pages_status_" + status + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype= &status=" + URLEncoder.encode(status) + "&version= &" + Math.random() + "\">" + status + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String RelationsPagesTypes(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("contentRelationsMenu.add(menuitem++,menuitem_content_pages_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("contentRelationsMenu.add(menuitem++,menuitem_content_pages_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_content_pages_types_none\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select * from contenttypes order by contenttype";
		Contenttype contenttypes = new Contenttype();
		contenttypes.records(db, SQL);
		if (false) {
			while (contenttypes.records(db, "")) {
				String contenttype = contenttypes.getContenttype();
				if (outputformat == dtree) {
					output.append("contentRelationsMenu.add(menuitem++,menuitem_content_pages_types,'" + contenttype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else if (outputformat == ajaxdtree) {
					output.append("contentRelationsMenu.add(menuitem++,menuitem_content_pages_types,'" + contenttype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else {
					output.append("<li id=\"menuitem_content_pages_types_" + contenttype + "\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &" + Math.random() + "\">" + contenttype + "</a></li>" + "\r\n");
				}
			}
		} else {
			String image;
			if ((! config.get(db,"use_versions").equals("")) || (! config.get(db,"use_checkout").equals("none")) || (! config.get(db,"use_publish").equals("auto-on-save"))) {
				image = "line";
			} else {
				image = "empty";
			}
			while (contenttypes.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contenttypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contenttype = contenttypes.getContenttype();
					if (outputformat == dtree) {
						output.append("contentRelationsMenu.pid[menuitem] = menuitem_content_pages_types;" + "\r\n");
						output.append("contentRelationsMenu.indent[menuitem] = 2;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/empty.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/" + image + ".gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.select(\\'contentRelationsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &\\');if(contentMenu)contentMenu.clearCookie();\"><img alt=\"\" id=\"contentRelationsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"contentRelationsMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.select(\\'contentRelationsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &\\');if(contentMenu)contentMenu.clearCookie();\">" + contenttype.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("contentRelationsMenu.pid[menuitem] = menuitem_content_pages_types;" + "\r\n");
						output.append("contentRelationsMenu.indent[menuitem] = 2;" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_content_pages_types_" + contenttype + "\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &" + Math.random() + "\">" + contenttype + "</a></li>" + "\r\n");
					}
				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("contentRelationsMenu.selectURLsubstring(contentRelationsMenu.getCookie('select' + 'contentRelationsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("contentRelationsMenu.selectURLsubstring(contentRelationsMenu.getCookie('select' + 'contentRelationsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String ajaxdtreeRelationsPagesTypes(Text text, DB db, Configuration config, Session session, String selected) {
		StringBuffer output = new StringBuffer();
		String SQL = "select * from contenttypes order by contenttype";
		Contenttype contenttypes = new Contenttype();
		contenttypes.records(db, SQL);
		if (false) {
			while (contenttypes.records(db, "")) {
				String contenttype = contenttypes.getContenttype();
			}
		} else {
			String image = "";
			if ((! config.get(db,"use_contenttypes").equals("")) || (! config.get(db,"use_versions").equals("")) || (! config.get(db,"use_checkout").equals("none")) || (! config.get(db,"use_publish").equals("auto-on-save"))) {
				image = "line";
			} else {
				image = "empty";
			}
			while (contenttypes.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contenttypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contenttype = contenttypes.getContenttype();
					String classname = "node";
					if (selected.equals("/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype).replaceAll("\\+", " ") + "&status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype=" + contenttype + "&status= &version= &")) {
						classname = "nodeSel";
					}
					output.append("<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/empty.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/" + image + ".gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.select('contentRelationsMenu','+menuitem+','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &');if(contentMenu)contentMenu.clearCookie();\"><img alt=\"\" id=\"contentRelationsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"contentRelationsMenuLink'+menuitem+'\" class=\"" + classname + "\" href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.select('contentRelationsMenu','+menuitem+','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &');if(contentMenu)contentMenu.clearCookie();\">" + contenttype + "</a><br></nobr>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String RelationsPagesGroups(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("contentRelationsMenu.add(menuitem++,menuitem_content_pages_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("contentRelationsMenu.add(menuitem++,menuitem_content_pages_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_content_pages_groups_none\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select * from contentgroups order by contentgroup";
		Contentgroup contentgroups = new Contentgroup();
		contentgroups.records(db, SQL);
		if (false) {
			while (contentgroups.records(db, "")) {
				String contentgroup = contentgroups.getContentgroup();
				if (outputformat == dtree) {
					output.append("contentRelationsMenu.add(menuitem++,menuitem_content_pages_groups,'" + contentgroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else if (outputformat == ajaxdtree) {
					output.append("contentRelationsMenu.add(menuitem++,menuitem_content_pages_groups,'" + contentgroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else {
					output.append("<li id=\"menuitem_content_pages_groups_" + contentgroup + "\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + contentgroup + "</a></li>" + "\r\n");
				}
			}
		} else {
			String image = "";
			if ((! config.get(db,"use_contenttypes").equals("")) || (! config.get(db,"use_versions").equals("")) || (! config.get(db,"use_checkout").equals("none")) || (! config.get(db,"use_publish").equals("auto-on-save"))) {
				image = "line";
			} else {
				image = "empty";
			}
			while (contentgroups.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contentgroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contentgroup = contentgroups.getContentgroup();
					if (outputformat == dtree) {
						output.append("contentRelationsMenu.pid[menuitem] = menuitem_content_pages_groups;" + "\r\n");
						output.append("contentRelationsMenu.indent[menuitem] = 2;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/empty.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/" + image + ".gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.select(\\'contentRelationsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &\\');if(contentMenu)contentMenu.clearCookie();\"><img alt=\"\" id=\"contentRelationsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"contentRelationsMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.select(\\'contentRelationsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &\\');if(contentMenu)contentMenu.clearCookie();\">" + contentgroup.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("contentRelationsMenu.pid[menuitem] = menuitem_content_pages_groups;" + "\r\n");
						output.append("contentRelationsMenu.indent[menuitem] = 2;" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_content_pages_groups_" + contentgroup + "\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + contentgroup + "</a></li>" + "\r\n");
					}
				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("contentRelationsMenu.selectURLsubstring(contentRelationsMenu.getCookie('select' + 'contentRelationsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("contentRelationsMenu.selectURLsubstring(contentRelationsMenu.getCookie('select' + 'contentRelationsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String ajaxdtreeRelationsPagesGroups(Text text, DB db, Configuration config, Session session, String selected) {
		StringBuffer output = new StringBuffer();
		String SQL = "select * from contentgroups order by contentgroup";
		Contentgroup contentgroups = new Contentgroup();
		contentgroups.records(db, SQL);
		if (false) {
			while (contentgroups.records(db, "")) {
				String contentgroup = contentgroups.getContentgroup();
			}
		} else {
			String image = "";
			if ((! config.get(db,"use_contenttypes").equals("")) || (! config.get(db,"use_versions").equals("")) || (! config.get(db,"use_checkout").equals("none")) || (! config.get(db,"use_publish").equals("auto-on-save"))) {
				image = "line";
			} else {
				image = "empty";
			}
			while (contentgroups.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contentgroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contentgroup = contentgroups.getContentgroup();
					String classname = "node";
					if (selected.equals("/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup).replaceAll("\\+", " ") + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup=" + contentgroup + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					}
					output.append("<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/empty.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/" + image + ".gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.select('contentRelationsMenu','+menuitem+','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &');if(contentMenu)contentMenu.clearCookie();\"><img alt=\"\" id=\"contentRelationsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"contentRelationsMenuLink'+menuitem+'\" class=\"" + classname + "\" href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.select('contentRelationsMenu','+menuitem+','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &');if(contentMenu)contentMenu.clearCookie();\">" + contentgroup + "</a><br></nobr>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String RelationsPagesVersions(Text text, DB db) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("contentRelationsMenu.add(menuitem++,menuitem_content_pages_versions,'" + text.display("master") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("contentRelationsMenu.add(menuitem++,menuitem_content_pages_versions,'" + text.display("master") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_content_pages_versions_none\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "\">" + text.display("master") + "</a></li>" + "\r\n");
		}
		String SQL = "select distinct version from versions order by version";
		Version versions = new Version();
		versions.records(db, SQL);
		while (versions.records(db, "")) {
			String version = versions.getVersion();
			if (outputformat == dtree) {
				output.append("contentRelationsMenu.add(menuitem++,menuitem_content_pages_versions,'" + version.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("contentRelationsMenu.add(menuitem++,menuitem_content_pages_versions,'" + version.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_content_pages_versions_" + version + "\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "\">" + version + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String RelationsPagesWorkflows(Text text, DB db) {
		StringBuffer output = new StringBuffer();
		String SQL = "select distinct tostate from workflow where " + db.is_not_blank("tostate") + " order by tostate";
		Workflow workflows = new Workflow(text);
		workflows.records(db, SQL);
		while (workflows.records(db, "")) {
			String status = workflows.getToState();
			if (outputformat == dtree) {
				output.append("contentRelationsMenu.add(menuitem++,menuitem_content_pages_status,'" + status.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype= &status=" + URLEncoder.encode(status) + "&version= &" + Math.random() + "','','',false,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("contentRelationsMenu.add(menuitem++,menuitem_content_pages_status,'" + status.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype= &status=" + URLEncoder.encode(status) + "&version= &" + Math.random() + "','','',false,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_content_pages_status_" + status + "\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype= &status=" + URLEncoder.encode(status) + "&version= &" + Math.random() + "\">" + status + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String RelationsProductsTypes(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("contentRelationsMenu.add(menuitem++,menuitem_content_products_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("contentRelationsMenu.add(menuitem++,menuitem_content_products_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_content_products_types_none\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select * from producttypes order by producttype";
		Producttype producttypes = new Producttype();
		producttypes.records(db, SQL);
		if (false) {
			while (producttypes.records(db, "")) {
				String producttype = producttypes.getProducttype();
				if (outputformat == dtree) {
					output.append("contentRelationsMenu.add(menuitem++,menuitem_content_products_types,'" + producttype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(producttype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else if (outputformat == ajaxdtree) {
					output.append("contentRelationsMenu.add(menuitem++,menuitem_content_products_types,'" + producttype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(producttype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else {
					output.append("<li id=\"menuitem_content_products_types_" + producttype + "\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(producttype) + "&status= &version= &" + Math.random() + "\">" + producttype + "</a></li>" + "\r\n");
				}
			}
		} else {
			String image;
			if ((! config.get(db,"use_versions").equals("")) || (! config.get(db,"use_checkout").equals("none")) || (! config.get(db,"use_publish").equals("auto-on-save"))) {
				image = "line";
			} else {
				image = "empty";
			}
			while (producttypes.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || producttypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String producttype = producttypes.getProducttype();
					if (outputformat == dtree) {
						output.append("contentRelationsMenu.pid[menuitem] = menuitem_content_products_types;" + "\r\n");
						output.append("contentRelationsMenu.indent[menuitem] = 2;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/empty.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/" + image + ".gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(producttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.select(\\'contentRelationsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(producttype) + "&status= &version= &" + Math.random() + "\\');if(contentMenu)contentMenu.clearCookie();\"><img alt=\"\" id=\"contentRelationsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"contentRelationsMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(producttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.select(\\'contentRelationsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(producttype) + "&status= &version= &" + Math.random() + "\\');if(contentMenu)contentMenu.clearCookie();\">" + producttype.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("contentRelationsMenu.pid[menuitem] = menuitem_content_products_types;" + "\r\n");
						output.append("contentRelationsMenu.indent[menuitem] = 2;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/empty.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/" + image + ".gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(producttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.select(\\'contentRelationsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(producttype) + "&status= &version= &" + Math.random() + "\\');if(contentMenu)contentMenu.clearCookie();\"><img alt=\"\" id=\"contentRelationsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"contentRelationsMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(producttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.select(\\'contentRelationsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(producttype) + "&status= &version= &" + Math.random() + "\\');if(contentMenu)contentMenu.clearCookie();\">" + producttype.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_content_products_types_" + producttype + "\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(producttype) + "&status= &version= &" + Math.random() + "\">" + producttype + "</a></li>" + "\r\n");
					}
				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("contentRelationsMenu.selectURLsubstring(contentRelationsMenu.getCookie('select' + 'contentRelationsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("contentRelationsMenu.selectURLsubstring(contentRelationsMenu.getCookie('select' + 'contentRelationsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String RelationsProductsGroups(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("contentRelationsMenu.add(menuitem++,menuitem_content_products_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("contentRelationsMenu.add(menuitem++,menuitem_content_products_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_content_products_groups_none\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select * from productgroups order by productgroup";
		Productgroup productgroups = new Productgroup();
		productgroups.records(db, SQL);
		if (false) {
			while (productgroups.records(db, "")) {
				String productgroup = productgroups.getProductgroup();
				if (outputformat == dtree) {
					output.append("contentRelationsMenu.add(menuitem++,menuitem_content_products_groups,'" + productgroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup=" + URLEncoder.encode(productgroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else if (outputformat == ajaxdtree) {
					output.append("contentRelationsMenu.add(menuitem++,menuitem_content_products_groups,'" + productgroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup=" + URLEncoder.encode(productgroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else {
					output.append("<li id=\"menuitem_content_products_groups_" + productgroup + "\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup=" + URLEncoder.encode(productgroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + productgroup + "</a></li>" + "\r\n");
				}
			}
		} else {
			String image = "";
			if ((! config.get(db,"use_contenttypes").equals("")) || (! config.get(db,"use_versions").equals("")) || (! config.get(db,"use_checkout").equals("none")) || (! config.get(db,"use_publish").equals("auto-on-save"))) {
				image = "line";
			} else {
				image = "empty";
			}
			while (productgroups.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || productgroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String productgroup = productgroups.getProductgroup();
					if (outputformat == dtree) {
						output.append("contentRelationsMenu.pid[menuitem] = menuitem_content_products_groups;" + "\r\n");
						output.append("contentRelationsMenu.indent[menuitem] = 2;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/empty.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/" + image + ".gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&amp;contentgroup=" + URLEncoder.encode(productgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.select(\\'contentRelationsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup=" + URLEncoder.encode(productgroup) + "&contenttype= &status= &version= &" + Math.random() + "\\');if(contentMenu)contentMenu.clearCookie();\"><img alt=\"\" id=\"contentRelationsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"contentRelationsMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&amp;contentgroup=" + URLEncoder.encode(productgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.select(\\'contentRelationsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup=" + URLEncoder.encode(productgroup) + "&contenttype= &status= &version= &" + Math.random() + "\\');if(contentMenu)contentMenu.clearCookie();\">" + productgroup.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("contentRelationsMenu.pid[menuitem] = menuitem_content_products_groups;" + "\r\n");
						output.append("contentRelationsMenu.indent[menuitem] = 2;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/empty.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/" + image + ".gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&amp;contentgroup=" + URLEncoder.encode(productgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.select(\\'contentRelationsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup=" + URLEncoder.encode(productgroup) + "&contenttype= &status= &version= &" + Math.random() + "\\');if(contentMenu)contentMenu.clearCookie();\"><img alt=\"\" id=\"contentRelationsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"contentRelationsMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&amp;contentgroup=" + URLEncoder.encode(productgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.select(\\'contentRelationsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup=" + URLEncoder.encode(productgroup) + "&contenttype= &status= &version= &" + Math.random() + "\\');if(contentMenu)contentMenu.clearCookie();\">" + productgroup.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_content_products_groups_" + productgroup + "\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup=" + URLEncoder.encode(productgroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + productgroup + "</a></li>" + "\r\n");
					}
				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("contentRelationsMenu.selectURLsubstring(contentRelationsMenu.getCookie('select' + 'contentRelationsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("contentRelationsMenu.selectURLsubstring(contentRelationsMenu.getCookie('select' + 'contentRelationsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String RelationsProductsVersions(Text text, DB db) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("contentRelationsMenu.add(menuitem++,menuitem_content_products_versions,'" + text.display("master") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("contentRelationsMenu.add(menuitem++,menuitem_content_products_versions,'" + text.display("master") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_content_products_versions_none\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "\">" + text.display("master") + "</a></li>" + "\r\n");
		}
		String SQL = "select distinct version from versions order by version";
		Version versions = new Version();
		versions.records(db, SQL);
		while (versions.records(db, "")) {
			String version = versions.getVersion();
			if (outputformat == dtree) {
				output.append("contentRelationsMenu.add(menuitem++,menuitem_content_products_versions,'" + version.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("contentRelationsMenu.add(menuitem++,menuitem_content_products_versions,'" + version.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_content_products_versions_" + version + "\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=product&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "\">" + version + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String RelationsProductsWorkflows(Text text, DB db) {
		StringBuffer output = new StringBuffer();
		String SQL = "select distinct tostate from workflow where " + db.is_not_blank("tostate") + " order by tostate";
		Workflow workflows = new Workflow(text);
		workflows.records(db, SQL);
		while (workflows.records(db, "")) {
			String status = workflows.getToState();
			if (outputformat == dtree) {
				output.append("contentRelationsMenu.add(menuitem++,menuitem_content_pages_status,'" + status.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype= &status=" + URLEncoder.encode(status) + "&version= &" + Math.random() + "','','',false,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("contentRelationsMenu.add(menuitem++,menuitem_content_pages_status,'" + status.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype= &status=" + URLEncoder.encode(status) + "&version= &" + Math.random() + "','','',false,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_content_pages_status_" + status + "\"><a href=\"/" + text.display("adminpath") + "/content/relations.jsp?menu=relations&contentclass=page&contentgroup= &contenttype= &status=" + URLEncoder.encode(status) + "&version= &" + Math.random() + "\">" + status + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String Packages(String menu, String menuitem, String contentclass, String contentgroup, String contenttype, String status, String version, Text text, DB db) {
		StringBuffer output = new StringBuffer();
		Content content = new Content();
		ArrayList contentpackages = content.getContentpackages(db);
		for (int i=0; i<contentpackages.size(); i++) {
			String contentpackage = "" + contentpackages.get(i);
			if (outputformat == dtree) {
				output.append("" + menu + ".add(menuitem++," + menuitem + ",'" + contentpackage.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage=" + URLEncoder.encode(contentpackage) + "&contentclass=" + URLEncoder.encode(contentclass) + "&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype=" + URLEncoder.encode(contenttype) + "&status=" + URLEncoder.encode(status) + "&version=" + URLEncoder.encode(version) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("" + menu + ".add(menuitem++," + menuitem + ",'" + contentpackage.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage=" + URLEncoder.encode(contentpackage) + "&contentclass=" + URLEncoder.encode(contentclass) + "&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype=" + URLEncoder.encode(contenttype) + "&status=" + URLEncoder.encode(status) + "&version=" + URLEncoder.encode(version) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_content_packages_" + contentpackage + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage=" + URLEncoder.encode(contentpackage) + "&contentclass=" + URLEncoder.encode(contentclass) + "&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype=" + URLEncoder.encode(contenttype) + "&status=" + URLEncoder.encode(status) + "&version=" + URLEncoder.encode(version) + "&" + Math.random() + "\">" + contentpackage + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String Elements(Text text, DB db) {
		StringBuffer output = new StringBuffer();
		String SQL = "select distinct element from elements order by element";
		Element elements = new Element();
		elements.records(db, SQL);
		while (elements.records(db, "")) {
			String element = elements.getElement();
			if (outputformat == dtree) {
				output.append("contentMenu.add(menuitem++,menuitem_content_elements_classes,'" + element.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=" + URLEncoder.encode(element) + "&contentgroup= &contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("contentMenu.add(menuitem++,menuitem_content_elements_classes,'" + element.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=" + URLEncoder.encode(element) + "&contentgroup= &contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_content_elements_classes_" + element + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=" + URLEncoder.encode(element) + "&contentgroup= &contenttype= &status= &version= &" + Math.random() + "\">" + element + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String ElementsTypes(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("contentMenu.add(menuitem++,menuitem_content_elements_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("contentMenu.add(menuitem++,menuitem_content_elements_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_content_elements_types_none\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select * from contenttypes order by contenttype";
		Contenttype contenttypes = new Contenttype();
		contenttypes.records(db, SQL);
		if (false) {
			while (contenttypes.records(db, "")) {
				String contenttype = contenttypes.getContenttype();
				if (outputformat == dtree) {
					output.append("contentMenu.add(menuitem++,menuitem_content_elements_types,'" + contenttype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else if (outputformat == ajaxdtree) {
					output.append("contentMenu.add(menuitem++,menuitem_content_elements_types,'" + contenttype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else {
					output.append("<li id=\"menuitem_content_elements_types_" + contenttype + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &" + Math.random() + "\">" + contenttype + "</a></li>" + "\r\n");
				}
			}
		} else {
			while (contenttypes.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contenttypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contenttype = contenttypes.getContenttype();
					if (outputformat == dtree) {
						output.append("contentMenu.pid[menuitem] = menuitem_content_elements_types;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.clearCookie();contentMenu.select(\\'contentMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &" + Math.random() + "\\')\"><img alt=\"\" id=\"contentMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"contentMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.clearCookie();contentMenu.select(\\'contentMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &" + Math.random() + "\\')\">" + contenttype.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("contentMenu.pid[menuitem] = menuitem_content_elements_types;" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_content_elements_types_" + contenttype + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &" + Math.random() + "\">" + contenttype + "</a></li>" + "\r\n");
					}
				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("contentMenu.selectURLsubstring(contentMenu.getCookie('select' + 'contentMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("contentMenu.selectURLsubstring(contentMenu.getCookie('select' + 'contentMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String ajaxdtreeElementsTypes(Text text, DB db, Configuration config, Session session, String selected) {
		StringBuffer output = new StringBuffer();
		String SQL = "select * from contenttypes order by contenttype";
		Contenttype contenttypes = new Contenttype();
		contenttypes.records(db, SQL);
		if (false) {
			while (contenttypes.records(db, "")) {
				String contenttype = contenttypes.getContenttype();
			}
		} else {
			while (contenttypes.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contenttypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contenttype = contenttypes.getContenttype();
					String classname = "node";
					if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup= &contenttype=" + URLEncoder.encode(contenttype).replaceAll("\\+", " ") + "&status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup= &contenttype=" + contenttype + "&status= &version= &")) {
						classname = "nodeSel";
					}
					output.append("<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.clearCookie();contentMenu.select('contentMenu','+menuitem+','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &')\"><img alt=\"\" id=\"contentMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"contentMenuLink'+menuitem+'\" class=\"" + classname + "\" href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.clearCookie();contentMenu.select('contentMenu','+menuitem+','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &')\">" + contenttype + "</a><br></nobr>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String ElementsGroups(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("contentMenu.add(menuitem++,menuitem_content_elements_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("contentMenu.add(menuitem++,menuitem_content_elements_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_content_elements_groups_none\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select * from contentgroups order by contentgroup";
		Contentgroup contentgroups = new Contentgroup();
		contentgroups.records(db, SQL);
		if (false) {
			while (contentgroups.records(db, "")) {
				String contentgroup = contentgroups.getContentgroup();
				if (outputformat == dtree) {
					output.append("contentMenu.add(menuitem++,menuitem_content_elements_groups,'" + contentgroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else if (outputformat == ajaxdtree) {
					output.append("contentMenu.add(menuitem++,menuitem_content_elements_groups,'" + contentgroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else {
					output.append("<li id=\"menuitem_content_elements_groups_" + contentgroup + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + contentgroup + "</a></li>" + "\r\n");
				}
			}
		} else {
			while (contentgroups.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contentgroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contentgroup = contentgroups.getContentgroup();
					if (outputformat == dtree) {
						output.append("contentMenu.pid[menuitem] = menuitem_content_elements_groups;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.clearCookie();contentMenu.select(\\'contentMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &" + Math.random() + "\\')\"><img alt=\"\" id=\"contentMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"contentMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.clearCookie();contentMenu.select(\\'contentMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &" + Math.random() + "\\')\">" + contentgroup.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("contentMenu.pid[menuitem] = menuitem_content_elements_groups;" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_content_elements_groups_" + contentgroup + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + contentgroup + "</a></li>" + "\r\n");
					}
				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("contentMenu.selectURLsubstring(contentMenu.getCookie('select' + 'contentMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("contentMenu.selectURLsubstring(contentMenu.getCookie('select' + 'contentMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String ajaxdtreeElementsGroups(Text text, DB db, Configuration config, Session session, String selected) {
		StringBuffer output = new StringBuffer();
		String SQL = "select * from contentgroups order by contentgroup";
		Contentgroup contentgroups = new Contentgroup();
		contentgroups.records(db, SQL);
		if (false) {
			while (contentgroups.records(db, "")) {
				String contentgroup = contentgroups.getContentgroup();
			}
		} else {
			while (contentgroups.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contentgroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contentgroup = contentgroups.getContentgroup();
					String classname = "node";
					if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup=" + URLEncoder.encode(contentgroup).replaceAll("\\+", " ") + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup=" + contentgroup + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					}
					output.append("<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.clearCookie();contentMenu.select('contentMenu','+menuitem+','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &')\"><img alt=\"\" id=\"contentMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"contentMenuLink'+menuitem+'\" class=\"" + classname + "\" href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"contentRelationsMenu.clearCookie();contentMenu.select('contentMenu','+menuitem+','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &')\">" + contentgroup + "</a><br></nobr>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String ElementsVersions(Text text, DB db) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("contentMenu.add(menuitem++,menuitem_content_elements_versions,'" + text.display("master") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("contentMenu.add(menuitem++,menuitem_content_elements_versions,'" + text.display("master") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_content_elements_versions_none\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "\">" + text.display("master") + "</a></li>" + "\r\n");
		}
		String SQL = "select distinct version from versions order by version";
		Version versions = new Version();
		versions.records(db, SQL);
		while (versions.records(db, "")) {
			String version = versions.getVersion();
			if (outputformat == dtree) {
				output.append("contentMenu.add(menuitem++,menuitem_content_elements_versions,'" + version.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("contentMenu.add(menuitem++,menuitem_content_elements_versions,'" + version.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_content_elements_versions_" + version + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "\">" + version + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String ElementsWorkflows(Text text, DB db) {
		StringBuffer output = new StringBuffer();
		String SQL = "select distinct tostate from workflow where " + db.is_not_blank("tostate") + " order by tostate";
		Workflow workflows = new Workflow(text);
		workflows.records(db, SQL);
		while (workflows.records(db, "")) {
			String status = workflows.getToState();
			if (outputformat == dtree) {
				output.append("contentMenu.add(menuitem++,menuitem_content_elements_status,'" + status.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup= &contenttype= &status=" + URLEncoder.encode(status) + "&version= &" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("contentMenu.add(menuitem++,menuitem_content_elements_status,'" + status.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup= &contenttype= &status=" + URLEncoder.encode(status) + "&version= &" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_content_elements_status_" + status + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=element&contentgroup= &contenttype= &status=" + URLEncoder.encode(status) + "&version= &" + Math.random() + "\">" + status + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String ImagesTypes(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("libraryMenu.add(menuitem++,menuitem_library_images_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("libraryMenu.add(menuitem++,menuitem_library_images_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_library_images_types_none\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select * from imagetypes order by imagetype";
		Imagetype imagetypes = new Imagetype();
		imagetypes.records(db, SQL);
		if (false) {
			while (imagetypes.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || imagetypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String imagetype = imagetypes.getImagetype();
					if (outputformat == dtree) {
						output.append("libraryMenu.add(menuitem++,menuitem_library_images_types,'" + imagetype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=" + URLEncoder.encode(imagetype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("libraryMenu.add(menuitem++,menuitem_library_images_types,'" + imagetype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=" + URLEncoder.encode(imagetype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_library_images_types_" + imagetype + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=" + URLEncoder.encode(imagetype) + "&status= &version= &" + Math.random() + "\">" + imagetype + "</a></li>" + "\r\n");
					}
				}
			}
		} else {
			while (imagetypes.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || imagetypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String imagetype = imagetypes.getImagetype();
					if (outputformat == dtree) {
						output.append("libraryMenu.pid[menuitem] = menuitem_library_images_types;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(imagetype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"libraryMenu.select(\\'libraryMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=" + URLEncoder.encode(imagetype) + "&status= &version= &" + Math.random() + "\\')\"><img alt=\"\" id=\"libraryMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"libraryMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(imagetype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"libraryMenu.select(\\'libraryMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=" + URLEncoder.encode(imagetype) + "&status= &version= &" + Math.random() + "\\')\">" + imagetype.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("libraryMenu.pid[menuitem] = menuitem_library_images_types;" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_library_images_types_" + imagetype + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=" + URLEncoder.encode(imagetype) + "&status= &version= &" + Math.random() + "\">" + imagetype + "</a></li>" + "\r\n");
					}
				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("libraryMenu.selectURLsubstring(libraryMenu.getCookie('select' + 'libraryMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("libraryMenu.selectURLsubstring(libraryMenu.getCookie('select' + 'libraryMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String ajaxdtreeImagesTypes(Text text, DB db, Configuration config, Session session, String selected) {
		StringBuffer output = new StringBuffer();
		String SQL = "select * from imagetypes order by imagetype";
		Imagetype imagetypes = new Imagetype();
		imagetypes.records(db, SQL);
		if (false) {
			while (imagetypes.records(db, "")) {
				String imagetype = imagetypes.getImagetype();
			}
		} else {
			while (imagetypes.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || imagetypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String imagetype = imagetypes.getImagetype();
					String classname = "node";
					if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=" + URLEncoder.encode(imagetype) + "&status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=" + URLEncoder.encode(imagetype).replaceAll("\\+", " ") + "&status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=" + imagetype + "&status= &version= &")) {
						classname = "nodeSel";
					}
					output.append("<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(imagetype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"libraryMenu.select('libraryMenu','+menuitem+','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=" + URLEncoder.encode(imagetype) + "&status= &version= &')\"><img alt=\"\" id=\"libraryMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"libraryMenuLink'+menuitem+'\" class=\"" + classname + "\" href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(imagetype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"libraryMenu.select('libraryMenu','+menuitem+','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=" + URLEncoder.encode(imagetype) + "&status= &version= &')\">" + imagetype + "</a><br></nobr>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String ImagesGroups(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("libraryMenu.add(menuitem++,menuitem_library_images_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("libraryMenu.add(menuitem++,menuitem_library_images_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_library_images_groups_none\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select * from imagegroups order by imagegroup";
		Imagegroup imagegroups = new Imagegroup();
		imagegroups.records(db, SQL);
		if (false) {
			while (imagegroups.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || imagegroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String imagegroup = imagegroups.getImagegroup();
					if (outputformat == dtree) {
						output.append("libraryMenu.add(menuitem++,menuitem_library_images_groups,'" + imagegroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup=" + URLEncoder.encode(imagegroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("libraryMenu.add(menuitem++,menuitem_library_images_groups,'" + imagegroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup=" + URLEncoder.encode(imagegroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_library_images_groups_" + imagegroup + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup=" + URLEncoder.encode(imagegroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + imagegroup + "</a></li>" + "\r\n");
					}
				}
			}
		} else {
			while (imagegroups.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || imagegroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String imagegroup = imagegroups.getImagegroup();
					if (outputformat == dtree) {
						output.append("libraryMenu.pid[menuitem] = menuitem_library_images_groups;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&amp;contentgroup=" + URLEncoder.encode(imagegroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"libraryMenu.select(\\'libraryMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup=" + URLEncoder.encode(imagegroup) + "&contenttype= &status= &version= &" + Math.random() + "\\')\"><img alt=\"\" id=\"libraryMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"libraryMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&amp;contentgroup=" + URLEncoder.encode(imagegroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"libraryMenu.select(\\'libraryMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup=" + URLEncoder.encode(imagegroup) + "&contenttype= &status= &version= &" + Math.random() + "\\')\">" + imagegroup.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("libraryMenu.pid[menuitem] = menuitem_library_images_groups;" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_library_images_groups_" + imagegroup + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup=" + URLEncoder.encode(imagegroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + imagegroup + "</a></li>" + "\r\n");
					}
				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("libraryMenu.selectURLsubstring(libraryMenu.getCookie('select' + 'libraryMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("libraryMenu.selectURLsubstring(libraryMenu.getCookie('select' + 'libraryMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String ajaxdtreeImagesGroups(Text text, DB db, Configuration config, Session session, String selected) {
		StringBuffer output = new StringBuffer();
		String SQL = "select * from imagegroups order by imagegroup";
		Imagegroup imagegroups = new Imagegroup();
		imagegroups.records(db, SQL);
		if (false) {
			while (imagegroups.records(db, "")) {
				String imagegroup = imagegroups.getImagegroup();
			}
		} else {
			while (imagegroups.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || imagegroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String imagegroup = imagegroups.getImagegroup();
					String classname = "node";
					if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup=" + URLEncoder.encode(imagegroup) + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup=" + URLEncoder.encode(imagegroup).replaceAll("\\+", " ") + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup=" + imagegroup + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					}
					output.append("<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&amp;contentgroup=" + URLEncoder.encode(imagegroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"libraryMenu.select('libraryMenu','+menuitem+','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup=" + URLEncoder.encode(imagegroup) + "&contenttype= &status= &version= &')\"><img alt=\"\" id=\"libraryMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"libraryMenuLink'+menuitem+'\" class=\"" + classname + "\" href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&amp;contentgroup=" + URLEncoder.encode(imagegroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"libraryMenu.select('libraryMenu','+menuitem+','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup=" + URLEncoder.encode(imagegroup) + "&contenttype= &status= &version= &')\">" + imagegroup + "</a><br></nobr>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String ImagesVersions(Text text, DB db) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("libraryMenu.add(menuitem++,menuitem_library_images_versions,'" + text.display("master") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("libraryMenu.add(menuitem++,menuitem_library_images_versions,'" + text.display("master") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_library_images_versions_none\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "\">" + text.display("master") + "</a></li>" + "\r\n");
		}
		String SQL = "select distinct version from versions order by version";
		Version versions = new Version();
		versions.records(db, SQL);
		while (versions.records(db, "")) {
			String version = versions.getVersion();
			if (outputformat == dtree) {
				output.append("libraryMenu.add(menuitem++,menuitem_library_images_versions,'" + version.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("libraryMenu.add(menuitem++,menuitem_library_images_versions,'" + version.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_library_images_versions_" + version + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "\">" + version + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String ImagesWorkflows(Text text, DB db) {
		StringBuffer output = new StringBuffer();
		String SQL = "select distinct tostate from workflow where " + db.is_not_blank("tostate") + " order by tostate";
		Workflow workflows = new Workflow(text);
		workflows.records(db, SQL);
		while (workflows.records(db, "")) {
			String status = workflows.getToState();
			if (outputformat == dtree) {
				output.append("libraryMenu.add(menuitem++,menuitem_library_images_status,'" + status.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype= &status=" + URLEncoder.encode(status) + "&version= &" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("libraryMenu.add(menuitem++,menuitem_library_images_status,'" + status.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype= &status=" + URLEncoder.encode(status) + "&version= &" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_library_images_status_" + status + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype= &status=" + URLEncoder.encode(status) + "&version= &" + Math.random() + "\">" + status + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String FilesTypes(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("libraryMenu.add(menuitem++,menuitem_library_files_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("libraryMenu.add(menuitem++,menuitem_library_files_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_library_files_types_none\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select * from filetypes order by filetype";
		Filetype filetypes = new Filetype();
		filetypes.records(db, SQL);
		if (false) {
			while (filetypes.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || filetypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String filetype = filetypes.getFiletype();
					if (outputformat == dtree) {
						output.append("libraryMenu.add(menuitem++,menuitem_library_files_types,'" + filetype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=" + URLEncoder.encode(filetype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("libraryMenu.add(menuitem++,menuitem_library_files_types,'" + filetype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=" + URLEncoder.encode(filetype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_library_files_types_" + filetype + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=" + URLEncoder.encode(filetype) + "&status= &version= &" + Math.random() + "\">" + filetype + "</a></li>" + "\r\n");
					}
				}
			}
		} else {
			while (filetypes.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || filetypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String filetype = filetypes.getFiletype();
					if (outputformat == dtree) {
						output.append("libraryMenu.pid[menuitem] = menuitem_library_files_types;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(filetype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"libraryMenu.select(\\'libraryMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=" + URLEncoder.encode(filetype) + "&status= &version= &" + Math.random() + "\\')\"><img alt=\"\" id=\"libraryMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"libraryMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(filetype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"libraryMenu.select(\\'libraryMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=" + URLEncoder.encode(filetype) + "&status= &version= &" + Math.random() + "\\')\">" + filetype.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("libraryMenu.pid[menuitem] = menuitem_library_files_types;" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_library_files_types_" + filetype + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=" + URLEncoder.encode(filetype) + "&status= &version= &" + Math.random() + "\">" + filetype + "</a></li>" + "\r\n");
					}
				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("libraryMenu.selectURLsubstring(libraryMenu.getCookie('select' + 'libraryMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("libraryMenu.selectURLsubstring(libraryMenu.getCookie('select' + 'libraryMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String ajaxdtreeFilesTypes(Text text, DB db, Configuration config, Session session, String selected) {
		StringBuffer output = new StringBuffer();
		String SQL = "select * from filetypes order by filetype";
		Filetype filetypes = new Filetype();
		filetypes.records(db, SQL);
		if (false) {
			while (filetypes.records(db, "")) {
				String filetype = filetypes.getFiletype();
			}
		} else {
			while (filetypes.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || filetypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String filetype = filetypes.getFiletype();
					String classname = "node";
					if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=" + URLEncoder.encode(filetype) + "&status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=" + URLEncoder.encode(filetype).replaceAll("\\+", " ") + "&status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=" + filetype + "&status= &version= &")) {
						classname = "nodeSel";
					}
					output.append("<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(filetype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"libraryMenu.select('libraryMenu','+menuitem+','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=" + URLEncoder.encode(filetype) + "&status= &version= &')\"><img alt=\"\" id=\"libraryMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"libraryMenuLink'+menuitem+'\" class=\"" + classname + "\" href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(filetype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"libraryMenu.select('libraryMenu','+menuitem+','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=" + URLEncoder.encode(filetype) + "&status= &version= &')\">" + filetype + "</a><br></nobr>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String FilesGroups(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("libraryMenu.add(menuitem++,menuitem_library_files_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("libraryMenu.add(menuitem++,menuitem_library_files_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_library_files_groups_none\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select * from filegroups order by filegroup";
		Filegroup filegroups = new Filegroup();
		filegroups.records(db, SQL);
		if (false) {
			while (filegroups.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || filegroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String filegroup = filegroups.getFilegroup();
					if (outputformat == dtree) {
						output.append("libraryMenu.add(menuitem++,menuitem_library_files_groups,'" + filegroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup=" + URLEncoder.encode(filegroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("libraryMenu.add(menuitem++,menuitem_library_files_groups,'" + filegroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup=" + URLEncoder.encode(filegroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_library_files_groups_" + filegroup + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup=" + URLEncoder.encode(filegroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + filegroup + "</a></li>" + "\r\n");
					}
				}
			}
		} else {
			while (filegroups.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || filegroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String filegroup = filegroups.getFilegroup();
					if (outputformat == dtree) {
						output.append("libraryMenu.pid[menuitem] = menuitem_library_files_groups;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&amp;contentgroup=" + URLEncoder.encode(filegroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"libraryMenu.select(\\'libraryMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup=" + URLEncoder.encode(filegroup) + "&contenttype= &status= &version= &" + Math.random() + "\\')\"><img alt=\"\" id=\"libraryMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"libraryMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&amp;contentgroup=" + URLEncoder.encode(filegroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"libraryMenu.select(\\'libraryMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup=" + URLEncoder.encode(filegroup) + "&contenttype= &status= &version= &" + Math.random() + "\\')\">" + filegroup.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("libraryMenu.pid[menuitem] = menuitem_library_files_groups;" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_library_files_groups_" + filegroup + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup=" + URLEncoder.encode(filegroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + filegroup + "</a></li>" + "\r\n");
					}
				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("libraryMenu.selectURLsubstring(libraryMenu.getCookie('select' + 'libraryMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("libraryMenu.selectURLsubstring(libraryMenu.getCookie('select' + 'libraryMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String ajaxdtreeFilesGroups(Text text, DB db, Configuration config, Session session, String selected) {
		StringBuffer output = new StringBuffer();
		String SQL = "select * from filegroups order by filegroup";
		Filegroup filegroups = new Filegroup();
		filegroups.records(db, SQL);
		if (false) {
			while (filegroups.records(db, "")) {
				String filegroup = filegroups.getFilegroup();
			}
		} else {
			while (filegroups.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || filegroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String filegroup = filegroups.getFilegroup();
					String classname = "node";
					if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup=" + URLEncoder.encode(filegroup) + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup=" + URLEncoder.encode(filegroup).replaceAll("\\+", " ") + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup=" + filegroup + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					}
					output.append("<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&amp;contentgroup=" + URLEncoder.encode(filegroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"libraryMenu.select('libraryMenu','+menuitem+','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup=" + URLEncoder.encode(filegroup) + "&contenttype= &status= &version= &')\"><img alt=\"\" id=\"libraryMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"libraryMenuLink'+menuitem+'\" class=\"" + classname + "\" href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&amp;contentgroup=" + URLEncoder.encode(filegroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"libraryMenu.select('libraryMenu','+menuitem+','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup=" + URLEncoder.encode(filegroup) + "&contenttype= &status= &version= &')\">" + filegroup + "</a><br></nobr>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String FilesVersions(Text text, DB db) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("libraryMenu.add(menuitem++,menuitem_library_files_versions,'" + text.display("master") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("libraryMenu.add(menuitem++,menuitem_library_files_versions,'" + text.display("master") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_library_files_versions_none\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "\">" + text.display("master") + "</a></li>" + "\r\n");
		}
		String SQL = "select distinct version from versions order by version";
		Version versions = new Version();
		versions.records(db, SQL);
		while (versions.records(db, "")) {
			String version = versions.getVersion();
			if (outputformat == dtree) {
				output.append("libraryMenu.add(menuitem++,menuitem_library_files_versions,'" + version.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("libraryMenu.add(menuitem++,menuitem_library_files_versions,'" + version.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_library_files_versions_" + version + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "\">" + version + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String FilesWorkflows(Text text, DB db) {
		StringBuffer output = new StringBuffer();
		String SQL = "select distinct tostate from workflow where " + db.is_not_blank("tostate") + " order by tostate";
		Workflow workflows = new Workflow(text);
		workflows.records(db, SQL);
		while (workflows.records(db, "")) {
			String status = workflows.getToState();
			if (outputformat == dtree) {
				output.append("libraryMenu.add(menuitem++,menuitem_library_files_status,'" + status.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype= &status=" + URLEncoder.encode(status) + "&version= &" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("libraryMenu.add(menuitem++,menuitem_library_files_status,'" + status.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype= &status=" + URLEncoder.encode(status) + "&version= &" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_library_files_status_" + status + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype= &status=" + URLEncoder.encode(status) + "&version= &" + Math.random() + "\">" + status + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String LinksTypes(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("libraryMenu.add(menuitem++,menuitem_library_links_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("libraryMenu.add(menuitem++,menuitem_library_links_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_library_links_types_none\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select * from linktypes order by linktype";
		Linktype linktypes = new Linktype();
		linktypes.records(db, SQL);
		if (false) {
			while (linktypes.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || linktypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String linktype = linktypes.getLinktype();
					if (outputformat == dtree) {
						output.append("libraryMenu.add(menuitem++,menuitem_library_links_types,'" + linktype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=" + URLEncoder.encode(linktype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("libraryMenu.add(menuitem++,menuitem_library_links_types,'" + linktype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=" + URLEncoder.encode(linktype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_library_links_types_" + linktype + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=" + URLEncoder.encode(linktype) + "&status= &version= &" + Math.random() + "\">" + linktype + "</a></li>" + "\r\n");
					}
				}
			}
		} else {
			while (linktypes.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || linktypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String linktype = linktypes.getLinktype();
					if (outputformat == dtree) {
						output.append("libraryMenu.pid[menuitem] = menuitem_library_links_types;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(linktype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"libraryMenu.select(\\'libraryMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=" + URLEncoder.encode(linktype) + "&status= &version= &" + Math.random() + "\\')\"><img alt=\"\" id=\"libraryMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"libraryMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(linktype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"libraryMenu.select(\\'libraryMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=" + URLEncoder.encode(linktype) + "&status= &version= &" + Math.random() + "\\')\">" + linktype.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("libraryMenu.pid[menuitem] = menuitem_library_links_types;" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_library_links_types_" + linktype + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=" + URLEncoder.encode(linktype) + "&status= &version= &" + Math.random() + "\">" + linktype + "</a></li>" + "\r\n");
					}
				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("libraryMenu.selectURLsubstring(libraryMenu.getCookie('select' + 'libraryMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("libraryMenu.selectURLsubstring(libraryMenu.getCookie('select' + 'libraryMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String ajaxdtreeLinksTypes(Text text, DB db, Configuration config, Session session, String selected) {
		StringBuffer output = new StringBuffer();
		String SQL = "select * from linktypes order by linktype";
		Linktype linktypes = new Linktype();
		linktypes.records(db, SQL);
		if (false) {
			while (linktypes.records(db, "")) {
				String linktype = linktypes.getLinktype();
			}
		} else {
			while (linktypes.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || linktypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String linktype = linktypes.getLinktype();
					String classname = "node";
					if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=" + URLEncoder.encode(linktype) + "&status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=" + URLEncoder.encode(linktype).replaceAll("\\+", " ") + "&status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=" + linktype + "&status= &version= &")) {
						classname = "nodeSel";
					}
					output.append("<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(linktype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"libraryMenu.select('libraryMenu','+menuitem+','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=" + URLEncoder.encode(linktype) + "&status= &version= &')\"><img alt=\"\" id=\"libraryMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"libraryMenuLink'+menuitem+'\" class=\"" + classname + "\" href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(linktype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"libraryMenu.select('libraryMenu','+menuitem+','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=" + URLEncoder.encode(linktype) + "&status= &version= &')\">" + linktype + "</a><br></nobr>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String LinksGroups(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("libraryMenu.add(menuitem++,menuitem_library_links_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("libraryMenu.add(menuitem++,menuitem_library_links_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_library_links_groups_none\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select * from linkgroups order by linkgroup";
		Linkgroup linkgroups = new Linkgroup();
		linkgroups.records(db, SQL);
		if (false) {
			while (linkgroups.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || linkgroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String linkgroup = linkgroups.getLinkgroup();
					if (outputformat == dtree) {
						output.append("libraryMenu.add(menuitem++,menuitem_library_links_groups,'" + linkgroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup=" + URLEncoder.encode(linkgroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("libraryMenu.add(menuitem++,menuitem_library_links_groups,'" + linkgroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup=" + URLEncoder.encode(linkgroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_library_links_groups_" + linkgroup + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup=" + URLEncoder.encode(linkgroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + linkgroup + "</a></li>" + "\r\n");
					}
				}
			}
		} else {
			while (linkgroups.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || linkgroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String linkgroup = linkgroups.getLinkgroup();
					if (outputformat == dtree) {
						output.append("libraryMenu.pid[menuitem] = menuitem_library_links_groups;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&amp;contentgroup=" + URLEncoder.encode(linkgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"libraryMenu.select(\\'libraryMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup=" + URLEncoder.encode(linkgroup) + "&contenttype= &status= &version= &" + Math.random() + "\\')\"><img alt=\"\" id=\"libraryMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"libraryMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&amp;contentgroup=" + URLEncoder.encode(linkgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"libraryMenu.select(\\'libraryMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup=" + URLEncoder.encode(linkgroup) + "&contenttype= &status= &version= &" + Math.random() + "\\')\">" + linkgroup.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("libraryMenu.pid[menuitem] = menuitem_library_links_groups;" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_library_links_groups_" + linkgroup + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup=" + URLEncoder.encode(linkgroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + linkgroup + "</a></li>" + "\r\n");
					}
				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("libraryMenu.selectURLsubstring(libraryMenu.getCookie('select' + 'libraryMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("libraryMenu.selectURLsubstring(libraryMenu.getCookie('select' + 'libraryMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String ajaxdtreeLinksGroups(Text text, DB db, Configuration config, Session session, String selected) {
		StringBuffer output = new StringBuffer();
		String SQL = "select * from linkgroups order by linkgroup";
		Linkgroup linkgroups = new Linkgroup();
		linkgroups.records(db, SQL);
		if (false) {
			while (linkgroups.records(db, "")) {
				String linkgroup = linkgroups.getLinkgroup();
			}
		} else {
			while (linkgroups.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || linkgroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String linkgroup = linkgroups.getLinkgroup();
					String classname = "node";
					if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup=" + URLEncoder.encode(linkgroup) + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup=" + URLEncoder.encode(linkgroup).replaceAll("\\+", " ") + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup=" + linkgroup + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					}
					output.append("<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&amp;contentgroup=" + URLEncoder.encode(linkgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"libraryMenu.select('libraryMenu','+menuitem+','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup=" + URLEncoder.encode(linkgroup) + "&contenttype= &status= &version= &')\"><img alt=\"\" id=\"libraryMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"libraryMenuLink'+menuitem+'\" class=\"" + classname + "\" href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&amp;contentgroup=" + URLEncoder.encode(linkgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"libraryMenu.select('libraryMenu','+menuitem+','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup=" + URLEncoder.encode(linkgroup) + "&contenttype= &status= &version= &')\">" + linkgroup + "</a><br></nobr>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String LinksVersions(Text text, DB db) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("libraryMenu.add(menuitem++,menuitem_library_links_versions,'" + text.display("master") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("libraryMenu.add(menuitem++,menuitem_library_links_versions,'" + text.display("master") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_library_links_versions_none\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "\">" + text.display("master") + "</a></li>" + "\r\n");
		}
		String SQL = "select distinct version from versions order by version";
		Version versions = new Version();
		versions.records(db, SQL);
		while (versions.records(db, "")) {
			String version = versions.getVersion();
			if (outputformat == dtree) {
				output.append("libraryMenu.add(menuitem++,menuitem_library_links_versions,'" + version.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("libraryMenu.add(menuitem++,menuitem_library_links_versions,'" + version.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_library_links_versions_" + version + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "\">" + version + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String LinksWorkflows(Text text, DB db) {
		StringBuffer output = new StringBuffer();
		String SQL = "select distinct tostate from workflow where " + db.is_not_blank("tostate") + " order by tostate";
		Workflow workflows = new Workflow(text);
		workflows.records(db, SQL);
		while (workflows.records(db, "")) {
			String status = workflows.getToState();
			if (outputformat == dtree) {
				output.append("libraryMenu.add(menuitem++,menuitem_library_links_status,'" + status.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype= &status=" + URLEncoder.encode(status) + "&version= &" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("libraryMenu.add(menuitem++,menuitem_library_links_status,'" + status.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype= &status=" + URLEncoder.encode(status) + "&version= &" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_library_links_status_" + status + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype= &status=" + URLEncoder.encode(status) + "&version= &" + Math.random() + "\">" + status + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String UsersTypes(Text text, DB db) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("userMenu.add(menuitem++,menuitem_user_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/users/index.jsp?usertype=-&usergroup= &userclass= &status= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("userMenu.add(menuitem++,menuitem_user_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/users/index.jsp?usertype=-&usergroup= &userclass= &status= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_user_types_none\"><a href=\"/" + text.display("adminpath") + "/users/index.jsp?usertype=-&usergroup= &userclass= &status= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select distinct usertype from usertypes order by usertype";
		Usertype usertypes = new Usertype();
		usertypes.records(db, SQL);
		while (usertypes.records(db, "")) {
			String usertype = usertypes.getUsertype();
			if (outputformat == dtree) {
				output.append("userMenu.add(menuitem++,menuitem_user_types,'" + usertype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/users/index.jsp?usertype=" + URLEncoder.encode(usertype) + "&usergroup= &userclass= &status= &" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("userMenu.add(menuitem++,menuitem_user_types,'" + usertype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/users/index.jsp?usertype=" + URLEncoder.encode(usertype) + "&usergroup= &userclass= &status= &" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_user_types_" + usertype + "\"><a href=\"/" + text.display("adminpath") + "/users/index.jsp?usertype=" + URLEncoder.encode(usertype) + "&usergroup= &userclass= &status= &" + Math.random() + "\">" + usertype + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String UsersGroups(Text text, DB db) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("userMenu.add(menuitem++,menuitem_user_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/users/index.jsp?usergroup=-&usertype= &userclass= &status= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("userMenu.add(menuitem++,menuitem_user_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/users/index.jsp?usergroup=-&usertype= &userclass= &status= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_user_groups_none\"><a href=\"/" + text.display("adminpath") + "/users/index.jsp?usergroup=-&usertype= &userclass= &status= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select distinct usergroup from usergroups order by usergroup";
		Usergroup usergroups = new Usergroup();
		usergroups.records(db, SQL);
		while (usergroups.records(db, "")) {
			String usergroup = usergroups.getUsergroup();
			if (outputformat == dtree) {
				output.append("userMenu.add(menuitem++,menuitem_user_groups,'" + usergroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/users/index.jsp?usergroup=" + URLEncoder.encode(usergroup) + "&usertype= &userclass= &status= &" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("userMenu.add(menuitem++,menuitem_user_groups,'" + usergroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/users/index.jsp?usergroup=" + URLEncoder.encode(usergroup) + "&usertype= &userclass= &status= &" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_user_groups_" + usergroup + "\"><a href=\"/" + text.display("adminpath") + "/users/index.jsp?usergroup=" + URLEncoder.encode(usergroup) + "&usertype= &userclass= &status= &" + Math.random() + "\">" + usergroup + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String ConfigWorkflows(Text text, DB db) {
		StringBuffer output = new StringBuffer();
		String SQL = "select distinct title from workflow order by title";
		Workflow workflows = new Workflow(text);
		workflows.records(db, SQL);
		while (workflows.records(db, "")) {
			String workflow = workflows.getTitle();
			if (outputformat == dtree) {
				output.append("configMenu.add(menuitem++,menuitem_workflows,'" + workflow.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/workflows/index.jsp?workflow=" + URLEncoder.encode(workflow) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("configMenu.add(menuitem++,menuitem_workflows,'" + workflow.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/workflows/index.jsp?workflow=" + URLEncoder.encode(workflow) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_workflows_" + workflow + "\"><a href=\"/" + text.display("adminpath") + "/workflows/index.jsp?workflow=" + URLEncoder.encode(workflow) + "&" + Math.random() + "\">" + workflow + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String EcommerceProductsTypes(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("ecommerceMenu.add(menuitem++,menuitem_ecommerce_products_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("ecommerceMenu.add(menuitem++,menuitem_ecommerce_products_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_ecommerce_products_types_none\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select * from producttypes order by producttype";
		Producttype producttypes = new Producttype();
		producttypes.records(db, SQL);
		if (false) {
			while (producttypes.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || producttypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String producttype = producttypes.getProducttype();
					if (outputformat == dtree) {
						output.append("ecommerceMenu.add(menuitem++,menuitem_ecommerce_products_types,'" + producttype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(producttype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("ecommerceMenu.add(menuitem++,menuitem_ecommerce_products_types,'" + producttype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(producttype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_ecommerce_products_types_" + producttype + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(producttype) + "&status= &version= &" + Math.random() + "\">" + producttype + "</a></li>" + "\r\n");
					}
				}
			}
		} else {
			while (producttypes.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || producttypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String producttype = producttypes.getProducttype();
					if (outputformat == dtree) {
						output.append("ecommerceMenu.pid[menuitem] = menuitem_ecommerce_products_types;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(producttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"ecommerceMenu.select(\\'ecommerceMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(producttype) + "&status= &version= &" + Math.random() + "\\')\"><img alt=\"\" id=\"ecommerceMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"ecommerceMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(producttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"ecommerceMenu.select(\\'ecommerceMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(producttype) + "&status= &version= &" + Math.random() + "\\')\">" + producttype.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("ecommerceMenu.pid[menuitem] = menuitem_ecommerce_products_types;" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_ecommerce_products_types_" + producttype + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(producttype) + "&status= &version= &" + Math.random() + "\">" + producttype + "</a></li>" + "\r\n");
					}
				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("ecommerceMenu.selectURLsubstring(ecommerceMenu.getCookie('select' + 'ecommerceMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("ecommerceMenu.selectURLsubstring(ecommerceMenu.getCookie('select' + 'ecommerceMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String ajaxdtreeEcommerceProductsTypes(Text text, DB db, Configuration config, Session session, String selected) {
		StringBuffer output = new StringBuffer();
		String SQL = "select * from producttypes order by producttype";
		Producttype producttypes = new Producttype();
		producttypes.records(db, SQL);
		if (false) {
			while (producttypes.records(db, "")) {
				String contenttype = producttypes.getProducttype();
			}
		} else {
			while (producttypes.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || producttypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contenttype = producttypes.getProducttype();
					String classname = "node";
					if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(contenttype).replaceAll("\\+", " ") + "&status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=" + contenttype + "&status= &version= &")) {
						classname = "nodeSel";
					}
					output.append("<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"ecommerceMenu.select('ecommerceMenu','+menuitem+','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &')\"><img alt=\"\" id=\"ecommerceMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"ecommerceMenuLink'+menuitem+'\" class=\"" + classname + "\" href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"ecommerceMenu.select('ecommerceMenu','+menuitem+','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &')\">" + contenttype + "</a><br></nobr>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String EcommerceProductsGroups(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("ecommerceMenu.add(menuitem++,menuitem_ecommerce_products_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("ecommerceMenu.add(menuitem++,menuitem_ecommerce_products_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_ecommerce_products_groups_none\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select * from productgroups order by productgroup";
		Productgroup productgroups = new Productgroup();
		productgroups.records(db, SQL);
		if (false) {
			while (productgroups.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || productgroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String productgroup = productgroups.getProductgroup();
					if (outputformat == dtree) {
						output.append("ecommerceMenu.add(menuitem++,menuitem_ecommerce_products_groups,'" + productgroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup=" + URLEncoder.encode(productgroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("ecommerceMenu.add(menuitem++,menuitem_ecommerce_products_groups,'" + productgroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup=" + URLEncoder.encode(productgroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_ecommerce_products_groups_" + productgroup + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup=" + URLEncoder.encode(productgroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + productgroup + "</a></li>" + "\r\n");
					}
				}
			}
		} else {
			while (productgroups.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || productgroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String productgroup = productgroups.getProductgroup();
					if (outputformat == dtree) {
						output.append("ecommerceMenu.pid[menuitem] = menuitem_ecommerce_products_groups;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&amp;contentgroup=" + URLEncoder.encode(productgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"ecommerceMenu.select(\\'ecommerceMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup=" + URLEncoder.encode(productgroup) + "&contenttype= &status= &version= &" + Math.random() + "\\')\"><img alt=\"\" id=\"ecommerceMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"ecommerceMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&amp;contentgroup=" + URLEncoder.encode(productgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"ecommerceMenu.select(\\'ecommerceMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup=" + URLEncoder.encode(productgroup) + "&contenttype= &status= &version= &" + Math.random() + "\\')\">" + productgroup.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("ecommerceMenu.pid[menuitem] = menuitem_ecommerce_products_groups;" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_ecommerce_products_groups_" + productgroup + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup=" + URLEncoder.encode(productgroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + productgroup + "</a></li>" + "\r\n");
					}
				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("ecommerceMenu.selectURLsubstring(ecommerceMenu.getCookie('select' + 'ecommerceMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("ecommerceMenu.selectURLsubstring(ecommerceMenu.getCookie('select' + 'ecommerceMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String ajaxdtreeEcommerceProductsGroups(Text text, DB db, Configuration config, Session session, String selected) {
		StringBuffer output = new StringBuffer();
		String SQL = "select * from productgroups order by productgroup";
		Productgroup productgroups = new Productgroup();
		productgroups.records(db, SQL);
		if (false) {
			while (productgroups.records(db, "")) {
				String contentgroup = productgroups.getProductgroup();
			}
		} else {
			while (productgroups.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || productgroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contentgroup = productgroups.getProductgroup();
					String classname = "node";
					if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup=" + URLEncoder.encode(contentgroup).replaceAll("\\+", " ") + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup=" + contentgroup + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					}
					output.append("<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"ecommerceMenu.select('ecommerceMenu','+menuitem+','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &')\"><img alt=\"\" id=\"ecommerceMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"ecommerceMenuLink'+menuitem+'\" class=\"" + classname + "\" href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"ecommerceMenu.select('ecommerceMenu','+menuitem+','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &')\">" + contentgroup + "</a><br></nobr>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String EcommerceProductsVersions(Text text, DB db) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("ecommerceMenu.add(menuitem++,menuitem_ecommerce_products_versions,'" + text.display("master") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("ecommerceMenu.add(menuitem++,menuitem_ecommerce_products_versions,'" + text.display("master") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_ecommerce_products_versions_none\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype= &status= &version=-&" + Math.random() + "\">" + text.display("master") + "</a></li>" + "\r\n");
		}
		String SQL = "select distinct version from versions order by version";
		Version versions = new Version();
		versions.records(db, SQL);
		while (versions.records(db, "")) {
			String version = versions.getVersion();
			if (outputformat == dtree) {
				output.append("ecommerceMenu.add(menuitem++,menuitem_ecommerce_products_versions,'" + version.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("ecommerceMenu.add(menuitem++,menuitem_ecommerce_products_versions,'" + version.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_ecommerce_products_versions_" + version + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype= &status= &version=" + URLEncoder.encode(version) + "&" + Math.random() + "\">" + version + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String EcommerceProductsWorkflows(Text text, DB db) {
		StringBuffer output = new StringBuffer();
		String SQL = "select distinct tostate from workflow where " + db.is_not_blank("tostate") + " order by tostate";
		Workflow workflows = new Workflow(text);
		workflows.records(db, SQL);
		while (workflows.records(db, "")) {
			String status = workflows.getToState();
			if (outputformat == dtree) {
				output.append("ecommerceMenu.add(menuitem++,menuitem_ecommerce_products_status,'" + status.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype= &status=" + URLEncoder.encode(status) + "&version= &" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("ecommerceMenu.add(menuitem++,menuitem_ecommerce_products_status,'" + status.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype= &status=" + URLEncoder.encode(status) + "&version= &" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_ecommerce_products_status_" + status + "\"><a href=\"/" + text.display("adminpath") + "/content/index.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype= &status=" + URLEncoder.encode(status) + "&version= &" + Math.random() + "\">" + status + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String Databases(Text text, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		StringBuffer output = new StringBuffer();
		String SQL = "select id, title from data order by title, id";
		Databases databases = new Databases(text);
		databases.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
		while (databases.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
			String id = databases.getId();
			String title = databases.getTitle();
			if (outputformat == dtree) {
				output.append("databasesMenu.add(menuitem++,menuitem_databases_data,'" + title.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/data/index.jsp?database=" + id + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("databasesMenu.add(menuitem++,menuitem_databases_data,'" + title.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/data/index.jsp?database=" + id + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_databases_data_" + title + "\"><a href=\"/" + text.display("adminpath") + "/data/index.jsp?database=" + id + "&" + Math.random() + "\">" + title + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String DatabasesExport(Text text, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		StringBuffer output = new StringBuffer();
		String SQL = "select id, title from data order by title, id";
		Databases databases = new Databases(text);
		databases.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
		while (databases.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
			String id = databases.getId();
			String title = databases.getTitle();
			if (outputformat == dtree) {
				output.append("databasesMenu.add(menuitem++,menuitem_databases_export,'" + title.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/data/export.jsp?id=" + id + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("databasesMenu.add(menuitem++,menuitem_databases_export,'" + title.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/data/export.jsp?id=" + id + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_databases_export_" + title + "\"><a href=\"/" + text.display("adminpath") + "/data/export.jsp?id=" + id + "&" + Math.random() + "\">" + title + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String DatabasesImport(Text text, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		StringBuffer output = new StringBuffer();
		String SQL = "select id, title from data order by title, id";
		Databases databases = new Databases(text);
		databases.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
		while (databases.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
			String id = databases.getId();
			String title = databases.getTitle();
			if (outputformat == dtree) {
				output.append("databasesMenu.add(menuitem++,menuitem_databases_import,'" + title.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/data/import.jsp?id=" + id + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("databasesMenu.add(menuitem++,menuitem_databases_import,'" + title.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/data/import.jsp?id=" + id + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_databases_import_" + title + "\"><a href=\"/" + text.display("adminpath") + "/data/import.jsp?id=" + id + "&" + Math.random() + "\">" + title + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String StatisticsPagesTypes(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_content_pages_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_content_pages_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_statistics_what_content_pages_types_none\"><a href=\"/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select distinct contenttype from contenttypes order by contenttype";
		Contenttype contenttypes = new Contenttype();
		contenttypes.records(db, SQL);
		if (false) {
			while (contenttypes.records(db, "")) {
				String contenttype = contenttypes.getContenttype();
				if (outputformat == dtree) {
					output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_content_pages_types,'" + contenttype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else if (outputformat == ajaxdtree) {
					output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_content_pages_types,'" + contenttype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else {
					output.append("<li id=\"menuitem_statistics_what_content_pages_types_" + contenttype + "\"><a href=\"/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &" + Math.random() + "\">" + contenttype + "</a></li>" + "\r\n");
				}
			}
		} else {
			while (contenttypes.records(db, "")) {
//				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contenttypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contenttype = contenttypes.getContenttype();
					if (outputformat == dtree) {
						output.append("statisticsMenu.pid[menuitem] = menuitem_statistics_what_content_pages_types;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select(\\'statisticsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &" + Math.random() + "\\')\"><img alt=\"\" id=\"statisticsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"statisticsMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select(\\'statisticsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &" + Math.random() + "\\')\">" + contenttype.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("statisticsMenu.pid[menuitem] = menuitem_statistics_what_content_pages_types;" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_statistics_what_content_pages_types_" + contenttype + "\"><a href=\"/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &" + Math.random() + "\">" + contenttype + "</a></li>" + "\r\n");
					}
//				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("statisticsMenu.selectURLsubstring(statisticsMenu.getCookie('select' + 'statisticsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("statisticsMenu.selectURLsubstring(statisticsMenu.getCookie('select' + 'statisticsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String ajaxdtreeStatisticsPagesTypes(Text text, DB db, Configuration config, Session session, String selected) {
		StringBuffer output = new StringBuffer();
		String SQL = "select * from contenttypes order by contenttype";
		Contenttype contenttypes = new Contenttype();
		contenttypes.records(db, SQL);
		if (false) {
			while (contenttypes.records(db, "")) {
				String contenttype = contenttypes.getContenttype();
			}
		} else {
			while (contenttypes.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contenttypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contenttype = contenttypes.getContenttype();
					String classname = "node";
					if (selected.equals("/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype).replaceAll("\\+", " ") + "&status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=" + contenttype + "&status= &version= &")) {
						classname = "nodeSel";
					}
					output.append("<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select('statisticsMenu','+menuitem+','/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &')\"><img alt=\"\" id=\"statisticsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"statisticsMenuLink'+menuitem+'\" class=\"" + classname + "\" href=\"/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select('statisticsMenu','+menuitem+','/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &')\">" + contenttype + "</a><br></nobr>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String StatisticsPagesGroups(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_content_pages_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_content_pages_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_statistics_what_content_pages_groups_none\"><a href=\"/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select distinct contentgroup from contentgroups order by contentgroup";
		Contentgroup contentgroups = new Contentgroup();
		contentgroups.records(db, SQL);
		if (false) {
			while (contentgroups.records(db, "")) {
				String contentgroup = contentgroups.getContentgroup();
				if (outputformat == dtree) {
					output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_content_pages_groups,'" + contentgroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else if (outputformat == ajaxdtree) {
					output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_content_pages_groups,'" + contentgroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else {
					output.append("<li id=\"menuitem_statistics_what_content_pages_groups_" + contentgroup + "\"><a href=\"/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + contentgroup + "</a></li>" + "\r\n");
				}
			}
		} else {
			while (contentgroups.records(db, "")) {
//				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contentgroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contentgroup = contentgroups.getContentgroup();
					if (outputformat == dtree) {
						output.append("statisticsMenu.pid[menuitem] = menuitem_statistics_what_content_pages_groups;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select(\\'statisticsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &" + Math.random() + "\\')\"><img alt=\"\" id=\"statisticsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"statisticsMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select(\\'statisticsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &" + Math.random() + "\\')\">" + contentgroup.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("statisticsMenu.pid[menuitem] = menuitem_statistics_what_content_pages_groups;" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_statistics_what_content_pages_groups_" + contentgroup + "\"><a href=\"/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + contentgroup + "</a></li>" + "\r\n");
					}
//				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("statisticsMenu.selectURLsubstring(statisticsMenu.getCookie('select' + 'statisticsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("statisticsMenu.selectURLsubstring(statisticsMenu.getCookie('select' + 'statisticsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String ajaxdtreeStatisticsPagesGroups(Text text, DB db, Configuration config, Session session, String selected) {
		StringBuffer output = new StringBuffer();
		String SQL = "select * from contentgroups order by contentgroup";
		Contentgroup contentgroups = new Contentgroup();
		contentgroups.records(db, SQL);
		if (false) {
			while (contentgroups.records(db, "")) {
				String contentgroup = contentgroups.getContentgroup();
			}
		} else {
			while (contentgroups.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || contentgroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contentgroup = contentgroups.getContentgroup();
					String classname = "node";
					if (selected.equals("/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup).replaceAll("\\+", " ") + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup=" + contentgroup + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					}
					output.append("<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select('statisticsMenu','+menuitem+','/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &')\"><img alt=\"\" id=\"statisticsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"statisticsMenuLink'+menuitem+'\" class=\"" + classname + "\" href=\"/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select('statisticsMenu','+menuitem+','/" + text.display("adminpath") + "/usage/pages.jsp?contentpackage= &contentclass=page&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &')\">" + contentgroup + "</a><br></nobr>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String StatisticsProductsTypes(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_ecommerce_products_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_ecommerce_products_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_statistics_what_ecommerce_products_types_none\"><a href=\"/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select distinct producttype from producttypes order by producttype";
		Producttype producttypes = new Producttype();
		producttypes.records(db, SQL);
		if (false) {
			while (producttypes.records(db, "")) {
				String producttype = producttypes.getProducttype();
				if (outputformat == dtree) {
					output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_ecommerce_products_types,'" + producttype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(producttype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else if (outputformat == ajaxdtree) {
					output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_ecommerce_products_types,'" + producttype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(producttype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else {
					output.append("<li id=\"menuitem_statistics_what_ecommerce_products_types_" + producttype + "\"><a href=\"/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(producttype) + "&status= &version= &" + Math.random() + "\">" + producttype + "</a></li>" + "\r\n");
				}
			}
		} else {
			while (producttypes.records(db, "")) {
//				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || producttypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String producttype = producttypes.getProducttype();
					if (outputformat == dtree) {
						output.append("statisticsMenu.pid[menuitem] = menuitem_statistics_what_ecommerce_products_types;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(producttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select(\\'statisticsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(producttype) + "&status= &version= &" + Math.random() + "\\')\"><img alt=\"\" id=\"statisticsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"statisticsMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(producttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select(\\'statisticsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(producttype) + "&status= &version= &" + Math.random() + "\\')\">" + producttype.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("statisticsMenu.pid[menuitem] = menuitem_statistics_what_ecommerce_products_types;" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_statistics_what_ecommerce_products_types_" + producttype + "\"><a href=\"/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(producttype) + "&status= &version= &" + Math.random() + "\">" + producttype + "</a></li>" + "\r\n");
					}
//				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("statisticsMenu.selectURLsubstring(statisticsMenu.getCookie('select' + 'statisticsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("statisticsMenu.selectURLsubstring(statisticsMenu.getCookie('select' + 'statisticsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String ajaxdtreeStatisticsProductsTypes(Text text, DB db, Configuration config, Session session, String selected) {
		StringBuffer output = new StringBuffer();
		String SQL = "select * from producttypes order by producttype";
		Producttype producttypes = new Producttype();
		producttypes.records(db, SQL);
		if (false) {
			while (producttypes.records(db, "")) {
				String contenttype = producttypes.getProducttype();
			}
		} else {
			while (producttypes.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || producttypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contenttype = producttypes.getProducttype();
					String classname = "node";
					if (selected.equals("/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(contenttype).replaceAll("\\+", " ") + "&status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=" + contenttype + "&status= &version= &")) {
						classname = "nodeSel";
					}
					output.append("<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select('statisticsMenu','+menuitem+','/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &')\"><img alt=\"\" id=\"statisticsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"statisticsMenuLink'+menuitem+'\" class=\"" + classname + "\" href=\"/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select('statisticsMenu','+menuitem+','/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &')\">" + contenttype + "</a><br></nobr>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String StatisticsProductsGroups(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_ecommerce_products_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_ecommerce_products_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_statistics_what_ecommerce_products_groups_none\"><a href=\"/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select distinct productgroup from productgroups order by productgroup";
		Productgroup productgroups = new Productgroup();
		productgroups.records(db, SQL);
		if (false) {
			while (productgroups.records(db, "")) {
				String productgroup = productgroups.getProductgroup();
				if (outputformat == dtree) {
					output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_ecommerce_products_groups,'" + productgroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup=" + URLEncoder.encode(productgroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else if (outputformat == ajaxdtree) {
					output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_ecommerce_products_groups,'" + productgroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup=" + URLEncoder.encode(productgroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else {
					output.append("<li id=\"menuitem_statistics_what_ecommerce_products_groups_" + productgroup + "\"><a href=\"/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup=" + URLEncoder.encode(productgroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + productgroup + "</a></li>" + "\r\n");
				}
			}
		} else {
			while (productgroups.records(db, "")) {
//				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || productgroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String productgroup = productgroups.getProductgroup();
					if (outputformat == dtree) {
						output.append("statisticsMenu.pid[menuitem] = menuitem_statistics_what_ecommerce_products_groups;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&amp;contentgroup=" + URLEncoder.encode(productgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select(\\'statisticsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup=" + URLEncoder.encode(productgroup) + "&contenttype= &status= &version= &" + Math.random() + "\\')\"><img alt=\"\" id=\"statisticsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"statisticsMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&amp;contentgroup=" + URLEncoder.encode(productgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select(\\'statisticsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup=" + URLEncoder.encode(productgroup) + "&contenttype= &status= &version= &" + Math.random() + "\\')\">" + productgroup.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("statisticsMenu.pid[menuitem] = menuitem_statistics_what_ecommerce_products_groups;" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_statistics_what_ecommerce_products_groups_" + productgroup + "\"><a href=\"/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup=" + URLEncoder.encode(productgroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + productgroup + "</a></li>" + "\r\n");
					}
//				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("statisticsMenu.selectURLsubstring(statisticsMenu.getCookie('select' + 'statisticsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("statisticsMenu.selectURLsubstring(statisticsMenu.getCookie('select' + 'statisticsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String ajaxdtreeStatisticsProductsGroups(Text text, DB db, Configuration config, Session session, String selected) {
		StringBuffer output = new StringBuffer();
		String SQL = "select * from productgroups order by productgroup";
		Productgroup productgroups = new Productgroup();
		productgroups.records(db, SQL);
		if (false) {
			while (productgroups.records(db, "")) {
				String contentgroup = productgroups.getProductgroup();
			}
		} else {
			while (productgroups.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || productgroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contentgroup = productgroups.getProductgroup();
					String classname = "node";
					if (selected.equals("/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup=" + URLEncoder.encode(contentgroup).replaceAll("\\+", " ") + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup=" + contentgroup + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					}
					output.append("<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select('statisticsMenu','+menuitem+','/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &')\"><img alt=\"\" id=\"statisticsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"statisticsMenuLink'+menuitem+'\" class=\"" + classname + "\" href=\"/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select('statisticsMenu','+menuitem+','/" + text.display("adminpath") + "/usage/products.jsp?contentpackage= &contentclass=product&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &')\">" + contentgroup + "</a><br></nobr>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String StatisticsImagesTypes(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_library_images_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_library_images_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_statistics_what_library_images_types_none\"><a href=\"/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select distinct imagetype from imagetypes order by imagetype";
		Imagetype imagetypes = new Imagetype();
		imagetypes.records(db, SQL);
		if (false) {
			while (imagetypes.records(db, "")) {
				String imagetype = imagetypes.getImagetype();
				if (outputformat == dtree) {
					output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_library_images_types,'" + imagetype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=" + URLEncoder.encode(imagetype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else if (outputformat == ajaxdtree) {
					output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_library_images_types,'" + imagetype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=" + URLEncoder.encode(imagetype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else {
					output.append("<li id=\"menuitem_statistics_what_library_images_types_" + imagetype + "\"><a href=\"/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=" + URLEncoder.encode(imagetype) + "&status= &version= &" + Math.random() + "\">" + imagetype + "</a></li>" + "\r\n");
				}
			}
		} else {
			while (imagetypes.records(db, "")) {
//				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || imagetypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String imagetype = imagetypes.getImagetype();
					if (outputformat == dtree) {
						output.append("statisticsMenu.pid[menuitem] = menuitem_statistics_what_library_images_types;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(imagetype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select(\\'statisticsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=" + URLEncoder.encode(imagetype) + "&status= &version= &" + Math.random() + "\\')\"><img alt=\"\" id=\"statisticsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"statisticsMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(imagetype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select(\\'statisticsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=" + URLEncoder.encode(imagetype) + "&status= &version= &" + Math.random() + "\\')\">" + imagetype.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("statisticsMenu.pid[menuitem] = menuitem_statistics_what_library_images_types;" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_statistics_what_library_images_types_" + imagetype + "\"><a href=\"/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=" + URLEncoder.encode(imagetype) + "&status= &version= &" + Math.random() + "\">" + imagetype + "</a></li>" + "\r\n");
					}
//				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("statisticsMenu.selectURLsubstring(statisticsMenu.getCookie('select' + 'statisticsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("statisticsMenu.selectURLsubstring(statisticsMenu.getCookie('select' + 'statisticsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String ajaxdtreeStatisticsImagesTypes(Text text, DB db, Configuration config, Session session, String selected) {
		StringBuffer output = new StringBuffer();
		String SQL = "select distinct imagetype from imagetypes order by imagetype";
		Imagetype imagetypes = new Imagetype();
		imagetypes.records(db, SQL);
		if (false) {
			while (imagetypes.records(db, "")) {
				String contenttype = imagetypes.getImagetype();
			}
		} else {
			while (imagetypes.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || imagetypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contenttype = imagetypes.getImagetype();
					String classname = "node";
					if (selected.equals("/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=" + URLEncoder.encode(contenttype).replaceAll("\\+", " ") + "&status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=" + contenttype + "&status= &version= &")) {
						classname = "nodeSel";
					}
					output.append("<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select('statisticsMenu','+menuitem+','/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &')\"><img alt=\"\" id=\"statisticsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"statisticsMenuLink'+menuitem+'\" class=\"" + classname + "\" href=\"/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select('statisticsMenu','+menuitem+','/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &')\">" + contenttype + "</a><br></nobr>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String StatisticsImagesGroups(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_library_images_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_library_images_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_statistics_what_library_images_groups_none\"><a href=\"/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select distinct imagegroup from imagegroups order by imagegroup";
		Imagegroup imagegroups = new Imagegroup();
		imagegroups.records(db, SQL);
		if (false) {
			while (imagegroups.records(db, "")) {
				String imagegroup = imagegroups.getImagegroup();
				if (outputformat == dtree) {
					output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_library_images_groups,'" + imagegroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup=" + URLEncoder.encode(imagegroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else if (outputformat == ajaxdtree) {
					output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_library_images_groups,'" + imagegroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup=" + URLEncoder.encode(imagegroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else {
					output.append("<li id=\"menuitem_statistics_what_library_images_groups_" + imagegroup + "\"><a href=\"/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup=" + URLEncoder.encode(imagegroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + imagegroup + "</a></li>" + "\r\n");
				}
			}
		} else {
			while (imagegroups.records(db, "")) {
//				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || imagegroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String imagegroup = imagegroups.getImagegroup();
					if (outputformat == dtree) {
						output.append("statisticsMenu.pid[menuitem] = menuitem_statistics_what_library_images_groups;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&amp;contentgroup=" + URLEncoder.encode(imagegroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select(\\'statisticsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup=" + URLEncoder.encode(imagegroup) + "&contenttype= &status= &version= &" + Math.random() + "\\')\"><img alt=\"\" id=\"statisticsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"statisticsMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&amp;contentgroup=" + URLEncoder.encode(imagegroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select(\\'statisticsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup=" + URLEncoder.encode(imagegroup) + "&contenttype= &status= &version= &" + Math.random() + "\\')\">" + imagegroup.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("statisticsMenu.pid[menuitem] = menuitem_statistics_what_library_images_groups;" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_statistics_what_library_images_groups_" + imagegroup + "\"><a href=\"/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup=" + URLEncoder.encode(imagegroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + imagegroup + "</a></li>" + "\r\n");
					}
//				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("statisticsMenu.selectURLsubstring(statisticsMenu.getCookie('select' + 'statisticsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("statisticsMenu.selectURLsubstring(statisticsMenu.getCookie('select' + 'statisticsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String ajaxdtreeStatisticsImagesGroups(Text text, DB db, Configuration config, Session session, String selected) {
		StringBuffer output = new StringBuffer();
		String SQL = "select distinct imagegroup from imagegroups order by imagegroup";
		Imagegroup imagegroups = new Imagegroup();
		imagegroups.records(db, SQL);
		if (false) {
			while (imagegroups.records(db, "")) {
				String contentgroup = imagegroups.getImagegroup();
			}
		} else {
			while (imagegroups.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || imagegroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contentgroup = imagegroups.getImagegroup();
					String classname = "node";
					if (selected.equals("/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup=" + URLEncoder.encode(contentgroup).replaceAll("\\+", " ") + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup=" + contentgroup + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					}
					output.append("<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select('statisticsMenu','+menuitem+','/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &')\"><img alt=\"\" id=\"statisticsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"statisticsMenuLink'+menuitem+'\" class=\"" + classname + "\" href=\"/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select('statisticsMenu','+menuitem+','/" + text.display("adminpath") + "/usage/images.jsp?contentpackage= &contentclass=image&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &')\">" + contentgroup + "</a><br></nobr>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String StatisticsFilesTypes(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_library_files_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_library_files_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_statistics_what_library_files_types_none\"><a href=\"/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select distinct filetype from filetypes order by filetype";
		Filetype filetypes = new Filetype();
		filetypes.records(db, SQL);
		if (false) {
			while (filetypes.records(db, "")) {
				String filetype = filetypes.getFiletype();
				if (outputformat == dtree) {
					output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_library_files_types,'" + filetype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=" + URLEncoder.encode(filetype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else if (outputformat == ajaxdtree) {
					output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_library_files_types,'" + filetype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=" + URLEncoder.encode(filetype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else {
					output.append("<li id=\"menuitem_statistics_what_library_files_types_" + filetype + "\"><a href=\"/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=" + URLEncoder.encode(filetype) + "&status= &version= &" + Math.random() + "\">" + filetype + "</a></li>" + "\r\n");
				}
			}
		} else {
			while (filetypes.records(db, "")) {
//				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || filetypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String filetype = filetypes.getFiletype();
					if (outputformat == dtree) {
						output.append("statisticsMenu.pid[menuitem] = menuitem_statistics_what_library_files_types;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(filetype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select(\\'statisticsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=" + URLEncoder.encode(filetype) + "&status= &version= &" + Math.random() + "\\')\"><img alt=\"\" id=\"statisticsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"statisticsMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(filetype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select(\\'statisticsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=" + URLEncoder.encode(filetype) + "&status= &version= &" + Math.random() + "\\')\">" + filetype.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("statisticsMenu.pid[menuitem] = menuitem_statistics_what_library_files_types;" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_statistics_what_library_files_types_" + filetype + "\"><a href=\"/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=" + URLEncoder.encode(filetype) + "&status= &version= &" + Math.random() + "\">" + filetype + "</a></li>" + "\r\n");
					}
//				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("statisticsMenu.selectURLsubstring(statisticsMenu.getCookie('select' + 'statisticsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("statisticsMenu.selectURLsubstring(statisticsMenu.getCookie('select' + 'statisticsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String ajaxdtreeStatisticsFilesTypes(Text text, DB db, Configuration config, Session session, String selected) {
		StringBuffer output = new StringBuffer();
		String SQL = "select distinct filetype from filetypes order by filetype";
		Filetype filetypes = new Filetype();
		filetypes.records(db, SQL);
		if (false) {
			while (filetypes.records(db, "")) {
				String contenttype = filetypes.getFiletype();
			}
		} else {
			while (filetypes.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || filetypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contenttype = filetypes.getFiletype();
					String classname = "node";
					if (selected.equals("/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=" + URLEncoder.encode(contenttype).replaceAll("\\+", " ") + "&status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=" + contenttype + "&status= &version= &")) {
						classname = "nodeSel";
					}
					output.append("<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select('statisticsMenu','+menuitem+','/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &')\"><img alt=\"\" id=\"statisticsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"statisticsMenuLink'+menuitem+'\" class=\"" + classname + "\" href=\"/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select('statisticsMenu','+menuitem+','/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &')\">" + contenttype + "</a><br></nobr>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String StatisticsFilesGroups(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_library_files_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_library_files_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_statistics_what_library_files_groups_none\"><a href=\"/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select distinct filegroup from filegroups order by filegroup";
		Filegroup filegroups = new Filegroup();
		filegroups.records(db, SQL);
		if (false) {
			while (filegroups.records(db, "")) {
				String filegroup = filegroups.getFilegroup();
				if (outputformat == dtree) {
					output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_library_files_groups,'" + filegroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup=" + URLEncoder.encode(filegroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else if (outputformat == ajaxdtree) {
					output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_library_files_groups,'" + filegroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup=" + URLEncoder.encode(filegroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else {
					output.append("<li id=\"menuitem_statistics_what_library_files_groups_" + filegroup + "\"><a href=\"/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup=" + URLEncoder.encode(filegroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + filegroup + "</a></li>" + "\r\n");
				}
			}
		} else {
			while (filegroups.records(db, "")) {
//				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || filegroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String filegroup = filegroups.getFilegroup();
					if (outputformat == dtree) {
						output.append("statisticsMenu.pid[menuitem] = menuitem_statistics_what_library_files_groups;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&amp;contentgroup=" + URLEncoder.encode(filegroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select(\\'statisticsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup=" + URLEncoder.encode(filegroup) + "&contenttype= &status= &version= &" + Math.random() + "\\')\"><img alt=\"\" id=\"statisticsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"statisticsMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&amp;contentgroup=" + URLEncoder.encode(filegroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select(\\'statisticsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup=" + URLEncoder.encode(filegroup) + "&contenttype= &status= &version= &" + Math.random() + "\\')\">" + filegroup.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("statisticsMenu.pid[menuitem] = menuitem_statistics_what_library_files_groups;" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_statistics_what_library_files_groups_" + filegroup + "\"><a href=\"/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup=" + URLEncoder.encode(filegroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + filegroup + "</a></li>" + "\r\n");
					}
//				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("statisticsMenu.selectURLsubstring(statisticsMenu.getCookie('select' + 'statisticsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("statisticsMenu.selectURLsubstring(statisticsMenu.getCookie('select' + 'statisticsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String ajaxdtreeStatisticsFilesGroups(Text text, DB db, Configuration config, Session session, String selected) {
		StringBuffer output = new StringBuffer();
		String SQL = "select distinct filegroup from filegroups order by filegroup";
		Filegroup filegroups = new Filegroup();
		filegroups.records(db, SQL);
		if (false) {
			while (filegroups.records(db, "")) {
				String contentgroup = filegroups.getFilegroup();
			}
		} else {
			while (filegroups.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || filegroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contentgroup = filegroups.getFilegroup();
					String classname = "node";
					if (selected.equals("/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup=" + URLEncoder.encode(contentgroup).replaceAll("\\+", " ") + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup=" + contentgroup + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					}
					output.append("<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select('statisticsMenu','+menuitem+','/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &')\"><img alt=\"\" id=\"statisticsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"statisticsMenuLink'+menuitem+'\" class=\"" + classname + "\" href=\"/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select('statisticsMenu','+menuitem+','/" + text.display("adminpath") + "/usage/files.jsp?contentpackage= &contentclass=file&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &')\">" + contentgroup + "</a><br></nobr>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String StatisticsLinksTypes(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_library_links_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_library_links_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_statistics_what_library_links_types_none\"><a href=\"/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=-&status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select distinct linktype from linktypes order by linktype";
		Linktype linktypes = new Linktype();
		linktypes.records(db, SQL);
		if (false) {
			while (linktypes.records(db, "")) {
				String linktype = linktypes.getLinktype();
				if (outputformat == dtree) {
					output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_library_links_types,'" + linktype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=" + URLEncoder.encode(linktype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else if (outputformat == ajaxdtree) {
					output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_library_links_types,'" + linktype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=" + URLEncoder.encode(linktype) + "&status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else {
					output.append("<li id=\"menuitem_statistics_what_library_links_types_" + linktype + "\"><a href=\"/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=" + URLEncoder.encode(linktype) + "&status= &version= &" + Math.random() + "\">" + linktype + "</a></li>" + "\r\n");
				}
			}
		} else {
			while (linktypes.records(db, "")) {
//				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || linktypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String linktype = linktypes.getLinktype();
					if (outputformat == dtree) {
						output.append("statisticsMenu.pid[menuitem] = menuitem_statistics_what_library_links_types;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(linktype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select(\\'statisticsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=" + URLEncoder.encode(linktype) + "&status= &version= &" + Math.random() + "\\')\"><img alt=\"\" id=\"statisticsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"statisticsMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(linktype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select(\\'statisticsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=" + URLEncoder.encode(linktype) + "&status= &version= &" + Math.random() + "\\')\">" + linktype.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("statisticsMenu.pid[menuitem] = menuitem_statistics_what_library_links_types;" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_statistics_what_library_links_types_" + linktype + "\"><a href=\"/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=" + URLEncoder.encode(linktype) + "&status= &version= &" + Math.random() + "\">" + linktype + "</a></li>" + "\r\n");
					}
//				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("statisticsMenu.selectURLsubstring(statisticsMenu.getCookie('select' + 'statisticsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("statisticsMenu.selectURLsubstring(statisticsMenu.getCookie('select' + 'statisticsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String ajaxdtreeStatisticsLinksTypes(Text text, DB db, Configuration config, Session session, String selected) {
		StringBuffer output = new StringBuffer();
		String SQL = "select distinct linktype from linktypes order by linktype";
		Linktype linktypes = new Linktype();
		linktypes.records(db, SQL);
		if (false) {
			while (linktypes.records(db, "")) {
				String contenttype = linktypes.getLinktype();
			}
		} else {
			while (linktypes.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || linktypes.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contenttype = linktypes.getLinktype();
					String classname = "node";
					if (selected.equals("/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=" + URLEncoder.encode(contenttype).replaceAll("\\+", " ") + "&status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=" + contenttype + "&status= &version= &")) {
						classname = "nodeSel";
					}
					output.append("<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select('statisticsMenu','+menuitem+','/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &')\"><img alt=\"\" id=\"statisticsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"statisticsMenuLink'+menuitem+'\" class=\"" + classname + "\" href=\"/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&amp;contentgroup=%20&amp;contenttype=" + URLEncoder.encode(contenttype) + "&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select('statisticsMenu','+menuitem+','/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup= &contenttype=" + URLEncoder.encode(contenttype) + "&status= &version= &')\">" + contenttype + "</a><br></nobr>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String StatisticsLinksGroups(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_library_links_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_library_links_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_statistics_what_library_links_groups_none\"><a href=\"/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup=-&contenttype= &status= &version= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select distinct linkgroup from linkgroups order by linkgroup";
		Linkgroup linkgroups = new Linkgroup();
		linkgroups.records(db, SQL);
		if (false) {
			while (linkgroups.records(db, "")) {
				String linkgroup = linkgroups.getLinkgroup();
				if (outputformat == dtree) {
					output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_library_links_groups,'" + linkgroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup=" + URLEncoder.encode(linkgroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else if (outputformat == ajaxdtree) {
					output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_library_links_groups,'" + linkgroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup=" + URLEncoder.encode(linkgroup) + "&contenttype= &status= &version= &" + Math.random() + "','','',true,'');" + "\r\n");
				} else {
					output.append("<li id=\"menuitem_statistics_what_library_links_groups_" + linkgroup + "\"><a href=\"/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup=" + URLEncoder.encode(linkgroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + linkgroup + "</a></li>" + "\r\n");
				}
			}
		} else {
			while (linkgroups.records(db, "")) {
//				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || linkgroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String linkgroup = linkgroups.getLinkgroup();
					if (outputformat == dtree) {
						output.append("statisticsMenu.pid[menuitem] = menuitem_statistics_what_library_links_groups;" + "\r\n");
						output.append("document.write('<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&amp;contentgroup=" + URLEncoder.encode(linkgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select(\\'statisticsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup=" + URLEncoder.encode(linkgroup) + "&contenttype= &status= &version= &" + Math.random() + "\\')\"><img alt=\"\" id=\"statisticsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"statisticsMenuLink'+menuitem+'\" class=\"node\" href=\"/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&amp;contentgroup=" + URLEncoder.encode(linkgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select(\\'statisticsMenu\\','+menuitem+',\\'/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup=" + URLEncoder.encode(linkgroup) + "&contenttype= &status= &version= &" + Math.random() + "\\')\">" + linkgroup.replaceAll("'", "\\\\'") + "</a><br></nobr>');" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else if (outputformat == ajaxdtree) {
						output.append("statisticsMenu.pid[menuitem] = menuitem_statistics_what_library_links_groups;" + "\r\n");
						output.append("menuitem++;" + "\r\n");
					} else {
						output.append("<li id=\"menuitem_statistics_what_library_links_groups_" + linkgroup + "\"><a href=\"/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup=" + URLEncoder.encode(linkgroup) + "&contenttype= &status= &version= &" + Math.random() + "\">" + linkgroup + "</a></li>" + "\r\n");
					}
//				}
			}
			if (outputformat == dtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("statisticsMenu.selectURLsubstring(statisticsMenu.getCookie('select' + 'statisticsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("document.write('</span>');" + "\r\n");
				output.append("statisticsMenu.selectURLsubstring(statisticsMenu.getCookie('select' + 'statisticsMenu').replace(/ /g,'%20'));" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



	public static String ajaxdtreeStatisticsLinksGroups(Text text, DB db, Configuration config, Session session, String selected) {
		StringBuffer output = new StringBuffer();
		String SQL = "select distinct linkgroup from linkgroups order by linkgroup";
		Linkgroup linkgroups = new Linkgroup();
		linkgroups.records(db, SQL);
		if (false) {
			while (linkgroups.records(db, "")) {
				String contentgroup = linkgroups.getLinkgroup();
			}
		} else {
			while (linkgroups.records(db, "")) {
				if ((! config.get(db, "hide_accessrestricted_menu_items").equals("yes")) || linkgroups.getAccessRestrictions(session.get("administrator"), session.get("userid"), session.get("username"), session.get("usertype"), session.get("usergroup"), session.get("usertypes"), session.get("usergroups"), db, config)) {
					String contentgroup = linkgroups.getLinkgroup();
					String classname = "node";
					if (selected.equals("/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup=" + URLEncoder.encode(contentgroup).replaceAll("\\+", " ") + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					} else if (selected.equals("/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup=" + contentgroup + "&contenttype= &status= &version= &")) {
						classname = "nodeSel";
					}
					output.append("<nobr><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/line.gif\"><img alt=\"\" src=\"/" + text.display("adminpath") + "/dtree/join.gif\"><a href=\"/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select('statisticsMenu','+menuitem+','/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &')\"><img alt=\"\" id=\"statisticsMenuIcon'+menuitem+'\" src=\"/" + text.display("adminpath") + "/dtree/page.gif\"></a><a id=\"statisticsMenuLink'+menuitem+'\" class=\"" + classname + "\" href=\"/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&amp;contentgroup=" + URLEncoder.encode(contentgroup) + "&amp;contenttype=%20&amp;status=%20&amp;version=%20&amp;\" onclick=\"statisticsMenu.select('statisticsMenu','+menuitem+','/" + text.display("adminpath") + "/usage/links.jsp?contentpackage= &contentclass=link&contentgroup=" + URLEncoder.encode(contentgroup) + "&contenttype= &status= &version= &')\">" + contentgroup + "</a><br></nobr>" + "\r\n");
				}
			}
		}
		return "" + output;
	}



	public static String StatisticsDatabases(Text text, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		StringBuffer output = new StringBuffer();
		String SQL = "select title from data order by title";
		Databases databases = new Databases(text);
		databases.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, SQL);
		while (databases.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
			String title = databases.getTitle();
			if (outputformat == dtree) {
				output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_databases,'" + title.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/database.jsp?database=" + URLEncoder.encode(title) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("statisticsMenu.add(menuitem++,menuitem_statistics_what_databases,'" + title.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/database.jsp?database=" + URLEncoder.encode(title) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_statistics_what_databases_" + title + "\"><a href=\"/" + text.display("adminpath") + "/usage/database.jsp?database=" + URLEncoder.encode(title) + "&" + Math.random() + "\">" + title + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String StatisticsUsersTypes(Text text, DB db) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("statisticsMenu.add(menuitem++,menuitem_statistics_who_users_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/usage/users.jsp?userclass= &usergroup= &usertype=-&" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("statisticsMenu.add(menuitem++,menuitem_statistics_who_users_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/usage/users.jsp?userclass= &usergroup= &usertype=-&" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_statistics_who_users_types_none\"><a href=\"/" + text.display("adminpath") + "/usage/users.jsp?userclass= &usergroup= &usertype=-&" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select distinct usertype from usertypes order by usertype";
		Usertype usertypes = new Usertype();
		usertypes.records(db, SQL);
		while (usertypes.records(db, "")) {
			String usertype = usertypes.getUsertype();
			if (outputformat == dtree) {
				output.append("statisticsMenu.add(menuitem++,menuitem_statistics_who_users_types,'" + usertype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/users.jsp?userclass= &usergroup= &usertype=" + URLEncoder.encode(usertype) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("statisticsMenu.add(menuitem++,menuitem_statistics_who_users_types,'" + usertype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/users.jsp?userclass= &usergroup= &usertype=" + URLEncoder.encode(usertype) + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_statistics_who_users_types_" + usertype + "\"><a href=\"/" + text.display("adminpath") + "/usage/users.jsp?userclass= &usergroup= &usertype=" + URLEncoder.encode(usertype) + "&" + Math.random() + "\">" + usertype + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String StatisticsUsersGroups(Text text, DB db) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("statisticsMenu.add(menuitem++,menuitem_statistics_who_users_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/usage/users.jsp?userclass= &usergroup=-&usertype= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("statisticsMenu.add(menuitem++,menuitem_statistics_who_users_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/usage/users.jsp?userclass= &usergroup=-&usertype= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_statistics_who_users_groups_none\"><a href=\"/" + text.display("adminpath") + "/usage/users.jsp?userclass= &usergroup=-&usertype= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select distinct usergroup from usergroups order by usergroup";
		Usergroup usergroups = new Usergroup();
		usergroups.records(db, SQL);
		while (usergroups.records(db, "")) {
			String usergroup = usergroups.getUsergroup();
			if (outputformat == dtree) {
				output.append("statisticsMenu.add(menuitem++,menuitem_statistics_who_users_groups,'" + usergroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/users.jsp?userclass= &usergroup=" + URLEncoder.encode(usergroup) + "&usertype= &" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("statisticsMenu.add(menuitem++,menuitem_statistics_who_users_groups,'" + usergroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/usage/users.jsp?userclass= &usergroup=" + URLEncoder.encode(usergroup) + "&usertype= &" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_statistics_who_users_groups_" + usergroup + "\"><a href=\"/" + text.display("adminpath") + "/usage/users.jsp?userclass= &usergroup=" + URLEncoder.encode(usergroup) + "&usertype= &" + Math.random() + "\">" + usergroup + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String ClientsTypes(Text text, DB db) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("clientsMenu.add(menuitem++,menuitem_clients_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/hosting/index.jsp?hostingclass= &hostinggroup= &hostingtype=-&status= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("clientsMenu.add(menuitem++,menuitem_clients_types,'" + text.display("none") + "','/" + text.display("adminpath") + "/hosting/index.jsp?hostingclass= &hostinggroup= &hostingtype=-&status= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_clients_types_none\"><a href=\"/" + text.display("adminpath") + "/hosting/index.jsp?hostingclass= &hostinggroup= &hostingtype=-&status= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select distinct hostingtype from hostingtypes order by hostingtype";
		Hostingtype hostingtypes = new Hostingtype();
		hostingtypes.records(db, SQL);
		while (hostingtypes.records(db, "")) {
			String hostingtype = hostingtypes.getHostingtype();
			if (outputformat == dtree) {
				output.append("clientsMenu.add(menuitem++,menuitem_clients_types,'" + hostingtype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/hosting/index.jsp?hostingclass= &hostinggroup= &hostingtype=" + URLEncoder.encode(hostingtype) + "&status= &" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("clientsMenu.add(menuitem++,menuitem_clients_types,'" + hostingtype.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/hosting/index.jsp?hostingclass= &hostinggroup= &hostingtype=" + URLEncoder.encode(hostingtype) + "&status= &" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_clients_types_" + hostingtype + "\"><a href=\"/" + text.display("adminpath") + "/hosting/index.jsp?hostingclass= &hostinggroup= &hostingtype=" + URLEncoder.encode(hostingtype) + "&status= &" + Math.random() + "\">" + hostingtype + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String ClientsGroups(Text text, DB db) {
		StringBuffer output = new StringBuffer();
		if (outputformat == dtree) {
			output.append("clientsMenu.add(menuitem++,menuitem_clients_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/hosting/index.jsp?hostingclass= &hostinggroup=-&hostingtype= &status= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else if (outputformat == ajaxdtree) {
			output.append("clientsMenu.add(menuitem++,menuitem_clients_groups,'" + text.display("none") + "','/" + text.display("adminpath") + "/hosting/index.jsp?hostingclass= &hostinggroup=-&hostingtype= &status= &" + Math.random() + "','','',true,'');" + "\r\n");
		} else {
			output.append("<li id=\"menuitem_clients_groups_none\"><a href=\"/" + text.display("adminpath") + "/hosting/index.jsp?hostingclass= &hostinggroup=-&hostingtype= &status= &" + Math.random() + "\">" + text.display("none") + "</a></li>" + "\r\n");
		}
		String SQL = "select distinct hostinggroup from hostinggroups order by hostinggroup";
		Hostinggroup hostinggroups = new Hostinggroup();
		hostinggroups.records(db, SQL);
		while (hostinggroups.records(db, "")) {
			String hostinggroup = hostinggroups.getHostinggroup();
			if (outputformat == dtree) {
				output.append("clientsMenu.add(menuitem++,menuitem_clients_groups,'" + hostinggroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/hosting/index.jsp?hostingclass= &hostinggroup=" + URLEncoder.encode(hostinggroup) + "&hostingtype= &status= &" + Math.random() + "','','',true,'');" + "\r\n");
			} else if (outputformat == ajaxdtree) {
				output.append("clientsMenu.add(menuitem++,menuitem_clients_groups,'" + hostinggroup.replaceAll("'", "\\\\'") + "','/" + text.display("adminpath") + "/hosting/index.jsp?hostingclass= &hostinggroup=" + URLEncoder.encode(hostinggroup) + "&hostingtype= &status= &" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
				output.append("<li id=\"menuitem_clients_groups_" + hostinggroup + "\"><a href=\"/" + text.display("adminpath") + "/hosting/index.jsp?hostingclass= &hostinggroup=" + URLEncoder.encode(hostinggroup) + "&hostingtype= &status= &" + Math.random() + "\">" + hostinggroup + "</a></li>" + "\r\n");
			}
		}
		return "" + output;
	}



	public static String RelationsWebsites(Text text, DB db, Configuration config, Session session) {
		StringBuffer output = new StringBuffer();
		String SQL = "select distinct domain, default_page from websites order by domain, default_page";
		Website websites = new Website();
		websites.records(db, SQL);
		while (websites.records(db, "")) {
			String title = URLEncoder.encode(websites.getDomain());
			title += " (" + websites.getDefaultPage() + ")";
			if (outputformat == dtree) {
			} else if (outputformat == ajaxdtree) {
				output.append("contentRelationsMenu.add(menuitem++,menuitem_websites,'" + title + "','/" + text.display("adminpath") + "/content/structure.jsp?menu=relations&contentclass=page,product&contentgroup=%20&contenttype=%20&status=%20&version=%20&root=" + websites.getDefaultPage() + "&title=" + title + "&" + Math.random() + "','','',true,'');" + "\r\n");
			} else {
			}
		}
		return "" + output;
	}



}
