package GenshinBot.listeners;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Help extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
    	
    	if(e.getAuthor().isBot()) {
			return;
		}
        
        if(e.getMessage().getContentDisplay().equals("!help")) {
        	EmbedBuilder eb = new EmbedBuilder();
        	User user = e.getAuthor();
        	eb.setTitle("Welcome to the Genshin Impact Wish Simulator, " + user.getName() + "!");
        	eb.setColor(Color.PINK);
        	eb.appendDescription("The simulation will cover the current banners found in Genshin Impact, which are listed as the 'Event Banner', 'Weapon Banner', and 'Standard Banner'.");
        	eb.addField("The commands to use this bot are listed below:", "!help - Displays list of commands and helpful information.", false);
        	eb.addField("", "!start - Starts the Genshin Impact Wish Simulation.", false);
        	eb.addField("", "!quit - Exits out of the Wish Simulation. Start it again with '!start'.", false);
        	e.getChannel().sendMessage(eb.build()).queue();
        }
    }
}
