package com.company;

import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int counter = 0;
        int rows;
        int cols;
        System.out.println("Input rows:");
        rows = in.nextInt();
        System.out.println("Input cols:");
        cols = in.nextInt();
        char[][] table = new char[rows][cols];
        System.out.println("Input table:");

        for (int i = 0; i < rows; i++) {
            String input = in.next();
            for (int j = 0; j < cols; j++) {
                table[i][j] = input.charAt(j);
            }
        }
        System.out.println("input word:");
        String word = in.next();
        boolean isInRows = word.length() <= cols;
        boolean isInCols = word.length() <= rows;
        boolean isInDiagonals = isInCols || isInRows;
        if (isInRows)
            counter += wordInRows(table, word, rows, cols);
        if (isInCols)
            counter += wordInCols(table, word, rows, cols);
        if (isInDiagonals)
            counter += wordInDiags(table, word, rows, cols);
        System.out.println("Number of occurrences");
        System.out.println(counter);

    }

    private static int wordInRows(char[][] table, String word, int rows, int cols) {
        int counter = 0;
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                string.append(table[i][j]);
            }
            counter += subStringCounter(string.toString(), word);
            string = string.reverse();
            counter += subStringCounter(string.toString(), word);//oh, damn
            string = string.delete(0, string.capacity());
        }
        return counter;
    }

    private static int wordInCols(char[][] table, String word, int rows, int cols) {
        int counter = 0;
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                string.append(table[j][i]);//stack overflow
            }
            counter += subStringCounter(string.toString(), word);
            string = string.reverse();
            counter += subStringCounter(string.toString(), word);//oh, damn
            string = string.delete(0, string.capacity());

        }
        return counter;
    }

    private static int wordInDiags(char[][] table, String word, int rows, int cols) {
        int counter = 0;
        StringBuilder string = new StringBuilder();
        StringBuilder secondString = new StringBuilder();//for the reverse diags
        for (int i = word.length() - rows; i <= cols - word.length(); i++) {
            for (int j = 0; j < Math.min(rows, cols); j++) {
                if (i < 0) {
                    if (j + Math.abs(i) < Math.min(rows, cols)) {
                        string.append(table[j + Math.abs(i)][j]);
                        secondString.append(table[j + Math.abs(i)][cols - j - 1]);
                    }
                } else {
                    if (j + i < Math.min(rows, cols)) {
                        string.append(table[j][j + i]);
                        secondString.append(table[rows - j - 1][j + i]);
                    }
                }
            }
            counter += subStringCounter(string.toString(), word);
            counter += subStringCounter(secondString.toString(), word);
            string = string.reverse();
            secondString = secondString.reverse();
            counter += subStringCounter(string.toString(), word);
            counter += subStringCounter(secondString.toString(), word);
            string = string.delete(0, string.capacity());

            string = string.delete(0, string.capacity());

        }
        return counter;
    }


    private static int subStringCounter(String string, String word) {
        int counter = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.contains(word)) {
                counter++;
                string = string.substring(string.indexOf(word) + word.length());
            }
        }

        return counter;
    }
}