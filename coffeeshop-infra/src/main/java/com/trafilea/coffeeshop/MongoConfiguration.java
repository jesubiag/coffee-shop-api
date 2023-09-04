package com.trafilea.coffeeshop;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
public class MongoConfiguration extends AbstractReactiveMongoConfiguration {

    @Bean
    public MongoClient reactiveMongoClient() {
        return MongoClients.create("mongodb://coffeeshop:password@mongodb:27017");
    }

    @Override
    protected String getDatabaseName() {
        return "coffeeshop";
    }

}
