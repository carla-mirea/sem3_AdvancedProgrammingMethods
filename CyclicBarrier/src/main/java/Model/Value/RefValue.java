package Model.Value;

import Model.Type.RefType;
import Model.Type.Type;

public class RefValue implements Value{
    int address;
    Type locationType;

    public RefValue(int address, Type locationType){
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public Value deep_copy() {
        return new RefValue(address, locationType.deep_copy());
    }

    public int getAddress(){
        return this.address;
    }

    public Type getLocationType(){
        return locationType;
    }

    public String toString(){
        return "(" + address + "," + locationType + ")";
    }
}
