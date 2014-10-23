package it.hackcaffebabe.jxswingplus.table.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * This is the abstract class to extends if you want to display an object into {@link JXObjectModel}.
 * The child class must call in the constructor:
 * <pre>{@code
 * super( MyFantasticChildClass.class); (because this object gets all private field's names to display as column name.)
 * setColumnNames( myFantasticArrayOfString ); (To display your custom column names use in the constructor)
 * }</pre>
 *    
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public abstract class DisplayableObject implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Class< ? > subClass;
	private List<String> columnNames = new ArrayList<String>();
	private List<Integer> columnNotEditable = new ArrayList<Integer>();

	/**
	 * Instance an adapter to display an object into a {@link JXObjectModel}
	 * @param subClass {@link Class} class of object to display.
	 * @throws IllegalArgumentException if class is null.
	 */
	public DisplayableObject(Class< ? > subClass) throws IllegalArgumentException{
		this.setClass( subClass );
		this.parseField();
	}

//====================================================================================================//
// METHOD
//====================================================================================================//	
	/* This method, through type reflection, parse all subclass fields. */
	private void parseField(){
		int index = 0;
		for(Field f: subClass.getDeclaredFields()) {
			this.columnNames.add( f.getName() );
			this.columnNotEditable.add( index++ );
		}
	}

//====================================================================================================//
// GETTER
//====================================================================================================//	
	/**
	 * This method returns an array of Object that represents the row of table.
	 * @return {@link Object} array that represents the row of table.
	 */
	public abstract Object[] getDisplayRow();

	/**
	 * This method returns a list of column names.
	 * @return {@link List} of column names.
	 */
	public List<String> getColumnNames(){
		return columnNames;
	}

	/**
	 * This method returns the list of index of not editable columns.
	 * @return {@link List} of index of not editable columns.
	 */
	public List<Integer> getColumnNotEditable(){
		return this.columnNotEditable;
	}

//====================================================================================================//
// SETTER
//====================================================================================================//	
	/**
	 * This method sets the name of table column to display the array of <code>getDisplayRow()</code>.
	 * @param columnNames {@link String} array of strings.
	 * @throws IllegalArgumentException if array given is null or empty.
	 */
	public void setColumnNames(String[] columnNames) throws IllegalArgumentException{
		if(columnNames == null || columnNames.length == 1)
			throw new IllegalArgumentException( "Array of column names can not be null or empty." );

		this.columnNames = new ArrayList<String>();
		this.columnNotEditable = new ArrayList<Integer>();
		int i = 0;
		for(String s: columnNames) {
			this.columnNames.add( s );
			this.columnNotEditable.add( i++ );
		}
	}

	/* Set the subclass. */
	private void setClass(Class< ? > c) throws IllegalArgumentException{
		if(c == null)
			throw new IllegalArgumentException( "Class to set can not be null." );
		this.subClass = c;
	}
}
