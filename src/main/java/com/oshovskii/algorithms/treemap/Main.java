package com.oshovskii.algorithms.treemap;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        creatorAndCheckBalancedCustomTreeMap(20,6);
    }

    private static void creatorAndCheckBalancedCustomTreeMap(int countTree, int depthTree) {
        int balanced = 0;
        for (int i = 0; i < countTree; i++) {
            CustomTreeMap<Integer, Integer> map = new CustomTreeMap<>();
            while (map.depth() < depthTree){
                map.put(insertNumber(100),0);
            }
            if (map.isBalanced()){
                balanced++;
            }
        }

        System.out.println("from " + countTree + " MyTreeMap balanced is " + balanced + "\r\nnot balanced is " + (countTree - balanced) + " trees");
    }

    private static Integer insertNumber(int i) {
        Random random = new Random();
        if (random.nextInt(i) % 2 == 0){
            return random.nextInt(i);
        } else{
            return -random.nextInt(i);
        }
    }
}
