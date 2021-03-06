package de.jensklingenberg.tuxoid.data;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import de.jensklingenberg.tuxoid.interfaces.Removable;
import de.jensklingenberg.tuxoid.model.Coordinate;
import de.jensklingenberg.tuxoid.model.element.Element;
import de.jensklingenberg.tuxoid.model.element.ElementType;

/*Loads the Level from Textfile
 *
 */


public class GameLoader {

    private LevelLoadListener loadListener;
    private AssetManager assetManager;


    public static Element[][][] test(int stage, int cols, int row) {
        return new Element[stage][cols][row];
    }


    public GameLoader(AssetManager assetManager) {
        this.assetManager = assetManager;
    }


    public void setListener(LevelLoadListener listener) {
        this.loadListener = listener;
    }

    public void createLevel(int aktLevel) throws IOException {
        createLevel(String.valueOf(aktLevel));
    }

    public void createLevel(String aktLevel) throws IOException {

        Integer[][][] intLevel;
        Integer[][][] intLevelo;

        int ebene = 0;
        String[] num = null;
        int colCount = 0; // Anzahl Felder/Spalten
        int[] rowCount = new int[5];

        InputStream in = assetManager.open("lvl/lvl" + aktLevel);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;

        while ((line = reader.readLine()) != null) {

            if (line.length() == 0) {
                ebene++;
            }

            rowCount[ebene]++;
            colCount = line.split(",").length;

        }

        reader.close();
        in.close();

        InputStream in2 = assetManager.open("lvl/lvl" + aktLevel);
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(in2));
        String line2;

        intLevel = new Integer[ebene + 1][rowCount[0]][colCount];
        intLevelo = new Integer[ebene + 1][rowCount[0]][colCount];

        int i = 0;
        ebene = 0;

        while ((line2 = reader2.readLine()) != null) {

            if (line2.length() == 0) {
                ebene++;
                i = 0;
            } else {
                line2 = line2.replaceAll("\\s", "");
                num = line2.split(",");

                for (int n = 0; n < num.length; n++) {
                    Element element = ElementFactory.parseElement(Integer.parseInt(num[n]), new Coordinate(ebene, i, n));

                    intLevel[ebene][i][n] = Integer.parseInt(num[n]);
                    intLevelo[ebene][i][n] = Integer.parseInt(num[n]);


                    if (element instanceof Removable) {
                        intLevelo[ebene][i][n] = ElementType.BACKGROUND;
                    }
                }
                i++;
            }

        }
        reader.close();

        if (loadListener != null) {
            loadListener.onIntLevelLoaded(intLevel,intLevelo);
        }

    }




}
