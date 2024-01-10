package net.estemon.studio.brickbreaker;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class AssetPacker {

    // constants
    public static final String RAW_DIR = "assets-raw";
    public static final String FINAL_DIR = "assets";

    // main
    public static void main(String[] args) {
        TexturePacker.process(
                RAW_DIR + "/gameplay",
                FINAL_DIR + "/gameplay",
                "gameplay"
        );

        TexturePacker.process(
                RAW_DIR + "/ui",
                FINAL_DIR + "/ui",
                "skin"
        );
    }
}
