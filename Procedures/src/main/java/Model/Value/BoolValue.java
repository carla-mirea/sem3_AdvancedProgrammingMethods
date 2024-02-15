package Model.Value;

import Model.Type.BoolType;
import Model.Type.Type;

public class BoolValue implements Value{
    boolean val;
    public BoolValue(boolean v){
        this.val = v;
    }
    public BoolValue(){
        val = false; // default value
    }

    public boolean getValue(){
        return val;
    }

    public String toString(){
        if (val)
            return "true";
        return "false";
    }

    @Override
    public Type getType(){
        return new BoolType();
    }

    @Override
    public Value deep_copy() {
        return new BoolValue(val);
    }

    public boolean equals(Object o){
        if (this == o) return true; //same object
        if (!(o instanceof BoolValue))
            return false;
        BoolValue value_of_object = (BoolValue) o;
        return value_of_object.val == this.val;

    }

}
