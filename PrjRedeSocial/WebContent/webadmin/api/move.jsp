<%@ include file="../config.jsp" %><%@ page import="java.io.File" %><%@ page import="com.amazonaws.*" %><%@ page import="com.amazonaws.auth.*" %><%@ page import="com.amazonaws.services.s3.*" %><%@ page import="com.amazonaws.services.s3.model.*" %><%

// "/webadmin/api/move.jsp" example/template file
// Modify this file to use your own custom cloud storage move file functionality

if (myrequest.getAttribute("parameters") != null) {
	String[] myparameters = (""+myrequest.getAttribute("parameters")).replaceAll("^\"", "").replaceAll("\"$", "").split("\" \"");
	if (myparameters.length >= 2) {
		String oldfilename = myparameters[0];
		String newfilename = myparameters[1];
//		String csURLrootpath = myparameters[2];
//		String DEBUG = myparameters[3];

		String service = myconfig.get(db, "csservice");
		String key = myconfig.get(db, "csusername");
		String secret = myconfig.get(db, "cspassword");
		String rootpath = myconfig.get(db, "csrootpath");

//		System.out.println("DEBUG:/webadmin/api/move.jsp:"+oldfilename+":::"+newfilename+":::"+service+":::"+rootpath);

		if ((oldfilename.equals("")) || (newfilename.equals(""))) {
			// ignore

		} else if (service.equals("amazons3")) {

			try {
				AmazonS3Client amazonS3client = new AmazonS3Client(new BasicAWSCredentials(key, secret));
				amazonS3client.copyObject(rootpath, oldfilename, rootpath, newfilename);
				amazonS3client.deleteObject(rootpath, oldfilename);
			} catch (Exception e) {
				System.out.println("DEBUG:ERROR:/webadmin/api/move.jsp:"+oldfilename+":::"+newfilename+":::"+service+":::"+rootpath+":::"+e);
			}

		} else {

			// other cloud storage service - place your own custom program code here or include "move.custom.jsp"

		}
	}
}

%><% if (db != null) db.close(); %><% if (logdb != null) logdb.close(); %>