package Model.Type;

import Model.Value.StringValue;
import Model.Value.Value;

public class StringType implements Type{

    public String toString(){
        return "string";
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    @Override
    public Type deep_copy() {
        return new StringType();
    }

    public boolean equals(Object o){
        return o instanceof StringType;
    }
}
