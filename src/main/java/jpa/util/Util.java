package jpa.util;

import jpa.service.ServiceUtil;

import static java.lang.System.out;

public class Util {

    public static final String RESET = "\033[0m";
    public static final String ANSI_RED = "\u001B[31m";


    public static void exitMessageAndQuit() {
        out.println("Goodbye!");
        ServiceUtil.closeSessionFactory();
        System.exit(0);
    }
}
