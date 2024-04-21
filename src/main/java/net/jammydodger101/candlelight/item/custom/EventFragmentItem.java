package net.jammydodger101.candlelight.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EventFragmentItem extends Item {

    public EventFragmentItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
