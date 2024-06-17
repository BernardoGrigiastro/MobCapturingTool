package com.matyrobbrt.mobcapturingtool;

import com.matyrobbrt.mobcapturingtool.item.MCTItems;
import com.matyrobbrt.mobcapturingtool.util.Constants;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@Mod(Constants.MOD_ID)
public class MobCapturingTool {
    
    public MobCapturingTool(IEventBus modBus) {
        MCTInit.modInit();

        modBus.addListener((final FMLCommonSetupEvent event) -> MCTInit.commonSetup());
        modBus.addListener((final BuildCreativeModeTabContentsEvent event) -> {
            if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
                event.accept(MCTItems.CAPTURING_TOOL.get());
            }
        });

        NeoForge.EVENT_BUS.addListener(this::onEntityInteract);
    }

    private void onEntityInteract(final PlayerInteractEvent.EntityInteract event) {
        final var res = MCTInit.handleEntityInteraction(event.getEntity(), event.getTarget(), event.getHand());
        if (res != null) {
            event.setCancellationResult(res);
            event.setCanceled(true);
        }
    }
}
