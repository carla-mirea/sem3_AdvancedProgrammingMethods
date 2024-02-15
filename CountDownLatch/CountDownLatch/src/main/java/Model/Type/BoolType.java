package Model.Type;

import Model.Value.BoolValue;
import Model.Value.Value;

public class BoolType implements Type{

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public Type deep_copy() {
        return new BoolType();
    }

    public boolean equals(Object another){
        return another instanceof BoolType; // checks if another object is an instance of BoolType
    }

    public String toString(){
        return "bool";
    }
}
