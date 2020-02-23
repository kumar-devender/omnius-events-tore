package com.omnius.omniuseventstore.domain.converter;

import com.omnius.omniuseventstore.converter.AbstractEnumUserType;
import com.omnius.omniuseventstore.domain.Status;

public class StatusConverter extends AbstractEnumUserType {
    @Override
    public Class returnedClass() {
        return Status.class;
    }
}
