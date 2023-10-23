import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;

class Main {
    private static HashMap<String, String> translationHashMap = new HashMap<String, String>();

    public static String getColoredDifferenceString(String translation, String answer) {
        String finalString = "";
        int answerLength = answer.length();

        for (int i = 0; i < translation.length(); i++) {
            if (i == answerLength) {
                break;
            } else {
                if (answer.substring(i, i+1).equals(translation.substring(i, i+1))) {
                    finalString += ("\u001B[32m" + answer.substring(i, i+1) + "\u001B[0m");
                } else {
                    finalString += ("\u001B[31m" + answer.substring(i) + "\u001B[0m");
                    break;
                }
            }

        }

        return finalString;
    }

    public static void main(String[] args) {
        System.out.println("----------------------------");
        System.out.println("Welcome to Language Learner!");
        label: while (true) {
            System.out.println("----------------------------");
            System.out.println("Please select a language to learn: spanish, german, french");

            Scanner scanner = new Scanner(System.in);
            String languageInput = scanner.nextLine();

            try {
                String fileString = languageInput.toLowerCase() + ".txt";

                Scanner input = new Scanner(new File(fileString));

                while(input.hasNextLine()){
                    String[] temp = input.nextLine().split(",");
                    translationHashMap.put(temp[0],temp[1]);
                }
                input.close();
            } catch(Exception e){
                System.out.println("That language is not supported");
                break;
            }

            System.out.println("----------------------------");

            System.out.println("Start practicing " + languageInput + "!");
            System.out.println("Type /quit to stop practicing!");

            Object[] integerToTranslationMaps = translationHashMap.keySet().toArray();

            label2: while (true) {
                int randIndex = new Random().nextInt(integerToTranslationMaps.length);
                Object key = integerToTranslationMaps[randIndex];

                String englishValue = key.toString();
                String translatedValue = translationHashMap.get(key);

                int randChoice = new Random().nextInt(2);

                System.out.println("----------------------------");

                if (randChoice == 1) {
                    // Show the english version first
                    System.out.print("Please translate the following to ENGLISH: ");
                    System.out.println(translatedValue);

                    while (true) {
                        String answer = scanner.nextLine();

                        if (answer.equals("/quit")) {
                            break label2;
                        }

                        if (answer.equals(englishValue)) {
                            System.out.print("Correct! ");
                            System.out.println("\u001B[32m" + answer + "\u001B[0m");
                            break;
                        } else {
                            System.out.print("Try again! ");
                            System.out.println(getColoredDifferenceString(englishValue, answer));
                        }
                    }


                } else {
                    // Show the translated version first
                    System.out.print("Please translate the following to " + languageInput.toUpperCase() + ": ");
                    System.out.println(englishValue);

                    while (true) {
                        String answer = scanner.nextLine();

                        if (answer.equals("/quit")) {
                            break label2;
                        }

                        if (answer.equals(translatedValue)) {
                            System.out.print("Correct! ");
                            System.out.println("\u001B[32m" + answer + "\u001B[0m");
                            break;
                        } else {
                            System.out.print("Try again! ");
                            System.out.println(getColoredDifferenceString(translatedValue, answer));
                        }
                    }
                }
            }


            break;
        }
    }
}