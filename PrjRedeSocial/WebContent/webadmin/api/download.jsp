<%@ include file="../config.jsp" %><%@ page import="java.io.File" %><%@ page import="com.amazonaws.*" %><%@ page import="com.amazonaws.auth.*" %><%@ page import="com.amazonaws.services.s3.*" %><%@ page import="com.amazonaws.services.s3.model.*" %><%

// "/webadmin/api/download.jsp" example/template file
// Modify this file to use your own custom cloud storage download file functionality

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

//		System.out.println("DEBUG:/webadmin/api/download.jsp:"+filename+":::"+service+":::"+rootpath);

		if (filename.equals("")) {
			// ignore

		} else if (service.equals("amazons3")) {

			try {
				AmazonS3Client amazonS3client = new AmazonS3Client(new BasicAWSCredentials(key, secret));
				java.io.File file = new java.io.File(Common.getRealPath(servletcontext, myconfig.get(db, "URLrootpath") + filename));
				amazonS3client.getObject(new GetObjectRequest(rootpath, filename), file);
			} catch (Exception e) {
				System.out.println("DEBUG:ERROR:/webadmin/api/download.jsp:"+filename+":::"+service+":::"+rootpath+":::"+e);
			}

		} else {

			// other cloud storage service - place your own custom program code here or include "download.custom.jsp"

		}
	}
}

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>