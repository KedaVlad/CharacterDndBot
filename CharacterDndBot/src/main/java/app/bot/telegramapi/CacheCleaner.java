package app.bot.telegramapi;

import app.bot.model.event.CacheClean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class CacheCleaner {
    private final Bot bot;

    public CacheCleaner(Bot bot) {
        this.bot = bot;
    }

    @EventListener
    public void cleanSpam(CacheClean cacheClean) {

        for(Integer messageId: cacheClean.getToDelete()) {
            try {
                bot.execute(DeleteMessage.builder()
                        .chatId(cacheClean.getUserId())
                        .messageId(messageId)
                        .build());
            } catch (TelegramApiException e) {
                log.error("CacheCleaner: " + e.getMessage());
            }
        }
    }
}
