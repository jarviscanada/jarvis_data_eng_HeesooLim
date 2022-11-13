package ca.jrvs.practice.codingChallenge;

import ca.jrvs.practice.dataStructure.map.HashJMap;
import ca.jrvs.practice.dataStructure.map.JMap;

import java.util.Map;

/**
 * Ticket URL: https://www.notion.so/jarvisdev/How-to-compare-two-maps-0de363ac622347178ec2884dca3e2d95
 */
public class CompareMaps {
    public <K,V> boolean compareMapsEquals(Map<K,V> m1, Map<K,V> m2){
        return m1.equals(m2);
    }

    public <K,V> boolean compareMapsHashJMap(JMap<K,V> m1, JMap<K,V> m2){
        return m1.equals(m2);
    }
}
