package com.matyrobbrt.mobcapturingtool;

import com.google.auto.service.AutoService;
import com.matyrobbrt.mobcapturingtool.util.LoaderData;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

@AutoService(LoaderData.class)
public class ForgeLoaderData implements LoaderData {

    @Override
    public Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }
}
