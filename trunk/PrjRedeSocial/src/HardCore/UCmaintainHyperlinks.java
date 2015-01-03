package HardCore;

import javax.servlet.*;

public class UCmaintainHyperlinks {
	private String error = "";
	private Text text = new Text();



	public UCmaintainHyperlinks() {
	}



	public UCmaintainHyperlinks(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public void getAccess(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean dummy = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		return;
	}
	public boolean getAccessPermission(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		return RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
	}



	public Usertype getUsertypesRecords(Request myrequest, Configuration myconfig, DB db) {
		Usertype myrecords = new Usertype();
		if (myrequest.getParameter("section").equals("User Types")) {
			String SQL = "select usertype from usertypes where " + db.is_not_blank("subscribers_type") + " and " + db.is_not_blank("subscribers_group") + " order by usertype";
			myrecords.records(db, SQL);
		}
		return myrecords;
	}



	public Usergroup getUsergroupsRecords(Request myrequest, Configuration myconfig, DB db) {
		Usergroup myrecords = new Usergroup();
		if (myrequest.getParameter("section").equals("User Groups")) {
			String SQL = "select usergroup from usergroups where " + db.is_not_blank("subscribers_type") + " and " + db.is_not_blank("subscribers_group") + " order by usergroup";
			myrecords.records(db, SQL);
		}
		return myrecords;
	}



	public Website getWebsitesRecords(Request myrequest, Configuration myconfig, DB db) {
		Website myrecords = new Website(text);
		if (myrequest.getParameter("section").equals("Websites")) {
			String SQL = "select distinct domain from websites group by domain order by domain";
			myrecords.records(db, SQL);
		}
		return myrecords;
	}



	public Content getTemplatesRecords(Request myrequest, Configuration myconfig, DB db) {
		Content myrecords = new Content(text);
		if (myrequest.getParameter("section").equals("Templates")) {
			String SQLwhere = Common.SQLwhere_equals(db, "", "contentclass", "template");
			String SQL = "select * from content " + SQLwhere + " order by title, version";
			myrecords.records("", "", "", "", "", "", "", db, myconfig, SQL);
		}
		return myrecords;
	}



	public Content getStyleSheetsRecords(Request myrequest, Configuration myconfig, DB db) {
		Content myrecords = new Content(text);
		if (myrequest.getParameter("section").equals("Style Sheets")) {
			String SQLwhere = Common.SQLwhere_equals(db, "", "contentclass", "stylesheet");
			String SQL = "select * from content " + SQLwhere + " order by title, version";
			myrecords.records("", "", "", "", "", "", "", db, myconfig, SQL);
		}
		return myrecords;
	}



	public Content getScriptsRecords(Request myrequest, Configuration myconfig, DB db) {
		Content myrecords = new Content(text);
		if (myrequest.getParameter("section").equals("Scripts")) {
			String SQLwhere = Common.SQLwhere_equals(db, "", "contentclass", "script");
			String SQL = "select * from content " + SQLwhere + " order by title, version";
			myrecords.records("", "", "", "", "", "", "", db, myconfig, SQL);
		}
		return myrecords;
	}



	public Content getProductsRecords(Request myrequest, Configuration myconfig, DB db) {
		Content myrecords = new Content(text);
		if (License.valid(db, myconfig, "ecommerce")) {
			if (myrequest.getParameter("section").equals("Products")) {
				String SQLwhere = Common.SQLwhere_equals(db, "", "contentclass", "product");
				if (myrequest.getParameter("category").equals("producttype")) {
					if (myrequest.getParameter("title").equals("-")) {
						SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contenttype", "");
					} else {
						SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contenttype", myrequest.getParameter("title"));
					}
				} else if (myrequest.getParameter("category").equals("productgroup")) {
					if (myrequest.getParameter("title").equals("-")) {
						SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contentgroup", "");
					} else {
						SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contentgroup", myrequest.getParameter("title"));
					}
				}
				String SQL = "select * from content " + SQLwhere + " order by title, version";
				myrecords.records("", "", "", "", "", "", "", db, myconfig, SQL);
			}
		}
		return myrecords;
	}



	public Content getPagesRecords(Request myrequest, Configuration myconfig, DB db) {
		Content myrecords = new Content(text);
		if (myrequest.getParameter("section").equals("Pages")) {
			String SQLwhere = Common.SQLwhere_equals(db, "", "contentclass", "page");
			if (myrequest.getParameter("category").equals("contenttype")) {
				if (myrequest.getParameter("title").equals("-")) {
					SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contenttype", "");
				} else {
					SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contenttype", myrequest.getParameter("title"));
				}
			} else if (myrequest.getParameter("category").equals("contentgroup")) {
				if (myrequest.getParameter("title").equals("-")) {
					SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contentgroup", "");
				} else {
					SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contentgroup", myrequest.getParameter("title"));
				}
			}
			String SQL = "select * from content " + SQLwhere + " order by title, version";
			myrecords.records("", "", "", "", "", "", "", db, myconfig, SQL);
		}
		return myrecords;
	}



	public Content getElementsRecords(Request myrequest, Configuration myconfig, DB db) {
		Content myrecords = new Content(text);
		if (myrequest.getParameter("section").equals("Elements")) {
			String SQLwhere = Common.SQLwhere_not_equals("", "contentclass", "page");
			SQLwhere = Common.SQLwhere_not_equals(SQLwhere, "contentclass", "image");
			SQLwhere = Common.SQLwhere_not_equals(SQLwhere, "contentclass", "file");
			SQLwhere = Common.SQLwhere_not_equals(SQLwhere, "contentclass", "link");
			SQLwhere = Common.SQLwhere_not_equals(SQLwhere, "contentclass", "product");
			SQLwhere = Common.SQLwhere_not_equals(SQLwhere, "contentclass", "template");
			SQLwhere = Common.SQLwhere_not_equals(SQLwhere, "contentclass", "stylesheet");
			SQLwhere = Common.SQLwhere_not_equals(SQLwhere, "contentclass", "script");
			if (myrequest.getParameter("category").equals("contentclass")) {
				SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contentclass", myrequest.getParameter("title"));
			} else if (myrequest.getParameter("category").equals("contenttype")) {
				if (myrequest.getParameter("title").equals("-")) {
					SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contenttype", "");
				} else {
					SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contenttype", myrequest.getParameter("title"));
				}
			} else if (myrequest.getParameter("category").equals("contentgroup")) {
				if (myrequest.getParameter("title").equals("-")) {
					SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contentgroup", "");
				} else {
					SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contentgroup", myrequest.getParameter("title"));
				}
			}
			String SQL = "select * from content " + SQLwhere + " order by title, version";
			myrecords.records("", "", "", "", "", "", "", db, myconfig, SQL);
		}
		return myrecords;
	}



	public Content getImagesRecords(Request myrequest, Configuration myconfig, DB db) {
		Content myrecords = new Content(text);
		if (myrequest.getParameter("section").equals("Images")) {
			String SQLwhere = Common.SQLwhere_equals(db, "", "contentclass", "image");
			if (myrequest.getParameter("category").equals("imagetype")) {
				if (myrequest.getParameter("title").equals("-")) {
					SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contenttype", "");
				} else {
					SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contenttype", myrequest.getParameter("title"));
				}
			} else if (myrequest.getParameter("category").equals("imagegroup")) {
				if (myrequest.getParameter("title").equals("-")) {
					SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contentgroup", "");
				} else {
					SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contentgroup", myrequest.getParameter("title"));
				}
			}
			String SQL = "select * from content " + SQLwhere + " order by title, version";
			myrecords.records("", "", "", "", "", "", "", db, myconfig, SQL);
		}
		return myrecords;
	}



	public Content getFilesRecords(Request myrequest, Configuration myconfig, DB db) {
		Content myrecords = new Content(text);
		if (myrequest.getParameter("section").equals("Files")) {
			String SQLwhere = Common.SQLwhere_equals(db, "", "contentclass", "file");
			if (myrequest.getParameter("category").equals("filetype")) {
				if (myrequest.getParameter("title").equals("-")) {
					SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contenttype", "");
				} else {
					SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contenttype", myrequest.getParameter("title"));
				}
			} else if (myrequest.getParameter("category").equals("filegroup")) {
				if (myrequest.getParameter("title").equals("-")) {
					SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contentgroup", "");
				} else {
					SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contentgroup", myrequest.getParameter("title"));
				}
			}
			String SQL = "select * from content " + SQLwhere + " order by title, version";
			myrecords.records("", "", "", "", "", "", "", db, myconfig, SQL);
		}
		return myrecords;
	}



	public Content getLinksRecords(Request myrequest, Configuration myconfig, DB db) {
		Content myrecords = new Content(text);
		if (myrequest.getParameter("section").equals("Links")) {
			String SQLwhere = Common.SQLwhere_equals(db, "", "contentclass", "link");
			if (myrequest.getParameter("category").equals("linktype")) {
				if (myrequest.getParameter("title").equals("-")) {
					SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contenttype", "");
				} else {
					SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contenttype", myrequest.getParameter("title"));
				}
			} else if (myrequest.getParameter("category").equals("linkgroup")) {
				if (myrequest.getParameter("title").equals("-")) {
					SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contentgroup", "");
				} else {
					SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "contentgroup", myrequest.getParameter("title"));
				}
			}
			String SQL = "select * from content " + SQLwhere + " order by title, version";
			myrecords.records("", "", "", "", "", "", "", db, myconfig, SQL);
		}
		return myrecords;
	}



