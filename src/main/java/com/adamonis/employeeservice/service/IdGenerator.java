package com.adamonis.employeeservice.service;

import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A class that generates unique IDs based on a file that stores the last used ID.
 */
public class IdGenerator {

    private final String idFilePath;
    private final AtomicLong currentId;

    /**
     * Constructs an IdGenerator with the given file path for storing the last used ID.
     *
     * @param idFilePath the path to the file that stores the last used ID
     */
    public IdGenerator(String idFilePath) {
        this.idFilePath = idFilePath;
        this.currentId = new AtomicLong(loadLastId());
    }

    @SneakyThrows
    private long loadLastId() {
        File file = new File(idFilePath);
        if (!file.exists()) {
            return 0L;
        }

        String lastIdStr = new String(Files.readAllBytes(Paths.get(idFilePath)));
        return Long.parseLong(lastIdStr.trim());
    }

    @SneakyThrows
    private void saveCurrentId(long id) {
        Files.write(Paths.get(idFilePath), String.valueOf(id).getBytes());
    }

    /**
     * Retrieves the next unique ID, increments the current ID, and saves it to the file.
     *
     * @return the next unique ID
     */
    public synchronized long getNextId() {
        long id = currentId.incrementAndGet();
        saveCurrentId(id);
        return id;
    }
}