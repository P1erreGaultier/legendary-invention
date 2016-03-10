
package com.extensions.coolclick;

import com.alma.application.interfaces.handler.IClickHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CoolClick implements IClickHandler {

    	public CoolClick() {

    	}

        public void setHandler(JLabel label){
            label.setCursor(
                    Toolkit.getDefaultToolkit().createCustomCursor(
                            new ImageIcon(this.getClass().getResource("cursor.png")).getImage(),
                            new Point(0,0),
                            "Target cursor")
            );
        }

}

