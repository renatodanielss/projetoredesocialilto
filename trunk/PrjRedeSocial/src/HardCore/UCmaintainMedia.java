package HardCore;

import javax.servlet.*;

public class UCmaintainMedia {
	private String error = "";
	private Text text = new Text();



	public UCmaintainMedia() {
	}



	public UCmaintainMedia(Text mytext) {
		if (mytext != null) text = mytext;
	}



	public void getAccess(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean dummy = RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
		return;
	}
	public boolean getAccessPermission(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		return RequireUser.Administrator(text, mysession.get("username"), myconfig.get(db, "superadmin"), mysession.get("administrator"), myrequest, myresponse, db.getDatabase(), mysession.get("database"));
	}



	public Content getImagesRecords(Request myrequest, Configuration myconfig, DB db) {
		Content myrecords = new Content(text);
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
		} else if (myrequest.getParameter("category").equals("all")) {
			// ok
		} else {
			SQLwhere = "1=0";
		}
		String SQL = "select * from content " + SQLwhere + " order by title, version, id";
		myrecords.records("", "", "", "", "", "", "", db, myconfig, SQL);
		return myrecords;
	}



	public Content getFilesRecords(Request myrequest, Configuration myconfig, DB db) {
		Content myrecords = new Content(text);
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
		} else if (myrequest.getParameter("category").equals("all")) {
			// ok
		} else {
			SQLwhere = "1=0";
		}
		String SQL = "select * from content " + SQLwhere + " order by title, version, id";
		myrecords.records("", "", "", "", "", "", "", db, myconfig, SQL);
		return myrecords;
	}



	public void doCategory(Session mysession, Request myrequest, Response myresponse, Configuration myconfig, DB db) {
		boolean accesspermission = getAccessPermission(mysession, myrequest, myresponse, myconfig, db);
		if (! accesspermission) return;
		if (! myrequest.getParameter("submit").equals("")) {
			if (myrequest.getParameter("category").equals("imagetype")) {
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
		if (myrequest.getParameter("section").equals("Images")) {
			mysession.set("admin_contentclass", "image");
		} else if (myrequest.getParameter("section").equals("Files")) {
			mysession.set("admin_contentclass", "file");
		} else {
			mysession.set("admin_contentclass", "image");
		}
		if (! myrequest.getParameter("imagegroup").equals("")) {
			mysession.set("admin_contentgroup", html.encodeHtmlEntities(myrequest.getParameter("imagegroup")));
		} else if (! myrequest.getParameter("filegroup").equals("")) {
			mysession.set("admin_contentgroup", html.encodeHtmlEntities(myrequest.getParameter("filegroup")));
		} else {
			mysession.set("admin_contentgroup", "");
		}
		if (! myrequest.getParameter("imagetype").equals("")) {
			mysession.set("admin_contenttype", html.encodeHtmlEntities(myrequest.getParameter("imagetype")));
		} else if (! myrequest.getParameter("filetype").equals("")) {
			mysession.set("admin_contenttype", html.encodeHtmlEntities(myrequest.getParameter("filetype")));
		} else {
			mysession.set("admin_contenttype", "");
		}
		maintainContent.getIndex(mysession, myrequest, myresponse, myconfig, db);
		Content pageCreate = maintainContent.getCreateRecords();
		Workflow workflow = new Workflow(text);
		while (pageCreate.records(mysession.get("administrator"), mysession.get("userid"), mysession.get("username"), mysession.get("usertype"), mysession.get("usergroup"), mysession.get("usertypes"), mysession.get("usergroups"), db, myconfig, "")) {
			if ((myconfig.get(db, "use_workflow").equals("yes")) && (! pageCreate.getAdministrator()) && (! workflow.permissions(db, pageCreate.getStatus(), mysession.get("usergroups"), mysession.get("usertypes"), pageCreate.getContentClass(), pageCreate.getContentGroup(), pageCreate.getContentType(), pageCreate.getVersion(), (pageCreate.getStatusBy().equals(mysession.get("username")))))) {
				// skip
			} else if (pageCreate.getCreator()) {
				options = options + "<option value=\"" + pageCreate.getId() + "\">" + pageCreate.getTitle();
				if (! pageCreate.getVersion().equals("")) {
					options = options + " (" + pageCreate.getVersion() + ")";
				}
				options = options + " (" + pageCreate.getId() + ")</option>\r\n";
			}
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
