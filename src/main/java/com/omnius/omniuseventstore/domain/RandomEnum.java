package com.omnius.omniuseventstore.domain;

import java.util.Random;

public class RandomEnum<E extends Enum> {
    Random RND = new Random();
    E[] values;

    public RandomEnum(Class<E> token) {
        values = token.getEnumConstants();
    }

    public E random() {
        return values[RND.nextInt(values.length)];
    }
}
