<% if ((request.getAttribute("extension") != null) && (! request.getAttribute("extension").equals(""))) { %><%= "WCM:REDIRECT:" + request.getAttribute("extension") %><% } else { %><%= "WCM:REDIRECT:" + session.getValue("extension") %><% } %>