package com.melardev;

import com.melardev.chat.ClientApp;

public class Launcher {

    public static void main(String[] args) {
        // parse args
        ClientApp app = new ClientApp(ClientApp.UiMode.SWING);
        app.run();
    }
}
