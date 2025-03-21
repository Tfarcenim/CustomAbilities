package tfar.customabilities.client;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import tfar.customabilities.ability.MariAbility;

public class SetPercentageScreen extends Screen {

    protected int imageWidth = 176;
    /**
     * The Y size of the inventory window in pixels.
     */
    protected int imageHeight = 166;
    protected int titleLabelX;
    protected int titleLabelY;

    /**
     * Starting X position for the Gui. Inconsistent use for Gui backgrounds.
     */
    protected int leftPos;
    /**
     * Starting Y position for the Gui. Inconsistent use for Gui backgrounds.
     */
    protected int topPos;

    protected SetPercentageScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        addRenderableWidget(new CommonSlider(leftPos,topPos,200,20,Component.empty(),Component.empty(),0,1, MariAbility.chance,0,2,true));
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return super.mouseReleased(mouseX, mouseY, button);
    }
}
