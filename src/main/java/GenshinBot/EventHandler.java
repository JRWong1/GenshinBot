package GenshinBot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

public abstract class EventHandler {
	public abstract void handleReaction(MessageReactionAddEvent e);
	public abstract void handleMessage(MessageReceivedEvent e);
}
