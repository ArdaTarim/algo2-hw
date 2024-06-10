import java.io.BufferedWriter;
import java.io.FileWriter;

public class Tester {
    public static void createTest(int questionNumber) {

        // Tests for question 2
        String question2Tests[] = new String[6];
        question2Tests[0] = "5 5 1 2 2 3 3 4 4 5 5 1 1 3";
        question2Tests[1] = "3 3 1 2 2 3 3 1 1 2";
        question2Tests[2] = "1 0 1 1";
        question2Tests[3] = "7 7 1 2 2 3 3 5 5 6 6 7 7 1 3 4 1 4";
        question2Tests[4] = "5 6 1 2 2 3 1 3 3 4 3 5 4 5 1 5";
        question2Tests[5] = "7 7 1 4 4 2 2 3 3 1 5 7 7 6 6 5 1 6";

        // Tests for question 1
        String question1Tests[] = new String[5];
        question1Tests[0] = "7 11 3 5 1 2 1 3 1 4 2 3 3 4 3 5 3 6 2 6 4 5 5 6 6 7 1 7";
        question1Tests[1] = "6 6 3 5 1 2 1 3 2 3 4 5 5 6 4 6 1 5";
        question1Tests[2] = "1 0 3 5 1 1";
        question1Tests[3] = "5 6 3 5 1 5 1 4 4 5 2 4 2 5 4 5 1 5";
        question1Tests[4] = "8 8 3 5 1 4 4 8 8 7 7 6 6 5 5 3 3 2 2 1 1 3";

        switch (questionNumber) {
            case 1:
                for (int i = 0; i < question1Tests.length; i++) {
                    String fileName = "test-q1" + i + ".txt";
                    try {
                        FileWriter fileWriter = new FileWriter(fileName);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        bufferedWriter.write(question1Tests[i]);
                        bufferedWriter.close();

                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
                break;

            case 2:
                for (int i = 0; i < question2Tests.length; i++) {
                    String fileName = "test-q2-" + i + ".txt";
                    try {
                        FileWriter fileWriter = new FileWriter(fileName);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        bufferedWriter.write(question2Tests[i]);
                        bufferedWriter.close();

                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
                break;

            default:
                System.out.println("Invalid question number");
                break;
        }
    }

    public static void main(String[] args) {
        createTest(1);
        createTest(2);
    }
}
