<%@ include file="../config.jsp" %><%@ page import="java.io.File" %><%@ page import="com.amazonaws.*" %><%@ page import="com.amazonaws.auth.*" %><%@ page import="com.amazonaws.services.s3.*" %><%@ page import="com.amazonaws.services.s3.model.*" %><%

// "/webadmin/api/exists.jsp" example/template file
// Modify this file to use your own custom cloud storage file/folder exists functionality

String output = ""; // default - file/folder does not exist - return nothing
if (myrequest.getAttribute("parameters") != null) {
	String[] myparameters = (""+myrequest.getAttribute("parameters")).replaceAll("^\"", "").replaceAll("\"$", "").split("\" \"");
	if (myparameters.length >= 1) {
		String filename = myparameters[0];
//		String csURLrootpath = myparameters[1];
//		String DEBUG = myparameters[2];

		String service = myconfig.get(db, "csservice");
		String key = myconfig.get(db, "csusername");
		String secret = myconfig.get(db, "cspassword");
		String rootpath = myconfig.get(db, "csrootpath");

//		System.out.println("DEBUG:/webadmin/api/exists.jsp:"+filename+":::"+service+":::"+rootpath);

		if (filename.equals("")) {
			// ignore

		} else if (service.equals("amazons3")) {

			try {
				AmazonS3Client amazonS3client = new AmazonS3Client(new BasicAWSCredentials(key, secret));
				String foldername = filename.replaceAll("\\\\", "/").replaceAll("/$", "") + "/";
				ObjectListing objlist = amazonS3client.listObjects(new ListObjectsRequest().withBucketName(rootpath).withPrefix(foldername).withDelimiter("/"));
				if (objlist.getObjectSummaries().size() > 0) {
					output = "folder"; // or file
				}
				try {
					ObjectMetadata objmd = amazonS3client.getObjectMetadata(rootpath, filename);
					if (objmd != null) {
						output = "file";
					}
				} catch (Exception e) {
					// not file
				}
			} catch (Exception e) {
				System.out.println("DEBUG:ERROR:/webadmin/api/exists.jsp:"+filename+":::"+service+":::"+rootpath+":::"+e);
			}

		} else {

			// other cloud storage service - place your own custom program code here or include "exists.custom.jsp"
			// output = ""; // if non-existing
			// output = "folder"; // if folder
			// output = "file"; // if file
			// output = "true"; // if other/unknown

//			java.io.File myfh = new java.io.File(Common.getRealPath(servletcontext, myconfig.get(db, "URLrootpath") + filename));
//			if (myfh.exists() && myfh.isDirectory()) {
//				output = "folder";
//			} else if (myfh.exists() && myfh.isFile()) {
//				output = "file";
//			} else if (myfh.exists()) {
//				output = "true";
//			} else {
//				output = "";
//			}

		}
	}
}

%><%= output %><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>