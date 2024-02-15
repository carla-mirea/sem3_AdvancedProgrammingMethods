package Model.Value;

import Model.Type.StringType;
import Model.Type.Type;

import java.util.Objects;

public class StringValue implements Value{
    private String str;

    public StringValue(String str){
        this.str = str;
    }
    public StringValue(){
        this.str = "";
    }
    public String getValue(){
        return str;
    }
    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deep_copy() {
        return new StringValue(str);
    }


    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof StringValue) )
            return false;
        StringValue string_obj = (StringValue) o;
        return Objects.equals(string_obj.str, this.str);
    }

    public String toString(){
        return str;
    }
}
