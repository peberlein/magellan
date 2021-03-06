package com.dreamcodex.ti.actions;

import com.dreamcodex.ti.component.MapEditor;
import com.dreamcodex.ti.exporters.MapImageExporter;
import com.dreamcodex.ti.iface.IconProvider;
import com.dreamcodex.ti.util.DataSet;
import com.dreamcodex.ti.util.Preferences;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class ExportMapImageAction extends MagellanAction {

    public ExportMapImageAction(String name, JFrame parent, IconProvider iconProvider, MapEditor mapEditor, DataSet dataSet, Preferences preferences) {
        super(name, parent, iconProvider, mapEditor, dataSet, preferences);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File file = getFileFromChooser(preferences.getCurrentDirectory(), JFileChooser.SAVE_DIALOG, IMGEXTS, "Image Files");
        if (file != null) {
            boolean isExtensionAdded = false;
            for (int ex = 0; ex < IMGEXTS.length; ex++) {
                if (file.getAbsolutePath().toLowerCase().endsWith("." + IMGEXTS[ex])) {
                    isExtensionAdded = true;
                }
            }
            if (!isExtensionAdded) {
                file = new File(file.getAbsolutePath() + "." + IMGEXT);
            }
            MapImageExporter magIO = new MapImageExporter(mapEditor, dataSet.getEcmPalettes(), dataSet.getClrSets(), dataSet.getCharGrids(), dataSet.getCharColors(), dataSet.getEcmCharPalettes(), dataSet.getEcmCharTransparency(), dataSet.getSpriteGrids(), dataSet.getSpriteColors(), dataSet.getEcmSpritePalettes(), preferences.getColorMode());
            try {
                magIO.writeMapImage(file);
            } catch (IOException ee) {
                showError("Exoprt failed", ee.getMessage());
            }
        }
    }
}
