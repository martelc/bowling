package martelc.bowling.application;

import com.google.inject.Guice;
import com.google.inject.Injector;
import martelc.bowling.crosscutting.DomainModule;
import martelc.bowling.domain.games.Game;
import martelc.bowling.domain.scoringstrategies.ScoringStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {

    private static final Injector INJECTOR = Guice.createInjector(new DomainModule());
    private static final String ROLL_DELIMITER = ",";

    public static void main(String[] args) {
        playPerfectGame();
        playUserGame();
    }

    private static void playPerfectGame() {
        Game game = new Game(INJECTOR.getInstance(ScoringStrategy.class));
        List<Integer> perfectRolls = buildPerfectRolls(game.getScoringStrategy());
        game.calculateScoreFromASequenceOfRolls(perfectRolls);
        System.out.println("The perfect game has the sequence of rolls (" + perfectRolls.size() + ") : " + perfectRolls + " with a score of: " + game.getScore());
    }

    private static List<Integer> buildPerfectRolls(ScoringStrategy scoringStrategy) {
        List<Integer> perfectRolls= new ArrayList<>();
        for (int count = 0; count < scoringStrategy.getMaximumNumberOfFrames(); count++) {
            perfectRolls.add(scoringStrategy.getMaximumNumberOfPointsPerBall());
        }
        perfectRolls.add(scoringStrategy.getMaximumNumberOfPointsPerBall());
        perfectRolls.add(scoringStrategy.getMaximumNumberOfPointsPerBall());

        return perfectRolls;
    }

    private static void playUserGame() {
        ScoringStrategy scoringStrategy = INJECTOR.getInstance(ScoringStrategy.class);
        Game game = new Game(scoringStrategy);
        List<Integer> userRolls = readUserInput();
        game.calculateScoreFromASequenceOfRolls(userRolls);
        System.out.println("Your game has the sequence of rolls (" + userRolls.size() + ") : " + userRolls + " with a score of: " + game.getScore());
    }

    private static List<Integer> readUserInput() {
        String rollsAsString = scanUserInput();
        List<Integer> rolls = convertStringsToIntegers(rollsAsString);
        return rolls;
    }

    private static String scanUserInput() {
        System.out.println(buildUserInstructions());
        Scanner scanner = new Scanner(System.in);
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
        String rollsAsString = scanner.nextLine();
        scanner.close();
        return rollsAsString;
    }

    private static String buildUserInstructions() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Enter a sequence of numeric rolls delimited by a '");
        stringBuilder.append(ROLL_DELIMITER);
        stringBuilder.append("' (then press <ENTER>): ");
        return stringBuilder.toString();
    }

    private static List<Integer> convertStringsToIntegers(String rollsAsString) {
        String[] rollsAsStringSplit  = rollsAsString.split(ROLL_DELIMITER);
        List<Integer> rolls = new ArrayList<>();
        for (String rollAsString : rollsAsStringSplit) {
            rolls.add(Integer.valueOf(rollAsString.trim()));
        }
        return rolls;
    }

}
