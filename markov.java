import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
class markov {
     public static void main(String args[])throws IOException {
          markov inst = new markov();
          Scanner sc = new Scanner(System.in);
          String file = "hello.txt";
          String input = readFromFile(file);
          System.out.println();
          System.out.println();
          System.out.println();
          System.out.println();
          HashMap<Character, Integer> map = inst.makeMap(input);
          HashMap<Integer, Character> revMap = inst.revMap(map);
          int matrix[][] = inst.makeMatrix(map,input);
          double pmatrix[][] = inst.makeProbabilityMatrix(matrix);
          char lastchar = input.charAt(input.length() - 1);
          int lastindex = map.get(lastchar);
          char c = revMap.get(inst.generateRandomVariable(pmatrix[lastindex]));
          System.out.print(c);
          for (int i = 0; i < 100; i++) {
               lastchar = c;
               lastindex = map.get(lastchar);
               c = revMap.get(inst.generateRandomVariable(pmatrix[lastindex]));
               System.out.print(c);
          }
     }
     public HashMap<Integer, Character> revMap(HashMap<Character, Integer> map) {
          HashMap<Integer, Character> reverseMap = new HashMap<>();
          for (char key : map.keySet()) {
               reverseMap.put(map.get(key), key);
          }
          return reverseMap;
     }
     public HashMap<Character, Integer> makeMap(String input) {
          HashMap<Character, Integer> map = new HashMap<>();
          for (int i = 0; i < input.length(); i++) {
               if (map.containsKey(input.charAt(i)) == false) {
                    map.put(input.charAt(i), map.size());
               }
          }
          return map;
     }
     public int[][] makeMatrix(HashMap<Character, Integer> map,
                                                     String input) {
          int matrix[][] = new int[map.size()][map.size()];
          for (int i = 0; i < input.length() - 1; i++) {
               matrix[map.get(input.charAt(i))][map.get(input.charAt(i + 1))]++;
          }
          return matrix;
     }
     public double[][] makeProbabilityMatrix(int matrix[][]) {
          double pmatrix[][] = new double[matrix.length][matrix.length];
          for (int i = 0; i < matrix.length; i++) {
               double total = 0;
               for (int j = 0; j < matrix.length; j++) {
                    total += matrix[i][j];
               }
               for (int j = 0; j < matrix.length; j++) {
                    pmatrix[i][j] = (double)matrix[i][j] / total;
               }
          }
          return pmatrix;
     }
     public int generateRandomVariable(double arr[]) {
          double rand = Math.random();
          double percent[] = new double[arr.length];
          percent[0] = arr[0];
          for (int i = 1; i < arr.length; i++) {
               percent[i] = arr[i] + percent[i - 1];
          }
          for (int i = 0; i < percent.length; i++) {
               if (rand <= percent[i]) {
                    return i;
               }
          }
          return 0;
     }
     public static String readFromFile(String filePath)throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }
}
