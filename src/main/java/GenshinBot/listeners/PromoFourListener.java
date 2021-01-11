package GenshinBot.listeners;

import GenshinBot.Driver;
import GenshinBot.model.UserInfo;
import GenshinBot.model.UserInfo.State;
import net.dv8tion.jda.api.entities.User;
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
		//Not in proper channel, ignore
		if(!e.getChannel().getName().equals(Driver.CHANNEL)) {
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
