package com.pluralsight;

/**
 *
 * The Progress class provides methods to display progress indicators in the console, such as loading bars, spinners,
 * and dancing characters.
 */
public class Progress {

    /**
     * Displays a welcome message for the Accounting Ledger in the console.
     * This method prints a colored banner with the program's name.
     * It also calls the "bar" method to create a progress bar.
     */
    public static void onLoadUpWelcome(){
        bar();
        System.out.println("\n");
        System.out.println(ConsoleColors.GREEN+ConsoleColors.GREEN_BACKGROUND+"--------------------------------------------"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.GREEN+ConsoleColors.GREEN_BACKGROUND+"--"+ConsoleColors.RESET+ConsoleColors.WHITE_UNDERLINED+ConsoleColors.WHITE_BOLD_BRIGHT+" WELCOME TO MUHAMRIF ACCOUNTING LEDGER! "+ConsoleColors.GREEN+ConsoleColors.GREEN_BACKGROUND+"--"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.GREEN+ConsoleColors.GREEN_BACKGROUND+"--------------------------------------------"+ConsoleColors.RESET);

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

    /**
     * A helper method to display a progress indicator while processing.
     */
    public static void progressLong() {
        boolean showProgress = true;
        String anim = "=====================";

        int x = 0;
        while (showProgress) {
            System.out.print("\rProcessing "
                    + anim.substring(0, x++ % anim.length())
                    + " ");
            if (x == 20) {
                showProgress = false;
                System.out.println("\n");
            }
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }

    /**
     * A smaller version of the progress indicator for shorter processes.
     */
    public static void progressSmall() {
        boolean showProgress = true;
        String anim = "=====================";

        int x = 0;
        while (showProgress) {
            System.out.print("\rProcessing "
                    + anim.substring(0, x++ % anim.length())
                    + " ");
            if (x == 10) {
                showProgress = false;
                System.out.println("\n");
            }
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }

}
