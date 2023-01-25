//This programme runs the two-player game 'Days of the Year'. 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DaysOfTheYear {

    //Variable definitions

    public static final Integer[] Y = {4, 6, 9, 11};
    public static final Integer[] X = {1, 3, 5, 7, 8, 10, 12};

    public static final int THIRTY_ONE_DAY_MAX = 31;
    public static final int MONTH_MAX = 12;
    public static final int FEB_DAY_MAX = 28;
    public static final int THIRTY_DAY_MAX = 30;

    public static final String DAY_OR_MONTH_MESSAGE = "Do you want to increase the day or the month? (day or month): ";
    public static final String PICK_DAY_MESSAGE = "Which day do you want to pick: ";
    public static final String PICK_MONTH_MESSAGE = "Which month do you want to pick: ";
    public static final String INPUT_INVALID_MESSAGE = "Input invalid, please try again!";

    private static boolean player1 = true;
    private static int day = 1;
    private static int month = 1;
    private static final String[] MONTH_NAME = {"January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"};
    private static boolean usingOrdinal = false;

    public static void main(String[] args) {
        //Command line statement (Level 3)
        checkForCommandLineArgs(args);
        Scanner in = new Scanner(System.in);
        usingOrdinal();

        //Sorts the months into Arrays and Arraylists. Y is  for months with 30 days and X for months
        // with 31 days(Level 1)

        Integer[] y = Y;
        ArrayList<Integer> thirtyDayMonths = new ArrayList<>(Arrays.asList(y));

        Integer[] x = X;
        ArrayList<Integer> thirtyOneDayMonths = new ArrayList<>(Arrays.asList(x));

        // The players' conditions of play are executed in the while loop, because these conditions have
        //to be met at every play(Level 1). If and else statements are used within the while loop to
        //separate the players' actions

        while (!(day == THIRTY_ONE_DAY_MAX && month == MONTH_MAX)) {

            if (player1) {
                System.out.println("It is Player 1's Turn!");
                System.out.println(DAY_OR_MONTH_MESSAGE);
                while (player1) {
                    try {
                        String playerChoice = in.next();
                        //Rejects player inputs that are neither day or month
                        while (!(playerChoice.equalsIgnoreCase("day") || playerChoice.equalsIgnoreCase("month"))) {
                            System.out.println(INPUT_INVALID_MESSAGE);
                            playerChoice = in.next();
                        }

                        if (playerChoice.equalsIgnoreCase("day") || playerChoice.equalsIgnoreCase("month")) {
                            if (playerChoice.equalsIgnoreCase("day")) {
                                System.out.println(PICK_DAY_MESSAGE);
                                // Validates player 1's choice of days 28,30, and 31(Level 2)
                                validateUserDayValue(in, thirtyDayMonths, thirtyOneDayMonths);
                            } else if (playerChoice.equalsIgnoreCase("month")) {
                                System.out.println(PICK_MONTH_MESSAGE);
                                //Validates player 1's choice of month(Level 2)
                                boolean userMonthValid = false;
                                validateUserMonthValue(in, thirtyDayMonths, userMonthValid);
                            }
                            checkIfGameWonAndSwitchPlayers(); //(Level 1 and 2)
                        } else {
                            System.out.println(INPUT_INVALID_MESSAGE);
                        }
                    } catch (InputMismatchException exception) {
                        System.out.println(INPUT_INVALID_MESSAGE);
                    }
                }
            } else {
                System.out.println("It is Player 2's Turn!");
                System.out.println(DAY_OR_MONTH_MESSAGE);

                while (!player1) {
                    try {

                        String playerChoice = in.next();
                        while (!(playerChoice.equalsIgnoreCase("day") || playerChoice.equalsIgnoreCase("month"))) {
                            System.out.println(INPUT_INVALID_MESSAGE);
                            playerChoice = in.next();
                        }
                        if (playerChoice.equalsIgnoreCase("day") || playerChoice.equalsIgnoreCase("month")) {
                            if (playerChoice.equalsIgnoreCase("day")) {
                                System.out.println(PICK_DAY_MESSAGE);
                                validateUserDayValue(in, thirtyDayMonths, thirtyOneDayMonths);
                            } else if (playerChoice.equalsIgnoreCase("month")) {
                                System.out.println(PICK_MONTH_MESSAGE);
                                boolean userMonthValid = false;
                                validateUserMonthValue(in, thirtyDayMonths, userMonthValid);
                            }
                            checkIfGameWonAndSwitchPlayers(); //(Fulfils Levels 1 & 2)
                        } else {
                            System.out.println(INPUT_INVALID_MESSAGE);
                        }
                    } catch (InputMismatchException e) {
                        System.out.println(INPUT_INVALID_MESSAGE);
                    }
                }
            }
        }
    }


    // Allows ordinal numbers to be used when overriding the start date

    private static void usingOrdinal() {
        if (usingOrdinal) {
            String ordinal = getOrdinal(day);
            System.out.println("The current date is: " + day + ordinal + " of " + MONTH_NAME[month - 1]);
        } else {
            System.out.println("The current date is: " + day + " of " + MONTH_NAME[month - 1]);
        }
    }

    // To pass arguments into the main program, the private method "checkForCommandLineArgs" is used
    private static void checkForCommandLineArgs(String[] args) {
        System.out.println(Arrays.toString(args));
        if (args.length == 2) {
            String argOne = args[0];
            String argTwo = args[1];
            day = Integer.parseInt(argOne);
            month = Integer.parseInt(argTwo);
            usingOrdinal = true;
        }
    }

    // To validate the players' input for month and day, and the overall winner and avoid repetition, private
    //methods "userMonthValid", "userDayValid", and "checkIfGameWonAndSwitchPlayers" are utilised
    private static void validateUserMonthValue(Scanner in, ArrayList<Integer> thirtyDayMonths,
                                               boolean userMonthValid) {
        while (!userMonthValid) {
            int userMonth = in.nextInt();
            if ((userMonth >= 1 && userMonth <= MONTH_MAX) && userMonth > month) {
                int currentDay = day;
                if (userMonth == 2) {
                    if (currentDay <= FEB_DAY_MAX) {
                        userMonthValid = true;
                        month = userMonth;
                    } else {
                        System.out.println(INPUT_INVALID_MESSAGE);
                    }
                } else if (thirtyDayMonths.contains(userMonth)) {
                    if (currentDay <= THIRTY_DAY_MAX) {
                        userMonthValid = true;
                        month = userMonth;
                    } else {
                        System.out.println(INPUT_INVALID_MESSAGE);
                    }
                } else {
                    userMonthValid = true;
                    month = userMonth;
                }

            } else {
                System.out.println(INPUT_INVALID_MESSAGE);
            }
        }
    }

    private static void validateUserDayValue(Scanner in, ArrayList<Integer> thirtyDayMonths,
                                             ArrayList<Integer> thirtyOneDayMonths) {
        boolean userDayValid = false;
        while (!userDayValid) {
            int userDay = in.nextInt();
            if (month == 2) {
                if (userDay > day && userDay <= FEB_DAY_MAX) {
                    day = userDay;
                    userDayValid = true;
                } else {
                    System.out.println(INPUT_INVALID_MESSAGE);
                }
            } else if (thirtyDayMonths.contains(month)) {
                if (userDay > day && userDay <= THIRTY_DAY_MAX) {
                    day = userDay;
                    userDayValid = true;
                } else {
                    System.out.println(INPUT_INVALID_MESSAGE);
                }
            } else if (thirtyOneDayMonths.contains(month)) {
                if (userDay > day && userDay <= THIRTY_ONE_DAY_MAX) {
                    day = userDay;
                    userDayValid = true;
                } else {
                    System.out.println(INPUT_INVALID_MESSAGE);
                }

            }
        }
    }

    private static void checkIfGameWonAndSwitchPlayers() {//(Level 1 & 2)

        usingOrdinal();

        // check if game won
        if (day == THIRTY_ONE_DAY_MAX && month == MONTH_MAX) {
            if (player1) {
                System.out.println("Player 1 is the winner of the game!");
            } else {
                System.out.println("Player 2 is the winner of the game!");
            }
        } else {
            // change current player if game not won
            if (player1) {
                player1 = false;
            } else {
                player1 = true;
            }
        }
    }

    // Converts the command line input into ordinal numbers using the private method "getOrdinal"
    private static String getOrdinal(int day) {
        String ordinal = "";
        String dayString = String.valueOf(day);
        if (dayString.length() == 1) {
            if (dayString.equals("1")) {
                ordinal = "st";
            } else if (dayString.equals("2")) {
                ordinal = "nd";
            } else if (dayString.equals("3")) {
                ordinal = "rd";
            } else {
                ordinal = "th";
            }
        } else {

            if (dayString.startsWith("1")) {
                ordinal = "th";
            } else {
                if (dayString.endsWith("1")) {
                    ordinal = "th";
                } else if (dayString.endsWith("2")) {
                    ordinal = "nd";
                } else if (dayString.endsWith("3")) {
                    ordinal = "rd";
                } else {
                    ordinal = "th";
                }

            }

        }
        return ordinal;
    }
}
	
	
	




