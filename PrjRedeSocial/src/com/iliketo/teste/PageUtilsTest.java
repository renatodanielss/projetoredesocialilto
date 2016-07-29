package com.iliketo.teste;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

import com.iliketo.dao.GenericDAO;
import com.iliketo.model.Collection;
import com.iliketo.util.ModelILiketo;

import HardCore.DB;

public class PageUtilsTest {

	@Test
	public void testPageListEntryBinding() 
			throws InstantiationException, IllegalAccessException, 
			ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		String className = "Collection";
		String idIliketoo = "1";
		if(className != null && !className.isEmpty()
				&&idIliketoo != null && !idIliketoo.isEmpty()){
				Class clazz = Class.forName("com.iliketo.model." + className);
				Class clazzDAO = Class.forName("com.iliketo.dao." + className + "DAO");
				Class array[] = {DB.class, HttpServletRequest.class};
				GenericDAO dao = (GenericDAO)clazzDAO.getConstructor(array).newInstance(null, null);
				Object c = new Collection();
				ModelILiketo model = new ModelILiketo(null, null);
				model.addAttribute("objeto", clazz.cast(c));
		}
		
	}

}
