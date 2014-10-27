package it.hackcaffebabe.jxswingplus.tableObj;

import it.hackcaffebabe.jxswingplus.table.model.DisplayableObject;


/**
 * Simple object to show how table with object works.
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class MySimpleObject extends DisplayableObject
{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String foo;
	private Float eggs;
	private int egg = 1;

	public MySimpleObject(Integer id, String foo, Float eggs){
		super( MySimpleObject.class );
		// override the default column name from the name of all the field to below strings.
		setColumnNames( new String[] { "Identifier", "STUFF", "trash", "egg" } );

		this.id = id;
		this.foo = foo;
		this.eggs = eggs;
	}

	@Override
	public Object[] getDisplayRow(){
		return new Object[] { id, foo, eggs, egg };
	}

	public Integer getId(){
		return id;
	}

	public String getFoo(){
		return foo;
	}

	public Float getEggs(){
		return eggs;
	}
}
