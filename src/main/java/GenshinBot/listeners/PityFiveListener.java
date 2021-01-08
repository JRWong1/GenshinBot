package GenshinBot.listeners;

import GenshinBot.Driver;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import GenshinBot.model.EventBanner;
import GenshinBot.model.Item;
import GenshinBot.model.StandardBanner;
import GenshinBot.model.UserInfo;
import GenshinBot.model.WeaponBanner;
import GenshinBot.model.UserInfo.State;
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

public class PityFiveListener extends ListenerAdapter {
	@Override
    public void onMessageReceived(MessageReceivedEvent e) {
		if(e.getAuthor().isBot()) {
			return;
		}
		User user = e.getAuthor();
		if(!Driver.users.containsKey(user)) {
			return;
		}
		UserInfo currUser = Driver.users.get(user);
		
		if(currUser.state == State.PITY_FIVE_STATE) {
			this.handlePityFive(e);
		}
		
	}
	private void handlePityFive(MessageReceivedEvent e) {
		User user = e.getAuthor();
		String command = e.getMessage().getContentDisplay();
		UserInfo currUser = Driver.users.get(user);
		//User entered valid pity value
		if(currUser.isValidPityFive(command)) {
			currUser.pityFive = Integer.parseInt(command);
			//Setup next state
			currUser.state = State.WAIT_STATE;
			//Ask for four star pity
			EmbedBuilder embedBuilder = new EmbedBuilder();
			embedBuilder.setColor(Color.BLUE);
			embedBuilder.addField("Please enter the following value:", "4 star pity (1-10)", false);
			embedBuilder.setTitle(Driver.getEmbedTitle(currUser));
			MessageEmbed message = embedBuilder.build();
			e.getChannel().sendMessage(message).queue(m ->{
				//Can only set state once message is actually sent
				UserInfo curr = Driver.users.get(user);
				curr.state = State.PITY_FOUR_STATE;
			});
		}
	}
}
