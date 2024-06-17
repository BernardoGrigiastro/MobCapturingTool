package com.matyrobbrt.mobcapturingtool;

import com.matyrobbrt.mobcapturingtool.util.Constants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD, modid = Constants.MOD_ID)
public class MCTClient {

    @SubscribeEvent
    static void registerItemColours(RegisterColorHandlersEvent.Item event) {
        MCTClientInit.registerItemColours(event::register);
    }

}
