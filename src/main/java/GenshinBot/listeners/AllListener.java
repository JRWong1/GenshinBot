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
