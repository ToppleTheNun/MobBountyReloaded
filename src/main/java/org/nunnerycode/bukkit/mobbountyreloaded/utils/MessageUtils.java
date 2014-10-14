package org.nunnerycode.bukkit.mobbountyreloaded.utils;

import org.bukkit.command.CommandSender;

public final class MessageUtils {

    private MessageUtils() {
        // do nothing
    }

    public static void sendMessage(CommandSender commandSender, String message) {
        if (commandSender == null || message == null) {
            return;
        }
        commandSender.sendMessage(message);
    }

    public static void sendArgumentMessage(CommandSender commandSender, String message,
                                           String[][] args) {
        if (commandSender == null || message == null || args == null) {
            return;
        }
        String newMessage = message;
        for (String[] arg : args) {
            newMessage = newMessage.replace(arg[0], arg[1]);
        }
        commandSender.sendMessage(newMessage);
    }

    public static void sendColoredMessage(CommandSender commandSender, String message) {
        if (commandSender == null || message == null) {
            return;
        }
        commandSender.sendMessage(message.replace('&', '\u00A7').replace("\u00A7\u00A7", "&"));
    }

    public static void sendColoredArgumentMessage(CommandSender commandSender, String message,
                                                  String[][] args) {
        if (commandSender == null || message == null || args == null) {
            return;
        }
        String newMessage = message;
        for (String[] arg : args) {
            newMessage = newMessage.replace(arg[0], arg[1]);
        }
        commandSender.sendMessage(newMessage.replace('&', '\u00A7').replace("\u00A7\u00A7", "&"));
    }

}