	public Version getVersionsRecords(Request myrequest, Configuration myconfig, DB db) {
		Version myrecords = new Version();
		if (myconfig.get(db, "use_versions").equals("yes")) {
			if (myrequest.getParameter("section").equals("Versions")) {
				String SQL = "select version from versions order by version";
				myrecords.records(db, SQL);
			}
		}
		return myrecords;
	}



	public User getUsersRecords(Request myrequest, Configuration myconfig, DB db) {
		User myrecords = new User(text);
		if (myrequest.getParameter("section").equals("Users")) {
			String SQLwhere = "";
			if (myrequest.getParameter("category").equals("administrator")) {
				SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "userclass", "administrator");
			} else if (myrequest.getParameter("category").equals("usertype")) {
				if (myrequest.getParameter("title").equals("-")) {
					SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "usertype", "");
				} else {
					SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "usertype", myrequest.getParameter("title"));
				}
			} else if (myrequest.getParameter("category").equals("usergroup")) {
				if (myrequest.getParameter("title").equals("-")) {
					SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "usergroup", "");
				} else {
					SQLwhere = Common.SQLwhere_equals(db, SQLwhere, "usergroup", myrequest.getParameter("title"));
				}
			}
			String SQL = "select id, username from users " + SQLwhere + " order by username, id";
			myrecords.records("", "", "", "", "", db, myconfig, SQL);
		}
		return myrecords;
	}



	public void doCategory(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = getAccessPermission(mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return;
		if (! myrequest.getParameter("submit").equals("")) {
			if (myrequest.getParameter("category").equals("contenttype")) {
				Contenttype contenttype = new Contenttype();
				if (myrequest.getParameter("action").equals("Create")) {
					contenttype.setContenttype(myrequest.getParameter("title"));
					contenttype.create(db);
				} else if (myrequest.getParameter("action").equals("Update")) {
					contenttype.readContenttype(db, myrequest.getParameter("old_title"));
					contenttype.setContenttype(myrequest.getParameter("title"));
					contenttype.update(db);
					Content content = new Content(text);
					content.renameContenttype(db, myrequest.getParameter("old_title"), myrequest.getParameter("title"));
				} else if (myrequest.getParameter("action").equals("Delete")) {
					contenttype.readContenttype(db, myrequest.getParameter("old_title"));
					contenttype.delete(db);
					Content content = new Content(text);
					content.renameContenttype(db, myrequest.getParameter("old_title"), "");
				}
			} else if (myrequest.getParameter("category").equals("contentgroup")) {
				Contentgroup contentgroup = new Contentgroup();
				if (myrequest.getParameter("action").equals("Create")) {
					contentgroup.setContentgroup(myrequest.getParameter("title"));
					contentgroup.create(db);
				} else if (myrequest.getParameter("action").equals("Update")) {
					contentgroup.readContentgroup(db, myrequest.getParameter("old_title"));
					contentgroup.setContentgroup(myrequest.getParameter("title"));
					contentgroup.update(db);
					Content content = new Content(text);
					content.renameContentgroup(db, myrequest.getParameter("old_title"), myrequest.getParameter("title"));
				} else if (myrequest.getParameter("action").equals("Delete")) {
					contentgroup.readContentgroup(db, myrequest.getParameter("old_title"));
					contentgroup.delete(db);
					Content content = new Content(text);
					content.renameContentgroup(db, myrequest.getParameter("old_title"), "");
				}
			} else if (myrequest.getParameter("category").equals("imagetype")) {
				Imagetype imagetype = new Imagetype();
				if (myrequest.getParameter("action").equals("Create")) {
					imagetype.setImagetype(myrequest.getParameter("title"));
					imagetype.create(db);
				} else if (myrequest.getParameter("action").equals("Update")) {
					imagetype.readImagetype(db, myrequest.getParameter("old_title"));
					imagetype.setImagetype(myrequest.getParameter("title"));
					imagetype.update(db);
					Content content = new Content(text);
					content.renameImagetype(db, myrequest.getParameter("old_title"), myrequest.getParameter("title"));
				} else if (myrequest.getParameter("action").equals("Delete")) {
					imagetype.readImagetype(db, myrequest.getParameter("old_title"));
					imagetype.delete(db);
					Content content = new Content(text);
					content.renameImagetype(db, myrequest.getParameter("old_title"), "");
				}
			} else if (myrequest.getParameter("category").equals("imagegroup")) {
				Imagegroup imagegroup = new Imagegroup();
				if (myrequest.getParameter("action").equals("Create")) {
					imagegroup.setImagegroup(myrequest.getParameter("title"));
					imagegroup.create(db);
				} else if (myrequest.getParameter("action").equals("Update")) {
					imagegroup.readImagegroup(db, myrequest.getParameter("old_title"));
					imagegroup.setImagegroup(myrequest.getParameter("title"));
					imagegroup.update(db);
					Content content = new Content(text);
					content.renameImagegroup(db, myrequest.getParameter("old_title"), myrequest.getParameter("title"));
				} else if (myrequest.getParameter("action").equals("Delete")) {
					imagegroup.readImagegroup(db, myrequest.getParameter("old_title"));
					imagegroup.delete(db);
					Content content = new Content(text);
					content.renameImagegroup(db, myrequest.getParameter("old_title"), "");
				}
			} else if (myrequest.getParameter("category").equals("filetype")) {
				Filetype filetype = new Filetype();
				if (myrequest.getParameter("action").equals("Create")) {
					filetype.setFiletype(myrequest.getParameter("title"));
					filetype.create(db);
				} else if (myrequest.getParameter("action").equals("Update")) {
					filetype.readFiletype(db, myrequest.getParameter("old_title"));
					filetype.setFiletype(myrequest.getParameter("title"));
					filetype.update(db);
					Content content = new Content(text);
					content.renameFiletype(db, myrequest.getParameter("old_title"), myrequest.getParameter("title"));
				} else if (myrequest.getParameter("action").equals("Delete")) {
					filetype.readFiletype(db, myrequest.getParameter("old_title"));
					filetype.delete(db);
					Content content = new Content(text);
					content.renameFiletype(db, myrequest.getParameter("old_title"), "");
				}
			} else if (myrequest.getParameter("category").equals("filegroup")) {
				Filegroup filegroup = new Filegroup();
				if (myrequest.getParameter("action").equals("Create")) {
					filegroup.setFilegroup(myrequest.getParameter("title"));
					filegroup.create(db);
				} else if (myrequest.getParameter("action").equals("Update")) {
					filegroup.readFilegroup(db, myrequest.getParameter("old_title"));
					filegroup.setFilegroup(myrequest.getParameter("title"));
					filegroup.update(db);
					Content content = new Content(text);
					content.renameFilegroup(db, myrequest.getParameter("old_title"), myrequest.getParameter("title"));
				} else if (myrequest.getParameter("action").equals("Delete")) {
					filegroup.readFilegroup(db, myrequest.getParameter("old_title"));
					filegroup.delete(db);
					Content content = new Content(text);
					content.renameFilegroup(db, myrequest.getParameter("old_title"), "");
				}
			} else if (myrequest.getParameter("category").equals("linktype")) {
				Linktype linktype = new Linktype();
				if (myrequest.getParameter("action").equals("Create")) {
					linktype.setLinktype(myrequest.getParameter("title"));
					linktype.create(db);
				} else if (myrequest.getParameter("action").equals("Update")) {
					linktype.readLinktype(db, myrequest.getParameter("old_title"));
					linktype.setLinktype(myrequest.getParameter("title"));
					linktype.update(db);
					Content content = new Content(text);
					content.renameLinktype(db, myrequest.getParameter("old_title"), myrequest.getParameter("title"));
				} else if (myrequest.getParameter("action").equals("Delete")) {
					linktype.readLinktype(db, myrequest.getParameter("old_title"));
					linktype.delete(db);
					Content content = new Content(text);
					content.renameLinktype(db, myrequest.getParameter("old_title"), "");
				}
			} else if (myrequest.getParameter("category").equals("linkgroup")) {
				Linkgroup linkgroup = new Linkgroup();
				if (myrequest.getParameter("action").equals("Create")) {
					linkgroup.setLinkgroup(myrequest.getParameter("title"));
					linkgroup.create(db);
				} else if (myrequest.getParameter("action").equals("Update")) {
					linkgroup.readLinkgroup(db, myrequest.getParameter("old_title"));
					linkgroup.setLinkgroup(myrequest.getParameter("title"));
					linkgroup.update(db);
					Content content = new Content(text);
					content.renameLinkgroup(db, myrequest.getParameter("old_title"), myrequest.getParameter("title"));
				} else if (myrequest.getParameter("action").equals("Delete")) {
					linkgroup.readLinkgroup(db, myrequest.getParameter("old_title"));
					linkgroup.delete(db);
					Content content = new Content(text);
					content.renameLinkgroup(db, myrequest.getParameter("old_title"), "");
				}
			} else if (myrequest.getParameter("category").equals("producttype")) {
				Producttype producttype = new Producttype();
				if (myrequest.getParameter("action").equals("Create")) {
					producttype.setProducttype(myrequest.getParameter("title"));
					producttype.create(db);
				} else if (myrequest.getParameter("action").equals("Update")) {
					producttype.readProducttype(db, myrequest.getParameter("old_title"));
					producttype.setProducttype(myrequest.getParameter("title"));
					producttype.update(db);
					Content content = new Content(text);
					content.renameProducttype(db, myrequest.getParameter("old_title"), myrequest.getParameter("title"));
				} else if (myrequest.getParameter("action").equals("Delete")) {
					producttype.readProducttype(db, myrequest.getParameter("old_title"));
					producttype.delete(db);
					Content content = new Content(text);
					content.renameProducttype(db, myrequest.getParameter("old_title"), "");
				}
			} else if (myrequest.getParameter("category").equals("productgroup")) {
				Productgroup productgroup = new Productgroup();
				if (myrequest.getParameter("action").equals("Create")) {
					productgroup.setProductgroup(myrequest.getParameter("title"));
					productgroup.create(db);
				} else if (myrequest.getParameter("action").equals("Update")) {
					productgroup.readProductgroup(db, myrequest.getParameter("old_title"));
					productgroup.setProductgroup(myrequest.getParameter("title"));
					productgroup.update(db);
					Content content = new Content(text);
					content.renameProductgroup(db, myrequest.getParameter("old_title"), myrequest.getParameter("title"));
				} else if (myrequest.getParameter("action").equals("Delete")) {
					productgroup.readProductgroup(db, myrequest.getParameter("old_title"));
					productgroup.delete(db);
					Content content = new Content(text);
					content.renameProductgroup(db, myrequest.getParameter("old_title"), "");
				}
			}
		}
	}



	public Page doCreate(ServletContext servletcontext, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return doCreate(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, null);
	}
	public Page doCreate(ServletContext servletcontext, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Fileupload filepost) throws Exception {
		String save_contentbundle = mysession.get("admin_contentbundle");
		String save_contentclass = mysession.get("admin_contentclass");
		String save_contentgroup = mysession.get("admin_contentgroup");
		String save_contenttype = mysession.get("admin_contenttype");
		String save_version = mysession.get("admin_version");
		mysession.set("admin_contentbundle", "");
		mysession.set("admin_contentclass", "");
		mysession.set("admin_contentgroup", "");
		mysession.set("admin_contenttype", "");
		mysession.set("admin_version", "");
		UCmaintainContent maintainContent = new UCmaintainContent(text);
		Page mypage = maintainContent.doCreate(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, filepost);
		error = maintainContent.getError();
		mysession.set("admin_contentbundle", save_contentbundle);
		mysession.set("admin_contentclass", save_contentclass);
		mysession.set("admin_contentgroup", save_contentgroup);
		mysession.set("admin_contenttype", save_contenttype);
		mysession.set("admin_version", save_version);
		return mypage;
	}



	public Page doUpdate(ServletContext servletcontext, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return doUpdate(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, null);
	}
	public Page doUpdate(ServletContext servletcontext, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Fileupload filepost) throws Exception {
		UCmaintainContent maintainContent = new UCmaintainContent(text);
		Page mypage = maintainContent.doUpdate(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, filepost);
		error = maintainContent.getError();
		return mypage;
	}



	public Page doDelete(ServletContext servletcontext, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) throws Exception {
		return doDelete(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, null);
	}
	public Page doDelete(ServletContext servletcontext, String DOCUMENT_ROOT, Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db, Fileupload filepost) throws Exception {
		UCmaintainContent maintainContent = new UCmaintainContent(text);
		Page mypage = maintainContent.doDelete(servletcontext, DOCUMENT_ROOT, mysession, myrequest, myresponse, myconfig, db, filepost);
		error = maintainContent.getError();
		return mypage;
	}



	public String select_options(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		String options = "";
		UCmaintainContent maintainContent = new UCmaintainContent(text);

		String save_admin_contentclass = mysession.get("admin_contentclass");
		String save_admin_contentgroup = mysession.get("admin_contentgroup");
		String save_admin_contenttype = mysession.get("admin_contenttype");
		if (myrequest.getParameter("section").equals("Pages")) {
			mysession.set("admin_contentclass", "page");
		} else if (myrequest.getParameter("section").equals("Products")) {
			mysession.set("admin_contentclass", "product");
		} else if (myrequest.getParameter("section").equals("Images")) {
			mysession.set("admin_contentclass", "image");
		} else if (myrequest.getParameter("section").equals("Files")) {
			mysession.set("admin_contentclass", "file");
		} else if (myrequest.getParameter("section").equals("Links")) {
			mysession.set("admin_contentclass", "link");
		}
		if (! myrequest.getParameter("contentgroup").equals("")) {
			mysession.set("admin_contentgroup", html.encodeHtmlEntities(myrequest.getParameter("contentgroup")));
		}
		if (! myrequest.getParameter("contenttype").equals("")) {
			mysession.set("admin_contenttype", html.encodeHtmlEntities(myrequest.getParameter("contenttype")));
		}
		if (! myrequest.getParameter("imagegroup").equals("")) {
			mysession.set("admin_contentgroup", html.encodeHtmlEntities(myrequest.getParameter("imagegroup")));
		}
		if (! myrequest.getParameter("imagetype").equals("")) {
			mysession.set("admin_contenttype", html.encodeHtmlEntities(myrequest.getParameter("imagetype")));
		}
		if (! myrequest.getParameter("filegroup").equals("")) {
			mysession.set("admin_contentgroup", html.encodeHtmlEntities(myrequest.getParameter("filegroup")));
		}
		if (! myrequest.getParameter("filetype").equals("")) {
			mysession.set("admin_contenttype", html.encodeHtmlEntities(myrequest.getParameter("filetype")));
		}
		if (! myrequest.getParameter("linkgroup").equals("")) {
			mysession.set("admin_contentgroup", html.encodeHtmlEntities(myrequest.getParameter("linkgroup")));
		}
		if (! myrequest.getParameter("linktype").equals("")) {
			mysession.set("admin_contenttype", html.encodeHtmlEntities(myrequest.getParameter("linktype")));
		}
		if (! myrequest.getParameter("productgroup").equals("")) {
			mysession.set("admin_contentgroup", html.encodeHtmlEntities(myrequest.getParameter("productgroup")));
		}
		if (! myrequest.getParameter("producttype").equals("")) {
			mysession.set("admin_contenttype", html.encodeHtmlEntities(myrequest.getParameter("producttype")));
		}
		maintainContent.getIndex(mysession, myrequest, myresponse, myconfig, db);
		Content pageCreate = maintainContent.getCreateRecords();
		while (pageCreate.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
			options = options + "<option value=\"" + pageCreate.getId() + "\">" + pageCreate.getTitle();
			if (! pageCreate.getVersion().equals("")) {
				options = options + " (" + pageCreate.getVersion() + ")";
			}
			options = options + " (" + pageCreate.getId() + ")</option>\r\n";
		}
		mysession.set("admin_contentclass", save_admin_contentclass);
		mysession.set("admin_contentgroup", save_admin_contentgroup);
		mysession.set("admin_contenttype", save_admin_contenttype);

		return options;
	}



	public String getError() {
		return error;
	}



}
