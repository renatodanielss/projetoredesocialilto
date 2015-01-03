<%

//System.setProperty("RDS_DB_NAME", "");
//System.setProperty("RDS_USERNAME", "");
//System.setProperty("RDS_PASSWORD", "");
//System.setProperty("RDS_HOSTNAME", "");
//System.setProperty("RDS_PORT", "");
//System.setProperty("AWS_ACCESS_KEY_ID", "");
//System.setProperty("AWS_SECRET_KEY", "");
//System.setProperty("AWS_S3_BUCKET", "");
//System.setProperty("AWS_S3_URL", "");

// Amazon Elastic Beanstalk RDS database

if ((System.getProperty("JDBC_CONNECTION_STRING") != null) && (! System.getProperty("JDBC_CONNECTION_STRING").equals(""))) {
	database = System.getProperty("JDBC_CONNECTION_STRING");

} else if ((System.getProperty("RDS_DB_NAME") != null) && (! System.getProperty("RDS_DB_NAME").equals(""))) {
	String mydbname = System.getProperty("RDS_DB_NAME");
	String myusername = System.getProperty("RDS_USERNAME");
	String mypassword = System.getProperty("RDS_PASSWORD");
	String myhostname = System.getProperty("RDS_HOSTNAME");
	String myport = System.getProperty("RDS_PORT");

	if (myport.equals("1433")) {
		// Microsoft SQL Server
		//database = "mssql:com.microsoft.jdbc.sqlserver.SQLServerDriver:" + myusername + ":" + mypassword + "@jdbc:microsoft:sqlserver://" + myhostname + ":" + myport;
		//database = "mssql:com.microsoft.jdbc.sqlserver.SQLServerDriver:" + myusername + ":" + mypassword + "@jdbc:microsoft:sqlserver://" + myhostname + "\\" + mydbname + ":" + myport;
		database = "mssql:com.microsoft.sqlserver.jdbc.SQLServerDriver:" + myusername + ":" + mypassword + "@jdbc:sqlserver://" + myhostname + ":" + myport;
		myconfig.setTemp("database_init", "!create database " + mydbname + ";" + "alter login " + myusername + " with default_database = " + mydbname);
		//database = "mssql:com.microsoft.sqlserver.jdbc.SQLServerDriver:" + myusername + ":" + mypassword + "@jdbc:sqlserver://" + myhostname + "\\" + mydbname + ":" + myport;
		//database = "mssql:com.microsoft.sqlserver.jdbc.SQLServerDriver:" + myusername + ":" + mypassword + "@jdbc:sqlserver://" + myhostname + "\\SQLExpress:" + myport;
		//database = "mssql:com.microsoft.sqlserver.jdbc.SQLServerDriver:" + myusername + ":" + mypassword + "@jdbc:sqlserver://" + myhostname + "\\SQLExpress:" + myport + ";Database=" + mydbname;
		//mssql:com.microsoft.sqlserver.jdbc.SQLServerDriver:vejrum:vejrum22@jdbc:sqlserver://aarv3bn0jgq1rx.cfvvuqwzxp7v.eu-west-1.rds.amazonaws.com\SQLExpress:1433

	} else if (myport.equals("1521")) {
		// Oracle
		//database = "oracle:oracle.jdbc.driver.OracleDriver:" + myusername + ":" + mypassword + "@jdbc:oracle:thin:@" + myhostname + ":" + myport + ":" + mydbname;
		database = "oracle:oracle.jdbc.driver.OracleDriver:" + myusername + ":" + mypassword + "@jdbc:oracle:thin:@//" + myhostname + ":" + myport + "/" + mydbname;

	} else if (myport.equals("3306")) {
		// MySQL
		database = "mysql:com.mysql.jdbc.Driver:" + myusername + ":" + mypassword + "@jdbc:mysql://" + myhostname + ":" + myport + "/" + mydbname;

	}
}

// Amazon S3 media storage

if ((System.getProperty("AWS_ACCESS_KEY_ID") != null) && (! System.getProperty("AWS_ACCESS_KEY_ID").equals(""))) {

	// Configuration / Features / Publishing / Do not create files for static web addresses (required for cloud storage)
	myconfig.setTemp("use_static_content", "no");

	// Configuration / System / Website / Media Storage / 
	String mycsservice = "amazons3";
	myconfig.setTemp("csservice", mycsservice);

	String mycsusername = "";
	if ((System.getProperty("AWS_ACCESS_KEY_ID") != null) && (! System.getProperty("AWS_ACCESS_KEY_ID").equals(""))) {
		mycsusername = "" + System.getProperty("AWS_ACCESS_KEY_ID");
		myconfig.setTemp("csusername", mycsusername);
	}

	String mycspassword = "";
	if ((System.getProperty("AWS_SECRET_KEY") != null) && (! System.getProperty("AWS_SECRET_KEY").equals(""))) {
		mycspassword = "" + System.getProperty("AWS_SECRET_KEY");
		myconfig.setTemp("cspassword", mycspassword);
	}

	String mycsrootpath = "";
	if ((System.getProperty("AWS_S3_BUCKET") != null) && (! System.getProperty("AWS_S3_BUCKET").equals(""))) {
		mycsrootpath = "" + System.getProperty("AWS_S3_BUCKET");
		myconfig.setTemp("csrootpath", mycsrootpath);
	} else if ((System.getProperty("RDS_HOSTNAME") != null) && (! System.getProperty("RDS_HOSTNAME").equals(""))) {
		mycsrootpath = "" + System.getProperty("RDS_HOSTNAME");
		myconfig.setTemp("csrootpath", mycsrootpath);
	} else {
		mycsrootpath = "" + java.net.InetAddress.getLocalHost().getHostName();
		myconfig.setTemp("csrootpath", mycsrootpath);
	}

	String mycsURLrootpath = "";
	if ((System.getProperty("AWS_S3_URL") != null) && (! System.getProperty("AWS_S3_URL").equals(""))) {
		mycsURLrootpath = "" + System.getProperty("AWS_S3_URL");
		myconfig.setTemp("csURLrootpath", mycsURLrootpath);
	} else if ((System.getProperty("RDS_HOSTNAME") != null) && (! System.getProperty("RDS_HOSTNAME").equals(""))) {
		mycsURLrootpath = "" + System.getProperty("RDS_HOSTNAME");
		mycsURLrootpath = mycsURLrootpath.replaceAll("^(.*?\\\\.)([^\\\\.]+)\\\\.rds(\\\\.amazonaws\\\\.com)$", "s3-website-$2.$3");
		mycsURLrootpath = mycsrootpath + mycsURLrootpath;
		myconfig.setTemp("csURLrootpath", mycsURLrootpath);
	}
}

%>