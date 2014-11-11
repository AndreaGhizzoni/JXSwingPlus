package it.hackcaffebabe.jxswingplus.table.model.exp;

/**
 * Simple interface to implements if you want that your object will be
 * displayable in a
 * {@link it.hackcaffebabe.jxswingplus.table.model.exp.JXObjectModelV2}
 */
public interface Displayable {
    /**
     * Return an array of Object that represents the row to display in a table
     * of the displayable object.
     * @return {@link Object} array of object.
     */
    public Object[] getDisplayRaw();
}
