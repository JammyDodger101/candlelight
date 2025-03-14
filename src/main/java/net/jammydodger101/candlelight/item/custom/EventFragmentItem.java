package net.jammydodger101.candlelight.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/*
Event fragments, given out in events, that are used to craft candle compasses and revivers
 */

public class EventFragmentItem extends Item {

    public EventFragmentItem(Settings settings) {
        super(settings);
    }

    // always has a glint
    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
