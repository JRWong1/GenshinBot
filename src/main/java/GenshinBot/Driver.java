
package GenshinBot;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import javax.security.auth.login.LoginException;

import GenshinBot.listeners.*;
import GenshinBot.model.EventBanner;
import GenshinBot.model.UserInfo;
import GenshinBot.model.WeaponBanner;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Driver {
	
	
	public static final int TIMEOUT = 60;

	
	//Emoji choices
	public static final String CHOICE_ONE = "U+31U+fe0fU+20e3";
	public static final String CHOICE_TWO = "U+32U+fe0fU+20e3";
	public static final String CHOICE_THREE = "U+33U+fe0fU+20e3";
	public static final String CHOICE_YES = "U+2705";
	public static final String CHOICE_NO = "U+274C";
	
	public static final String CHANNEL = "genshin-bot-commands";
	
	
	public static HashMap<Long, UserInfo> users;
	
	private static final String TOKEN = "";
	
	public static void main(String[] args) {
		try {
			Driver.users = new HashMap<Long, UserInfo>();
			JDA jda = JDABuilder.createDefault(TOKEN)
					.setChunkingFilter(ChunkingFilter.ALL)
					.setMemberCachePolicy(MemberCachePolicy.ALL)
					.enableIntents(GatewayIntent.GUILD_MEMBERS)
					.build();
	        
	        jda.addEventListener(new Help());
	        jda.addEventListener(new AllListener());
	        jda.addEventListener(new StartListener());
	        jda.addEventListener(new BannerListener());
	        jda.addEventListener(new PityFiveListener());
	        jda.addEventListener(new PityFourListener());
	        jda.addEventListener(new PromoFiveListener());
	        jda.addEventListener(new PromoFourListener());
	        jda.addEventListener(new WishListener());
	        jda.addEventListener(new QuitListener());
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
	public static void setTimeoutUser(User user, UserInfo currUser) {
		
		//User already removed
		if(!Driver.users.containsKey(user.getIdLong())) {
			return;
		}
		
		CompletableFuture.delayedExecutor(Driver.TIMEOUT, TimeUnit.SECONDS).execute(() -> {
			//User already removed
			if(!Driver.users.containsKey(user.getIdLong())) {
				return;
			}
			long currTime = System.currentTimeMillis();
			if(Math.abs(currUser.lastInteraction - currTime) >= (long)Driver.TIMEOUT * (long)1000) {
				user.openPrivateChannel()
					.flatMap(channel -> channel.sendMessage("You have timed out, please try again with !start in your server's #genshin-bot-commands."))
					.queue();
				Driver.users.remove(user.getIdLong());
				return;
			}
			else {
				Driver.setTimeoutUser(user, currUser);
			}
		});
	}
	

	public static String getEmbedTitle(User user, UserInfo currUser) {
		if(currUser.currentBanner instanceof EventBanner) {
			return user.getName() + "'s " + "Event Banner";
		}
		else if(currUser.currentBanner instanceof WeaponBanner) {
			return user.getName() + "'s " + "Weapon Banner";
		}
		else {
			return user.getName() + "'s " +"Standard Banner";
		}
	}

}
