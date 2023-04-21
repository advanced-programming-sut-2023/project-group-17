package Utils;

import java.util.Arrays;
import java.util.Random;

public class Randoms {
    public static String generateRandomPassword() {
        String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String number = "0123456789";
        String specialCharacters = "!@#$%^&*_=+-/";
        int maxLength = 12;
        int minlength = 6;

        Random random = new Random();
        int passwordLength = random.nextInt(maxLength - minlength + 1) + minlength;
        char[] password = new char[passwordLength];
        password[getNextIndex(random, passwordLength, password)] = uppercase.charAt(random.nextInt(uppercase.length()));
        password[getNextIndex(random, passwordLength, password)] = lowercase.charAt(random.nextInt(lowercase.length()));
        password[getNextIndex(random, passwordLength, password)] = number.charAt(random.nextInt(number.length()));
        password[getNextIndex(random, passwordLength, password)] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        for(int i = 0; i < passwordLength; i++) {
            if(password[i] == 0)
                password[i] = lowercase.charAt(random.nextInt(lowercase.length()));
        }
        return Arrays.toString(password);
    }
    private static int getNextIndex(Random random, int length, char[] password) {
            int index = random.nextInt(length);
            while(password[index = random.nextInt(length)] != 0);
            return index;
    }

    static String[] Slogans = {
        "Give up or I will make you give up",
        "I shall have my revenge, in this life or in next",
        "Greatest player of all time is playing",
        "I am the reason of your nightmares",
        "You will remember me as a legend",
        "join me or die"
    };

    public static String generateRandomSlogan() {
        Random random = new Random();
        int index = random.nextInt(Slogans.length);
        return Slogans[index];
    }

}
