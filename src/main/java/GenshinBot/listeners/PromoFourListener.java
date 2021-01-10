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

public class PromoFourListener extends ListenerAdapter{
	
	
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
		if(currUser.state == State.PROMO_FOUR_STATE) {
			if(currUser.currentMessage == e.getMessageIdLong()) {
				handlePromoFour(e);
			}
		}
	}

	private void handlePromoFour(MessageReactionAddEvent e) {
		User user = e.getUser();
		UserInfo currUser = Driver.users.get(user.getIdLong());
		
		
		currUser.state = State.WAIT_STATE;
		
		//Yes promo four star
		if(e.getReactionEmote().getAsCodepoints().equals(Driver.CHOICE_YES)) {
			currUser.promoFour = true;
		}
		//No promo four star
		else if(e.getReactionEmote().getAsCodepoints().equals(Driver.CHOICE_NO)) {
			currUser.promoFour = false;
		}
		
		currUser.setBannerValues(currUser.pityFive, 
				currUser.pityFour, 
				currUser.promoFive, 
				currUser.promoFour);
		
		WishListener.showWishEmbed(e);
	}
}
