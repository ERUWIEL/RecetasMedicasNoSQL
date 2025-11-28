package com.mycompany.resetasmedicasnosql.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author martinbl
 */
public enum MongoClientProvider {
    INSTANCE;

    private MongoClient client;
    private final String dbName = "RecetasMedicasDB";
    private final String uri = "mongodb://localhost:27017/";

    public synchronized void init() {
        if (client == null) {
            client = MongoClients.create(MongoConfig.buildSettings(this.uri));
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    client.close();
                } catch (Exception ignored) {
                }
            }));
        }
    }

    public MongoClient client() {
        if (client == null) {
            throw new IllegalStateException("MongoClientProvider no inicializado. Llama a init(uri) antes.");
        }
        return client;
    }

    public MongoDatabase database() {
        return client().getDatabase(this.dbName);
    }

    public <T> MongoCollection<T> getCollection(String collectionName, Class<T> clazz) {
        if (client == null) {
            throw new IllegalStateException("MongoClientProvider no inicializado. Llama a init(uri) antes.");
        }

        MongoDatabase db = client.getDatabase(this.dbName);
        return db.getCollection(collectionName, clazz);
    }
}
