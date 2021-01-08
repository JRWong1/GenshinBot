package GenshinBot;


import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import GenshinBot.UserInfo.State;
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

public class Simulator extends ListenerAdapter {
	
	
	//Map of concurrent users of bot
	public HashMap<User, UserInfo> users;
	//Removes a user from consideration after this many seconds
	public static final int TIMEOUT = 60;

	
	//Emoji choices
	public static final String CHOICE_ONE = "U+31U+fe0fU+20e3";
	public static final String CHOICE_TWO = "U+32U+fe0fU+20e3";
	public static final String CHOICE_THREE = "U+33U+fe0fU+20e3";
	public static final String CHOICE_YES = "U+2705";
	public static final String CHOICE_NO = "U+274C";
	
	
	public Simulator() {
		super();
		this.users = new HashMap<User, UserInfo>();
	}
	
	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent e) {
		//We don't care about the bot reacting
		if(e.getUser().isBot()) {
			return;
		}
		User user = e.getUser();
		//Random reaction, ignore
		if(!this.users.containsKey(user)) {
			return;
		}
		UserInfo currUser = this.users.get(user);
		currUser.lastInteraction = System.currentTimeMillis();
		if(currUser.state == State.CHOOSE_BANNER_STATE) {
			//Must be choose banner state and must react to the corresponding message
			if(currUser.currentMessage == e.getMessageIdLong()) {
				handleBannerChoice(e);
			}
		}
		else if(currUser.state == State.PROMO_FIVE_STATE) {
			if(currUser.currentMessage == e.getMessageIdLong()) {
				handlePromoFive(e);
			}
		}
		else if(currUser.state == State.PROMO_FOUR_STATE) {
			if(currUser.currentMessage == e.getMessageIdLong()) {
				handlePromoFour(e);
			}
		}
		else if(currUser.state == State.WISH_STATE) {
			if(currUser.currentMessage == e.getMessageIdLong()) {
				handleWish(e);
			}
		}
		
	}
	
	private void handleWish(MessageReactionAddEvent e) {
		
		
		User user = e.getUser();
		UserInfo currUser = this.users.get(user);
		currUser.state = State.WAIT_STATE;
		//Roll once
		if(e.getReactionEmote().getAsCodepoints().equals(this.CHOICE_ONE)) {
			Item item = currUser.currentBanner.rollOne();
			EmbedBuilder eb = this.makeItemEmbed(item, currUser);
			e.getChannel().sendMessage(eb.build()).queue();
			this.sendWishEmbed(e);
		}
		//Roll ten
		else if(e.getReactionEmote().getAsCodepoints().equals(this.CHOICE_TWO)) {
			
			List<Item> items = currUser.currentBanner.rollTen();
			for(Item item : items) {
				EmbedBuilder eb = this.makeItemEmbed(item, currUser);
				e.getChannel().sendMessage(eb.build()).queue();
			}
			
			this.sendWishEmbed(e);
			
			
		}
	}
	
	private EmbedBuilder makeItemEmbed(Item item, UserInfo currUser) {
		int newPityFive = currUser.currentBanner.pityFiveStar;
		int newPityFour = currUser.currentBanner.pityFourStar;
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle(this.getEmbedTitle(currUser));

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
	
	private void sendWishEmbed(MessageReactionAddEvent e) {
		User user = e.getUser();
		UserInfo currUser = this.users.get(user);
		EmbedBuilder wishEmbed = new EmbedBuilder();
		wishEmbed.setColor(Color.BLUE);
		wishEmbed.setTitle(this.getEmbedTitle(currUser));
		wishEmbed.addField("Wish one time:", 
				"React with :one:", 
				false);
		wishEmbed.addField("Wish ten times:", 
				"React with :two:", 
				false);
		e.getChannel().sendMessage(wishEmbed.build()).queue(m ->{
			m.addReaction(this.CHOICE_ONE).queue();
			m.addReaction(this.CHOICE_TWO).queue();
			//Can only set state once message is actually sent
			UserInfo curr = this.users.get(user);
			curr.currentMessage = m.getIdLong();
			curr.state = State.WISH_STATE;
		});
	}
	
	private void handlePromoFour(MessageReactionAddEvent e) {
		
		User user = e.getUser();
		UserInfo currUser = this.users.get(user);
		
		
		currUser.state = State.WAIT_STATE;
		
		//Yes promo four star
		if(e.getReactionEmote().getAsCodepoints().equals(this.CHOICE_YES)) {
			currUser.promoFour = true;
		}
		//No promo four star
		else if(e.getReactionEmote().getAsCodepoints().equals(this.CHOICE_NO)) {
			currUser.promoFour = false;
		}
		
		currUser.setBannerValues(currUser.pityFive, 
				currUser.pityFour, 
				currUser.promoFive, 
				currUser.promoFour);
		
		this.sendWishEmbed(e);

	}

	private void handlePromoFive(MessageReactionAddEvent e) {
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setColor(Color.BLUE);
		
		User user = e.getUser();
		UserInfo currUser = this.users.get(user);
		
		embedBuilder.setTitle(this.getEmbedTitle(currUser));
		
		currUser.state = State.WAIT_STATE;
		
		//Yes promo five star
		if(e.getReactionEmote().getAsCodepoints().equals(this.CHOICE_YES)) {
			currUser.promoFive = true;
		}
		//No promo five star
		else if(e.getReactionEmote().getAsCodepoints().equals(this.CHOICE_NO)) {
			currUser.promoFive = false;
		}
		embedBuilder.addField("If you are guaranteed a promotional 4 star item:", 
				"React with :white_check_mark:", 
				false);
		embedBuilder.addField("Otherwise:", 
				"React with :x:", 
				false);
		MessageEmbed message = embedBuilder.build();
		e.getChannel().sendMessage(message).queue(m ->{
			m.addReaction(this.CHOICE_YES).queue();
			m.addReaction(this.CHOICE_NO).queue();
			//Can only set state once message is actually sent
			UserInfo curr = this.users.get(user);
			curr.currentMessage = m.getIdLong();
			curr.state = State.PROMO_FOUR_STATE;
		});
	}

	@Override
    public void onMessageReceived(MessageReceivedEvent e) {
		
		if(e.getAuthor().isBot()) {
			return;
		}
		
		User user = e.getAuthor();
		UserInfo currUser = null;
		if(this.users.containsKey(user)) {
			currUser = this.users.get(user);
			currUser.lastInteraction = System.currentTimeMillis();
		}
		String command = e.getMessage().getContentDisplay();
		if(command.equals("!start")) {
			
			if(this.users.containsKey(user)) {
				e.getChannel().sendMessage(
						"You have already begun the simulation, please check your Direct Messages."
						).queue();
			}
			else {
				
				
				users.put(user, new UserInfo());
				
				this.users.get(user).lastInteraction = System.currentTimeMillis();
				this.setTimeoutUser(user, this.users.get(user));
				
				user.openPrivateChannel()
					.flatMap(channel -> channel.sendMessage("Starting Genshin Simulation"))
					.queue();
				
				EmbedBuilder embedBuilder = new EmbedBuilder();
				embedBuilder.setTitle("Choose a banner by reacting below.");
				embedBuilder.addField("Event Banner", ":one:", false);
				embedBuilder.addField("Weapon Banner", ":two:", false);
				embedBuilder.addField("Standard Banner", ":three:", false);
				embedBuilder.setColor(Color.BLUE);
				MessageEmbed message = embedBuilder.build();
				
				
				user.openPrivateChannel()
					.flatMap(channel -> channel.sendMessage(message))
					.queue(m -> {
						m.addReaction(this.CHOICE_ONE).queue();
						m.addReaction(this.CHOICE_TWO).queue();
						m.addReaction(this.CHOICE_THREE).queue();
						
						//Set current state
						long messageID = m.getIdLong();
						
						UserInfo currInfo = users.get(user);
						currInfo.currentMessage = messageID;
						currInfo.state = State.CHOOSE_BANNER_STATE;
					});
				
			}
		}
		else if(currUser != null && currUser.state == State.PITY_FIVE_STATE) {
			handlePityFive(e);
		}
		else if(currUser != null && currUser.state == State.PITY_FOUR_STATE) {
			handlePityFour(e);
		}
		if(command.equals("!quit")) {
			if(this.users.containsKey(user)) {
				this.users.remove(user);
			}
			user.openPrivateChannel()
				.flatMap(channel -> channel.sendMessage("You may begin again with !start")).queue();
		}
			
		//End of method
	}
	
	private void setTimeoutUser(User user, UserInfo currUser) {
		
		//User already removed
		if(!this.users.containsKey(user)) {
			return;
		}
		
		CompletableFuture.delayedExecutor(Simulator.TIMEOUT, TimeUnit.SECONDS).execute(() -> {
			//User already removed
			if(!this.users.containsKey(user)) {
				return;
			}
			long currTime = System.currentTimeMillis();
			if(Math.abs(currUser.lastInteraction - currTime) >= (long)Simulator.TIMEOUT * (long)1000) {
				user.openPrivateChannel()
					.flatMap(channel -> channel.sendMessage("You have timed out, please try again."))
					.queue();
				this.users.remove(user);
				return;
			}
			else {
				this.setTimeoutUser(user, currUser);
			}
		});
	}
	
	private void handlePityFour(MessageReceivedEvent e) {
		
		User user = e.getAuthor();
		String command = e.getMessage().getContentDisplay();
		UserInfo currUser = this.users.get(user);
		
		//User entered valid pity value
		if(currUser.isValidPityFour(command)) {
			currUser.pityFour = Integer.parseInt(command);
			//Setup next state, only standard banner can immediately wish
			if(currUser.currentBanner instanceof StandardBanner) {
				currUser.state = State.WAIT_STATE;
				currUser.setBannerValues(currUser.pityFive, 
						currUser.pityFour, 
						currUser.promoFive, 
						currUser.promoFour);
				EmbedBuilder embedBuilder = new EmbedBuilder();
				embedBuilder.setColor(Color.BLUE);
				embedBuilder.addField("Wish one time:", 
						"React with :one:", 
						false);
				embedBuilder.addField("Wish ten times:", 
						"React with :two:", 
						false);
				embedBuilder.setTitle(this.getEmbedTitle(currUser));
				MessageEmbed message = embedBuilder.build();
				e.getChannel().sendMessage(message).queue(m ->{
					m.addReaction(this.CHOICE_ONE).queue();
					m.addReaction(this.CHOICE_TWO).queue();
					long messageID = m.getIdLong();
					
					UserInfo curr = this.users.get(user);
					curr.currentMessage = messageID;
					curr.state = State.WISH_STATE;
					
				});
			}
			else {
				currUser.state = State.WAIT_STATE;
				EmbedBuilder embedBuilder = new EmbedBuilder();
				embedBuilder.setColor(Color.BLUE);
				embedBuilder.addField("If you are guaranteed a promotional 5 star item:", 
						"React with :white_check_mark:", 
						false);
				embedBuilder.addField("Otherwise:", 
						"React with :x:", 
						false);
				embedBuilder.setTitle(this.getEmbedTitle(currUser));
				MessageEmbed message = embedBuilder.build();
				e.getChannel().sendMessage(message).queue(m ->{
					m.addReaction(this.CHOICE_YES).queue();
					m.addReaction(this.CHOICE_NO).queue();
					long messageID = m.getIdLong();
					
					UserInfo curr = this.users.get(user);
					curr.currentMessage = messageID;
					curr.state = State.PROMO_FIVE_STATE;
					
				});
			}
		}
	}

	private void handlePityFive(MessageReceivedEvent e) {
		User user = e.getAuthor();
		String command = e.getMessage().getContentDisplay();
		UserInfo currUser = this.users.get(user);
		//User entered valid pity value
		if(currUser.isValidPityFive(command)) {
			currUser.pityFive = Integer.parseInt(command);
			//Setup next state
			currUser.state = State.WAIT_STATE;
			//Ask for four star pity
			EmbedBuilder embedBuilder = new EmbedBuilder();
			embedBuilder.setColor(Color.BLUE);
			embedBuilder.addField("Please enter the following value:", "4 star pity (1-10)", false);
			embedBuilder.setTitle(this.getEmbedTitle(currUser));
			MessageEmbed message = embedBuilder.build();
			e.getChannel().sendMessage(message).queue(m ->{
				//Can only set state once message is actually sent
				UserInfo curr = this.users.get(user);
				curr.state = State.PITY_FOUR_STATE;
			});
		}
	}

	private void handleBannerChoice(MessageReactionAddEvent e) {
		

		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setColor(Color.BLUE);
		
		User user = e.getUser();
		UserInfo currUser = this.users.get(user);
		
		currUser.state = State.WAIT_STATE;
		
		if(e.getReactionEmote().getAsCodepoints().equals(this.CHOICE_ONE)) {
			embedBuilder.addField("Please enter the following value:", "5 star pity (1-90)", false);
			embedBuilder.setTitle("You chose the event banner.");
			currUser.currentBanner = currUser.eventBanner;
		}
		else if(e.getReactionEmote().getAsCodepoints().equals(this.CHOICE_TWO)) {
			embedBuilder.addField("Please enter the following value:", "5 star pity (1-80)", false);
			embedBuilder.setTitle("You chose the weapon banner.");
			currUser.currentBanner = currUser.weaponBanner;
		}
		else if(e.getReactionEmote().getAsCodepoints().equals(this.CHOICE_THREE)) {
			embedBuilder.addField("Please enter the following value:", "5 star pity (1-90)", false);
			embedBuilder.setTitle("You chose the standard banner.");
			currUser.currentBanner = currUser.standardBanner;
		}
		MessageEmbed message = embedBuilder.build();
		e.getChannel().sendMessage(message).queue(m ->{
			//Can only set state once message is actually sent
			UserInfo curr = this.users.get(user);
			curr.state = State.PITY_FIVE_STATE;
		});
	}
	
	private String getEmbedTitle(UserInfo currUser) {
		if(currUser.currentBanner instanceof EventBanner) {
			return "Event Banner";
		}
		else if(currUser.currentBanner instanceof WeaponBanner) {
			return "Weapon Banner";
		}
		else {
			return "Standard Banner";
		}
	}
}
