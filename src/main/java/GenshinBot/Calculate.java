package GenshinBot;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Calculate extends ListenerAdapter {
	@Override
    public void onMessageReceived(MessageReceivedEvent e) {
		
		if(e.getMessage().getContentDisplay().equals("!calculate")) {
			
			//Send a message
			e.getChannel().sendMessage("trying to calculate").queue();
			User user = e.getAuthor();
			
			//Direct message
			user.openPrivateChannel()
				.flatMap(channel -> channel.sendMessage("hello DM test"))
				.queue();
		}
		
	}
}
