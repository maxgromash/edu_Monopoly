/**
 * @author <a href="mailto: mmgrebenschikov@edu.hse.ru"> Maxim Grebenschikov</a>
 */

package com.maxgromash.cells;

import com.maxgromash.Main;

public class Bank extends Cell {

    static public double creditCoeff;
    static public double debtCoeff;

    /**
     * Конструктор без параметров
     */
    public Bank() {
        sign = "$";
    }

    /**
     * Переопределенный метод взаимоджействия с игроком
     * при по падании на клетку
     */
    @Override
    public void onPlayerStepCell() {
        if (Main.player.getCredit() > 0) hasCredit();
        else {
            System.out.println("- You are in the bank office. Would you like to get a credit?");
            if (Main.funYesNo()) {
                long max = Math.round(creditCoeff * Main.player.getSumOfSpentMoney());
                if (max == 0) {
                    System.out.println("- Bank can't give you a credit, you should spend some money on shops!");
                    return;
                }
                System.out.print("- Enter a sum from 1$ to " + max + "$\nSum: ");
                int x;
                while (true) {
                    try {
                        x = Integer.parseInt(Main.in.nextLine());
                        if (x < 1 || x > max)
                            throw new Exception();
                        break;
                    } catch (Exception ex) {
                        System.out.print("I don't understand, repeat plz: ");
                    }
                }
                Main.player.addMoney(x);
                Main.player.setCredit((int) Math.round(x * debtCoeff));
            } else {
                System.out.println("- Ok, no problem");
            }
        }
    }

    /**
     * Взаимодействие с игроком при условии, что он должник банка
     */
    private void hasCredit() {
        System.out.print("- You are in the bank office and you are a bank debtor.\n- You need to pay "
                + Main.player.getCredit() + "$\nPress Enter to pay");
        Main.in.nextLine();
        Main.player.reduceMoney(Main.player.getCredit());
        Main.player.setCredit(0);
    }

    /**
     * Переопределенный метод взаимоджействия c ботов
     * при по падании его на клетку
     */
    @Override
    public void onBotStepCell() {
        System.out.println("Bot was in the bank office and he drank some coffee");
    }
}
