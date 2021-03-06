package GenshinBot.listeners;

import GenshinBot.Driver;

import java.awt.Color;
import GenshinBot.model.UserInfo;
import GenshinBot.model.UserInfo.State;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BannerListener extends ListenerAdapter {
	
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
		//Not in proper channel, ignore
		if(!e.getChannel().getName().equals(Driver.CHANNEL)) {
			System.out.println("ignoring channel");
			return;
		}
		UserInfo currUser = Driver.users.get(user.getIdLong());
		if(currUser.state == State.CHOOSE_BANNER_STATE) {
			//Must be choose banner state and must react to the corresponding message
			if(currUser.currentMessage == e.getMessageIdLong()) {
				handleBannerChoice(e);
			}
		}
	}
	private void handleBannerChoice(MessageReactionAddEvent e) {
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setColor(Color.BLUE);
		
		User user = e.getUser();
		UserInfo currUser = Driver.users.get(user.getIdLong());
		
		currUser.state = State.WAIT_STATE;
		
		if(e.getReactionEmote().getAsCodepoints().equals(Driver.CHOICE_ONE)) {
			embedBuilder.addField("Please enter the following value:", "5 star pity (1-90)", false);
			embedBuilder.setTitle(user.getName() + " chose the event banner.");
			currUser.currentBanner = currUser.eventBanner;
		}
		else if(e.getReactionEmote().getAsCodepoints().equals(Driver.CHOICE_TWO)) {
			embedBuilder.addField("Please enter the following value:", "5 star pity (1-80)", false);
			embedBuilder.setTitle(user.getName() + " chose the weapon banner.");
			currUser.currentBanner = currUser.weaponBanner;
		}
		else if(e.getReactionEmote().getAsCodepoints().equals(Driver.CHOICE_THREE)) {
			embedBuilder.addField("Please enter the following value:", "5 star pity (1-90)", false);
			embedBuilder.setTitle(user.getName() + " chose the standard banner.");
			currUser.currentBanner = currUser.standardBanner;
		}
		MessageEmbed message = embedBuilder.build();
		e.getChannel().sendMessage(message).queue(m ->{
			//Can only set state once message is actually sent
			UserInfo curr = Driver.users.get(user.getIdLong());
			curr.state = State.PITY_FIVE_STATE;
		});
		
	}
	public static void showBannerEmbed(MessageReceivedEvent e) {
		
		User user = e.getAuthor();
		
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setTitle(user.getName() + ", Choose a banner by reacting below.");
		embedBuilder.addField("Event Banner", ":one:", false);
		embedBuilder.addField("Weapon Banner", ":two:", false);
		embedBuilder.addField("Standard Banner", ":three:", false);
		embedBuilder.setColor(Color.BLUE);
		MessageEmbed message = embedBuilder.build();
		
		e.getChannel().sendMessage(message).queue(m -> {
			m.addReaction(Driver.CHOICE_ONE).queue();
			m.addReaction(Driver.CHOICE_TWO).queue();
			m.addReaction(Driver.CHOICE_THREE).queue();
			
			//Set current state
			long messageID = m.getIdLong();
			
			UserInfo currInfo = Driver.users.get(user.getIdLong());
			currInfo.currentMessage = messageID;
			currInfo.state = State.CHOOSE_BANNER_STATE;
		});

	}
}
