CREATE SCHEMA IF NOT EXISTS omnius_event_store;

CREATE TYPE omnius_event_store.STATUS AS ENUM (
    'NEW',
    'CLOSED',
    'OPEN',
    'IN_PROGRESS'
);

CREATE TYPE omnius_event_store.PRIORITY AS ENUM (
    'LOW',
    'HIGH',
    'MEDIUM'
);

CREATE TABLE omnius_event_store.task (
id UUID NOT NULL PRIMARY KEY,
title TEXT,
description TEXT,
status STATUS NOT NULL,
priority PRIORITY NOT NULL,
due_date TIMESTAMP WITH TIME ZONE NOT NULL,
resolved_at TIMESTAMP WITH TIME ZONE,
created_at TIMESTAMP WITH TIME ZONE NOT NULL,
updated_at TIMESTAMP WITH TIME ZONE
);