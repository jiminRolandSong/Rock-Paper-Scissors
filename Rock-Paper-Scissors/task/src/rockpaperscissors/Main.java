package rockpaperscissors;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static String rspAI(String[] array){
        Random random = new Random();
        int number = random.nextInt(array.length);
        return array[number];
    }

    public static int result(String user, String AI, String[] array){
        int userIndex = 0;
        for (String s : array) {
            if (Objects.equals(user, s)) {
                break;
            }
            userIndex += 1;
        }
        int halfNum = array.length / 2;
        String[] userWin = new String[halfNum];
        String[] AiWin = new String[halfNum];
        for (int n = 0; n < halfNum; n++){
            String weapon;
            if (userIndex + n + 1 > array.length - 1 ){
                weapon = array[userIndex + n + 1 - array.length];
            }else{
                weapon = array[userIndex + n + 1];
            }
            AiWin[n] = weapon;
        }
        for (int n = 0; n < halfNum; n++){
            String weapon;
            if (userIndex - n  - 1 < 0){
                weapon = array[userIndex - n - 1 + array.length];
            }else {
                weapon = array[userIndex - n - 1];
            }
            userWin[n] = weapon;
        }

        boolean userWon = false;
        for (String s: userWin){
            if(Objects.equals(s,AI)) {
                userWon = true;
                break;
            }
        }
        boolean AIWon = false;
        for (String s: AiWin){
            if(Objects.equals(s,AI)) {
                AIWon = true;
                break;
            }
        }
        boolean draw = Objects.equals(user,AI);
        if (draw) {
            System.out.printf("There is a draw (%s)", AI);
            return 50;
        }
        else if (userWon) {
            System.out.printf("Well done. The computer chose %s and failed", AI);
            return 100;
        }
        else if (AIWon) {
            System.out.printf("Sorry, but the computer chose %s", AI);
            return 0;
        }
        else {
            System.out.println("Invalid input");
            return 0;
        }

    }
    public static String[][] fileReader(String fileName){
        File file =  new File(fileName);

        int count = 0;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                count += 1;
            }

        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + file);
        }
        String[][] info = new String[count][2];
        int index = 0;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String[] user = scanner.nextLine().split(" ");
                info[index][0] = user[0];
                info[index][1] = user[1];
                index += 1;
            }

        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + file);
        }

        return info;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.printf("Hello, %s",name);
        System.out.println();
        String[][] playerInfo = fileReader("rating.txt");
        int userRating = 350;
        for (int i = 0; i < playerInfo.length; i++){
            if (Objects.equals(playerInfo[i][0], name)) {
                userRating = Integer.parseInt(playerInfo[i][1]);
                break;
            }
        }
        String weapons = scanner.nextLine();
        String[] weaponsArray = weapons.split(",");
        String[] basic = {"rock","paper","scissors"};
        if (weaponsArray.length == 1) weaponsArray = basic;
        System.out.println("Okay, let's start");
        String user = scanner.nextLine();
        while (!Objects.equals(user,"!exit")) {
            boolean invalid = true;
            for (String s: weaponsArray){
                if(Objects.equals(s,user)){
                    invalid = false;
                    break;
                }
            }
            if (Objects.equals(user,"!rating")){

                System.out.println("Your rating: " + userRating);
                user = scanner.nextLine();

            }else if(invalid){
                System.out.println("Invalid input");
                user = scanner.nextLine();
            }else {
                String AI = rspAI(weaponsArray);
                int score = result(user, AI,weaponsArray);
                userRating = userRating + score;
                System.out.println();
                user = scanner.nextLine();
            }
        }
        System.out.println("Bye!");

    }
}
