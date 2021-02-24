/**
 * @author <a href="mailto: mmgrebenschikov@edu.hse.ru"> Maxim Grebenschikov</a>
 */

package com.maxgromash;

import com.maxgromash.cells.Bank;
import com.maxgromash.cells.PenaltyCell;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static Player player, bot;
    public static GameField field;
    public static final Random rand = new Random();
    public static final Scanner in = new Scanner(System.in);

    /**
     * Старт программы и запуск letsPlay
     *
     * @param args - ширина поля, высота поля, количество денег
     */
    public static void main(String[] args) {
        int height = 0, width = 0, money = 0;
        try {
            height = Integer.parseInt(args[1]);
            width = Integer.parseInt(args[0]);
            money = Integer.parseInt(args[2]);
        } catch (Exception ex) {
        }

        //Проверка на корректность параметров запуска игры
        if (width < 6 || width > 30 || height < 6 || height > 30 || money < 500 || money > 15000) {
            System.out.println("Argument out of range, sorry");
            return;
        }

        field = new GameField(height, width);
        player = new Player(money);
        bot = new Player(money);

        switch (helloPlayer()) {
            case 0:
                System.out.print("- Ok, bye!");
                return;
            case 1:
                letsPlay(true);
                break;
            case 2:
                letsPlay(false);
                break;
        }
    }

    /**
     * Метод взаимодействия с пользователем при его входе в игру
     *
     * @return Первым или втром ходит пользователель
     */
    public static int helloPlayer() {
        System.out.println("- Hello! Do you want to play in Monopoly 2.0?");

        if (!funYesNo())
            return 0;

        System.out.print("- Ohhh year!!!\n\n <<X>> - It's you\n {{X}} - It's your opponent\n <{X}> - You two on the same cell \n");

        generateCoeffs();
        fieldExplanation();

        System.out.print("\n- Let's flip a coin to see who goes first.\nPress Enter to flip ");
        in.nextLine();

        if (rand.nextInt(2) == 0) {
            System.out.print("- You will go first!\nPress Enter to start ");
            in.nextLine();
            return 1;
        } else {
            System.out.print("- You will be second!\nPress Enter to start ");
            in.nextLine();
            return 2;
        }
    }

    /**
     * Генерация постоянных коэфициентов
     */
    public static void generateCoeffs() {
        PenaltyCell.penaltyCoeff = (rand.nextDouble() * 0.9 + 0.1) / 10.0;
        Bank.debtCoeff = rand.nextDouble() * 2.0 + 1;
        Bank.creditCoeff = rand.nextDouble() * 0.198 + 0.002;
        System.out.println("\n Penalty: " + PenaltyCell.penaltyCoeff + "\n Debt coeff: " + Bank.debtCoeff +
                "\n Credit coeff : " + Bank.creditCoeff + "\n");
    }

    /**
     * Объяснение карты
     */
    public static void fieldExplanation() {
        field.drawField();
        System.out.print("\n- This is a game field. " +
                "Signs:\n S - free shop\n M - your shop\n O - opponent's shop\n" +
                " T - taxi\n % - penalty cell\n $ - bank\n E - empty cell");
        System.out.print("\n\nPress Enter to start");
        in.nextLine();
    }

    /**
     * Метод запуска игры
     *
     * @param first - первым или вторым ходит пользователь
     */
    static void letsPlay(boolean first) {
        boolean isPlaying = true;
        while (isPlaying) {
            try {
                if (first) {
                    playerTurn();
                    botTurn();
                } else {
                    botTurn();
                    playerTurn();

                }
            } catch (Exception ex) {
                System.out.println("\n" + ex.getMessage());
            }
        }
    }

    /**
     * Метод хода бота
     */
    private static void botTurn() {
        try {
            int dice = rollDice(false);
            bot.moveCell(dice);
            field.cells[bot.getCurrentCell()].onBotStepCell();
            System.out.println("Bot's current balance is " + bot.getMoney() + "$\n");
            field.drawField();
        } catch (Exception ex) {
            throw new ArithmeticException("You win!");
        }
    }

    /**
     * Метод хода игрока
     */
    private static void playerTurn() {
        try {
            int dice = rollDice(true);
            player.moveCell(dice);
            field.drawField();
            field.cells[player.getCurrentCell()].onPlayerStepCell();
            System.out.println("Your current balance is " + player.getMoney() + "$");
            if (player.getCredit() != 0)
                System.out.println("Your current balance is " + player.getCredit() + "$");
        } catch (Exception ex) {
            throw new ArithmeticException("You lose!");
        }
    }


    /**
     * Метод для бросания кубиков
     *
     * @param isUser - кто бросает кубики
     * @return суммарное количество очков на кубиках
     */
    public static int rollDice(boolean isUser) {
        int x = rand.nextInt(6) + 1;
        int y = rand.nextInt(6) + 1;
        int c = x + y;
        if (isUser) {
            System.out.print("\n- Let's roll the dice!\nPress Enter to roll");
            in.nextLine();
            System.out.println("\n- You have " + x + " and " + y + ". It's " + c + " together!\n");
        } else {
            System.out.println("\n- Bot threw " + c + " points");
        }
        return c;
    }

    /**
     * Метод для получения ответа от пользователя "да" или "нет"
     *
     * @return ответ пользователя
     */
    public static boolean funYesNo() {
        System.out.print("y - yes, n - no: ");
        String ans;
        do {
            ans = in.nextLine().trim();
            if (ans.equals("n")) {
                return false;
            } else if (!ans.equals("y")) {
                System.out.print("- I don't understand, repeat plz: ");
            }
        } while (!ans.equals("y"));
        return true;
    }

}