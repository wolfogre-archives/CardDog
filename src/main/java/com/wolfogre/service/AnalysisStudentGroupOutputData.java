package com.wolfogre.service;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wolfogre on 11/21/16.
 */
@Service
public class AnalysisStudentGroupOutputData {
    private final Logger logger = Logger.getLogger(this.getClass());

    private final FriendshipService friendshipService;

    private final MongoCollection mongoCollection;

    @Autowired
    public AnalysisStudentGroupOutputData(FriendshipService friendshipService, MongoClient mongoClient) {
        this.friendshipService = friendshipService;
        mongoCollection = mongoClient.getDatabase("carddog").getCollection("friendship");
    }

    public void run(String... strings) throws Exception {
        Iterator<Document> result = mongoCollection.find(new Document("value", new Document("$gt", 8))).iterator();

        HashMap<String, Integer> nodes = new HashMap<>();

        int[][] link = new int[400][400];

        while(result.hasNext()) {
            Document d = result.next();
            String first = d.get("first", String.class);
            String second = d.get("second", String.class);
            Integer value = d.get("value", Integer.class);
            if(!nodes.containsKey(first))
                nodes.put(first, nodes.size());
            if(!nodes.containsKey(second))
                nodes.put(second, nodes.size());
            link[nodes.get(first)][nodes.get(second)] = value;
            link[nodes.get(second)][nodes.get(first)] = value;
        }

        PrintWriter nodeOutput = new PrintWriter("output/AnalysisStudentGroupOutputData-node.txt");
        for(Map.Entry<String, Integer> e : nodes.entrySet()) {
            nodeOutput.println(e.getValue() + " # " + e.getKey());
        }
        nodeOutput.close();

        PrintWriter linkOutput = new PrintWriter("output/AnalysisStudentGroupOutputData-link.txt");
        for(int i = 0; i < nodes.size(); ++i) {
            for(int j = 0; j < i; ++j) {
                if(link[i][j] != 0)
                    linkOutput.println(i + " # " + j + " $ " + (link[i][j] - 9) * 0.083333333);
            }
        }
        linkOutput.close();
    }
}
