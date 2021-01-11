package GenshinBot.listeners;

import GenshinBot.Driver;
import GenshinBot.model.UserInfo;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AllListener extends ListenerAdapter{
	@Override
    public void onMessageReceived(MessageReceivedEvent e) {
				
		if(e.getAuthor().isBot()) {
			return;
		}
		
		User user = e.getAuthor();
		UserInfo currUser = null;
		if(Driver.users.containsKey(user.getIdLong())) {
			currUser = Driver.users.get(user.getIdLong());
			currUser.lastInteraction = System.currentTimeMillis();
		}
	}
	
	@Override
    public void onMessageReactionAdd(MessageReactionAddEvent e) {
		
		
		
		if(e.getUser().isBot()) {
			return;
		}
		User user = e.getUser();
		//Random reaction, ignore
		if(!Driver.users.containsKey(user.getIdLong())) {
			return;
		}
		UserInfo currUser = Driver.users.get(user.getIdLong());
		currUser.lastInteraction = System.currentTimeMillis();
	}
}
