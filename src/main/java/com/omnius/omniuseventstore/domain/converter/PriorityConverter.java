package com.omnius.omniuseventstore.domain.converter;

import com.omnius.omniuseventstore.converter.AbstractEnumUserType;
import com.omnius.omniuseventstore.domain.Priority;

public class PriorityConverter extends AbstractEnumUserType {
    @Override
    public Class returnedClass() {
        return Priority.class;
    }
}
