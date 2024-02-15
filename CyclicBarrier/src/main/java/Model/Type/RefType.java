package Model.Type;

import Model.Value.RefValue;
import Model.Value.Value;

public class RefType implements Type{
    Type inner;
    public RefType(Type inner) {
        this.inner = inner;
    }

    public Type getInner() {
        return this.inner;
    }
    @Override
    public Value defaultValue() {
        return new RefValue(0, inner);
    }

    @Override
    public Type deep_copy() {
        return new RefType(this.inner.deep_copy());
    }

    public boolean equals(Object another){
        if (another instanceof RefType)
            return inner.equals(((RefType) another).getInner());
        else
            return false;
    }

    public String toString() {
        return "Ref(" + inner + ")";
    }
}
