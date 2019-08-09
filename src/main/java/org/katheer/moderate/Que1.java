package org.katheer.moderate;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Que1 {
   public static void main(String[] args) {
      /*
      Given a list of Strings, write a method that returns a list of all strings that start with the letter 'a' (lower case)
      and have exactly 3 letters. TIP: Use Java 8 Lambdas and Streams API's.
      */
      List<String> stringList = Arrays.asList("abdul", "aaa", "aba", "bba", "bdjks", "aaaaaa");
      List<String> result1 = stringList.stream()
            .filter(s -> s.startsWith("a") && s.length() == 3)
            .collect(Collectors.toList());
      System.out.println(result1);

      /*
      Write a method that returns a comma separated string based on a given list of integers. Each element should be preceded
      by the letter 'e' if the number is even, and preceded by the letter 'o' if the number is odd.
      For example, if the input list is (3,44), the output should be 'o3,e44'.
       */
      List<Integer> integerList = Arrays.asList(1,2,3,4);
      List<String> result2 = integerList.stream()
            .map(integer -> integer%2==0 ? "e" + integer : "o" + integer)
            .collect(Collectors.toList());
      System.out.println(result2);

   }
}
