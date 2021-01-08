package GenshinBot.listeners;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import GenshinBot.Driver;
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

public class PromoFiveListener extends ListenerAdapter{	
	@Override
    public void onMessageReactionAdd(MessageReactionAddEvent e) {
		if(e.getUser().isBot()) {
			return;
		}
		User user = e.getUser();
		//Random reaction, ignore
		if(!Driver.users.containsKey(user)) {
			return;
		}
		UserInfo currUser = Driver.users.get(user);
		if(currUser.state == State.PROMO_FIVE_STATE) {
			if(currUser.currentMessage == e.getMessageIdLong()) {
				handlePromoFive(e);
			}
		}
	}
	private void handlePromoFive(MessageReactionAddEvent e) {
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setColor(Color.BLUE);
		
		User user = e.getUser();
		UserInfo currUser = Driver.users.get(user);
		
		embedBuilder.setTitle(Driver.getEmbedTitle(currUser));
		
		currUser.state = State.WAIT_STATE;
		
		//Yes promo five star
		if(e.getReactionEmote().getAsCodepoints().equals(Driver.CHOICE_YES)) {
			currUser.promoFive = true;
		}
		//No promo five star
		else if(e.getReactionEmote().getAsCodepoints().equals(Driver.CHOICE_NO)) {
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
			m.addReaction(Driver.CHOICE_YES).queue();
			m.addReaction(Driver.CHOICE_NO).queue();
			//Can only set state once message is actually sent
			UserInfo curr = Driver.users.get(user);
			curr.currentMessage = m.getIdLong();
			curr.state = State.PROMO_FOUR_STATE;
		});
	}
}