package net.eofitg.healthbarmod.commands;

import net.eofitg.healthbarmod.HealthBarMod;
import net.eofitg.healthbarmod.hud.PlayerHealthBarRenderer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandHealthBar extends CommandBase {

    @Override
    public String getCommandName() {
        return "healthbar";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/healthbar toggle|self|sneak|distance <value>|scale <value>|xoffset <value>|yoffset <value>|zoffset <value>|barwidth <value>|barheight <value>|barmargin <value>|barrotation <value>|barxoffset <value>|baryoffset <value>|barzoffset <value>|reload>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.addChatMessage(new ChatComponentText("§eUsage: " + getCommandUsage(sender)));
            return;
        }

        String sub = args[0].toLowerCase();

        switch (sub) {
            case "toggle": {
                PlayerHealthBarRenderer.ENABLED = !PlayerHealthBarRenderer.ENABLED;
                sender.addChatMessage(new ChatComponentText("§aHealth bar enabled:: " + PlayerHealthBarRenderer.ENABLED));
                HealthBarMod.configHandler.save();
                break;
            }
            case "self": {
                PlayerHealthBarRenderer.SHOW_SELF = !PlayerHealthBarRenderer.SHOW_SELF;
                sender.addChatMessage(new ChatComponentText("§aShow own health bar: " + PlayerHealthBarRenderer.SHOW_SELF));
                HealthBarMod.configHandler.save();
                break;
            }
            case "sneak": {
                PlayerHealthBarRenderer.HIDE_WHEN_SNEAKING = !PlayerHealthBarRenderer.HIDE_WHEN_SNEAKING;
                sender.addChatMessage(new ChatComponentText("§aHide when sneaking: " + PlayerHealthBarRenderer.HIDE_WHEN_SNEAKING));
                HealthBarMod.configHandler.save();
                break;
            }
            case "distance": {
                if (args.length >= 2) {
                    try {
                        double d = Double.parseDouble(args[1]);
                        PlayerHealthBarRenderer.MAX_DISTANCE = d;
                        sender.addChatMessage(new ChatComponentText("§aMax render distance: " + d));
                        HealthBarMod.configHandler.save();
                    } catch (NumberFormatException e) {
                        sender.addChatMessage(new ChatComponentText("§cPlease enter a valid number!"));
                    }
                } else {
                    sender.addChatMessage(new ChatComponentText("§eCurrent max render distance: " + PlayerHealthBarRenderer.MAX_DISTANCE));
                }
                break;
            }
            case "scale": {
                if (args.length >= 2) {
                    try {
                        float s = Float.parseFloat(args[1]);
                        PlayerHealthBarRenderer.SCALE = s;
                        sender.addChatMessage(new ChatComponentText("§aScale: " + s));
                        HealthBarMod.configHandler.save();
                    } catch (NumberFormatException e) {
                        sender.addChatMessage(new ChatComponentText("§cPlease enter a valid number!"));
                    }
                } else {
                    sender.addChatMessage(new ChatComponentText("§eCurrent scale: " + PlayerHealthBarRenderer.SCALE));
                }
                break;
            }
            case "xoffset": {
                if(args.length >= 2){
                    try{
                        PlayerHealthBarRenderer.X_OFFSET = Float.parseFloat(args[1]);
                        sender.addChatMessage(new ChatComponentText("§aX offset: " + PlayerHealthBarRenderer.X_OFFSET));
                        HealthBarMod.configHandler.save();
                    } catch(NumberFormatException e){ sender.addChatMessage(new ChatComponentText("§cPlease enter a valid number!")); }
                } else { sender.addChatMessage(new ChatComponentText("§eCurrent X offset: " + PlayerHealthBarRenderer.X_OFFSET)); }
                break;
            }
            case "yoffset": {
                if (args.length >= 2) {
                    try {
                        float y = Float.parseFloat(args[1]);
                        PlayerHealthBarRenderer.Y_OFFSET = y;
                        sender.addChatMessage(new ChatComponentText("§aY offset: " + y));
                        HealthBarMod.configHandler.save();
                    } catch (NumberFormatException e) {
                        sender.addChatMessage(new ChatComponentText("§cPlease enter a valid number!"));
                    }
                } else {
                    sender.addChatMessage(new ChatComponentText("§eCurrent Y offset: " + PlayerHealthBarRenderer.Y_OFFSET));
                }
                break;
            }
            case "zoffset": {
                if(args.length >= 2){
                    try{
                        PlayerHealthBarRenderer.Z_OFFSET = Float.parseFloat(args[1]);
                        sender.addChatMessage(new ChatComponentText("§aZ offset " + PlayerHealthBarRenderer.Z_OFFSET));
                        HealthBarMod.configHandler.save();
                    } catch(NumberFormatException e){ sender.addChatMessage(new ChatComponentText("§cPlease enter a valid number!")); }
                } else { sender.addChatMessage(new ChatComponentText("§eCurrent Z offset: " + PlayerHealthBarRenderer.Z_OFFSET)); }
                break;
            }
            case "barwidth": {
                if (args.length >= 2) {
                    try {
                        float w = Float.parseFloat(args[1]);
                        PlayerHealthBarRenderer.BAR_WIDTH = w;
                        sender.addChatMessage(new ChatComponentText("§aBar width: " + w));
                        HealthBarMod.configHandler.save();
                    } catch (NumberFormatException e) {
                        sender.addChatMessage(new ChatComponentText("§cPlease enter a valid number!"));
                    }
                } else {
                    sender.addChatMessage(new ChatComponentText("§eCurrent bar width: " + PlayerHealthBarRenderer.BAR_WIDTH));
                }
                break;
            }
            case "barheight": {
                if (args.length >= 2) {
                    try {
                        float h = Float.parseFloat(args[1]);
                        PlayerHealthBarRenderer.BAR_HEIGHT = h;
                        sender.addChatMessage(new ChatComponentText("§aBar height: " + h));
                        HealthBarMod.configHandler.save();
                    } catch (NumberFormatException e) {
                        sender.addChatMessage(new ChatComponentText("§cPlease enter a valid number!"));
                    }
                } else {
                    sender.addChatMessage(new ChatComponentText("§eCurrent bar height: " + PlayerHealthBarRenderer.BAR_HEIGHT));
                }
                break;
            }
            case "barmargin": {
                if (args.length >= 2) {
                    try {
                        float m = Float.parseFloat(args[1]);
                        PlayerHealthBarRenderer.BAR_MARGIN = m;
                        sender.addChatMessage(new ChatComponentText("§aBar-name margin: " + m));
                        HealthBarMod.configHandler.save();
                    } catch (NumberFormatException e) {
                        sender.addChatMessage(new ChatComponentText("§cPlease enter a valid number!"));
                    }
                } else {
                    sender.addChatMessage(new ChatComponentText("§eCurrent bar-name margin: " + PlayerHealthBarRenderer.BAR_MARGIN));
                }
                break;
            }
            case "barrotation": {
                if (args.length >= 2) {
                    try {
                        float r = Float.parseFloat(args[1]);
                        PlayerHealthBarRenderer.BAR_ROTATION = r;
                        sender.addChatMessage(new ChatComponentText("§aBar rotation: " + r));
                        HealthBarMod.configHandler.save();
                    } catch (NumberFormatException e) {
                        sender.addChatMessage(new ChatComponentText("§cPlease enter a valid number!"));
                    }
                } else {
                    sender.addChatMessage(new ChatComponentText("§eCurrent bar rotation: " + PlayerHealthBarRenderer.BAR_ROTATION));
                }
                break;
            }
            case "barxoffset": {
                if(args.length >= 2){
                    try{
                        PlayerHealthBarRenderer.BAR_X_OFFSET = Float.parseFloat(args[1]);
                        sender.addChatMessage(new ChatComponentText("§aHealth bar X offset: " + PlayerHealthBarRenderer.BAR_X_OFFSET));
                        HealthBarMod.configHandler.save();
                    } catch(NumberFormatException e){ sender.addChatMessage(new ChatComponentText("§cPlease enter a valid number!")); }
                } else { sender.addChatMessage(new ChatComponentText("§eCurrent health bar X offset: " + PlayerHealthBarRenderer.BAR_X_OFFSET)); }
                break;
            }
            case "baryoffset": {
                if(args.length >= 2){
                    try{
                        PlayerHealthBarRenderer.BAR_Y_OFFSET = Float.parseFloat(args[1]);
                        sender.addChatMessage(new ChatComponentText("§aHealth bar Y offset: " + PlayerHealthBarRenderer.BAR_Y_OFFSET));
                        HealthBarMod.configHandler.save();
                    } catch(NumberFormatException e){ sender.addChatMessage(new ChatComponentText("§cPlease enter a valid number!")); }
                } else { sender.addChatMessage(new ChatComponentText("§eCurrent health bar Y offset: " + PlayerHealthBarRenderer.BAR_Y_OFFSET)); }
                break;
            }
            case "barzoffset": {
                if(args.length >= 2){
                    try{
                        PlayerHealthBarRenderer.BAR_Z_OFFSET = Float.parseFloat(args[1]);
                        sender.addChatMessage(new ChatComponentText("§aHealth bar Z offset: " + PlayerHealthBarRenderer.BAR_Z_OFFSET));
                        HealthBarMod.configHandler.save();
                    } catch(NumberFormatException e){ sender.addChatMessage(new ChatComponentText("§cPlease enter a valid number!")); }
                } else { sender.addChatMessage(new ChatComponentText("§eCurrent health bar Z offset: " + PlayerHealthBarRenderer.BAR_Z_OFFSET)); }
                break;
            }
            case "reload": {
                HealthBarMod.configHandler.load();
                sender.addChatMessage(new ChatComponentText("§aConfiguration reloaded"));
                break;
            }
            default: {
                sender.addChatMessage(new ChatComponentText("§cUnknown argument: " + sub));
                sender.addChatMessage(new ChatComponentText("§eUsage: " + getCommandUsage(sender)));
            }
        }
    }



    private static final List<String> SUBCOMMANDS = Arrays.asList(
            "toggle", "self", "sneak", "distance", "scale",
            "barwidth", "barheight", "barmargin", "barrotation",
            "barxoffset", "baryoffset", "barzoffset",
            "xoffset", "yoffset", "zoffset", "reload"
    );

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            String prefix = args[0].toLowerCase();
            for (String cmd : SUBCOMMANDS) {
                if (cmd.startsWith(prefix)) {
                    completions.add(cmd);
                }
            }
        } else if (args.length == 2) {
            String sub = args[0].toLowerCase();
            switch (sub) {
                case "toggle":
                case "self":
                case "sneak":
                    completions.add("true");
                    completions.add("false");
                    break;
                case "distance":
                    completions.add("24.0");
                    break;
                case "scale":
                    completions.add("0.0165");
                    break;
                case "barwidth":
                    completions.add("70");
                    break;
                case "barheight":
                    completions.add("7");
                    break;
                case "barmargin":
                    completions.add("10");
                    break;
                case "barrotation":
                case "barxoffset":
                case "baryoffset":
                case "barzoffset":
                case "xoffset":
                case "yoffset":
                case "zoffset":
                    completions.add("0");
                    break;
            }
        }

        return completions.isEmpty() ? null : completions;
    }

}