package GenshinBot.listeners;

import GenshinBot.Driver;
import java.awt.Color;
import java.util.List;
import GenshinBot.model.Item;
import GenshinBot.model.UserInfo;
import GenshinBot.model.UserInfo.State;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class WishListener extends ListenerAdapter {
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
			return;
		}
		UserInfo currUser = Driver.users.get(user.getIdLong());
		if(currUser.state == State.WISH_STATE) {
			if(currUser.currentMessage == e.getMessageIdLong()) {
				handleWish(e);
			}
		}
	}
	
	private void handleWish(MessageReactionAddEvent e) {
		User user = e.getUser();
		UserInfo currUser = Driver.users.get(user.getIdLong());
		currUser.state = State.WAIT_STATE;
		//Roll once
		if(e.getReactionEmote().getAsCodepoints().equals(Driver.CHOICE_ONE)) {
			Item item = currUser.currentBanner.rollOne();
			EmbedBuilder eb = this.makeItemEmbed(user, item, currUser);
			e.getChannel().sendMessage(eb.build()).queue();
			WishListener.showWishEmbed(e);
		}
		//Roll ten
		else if(e.getReactionEmote().getAsCodepoints().equals(Driver.CHOICE_TWO)) {
			
			List<Item> items = currUser.currentBanner.rollTen();
			for(Item item : items) {
				EmbedBuilder eb = this.makeItemEmbed(user, item, currUser);
				e.getChannel().sendMessage(eb.build()).queue();
			}
			
			WishListener.showWishEmbed(e);
			
			
		}
	}
	private EmbedBuilder makeItemEmbed(User user, Item item, UserInfo currUser) {
		int newPityFive = currUser.currentBanner.pityFiveStar;
		int newPityFour = currUser.currentBanner.pityFourStar;
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle(Driver.getEmbedTitle(user, currUser));

		Color threeStar = new Color(99, 140, 175);
		Color fourStar = new Color(130, 111, 179);
		Color fiveStar = new Color(172, 117, 47);
		if(item.starCount == 3) {
			eb.setColor(threeStar);
		}
		else if(item.starCount == 4) {
			eb.setColor(fourStar);
		}
		else if(item.starCount == 5) {
			eb.setColor(fiveStar);
		}
		
		
		eb.addField("New Five Star Pity", Integer.toString(newPityFive), false);
		eb.addField("New Four Star Pity", Integer.toString(newPityFour), false);
		if(item.isChar) {
			eb.addField("You got a Character", item.name, false);
		}
		else {
			eb.addField("You got a Weapon", item.name, false);
		}
		eb.setImage(item.imageLink);
		return eb;
	}

	public static void showWishEmbed(MessageReceivedEvent e) {
		User user = e.getAuthor();
		UserInfo currUser = Driver.users.get(user.getIdLong());
		MessageEmbed message = WishListener.getEmbed(user, currUser);
		e.getChannel().sendMessage(message).queue(m ->{
			m.addReaction(Driver.CHOICE_ONE).queue();
			m.addReaction(Driver.CHOICE_TWO).queue();
			long messageID = m.getIdLong();
			
			UserInfo curr = Driver.users.get(user.getIdLong());
			curr.currentMessage = messageID;
			curr.state = State.WISH_STATE;
			
		});
	}
	public static void showWishEmbed(MessageReactionAddEvent e) {
		User user = e.getUser();
		UserInfo currUser = Driver.users.get(user.getIdLong());
		
		MessageEmbed message = WishListener.getEmbed(user, currUser);
		e.getChannel().sendMessage(message).queue(m ->{
			m.addReaction(Driver.CHOICE_ONE).queue();
			m.addReaction(Driver.CHOICE_TWO).queue();
			long messageID = m.getIdLong();
			
			UserInfo curr = Driver.users.get(user.getIdLong());
			curr.currentMessage = messageID;
			curr.state = State.WISH_STATE;
			
		});
	}
	
	private static MessageEmbed getEmbed(User user, UserInfo currUser) {
		currUser.state = State.WAIT_STATE;
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setColor(Color.BLUE);
		embedBuilder.addField("Wish one time:", 
				"React with :one:", 
				false);
		embedBuilder.addField("Wish ten times:", 
				"React with :two:", 
				false);
		embedBuilder.setTitle(Driver.getEmbedTitle(user, currUser));
		MessageEmbed message = embedBuilder.build();
		return message;
	}

}
