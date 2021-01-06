package GenshinBot;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Calculate extends ListenerAdapter {
	
	//
	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent e) {
		
	}
	
	@Override
    public void onMessageReceived(MessageReceivedEvent e) {
		
		if(e.getMessage().getContentDisplay().equals("!calculate")) {
			
			//Send a message
			e.getChannel().sendMessage("trying to calculate").queue();
			
			Guild guild = e.getGuild();
			
			//Embeds
			EmbedBuilder embedBuilder = new EmbedBuilder();
			embedBuilder.addField("Line 1", "Value1", false);
			embedBuilder.addField("Line 2", "Value2", false);
			embedBuilder.setColor(Color.BLUE);
			embedBuilder.setTitle("example title");
			MessageEmbed embed = embedBuilder.build();
			
			//Emote checkMark = guild.getEmotesByName("white_check_mark", false).get(0);
			//System.out.println(checkMark.getName());
			
			
			//React to message
			e.getChannel().sendMessage(embed).queue(
					message -> {
						message.addReaction("U+1F600").queue();
					});
			
			
			//Direct messaging
			
			/*
			User user = e.getAuthor();
			
			user.openPrivateChannel()
				.flatMap(channel -> channel.sendMessage("hello DM test"))
				.queue();
				
			*/
		}
		
	}
}
