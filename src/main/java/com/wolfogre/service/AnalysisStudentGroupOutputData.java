package com.wolfogre.service;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by wolfogre on 11/21/16.
 */
@Service
public class AnalysisStudentGroupOutputData {
    private final Logger logger = Logger.getLogger(this.getClass());

    private final FriendshipService friendshipService;

    @Autowired
    public AnalysisStudentGroupOutputData(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    public void run(String... strings) throws Exception {

        int maxValue = friendshipService.getMaxValue();

        logger.info("Max value : " + maxValue);

        final int MAX_NODES = 200;

        int lowLine = 0;
        long count;
        while((count = friendshipService.countValueNotLessThan(lowLine)) > MAX_NODES) {
            ++lowLine;
            logger.info("Not less than " + lowLine + " : " + count);
        }


        Iterator<Document> result = friendshipService.getValueNotLessThan(lowLine);

        HashMap<String, Integer> nodes = new HashMap<>();

        HashMap<String, HashSet<Integer>> nodeLinks = new HashMap<>();

        int[][] link = new int[MAX_NODES * 2][MAX_NODES * 2];

        while(result.hasNext()) {
            Document d = result.next();
            String first = d.get("first", String.class);
            String second = d.get("second", String.class);
            Integer value = d.get("value", Integer.class);
            if(!nodes.containsKey(first)) {
                nodes.put(first, nodes.size());
                nodeLinks.put(first, new HashSet<>());
            }
            if(!nodes.containsKey(second)){
                nodes.put(second, nodes.size());
                nodeLinks.put(second, new HashSet<>());
            }

            link[nodes.get(first)][nodes.get(second)] = value;
            nodeLinks.get(first).add(nodes.get(second));

            link[nodes.get(second)][nodes.get(first)] = value;
            nodeLinks.get(second).add(nodes.get(first));
        }

        PrintWriter output = new PrintWriter("output/AnalysisStudentGroupOutputData.json");
        output.println("{ \"nodes\": [");

        int maxSize = 0;
        for(Map.Entry<String, HashSet<Integer>> e : nodeLinks.entrySet()) {
            if(e.getValue().size() > maxSize)
                maxSize = e.getValue().size();
        }

        Map.Entry<String, Integer>[] nodeArray = new Map.Entry[nodes.size()];
        nodeArray = nodes.entrySet().toArray(nodeArray);

        Arrays.sort(nodeArray, new NodeComparator());

        output.println("{" + MessageFormat.format("\"index\":{0}, \"links\":[{1}], \"score\":{2}, \"level\":{3}, \"label\":\"{4}\", \"id\":{5}",
                nodeArray[0].getValue(),
                getJsonArray(nodeLinks.get(nodeArray[0].getKey())),
                nodeLinks.get(nodeArray[0].getKey()).size() * (5.0 / maxSize) + 5,
                1,
                nodeArray[0].getKey(),
                nodeArray[0].getValue()
        )
                + "}");

        for(int i = 0 + 1; i < nodeArray.length; ++i) {
            output.println(",{" + MessageFormat.format("\"index\":{0}, \"links\":[{1}], \"score\":{2}, \"level\":{3}, \"label\":\"{4}\", \"id\":{5}",
                    nodeArray[i].getValue(),
                    getJsonArray(nodeLinks.get(nodeArray[i].getKey())),
                    nodeLinks.get(nodeArray[i].getKey()).size() * (5.0 / maxSize) + 5,
                    1,
                    nodeArray[i].getKey(),
                    nodeArray[i].getValue()
                    )
            + "}");
        }

        output.println("], \"links\": [");

        int firstI = 0, firstJ = 0;
        boolean stop = false;
        for(int i = 0; i < nodes.size() && !stop; ++i) {
            for (int j = 0; j < i && !stop; ++j) {
                if(link[i][j] != 0) {
                    output.println("{" + MessageFormat.format("\"source\":{0}, \"target\":{1}, \"weight\":{2}",
                            i,
                            j,
                            (link[i][j] - lowLine) * (1.0 / (maxValue - lowLine))
                    )
                            + "}");
                    firstI = i;
                    firstJ = j;
                    stop = true;
                }
            }
        }

        for(int i = 0; i < nodes.size(); ++i) {
            for(int j = 0; j < i; ++j) {
                if(link[i][j] != 0) {
                    if(firstI == i && firstJ == j)
                        continue;
                    output.println(",{" + MessageFormat.format("\"source\":{0}, \"target\":{1}, \"weight\":{2}",
                            i,
                            j,
                            (link[i][j] - lowLine) * (1.0 / (maxValue - lowLine))
                    )
                            + "}");
                }
            }
        }

        output.println("]}");
        output.close();
    }

    private String getJsonArray(HashSet<Integer> input) {
        if(input.isEmpty())
            return "[]";
        Integer[] array = new Integer[input.size()];
        array = input.toArray(array);
        StringBuilder result = new StringBuilder();
        result.append(array[0]);
        for(int i = 0 + 1; i < array.length; ++i) {
            result.append(",").append(array[i]);
        }
        return result.toString();
    }

    class NodeComparator implements Comparator<Map.Entry<String, Integer>> {

        @Override
        public int compare(Map.Entry<String, Integer> t1, Map.Entry<String, Integer> t2) {
            if(t1.getValue() > t2.getValue())
                return 1;
            if(t1.getValue() < t2.getValue())
                return -1;
            return 0;
        }
    }
}
