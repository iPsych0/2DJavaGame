package dev.ipsych0.myrinnia.skills;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.skills.ui.SkillCategory;

import java.awt.image.BufferedImage;

public class MiningSkill extends Skill {

    /**
     *
     */
    private static final long serialVersionUID = -3083043178984091437L;

    public MiningSkill() {

        initResources();

        categories.add(SkillCategory.Ores);
    }

    @Override
    public BufferedImage getImg() {
        return Assets.miningIcon;
    }

    private void initResources() {
        resources.add(new SkillResource(1, Item.azuriteOre, SkillCategory.Ores, "Obtained by mining Azurite Rocks."));
        resources.add(new SkillResource(2, Item.copperOre, SkillCategory.Ores, "Obtained by mining Copper Rocks."));
        resources.add(new SkillResource(5, Item.ironOre, SkillCategory.Ores, "Obtained by mining Iron Rocks."));
        resources.add(new SkillResource(7, Item.clay, SkillCategory.Ores, "Obtained by mining Clay Rocks."));
        resources.add(new SkillResource(7, Item.tungstenOre, SkillCategory.Ores, "Obtained by mining Tungsten Rocks."));
//        resources.add(new SkillResource(10, Item, SkillCategory.Ores, "Obtained by mining Coal Rocks."));
    }

    @Override
    public String toString() {
        return "Mining";
    }

}
