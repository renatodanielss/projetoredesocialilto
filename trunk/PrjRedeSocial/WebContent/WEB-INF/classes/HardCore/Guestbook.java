package HardCore;

import java.sql.*;
import java.util.HashMap;
import java.util.regex.*;

public class Guestbook {
	private String id = "";
	private String created = "";
	private String updated = "";
	private String published = "";
	private String created_by = "";
	private String updated_by = "";
	private String published_by = "";
	private String status = "";
	private String title = "";
	private String content = "";
	private String name = "";
	private String email = "";
	private String website = "";
	private String company = "";

	private	Statement rs = null;



	public Guestbook() {
		init();
	}



	public void init() {
		id = "";
		created = "";
		updated = "";
		published = "";
		created_by = "";
		updated_by = "";
		published_by = "";
		status = "";
		title = "";
		content = "";
		name = "";
		email = "";
		website = "";
		company = "";
	}



	public void read(DB db, String id) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			String SQL = "select * from guestbook where id=" + Common.integer(id);
			HashMap<String,String> row = db.query_record(SQL);
			if (row != null) {
				getRecord(db, row);
			} else {
				init();
			}
		}
	}



	public void getRecord(DB db, HashMap<String,String> record) {
		id = "" + record.get("id");
		created = "" + record.get("created");
		updated = "" + record.get("updated");
		published = "" + record.get("published");
		created_by = "" + record.get("created_by");
		updated_by = "" + record.get("updated_by");
		published_by = "" + record.get("published_by");
		status = "" + record.get("status");
		title = "" + record.get("title");
		content = "" + record.get("content");
		name = "" + record.get("name");
		email = "" + record.get("email");
		website = "" + record.get("website");
		company = "" + record.get("company");
	}



	public void getForm(Request form) {
		title = "";
		if (form.getParameter("subject") != null) title += form.getParameter("subject");
		if (form.getParameter("title") != null) title += form.getParameter("title");
		content = "";
		if (form.getParameter("content") != null) content += form.getParameter("content");
		if ((form.getParameter("name") != null) && (! form.getParameter("name").equals(""))) {
			name = form.getParameter("name");
		} else {
			name = "Anonymous";
		}
		email = "";
		if (form.getParameter("email") != null) email += form.getParameter("email");
		website = "";
		if (form.getParameter("website") != null) website += form.getParameter("website");
		if ((form.getParameter("company") != null) && (! form.getParameter("company").equals(""))) {
			company = form.getParameter("company");
		} else {
			company = "Anonymous";
		}
	}



	public void create(DB db) {
		if (db == null) return;
		HashMap<String,String> mydata = new HashMap<String,String>();
		mydata.put("created", "" + created);
		mydata.put("created_by", "" + created_by);
		mydata.put("updated", "" + updated);
		mydata.put("updated_by", "" + updated_by);
		mydata.put("published", "" + published);
		mydata.put("published_by", "" + published_by);
		mydata.put("status", "" + status);
		mydata.put("title", "" + title);
		mydata.put("content", "" + content);
		mydata.put("name", "" + name);
		mydata.put("email", "" + email);
		mydata.put("website", "" + website);
		mydata.put("company", "" + company);
		db.insert("guestbook", mydata);
	}



	public void update(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			HashMap<String,String> mydata = new HashMap<String,String>();
			mydata.put("created", "" + created);
			mydata.put("created_by", "" + created_by);
			mydata.put("updated", "" + updated);
			mydata.put("updated_by", "" + updated_by);
			mydata.put("published", "" + published);
			mydata.put("published_by", "" + published_by);
			mydata.put("status", "" + status);
			mydata.put("title", "" + title);
			mydata.put("content", "" + content);
			mydata.put("name", "" + name);
			mydata.put("email", "" + email);
			mydata.put("website", "" + website);
			mydata.put("company", "" + company);
			db.update("guestbook", "id", id, mydata);
		}
	}



	public void delete(DB db) {
		if (db == null) return;
		if ((id != null) && (! id.equals(""))) {
			db.delete("gusestbook", "id", id);
		}
	}



	public boolean records(DB db, String SQL) {
		if ((db == null) || db.isClosed()) return false;
		if ((SQL != null) && (! SQL.equals(""))) {
			rs = db.records(SQL);
			return true;
		} else {
			HashMap<String,String> row = db.records(rs);
			if (row != null) {
				getRecord(db, row);
				return true;
			} else {
				init();
				return false;
			}
		}
	}



	public String getId() {
		return id;
	}
	public void setId(String value) {
		id = value;
	}



	public String getCreated() {
		return created;
	}
	public void setCreated(String value) {
		created = value;
	}



	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String value) {
		updated = value;
	}



	public String getPublished() {
		return published;
	}
	public void setPublished(String value) {
		published = value;
	}



	public String getCreatedBy() {
		return created_by;
	}
	public void setCreatedBy(String value) {
		created_by = value;
	}



	public String getUpdatedBy() {
		return updated_by;
	}
	public void setUpdatedBy(String value) {
		updated_by = value;
	}



	public String getPublishedBy() {
		return published_by;
	}
	public void setPublishedBy(String value) {
		published_by = value;
	}



	public String getStatus() {
		return status;
	}
	public void setStatus(String value) {
		status = value;
	}



	public String getTitle() {
		return title;
	}
	public void setTitle(String value) {
		title = value;
	}



	public String getContent() {
		return content;
	}
	public void setContent(String value) {
		content = value;
	}



	public String getName() {
		return name;
	}
	public void setName(String value) {
		name = value;
	}



	public String getEmail() {
		return email;
	}
	public void setEmail(String value) {
		email = value;
	}



	public String getWebsite() {
		return website;
	}
	public void setWebsite(String value) {
		website = value;
	}



	public String getCompany() {
		return company;
	}
	public void setCompany(String value) {
		company = value;
	}



}
