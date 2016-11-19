package com.wolfogre.service;

import com.mongodb.MongoClient;
import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.MongoCollection;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wolfogre on 11/20/16.
 */
@Service
public class FriendshipService {

    private final MongoCollection mongoCollection;

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    public FriendshipService(MongoClient mongoClient) {
        mongoCollection = mongoClient.getDatabase("carddog").getCollection("friendship");
        mongoCollection.createIndex(new Document("first", 1).append("second", 1));

        ListIndexesIterable<Document> indexInfo = mongoCollection.listIndexes();
        logger.info("indexes of carddog.friendship");
        for(Document document : indexInfo)
            logger.info(document.toString());
    }

    Integer get(String first, String second) {
        if(first.equals(second))
            return null;
        if(first.compareTo(second) > 0) {
            String temp = first;
            first = second;
            second = temp;
        }
        Document result = ((Document)(mongoCollection.find(new Document("first", first).append("second", second)).first()));
        return result == null ? null :result.get("value", Integer.class);
    }

    void set(String first, String second, Integer value){
        if(first.equals(second))
            return;
        if(first.compareTo(second) > 0) {
            String temp = first;
            first = second;
            second = temp;
        }
        if(mongoCollection.updateOne(
                new Document("first", first).append("second", second),
                new Document("$set", new Document("first", second).append("second", first).append("value", value)))
           .getMatchedCount() == 0) {
            mongoCollection.insertOne(new Document("first", second).append("second", first).append("value", value));
        }
    }
}
