package com.pluralsight;

import java.util.Random;
/**
 *
 * The Progress class provides methods to display progress indicators in the console, such as loading bars, spinners,
 * and dancing characters.
 */
public class Progress {
    /**
     * Main method for testing the progress indicators. Mainly for testing purposes.
     * @param args Command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        dance();
        bar();
        spin();

    }

    /**
     * Displays a loading bar with a visual representation of progress. The loading bar is updated in real-time to show
     * the percentage of completion.
     */
    public static void bar() {
        int totalSteps = 100;
        for (int i = 0; i <= totalSteps; i++) {
            int progress = i * 100 / totalSteps;
            String progressBar = "[" + ConsoleColors.GREEN+ConsoleColors.GREEN_BACKGROUND+"=".repeat(progress)+ConsoleColors.RESET +" ".repeat(100 - progress) +  "]";
            System.out.print("\rLOADING MUHAMRIF LEGDER: " + progressBar + " " + progress + "%");
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Displays a spinning progress indicator, often used to indicate ongoing activity without showing specific progress.
     */
    public static void spin() {
        String[] spinnerFrames = {"|", "/", "-", "\\"};
        int currentFrame = 0;

        while (true) {
            System.out.print("\rProgress: " + spinnerFrames[currentFrame]);
            currentFrame = (currentFrame + 1) % spinnerFrames.length;
            // Update progress logic here
        }
    }

    /**
     * Displays a dancing character animation in the console, providing a playful and visually appealing progress indicator.
     */
        public static void dance(){
            String[] dancingCharacter = {"(>^.^)>", "(^.^<)", "<(^.^<)", "^(^.^)^"};
            int currentCharacter = 0;
            boolean flag = true;
            int acc =0;
            while (flag) {
                System.out.print("\r" + dancingCharacter[currentCharacter]);
                currentCharacter = (currentCharacter + 1) % dancingCharacter.length;
                if (acc==10) flag=false;
                try {
                    Thread.sleep(500);
                    acc++;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

}
