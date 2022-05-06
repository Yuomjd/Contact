package contacts;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException, ClassNotFoundException {

        Scanner scanner = new Scanner(System.in);
        //对象持久化
        Contacts contacts;
        if (args != null && args.length != 0) {
            contacts = new Contacts(args[0]);
        }
        contacts = new Contacts();
        contacts.menu();
    }
}
