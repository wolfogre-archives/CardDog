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

    private final MongoCollection friendshipCollection;

    private final MongoCollection recordCollection;

    private final MongoCollection commandCollection;

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    public FriendshipService(MongoClient mongoClient) {
        friendshipCollection = mongoClient.getDatabase("carddog").getCollection("friendship");
        friendshipCollection.createIndex(new Document("first", 1).append("second", 1));

        recordCollection = mongoClient.getDatabase("carddog").getCollection("record");

        commandCollection = mongoClient.getDatabase("carddog").getCollection("command");

        ListIndexesIterable<Document> indexInfo = friendshipCollection.listIndexes();
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
        Document result = ((Document)(friendshipCollection.find(new Document("first", first).append("second", second)).first()));
        return result == null ? null : result.get("value", Integer.class);
    }

    void set(String first, String second, Integer value){
        if(first.equals(second))
            return;
        if(first.compareTo(second) > 0) {
            String temp = first;
            first = second;
            second = temp;
        }
        if(friendshipCollection.updateOne(
                new Document("first", first).append("second", second),
                new Document("$set", new Document("first", first).append("second", second).append("value", value)))
           .getMatchedCount() == 0) {
            friendshipCollection.insertOne(new Document("first", first).append("second", second).append("value", value));
        }
    }

    boolean isRecorded(String date) {
        return recordCollection.count(new Document("date", date)) != 0;
    }

    void recorded(String date) {
        recordCollection.insertOne(new Document("date", date));
    }

    boolean needStop() {
        Document result = ((Document)(commandCollection.find(new Document("command", "shutdown")).first()));
        return result != null && (Boolean)(result.get("value"));
    }
}
