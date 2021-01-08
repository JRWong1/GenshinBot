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

public class PityFourListener extends ListenerAdapter {
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
		
		if(currUser.state == State.PITY_FOUR_STATE) {
			this.handlePityFour(e);
		}
		
	}
	private void handlePityFour(MessageReceivedEvent e) {
		User user = e.getAuthor();
		String command = e.getMessage().getContentDisplay();
		UserInfo currUser = Driver.users.get(user);
		
		//User entered valid pity value
		if(currUser.isValidPityFour(command)) {
			currUser.pityFour = Integer.parseInt(command);
			//Setup next state, only standard banner can immediately wish
			currUser.state = State.WAIT_STATE;
			if(currUser.currentBanner instanceof StandardBanner) {
				currUser.setBannerValues(currUser.pityFive, 
						currUser.pityFour, 
						currUser.promoFive, 
						currUser.promoFour);
				WishListener.showWishEmbed(e);
			}
			else {
				EmbedBuilder embedBuilder = new EmbedBuilder();
				embedBuilder.setColor(Color.BLUE);
				embedBuilder.addField("If you are guaranteed a promotional 5 star item:", 
						"React with :white_check_mark:", 
						false);
				embedBuilder.addField("Otherwise:", 
						"React with :x:", 
						false);
				embedBuilder.setTitle(Driver.getEmbedTitle(currUser));
				MessageEmbed message = embedBuilder.build();
				e.getChannel().sendMessage(message).queue(m ->{
					m.addReaction(Driver.CHOICE_YES).queue();
					m.addReaction(Driver.CHOICE_NO).queue();
					long messageID = m.getIdLong();
					
					UserInfo curr = Driver.users.get(user);
					curr.currentMessage = messageID;
					curr.state = State.PROMO_FIVE_STATE;
					
				});
			}
		}
	}
}
