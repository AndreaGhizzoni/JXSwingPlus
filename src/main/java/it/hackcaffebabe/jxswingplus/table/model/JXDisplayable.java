package it.hackcaffebabe.jxswingplus.table.model;

/**
 * Simple interface to implements if you want that your object will be
 * displayable in a
 * {@link JXObjectModel}
 */
public interface JXDisplayable {
    /**
     * Return an array of Object that represents the row to display in a table
     * of the displayable object.
     * @return {@link Object} array of object.
     */
    public Object[] getDisplayRaw();
}
