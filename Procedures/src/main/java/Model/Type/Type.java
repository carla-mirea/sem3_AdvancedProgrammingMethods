package Model.Type;

import Model.Value.Value;

public interface Type {

    Value defaultValue();
    Type deep_copy();
}
