package com.iliketo.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation indica que o atributo contem o nome de uma 'coluna' na tabela ou database do banco de dados
 * @author OSVALDIMAR
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnILiketo {
	
	String name();

}
