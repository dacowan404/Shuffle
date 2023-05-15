import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Shuffle {
    public static void main(String[] args) {
        boolean result = false;

        // reads in from file
        if (args.length == 1)  {
            try {
                String fileName = args[0];
                File file = new File(fileName);
                Scanner myScanner = new Scanner(file);
                String[] inputWords = myScanner.nextLine().split(" ");
                result = compare(inputWords[0], inputWords[1], inputWords[2]);
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
                e.printStackTrace();
                System.exit(1);
            }
        // reads data from command line args
        } else if (args.length == 3) {
            result = compare(args[0], args[1], args[2]);
        // incorrect data input
        } else {
            System.out.println("Incorrect input given, see README");
            System.exit(2);
        }

        try {
            File outputFile = new File("output.txt");
            outputFile.createNewFile();
            FileWriter myWriter = new FileWriter("output.txt");
            if (result) {
                myWriter.write("CORRECT");
            } else {
                myWriter.write("INCORRECT");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Unable to create output file");
            e.printStackTrace();
        }

    }

    // calls compareHelper method to determine if word is shuffle or not
    public static boolean compare(String word1, String word2, String shuffleWord) {
        if (shuffleWord.length() == 0) {
            return true;
        }
        if (word1.length() == 0 || word2.length() == 0) {
            return false;
        }
        return compareHelper(word1.toUpperCase(), word2.toUpperCase(), shuffleWord.toUpperCase(), 0, 0, 0);

    }

    // input: 3 strings and 3 int representing current location (char) for each String
    // output: boolean
    // determines if words are shuffle by keeping track of location for each word and moving to next letter if match is found
    // if both are possible then recursively check both options
    public static boolean compareHelper(String word1, String word2, String shuffleWord, int loc1, int loc2, int locShuffle){
        char char1 = word1.charAt(loc1);
        char char2 = word2.charAt(loc2);

        // loops over every letter from shuffleWord
        for ( int i = locShuffle; i<shuffleWord.length(); i++) {

            if (char1 == shuffleWord.charAt(i)) {

                // if current letter from both input words are possible then recursively checks each option
                if (char1 == char2) {
                    if (compareHelper(word1, word2, shuffleWord, loc1+1, loc2, i+1)) {
                        return true;
                    } else if (compareHelper(word1, word2, shuffleWord, loc1, loc2+1, i+1)) {
                        return true;
                    } else {
                        return false;
                    }

                // if only letter from word1 is correct, then increments loc1
                } else {
                    loc1++;
                    if (loc1 < word1.length()) {
                        char1 = word1.charAt(loc1);
                    } else {
                        char1 = ' ';
                    }
                }
            }

            // if only letter from word2 is correct, then increments loc2
            else if (char2 == shuffleWord.charAt(i)) {
                loc2++;
                if (loc2 < word2.length()) {
                    char2 = word2.charAt(loc2);
                } else {
                    char2 = ' ';
                }

            // if neither letter are correct, returns false
            } else {
                return false;
            }
        }

        // if all letters in shuffleWord have been verified, returns true
        return true;
    }
}