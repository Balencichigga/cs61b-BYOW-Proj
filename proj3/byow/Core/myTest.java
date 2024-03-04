package byow.Core;

import byow.TileEngine.TETile;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class myTest {
    @Test

    public void myTest() {

        Engine e1 = new Engine();

        Engine e2 = new Engine();

        TETile[][] result = e1.interactWithInputString("n1392967723524655428sddsaawwsaddw");

        e2.interactWithInputString("n1392967723524655428sddsaawws:");

        TETile[][] result2 = e2.interactWithInputString("laddw");

        assertThat(result2).isEqualTo(result);

    }
}
