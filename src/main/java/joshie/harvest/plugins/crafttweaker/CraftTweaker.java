package joshie.harvest.plugins.crafttweaker;

import joshie.harvest.core.util.HFLoader;
import minetweaker.MineTweakerAPI;

@HFLoader(mods = "MineTweaker3")
public class CraftTweaker {
    public static void init() {
        MineTweakerAPI.registerClass(Shipping.class);
        MineTweakerAPI.registerClass(Shops.class);
    }
}
