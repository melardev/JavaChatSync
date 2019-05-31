package com.melardev.chat.ui.console;

import java.util.Scanner;

public class Console {

    private final Scanner scan;

    public Console() {
        scan = new Scanner(System.in);
    }

    public void loop() {
        String line;
        while (true) {
            line = scan.nextLine();
            onLineEntered(line);
        }
    }

    protected void onLineEntered(String line) {

    }

}
