package Model.Type;

import Model.Value.IntValue;
import Model.Value.Value;

public class IntType implements Type{


    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }

    @Override
    public Type deep_copy() {
        return new IntType();
    }

    public String toString(){
        return "int";
    }

    public boolean equals(Object o){
        return o instanceof IntType;
    }
}
