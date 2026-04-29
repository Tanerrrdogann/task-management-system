package com.gorevyonetimi;

import com.gorevyonetimi.util.FileLogger;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ShutdownHandler {

    @EventListener(ContextClosedEvent.class)
    public void onShutdown() {
        FileLogger.log("🛑 Uygulama kapatılıyor: Kapanış işlemleri başlatıldı.");
    }
}
