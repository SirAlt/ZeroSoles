package com.example.zerosoles.data.dao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {
    @NonNull
    List<T> getAll() throws SQLException;

    @Nullable
    T getById(long id) throws SQLException;

    @NonNull
    List<T> getByName(String name) throws SQLException;

    int add(T... entity) throws SQLException;

    int update(T... entity) throws SQLException;

    int delete(T... entity) throws SQLException;
}
