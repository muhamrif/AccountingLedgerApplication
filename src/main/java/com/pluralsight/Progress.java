package com.pluralsight;

import java.util.Random;

public class Progress {
    public static void main(String[] args) {
        dance();
        bar();
        spin();

    }
    public static void bar() {
        int totalSteps = 100;
        for (int i = 0; i <= totalSteps; i++) {
            int progress = i * 100 / totalSteps;
            String progressBar = "[" + ConsoleColors.GREEN+ConsoleColors.GREEN_BACKGROUND+"=".repeat(progress)+ConsoleColors.RESET +" ".repeat(100 - progress) +  "]";
            System.out.print("\rLOADING MUHAMRIF LEGDER: " + progressBar + " " + progress + "%");
            try {
                Thread.sleep(20); // Simulate work
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void spin() {
        String[] spinnerFrames = {"|", "/", "-", "\\"};
        int currentFrame = 0;

        while (true) {
            System.out.print("\rProgress: " + spinnerFrames[currentFrame]);
            currentFrame = (currentFrame + 1) % spinnerFrames.length;
            // Update progress logic here
        }
    }

        public static void dance(){
            String[] dancingCharacter = {"(>^.^)>", "(^.^<)", "<(^.^<)", "^(^.^)^"};
            int currentCharacter = 0;
            boolean flag = true;
            int acc =0;
            while (flag) {
                System.out.print("\r" + dancingCharacter[currentCharacter]);
                currentCharacter = (currentCharacter + 1) % dancingCharacter.length;
                // Update progress logic here
                if (acc==10) flag=false;
                try {
                    Thread.sleep(500);
                    acc++;// Adjust animation speed
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

}
