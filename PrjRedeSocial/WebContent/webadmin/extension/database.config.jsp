<%

// use database name as key - to permit this extension to access/output all database tables and columns in the database
databaseConnection.put("mydatabase", "mysql:com.mysql.jdbc.Driver:myusername:mypassword@jdbc:mysql://localhost/mydatabase");

// use database name and table name as key - to permit this extension to access/output only that database table's columns
// for example @@@extension:database(joomla:jos_content:title:id:123)@@@ will be permitted
// but @@@extension:database(joomla:jos_users:email:id:123)@@@ will always be blank
databaseConnection.put("joomla:jos_content", "mysql:com.mysql.jdbc.Driver:myusername:mypassword@jdbc:mysql://localhost/joomla");

// use database name and table name and column name as key - to permit this extension to access/output only that database table column
// for example @@@extension:database(joomla:jos_users:email:id:123)@@@ will be permitted
// but @@@extension:database(joomla:jos_users:password:id:123)@@@ will always be blank
databaseConnection.put("joomla:jos_users:email", "mysql:com.mysql.jdbc.Driver:myusername:mypassword@jdbc:mysql://localhost/joomla");

%>