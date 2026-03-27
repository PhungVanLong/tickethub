package com.tickethub.indentity.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventPublisherLogger {
    private static final Logger LOG = LoggerFactory.getLogger(EventPublisherLogger.class);

    @EventListener
    public void onUserUpdated(UserUpdatedEvent event) {
        LOG.info("Published UserUpdated event for userId={}", event.userId());
    }

    @EventListener
    public void onVoucherUpdated(VoucherUpdatedEvent event) {
        LOG.info("Published VoucherUpdated event for voucherId={}", event.voucherId());
    }
}
