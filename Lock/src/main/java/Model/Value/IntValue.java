package Model.Value;

import Model.Type.IntType;
import Model.Type.Type;

public class IntValue implements Value{
    int val;

    public IntValue(){
        val = 0;
    }

    public IntValue(int val){
        this.val = val;
    }

    public String toString(){
        return Integer.toString(val);
    }
    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deep_copy() {
        return new IntValue(val);
    }

    public boolean equals(Object o){
        if (this == o ) return true;
        if (!(o instanceof IntValue))
            return false;
        IntValue value = (IntValue) o;
        return value.val == this.val;
    }

    public int getValue(){
        return val;
    }
}
