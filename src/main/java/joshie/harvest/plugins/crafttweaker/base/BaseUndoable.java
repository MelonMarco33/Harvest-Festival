package joshie.harvest.plugins.crafttweaker.base;

import minetweaker.IUndoableAction;

public abstract class BaseUndoable implements IUndoableAction {
    @Override
    public boolean canUndo() {
        return true;
    }

    public abstract String getDescription();

    @Override
    public String describe() {
        return "[Harvest Festival] " + getDescription();
    }

    @Override
    public String describeUndo() {
        return "";
    }

    @Override
    public Object getOverrideKey() {
        return null;
    }
}
