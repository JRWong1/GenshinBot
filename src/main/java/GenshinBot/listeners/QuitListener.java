package GenshinBot.listeners;

import GenshinBot.Driver;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class QuitListener extends ListenerAdapter{
	@Override
    public void onMessageReceived(MessageReceivedEvent e) {
		if(e.getAuthor().isBot()) {
			return;
		}
		User user = e.getAuthor();
		if(!Driver.users.containsKey(user.getIdLong())) {
			return;
		}
		//Not in proper channel, ignore
		if(!e.getChannel().getName().equals(Driver.CHANNEL)) {
			return;
		}
		String command = e.getMessage().getContentDisplay();
		if(command.equals("!quit")) {
			if(Driver.users.containsKey(user.getIdLong())) {
				Driver.users.remove(user.getIdLong());
			}
			e.getChannel().sendMessage(user.getName() + ", you may begin again with !start").queue();
		}
	}
}
