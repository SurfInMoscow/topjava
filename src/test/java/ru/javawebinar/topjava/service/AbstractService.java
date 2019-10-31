package ru.javawebinar.topjava.service;

public interface AbstractService {
    void save() throws Exception;

    void delete() throws Exception;

    void deleteNotFound() throws Exception;

    void get() throws Exception;

    void getNotFound() throws Exception;

    void update() throws Exception;

    void getAll() throws Exception;
}
