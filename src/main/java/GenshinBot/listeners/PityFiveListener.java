package GenshinBot.listeners;

import GenshinBot.Driver;
import java.awt.Color;
import GenshinBot.model.UserInfo;
import GenshinBot.model.UserInfo.State;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PityFiveListener extends ListenerAdapter {
	@Override
    public void onMessageReceived(MessageReceivedEvent e) {
		if(e.getAuthor().isBot()) {
			return;
		}
		User user = e.getAuthor();
		if(!Driver.users.containsKey(user.getIdLong())) {
			return;
		}
		UserInfo currUser = Driver.users.get(user.getIdLong());
		
		if(currUser.state == State.PITY_FIVE_STATE) {
			this.handlePityFive(e);
		}
		
	}
	private void handlePityFive(MessageReceivedEvent e) {
		User user = e.getAuthor();
		String command = e.getMessage().getContentDisplay();
		UserInfo currUser = Driver.users.get(user.getIdLong());
		//User entered valid pity value
		if(currUser.isValidPityFive(command)) {
			currUser.pityFive = Integer.parseInt(command);
			//Setup next state
			currUser.state = State.WAIT_STATE;
			//Ask for four star pity
			EmbedBuilder embedBuilder = new EmbedBuilder();
			embedBuilder.setColor(Color.BLUE);
			embedBuilder.addField("Please enter the following value:", "4 star pity (1-10)", false);
			embedBuilder.setTitle(Driver.getEmbedTitle(user, currUser));
			MessageEmbed message = embedBuilder.build();
			e.getChannel().sendMessage(message).queue(m ->{
				//Can only set state once message is actually sent
				UserInfo curr = Driver.users.get(user.getIdLong());
				curr.state = State.PITY_FOUR_STATE;
			});
		}
	}
}
