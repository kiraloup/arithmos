package com.example.arithmos.db;

public interface RepositoryCallback<T> {
    void onComplete(Result<T> result);
}
