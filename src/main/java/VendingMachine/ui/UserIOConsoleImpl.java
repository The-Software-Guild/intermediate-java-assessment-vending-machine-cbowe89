package VendingMachine.ui;

import java.math.BigDecimal;
import java.util.Scanner;

public class UserIOConsoleImpl implements UserIO {
    Scanner scanner;

    /**
     * Constructor for UserIOConsoleImpl
     */
    public UserIOConsoleImpl() {
        scanner = new Scanner(System.in);
    }

    /**
     * Takes in a message to display to the console
     * @param message String of info to display to the user
     */
    @Override
    public void print(String message) {
        System.out.println(message);
    }

    /**
     * Prints formatted string. Used for printing item name
     * and item price.
     * @param num Item number
     * @param str Item name
     * @param bigDecimal Item price
     */
    @Override
    public void printf(int num, String str, BigDecimal bigDecimal) {
        System.out.printf("%d. %-15s $%-15s\n", num, str, bigDecimal);
    }

    /**
     * Takes in a prompt to display to the console, waits
     * for an answer (String) from the user to return
     * @param prompt String, explains what info is wanted
     *               from the user
     * @return Answer to the prompt as a String
     */
    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    /**
     * Simple Method - Takes in a prompt to display to the
     * console, continually re-prompts the user with prompt
     * until they enter an integer to be returned
     * as the answer to the prompt.
     * @param prompt String, explains what info is wanted from the user
     * @return Answer to the prompt as an integer
     */
    @Override
    public int readInt(String prompt) {
        //System.out.println(prompt);
        //return Integer.parseInt(sc.nextLine());

        boolean invalidInput = true;
        int num = 0;
        while (invalidInput) {
            try {
                // Print prompt
                String stringValue= this.readString(prompt);
                // Get input, try to parse
                num = Integer.parseInt(stringValue);
                invalidInput = false;
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
        return num;
    }

    /**
     * Complex method - takes in a prompt to display to the
     * console, continually re-prompts the user with prompt until
     * they enter an integer within the specified min/max range
     * to be returned as the answer to the prompt.
     * @param prompt String, explains what info is wanted from the user
     * @param min Minimum acceptable value for return
     * @param max Maximum acceptable value for return
     * @return An integer value as an answer to the prompt within
     * the min/max range
     */
    @Override
    public int readInt(String prompt, int min, int max) {
        int num;
        do {
            System.out.println(prompt);
            num = Integer.parseInt(scanner.nextLine());
        }
        while(num < min || num > max);

        return num;
    }

    /**
     * Simple Method - Takes in a prompt to display to the
     * console, continually re-prompts the user with prompt
     * until they enter a double to be returned as an answer
     * to the prompt.
     * @param prompt String, explains what info is wanted from the user
     * @return Answer to the prompt as a double
     */
    @Override
    public double readDouble(String prompt) {
        // System.out.println(prompt);
        // return Double.parseDouble(sc.nextLine());

        boolean invalidInput = true;
        double num = 0.0;
        while (invalidInput) {
            try {
                String stringValue = this.readString(prompt);
                num = Double.parseDouble(stringValue);
                invalidInput = false;
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
        return num;
    }

    /**
     * Complex method - takes in a prompt to display to the
     * console, continually re-prompts the user with prompt until
     * they enter a double within the specified min/max range
     * to be returned as the answer to the prompt.
     * @param prompt String, explains what info is wanted from the user
     * @param min Minimum acceptable value for return
     * @param max Maximum acceptable value for return
     * @return A double value as an answer to the prompt within
     * the min/max range
     */
    @Override
    public double readDouble(String prompt, double min, double max) {
        double num;
        do {
            System.out.println(prompt);
            num = scanner.nextDouble();
        } while (num < min || num > max);

        return num;
    }

    /**
     * Simple Method - Takes in a prompt to display to the
     * console, continually re-prompts the user with prompt
     * until they enter a float to be returned as an answer
     * to the prompt.
     * @param prompt String, explains what info is wanted from the user
     * @return Answer to the prompt as a float
     */
    @Override
    public float readFloat(String prompt) {
        //System.out.println(prompt);
        //return sc.nextFloat();

        boolean invalidInput = true;
        float num = 0.0f;
        while (invalidInput) {
            try {
                String stringValue = this.readString(prompt);
                num = Float.parseFloat(stringValue);
                invalidInput = false;
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
        return num;
    }

    /**
     * Complex method - takes in a prompt to display to the
     * console, continually re-prompts the user with prompt until
     * they enter a float within the specified min/max range
     * to be returned as the answer to the prompt.
     * @param prompt String, explains what info is wanted from the user
     * @param min Minimum acceptable value for return
     * @param max Maximum acceptable value for return
     * @return A float value as an answer to the prompt within
     * the min/max range
     */
    @Override
    public float readFloat(String prompt, float min, float max) {
        float num;
        do {
            System.out.println(prompt);
            num = scanner.nextFloat();
        }
        while(num < min || num > max);

        return num;
    }

    /**
     * Simple Method - Takes in a prompt to display to the
     * console, continually re-prompts the user with prompt
     * until they enter a long to be returned as an answer
     * to the prompt.
     * @param prompt String, explains what info is wanted from the user
     * @return Answer to the prompt as a long
     */
    @Override
    public long readLong(String prompt) {
        //System.out.println(prompt);
        //return sc.nextLong();

        boolean invalidInput = true;
        long num = 0L;
        while (invalidInput) {
            try {
                String stringValue = this.readString(prompt);
                num = Long.parseLong(stringValue);
                invalidInput = false;
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
        return num;
    }

    /**
     * Complex method - takes in a prompt to display to the
     * console, continually re-prompts the user with prompt until
     * they enter a long within the specified min/max range
     * to be returned as the answer to the prompt.
     * @param prompt String, explains what info is wanted from the user
     * @param min Minimum acceptable value for return
     * @param max Maximum acceptable value for return
     * @return A long value as an answer to the prompt within
     * the min/max range
     */
    @Override
    public long readLong(String prompt, long min, long max) {
        long num;
        do {
            System.out.println(prompt);
            num = scanner.nextLong();
        }
        while(num < min || num > max);

        return num;
    }

    /**
     * Simple Method - Takes in a prompt to display to the
     * console, continually re-prompts the user with prompt
     * until they enter a BigDecimal to be returned as an answer
     * to the prompt.
     * @param prompt String, explains what info is wanted from the user
     * @return Answer to the prompt as a BigDecimal
     */
    @Override
    public BigDecimal readBigDecimal(String prompt) {
        //System.out.println(prompt);
        //return new BigDecimal(sc.nextLine());

        boolean invalidInput = true;
        BigDecimal num = new BigDecimal("0.0");
        while (invalidInput) {
            try {
                String stringValue = this.readString(prompt);
                num = new BigDecimal(stringValue);
                invalidInput = false;
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
        return num;
    }

    /**
     * Complex method - takes in a prompt to display to the
     * console, continually re-prompts the user with prompt until
     * they enter a BigDecimal within the specified min/max range
     * to be returned as the answer to the prompt.
     * @param prompt String, explains what info is wanted from the user
     * @param min Minimum acceptable value for return
     * @param max Maximum acceptable value for return
     * @return A BigDecimal value as an answer to the prompt within
     * the min/max range
     */
    @Override
    public BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max) {
        BigDecimal num;
        do {
            System.out.println(prompt);
            num = new BigDecimal(scanner.nextLine());
        }
        while(num.doubleValue() < min.doubleValue()
                || num.doubleValue() > max.doubleValue()); // removed || num = null

        return num;
    }
}
