package cn.mzmzup;

import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static void main(String[] args) {
        ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>(128);
        for(int i = 1; i <= 10; i++) {
            concurrentHashMap.put(i, i);
        }
        System.out.println("now concurrentHashMap: " + concurrentHashMap);
        System.out.println("===========================================");
        Integer reduce = concurrentHashMap.reduce(5,
                (key, value) -> { return value; },
                (obj1, obj2) -> { return obj1 + obj2;}
        );
        System.out.println("reduce(parallelismThreshold, transformer, reducer): " + reduce);

    }
}
