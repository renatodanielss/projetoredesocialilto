package HardCore;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.*;

public class Session {
	private HttpSession session;
	private HashMap<String,String> mysession = new HashMap<String,String>();



	public Session() {
		session = null;
	}



	public Session(Session othersession) {
		session = null;
		copy(othersession);
	}



	public Session(HttpSession httpsession) {
		session = httpsession;
	}



	public HttpSession getSession() {
		return session;
	}



	public void invalidate() {
		try {
			if (session != null) {
				session.invalidate();
			}
		} catch (Exception e) {
		}
	}



	public void regenerate(HttpServletRequest request) {
		try {
			if (session != null) {
				HashMap<String,Object> saved_session = new HashMap<String,Object>();
				Enumeration sessionparameters = session.getAttributeNames();
				while (sessionparameters.hasMoreElements()) {
					String key = "" + sessionparameters.nextElement();
					saved_session.put(key, session.getAttribute(key));
				}
		                request.getSession(true).invalidate();
		                session = request.getSession(true);
				Iterator saved_sessionparameters = saved_session.keySet().iterator();
				while (saved_sessionparameters.hasNext()) {
					String key = "" + saved_sessionparameters.next();
					session.setAttribute(key, saved_session.get(key));
				}
			}
		} catch (Exception e) {
		}
	}



	public boolean exists(String name) {
		try {
			if ((session != null) && (session.getAttribute(name) != null)) {
				return true;
			} else if (mysession.containsKey(name)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			if (mysession.containsKey(name)) {
				return true;
			} else {
				return false;
			}
		}
	}



	public String get(String name) {
		try {
			if ((session != null) && (session.getAttribute(name) != null)) {
				return "" + session.getAttribute(name);
			} else if (mysession.containsKey(name)) {
				return "" + mysession.get(name);
			} else {
				return "";
			}
		} catch (Exception e) {
			if (mysession.containsKey(name)) {
				return "" + mysession.get(name);
			} else {
				return "";
			}
		}
	}



	public void set(String name, String value) {
		try {
			if (session != null) {
				session.setAttribute(name, value);
				mysession.remove(name);
				if (! session.getAttribute("DEBUG").equals("")) System.out.println("DEBUG:Session:set:"+session.getAttribute("DEBUG")+":"+name+"="+value);
			} else {
				mysession.put(name, value);
				if ((mysession.get("DEBUG") != null) && (! mysession.get("DEBUG").equals(""))) System.out.println("DEBUG:Session:set:"+mysession.get("DEBUG")+":"+name+"="+value);
			}
		} catch (Exception e) {
			mysession.put(name, value);
			if ((mysession.get("DEBUG") != null) && (! mysession.get("DEBUG").equals(""))) System.out.println("DEBUG:Session:set:"+mysession.get("DEBUG")+":"+name+"="+value);
		}
	}



	public void remove(String name) {
		try {
			if (session != null) {
				session.removeAttribute(name);
				mysession.remove(name);
			} else {
				mysession.remove(name);
			}
		} catch (Exception e) {
			mysession.remove(name);
		}
	}



	public void copy(Session othersession) {
		try {
			if ((othersession != null) && (othersession.getSession() != null)) {
				Enumeration sessionparameters = othersession.getSession().getAttributeNames();
				while (sessionparameters.hasMoreElements()) {
					String key = "" + sessionparameters.nextElement();
					set(key, othersession.get(key));
				}
			}
		} catch (Exception e) {
		}
	}



}
