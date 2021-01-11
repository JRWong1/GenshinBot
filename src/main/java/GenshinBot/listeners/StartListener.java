package GenshinBot.listeners;

import GenshinBot.Driver;
import GenshinBot.model.UserInfo;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class StartListener extends ListenerAdapter{
	@Override
    public void onMessageReceived(MessageReceivedEvent e) {
		if(e.getAuthor().isBot()) {
			return;
		}
		User user = e.getAuthor();
		
		//Not in proper channel, ignore
		if(!e.getChannel().getName().equals(Driver.CHANNEL)) {
			return;
		}

		String command = e.getMessage().getContentDisplay();
		if(command.equals("!start")) {
			
			if(Driver.users.containsKey(user.getIdLong())) {
				e.getChannel().sendMessage(
						user.getName() + ", you have already begun the simulation, please check your Direct Messages."
						).queue();
			}
			else {
				
				
				Driver.users.put(user.getIdLong(), new UserInfo());
				
				Driver.users.get(user.getIdLong()).lastInteraction = System.currentTimeMillis();
				Driver.setTimeoutUser(user, Driver.users.get(user.getIdLong()));
				
				
				e.getChannel().sendMessage("Starting " + user.getName() + "'s Genshin Simulation").queue();
				
				BannerListener.showBannerEmbed(e);
				
			}
		}
	}
	

}
