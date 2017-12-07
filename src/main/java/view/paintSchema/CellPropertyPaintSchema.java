package view.paintSchema;

import common.CellType;
import org.apache.log4j.Logger;
import view.component.CellPaintSchema;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class CellPropertyPaintSchema implements CellPaintSchema {
    final private static String prefix = "/CellPropertyPaintSchema/";
    final private Logger logger = Logger.getLogger(this.getClass());
    private Map<CellType, Image> images = new EnumMap<>(CellType.class);

    private static Image loadImage(String filename) {
        return new ImageIcon(CellPropertyPaintSchema.class.getResource(prefix + filename)).getImage();
    }

    public CellPropertyPaintSchema() {
        try (InputStream settings = getClass().getResourceAsStream(prefix + "settings.properties")) {
            if (settings == null) {
                throw new FileNotFoundException();
            }
            Properties schema = new Properties();
            schema.load(settings);
            Set<String> names = schema.stringPropertyNames();
            Arrays.stream(CellType.values())
                    .map(CellType::name)
                    .filter(names::contains)
                    .map(CellType::valueOf)
                    .forEach(k -> images.put(
                            k,
                            loadImage(schema.getProperty(k.name()))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Image getImageForCellType(CellType cellType) {
        Image result = images.get(cellType);
        if (result == null) {
            logger.warn("no find cell type: " + cellType);
            result = images.get(CellType.Error);
        }
        if (result == null) {
            logger.error("no find cell type: " + CellType.Error);
            throw new RuntimeException("Error in schema property");
        }
        return result;

    }

}
