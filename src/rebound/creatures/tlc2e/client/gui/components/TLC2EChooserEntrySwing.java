package rebound.creatures.tlc2e.client.gui.components;

import static rebound.hci.graphics2d.gui.layout.colinear.ColinearLayout.*;
import static rebound.math.SmallIntegerMathUtilities.*;
import static rebound.math.geom2d.Direction2D.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import rebound.hci.graphics2d.ColorUtilities;
import rebound.hci.graphics2d.gui.awt.components.HighlightableComponent;
import rebound.hci.graphics2d.gui.awt.layout.colinear.ColinearAwtLayoutManager;
import rebound.hci.graphics2d.gui.layout.colinear.data.targetful.ColinearLayoutEntry;
import rebound.hci.graphics2d.gui.layout.colinear.data.targetful.ColinearLayoutParent;
import rebound.hci.util.awt.JavaGUIUtilities;

public class TLC2EChooserEntrySwing
extends JComponent
implements HighlightableComponent
{
	private static final long serialVersionUID = 1L;
	
	
	public static final Color FG = new Color(192, 192, 208);
	public static final Color BorderColor = new Color(208, 208, 255);
	public static final Color BGHighlighted = new Color(80, 80, 90);
	public static final Color BGUnhighlighted = new Color(64, 64, 72);
	
	
	
	
	protected @Nullable BufferedImage icon;  //make it empty rather than null :3
	
	protected int nameHeight = 15;
	
	protected int paddingAroundImageOuter = 2;
	protected int paddingAroundImageInner = 2;
	
	protected Color highlightedBGColor = BGHighlighted;
	protected Color unhighlightedBGColor = BGUnhighlighted;
	
	protected @Nullable JLabel nameLabel;
	protected @Nullable JTextArea descriptionTextArea;
	
	protected ColinearLayoutParent layoutData;
	
	
	
	public TLC2EChooserEntrySwing(@Nullable BufferedImage icon, @Nullable String name, @Nullable String description)
	{
		setHighlighted(false);
		
		this.setForeground(FG);
		this.setBorder(new LineBorder(BorderColor, 1));
		
		if (description == null)
			description = "";
		
		
		this.icon = icon;
		
		
		//Description
		{
			descriptionTextArea = new JTextArea();
			
			descriptionTextArea.setText(description);
			
			descriptionTextArea.setBorder(new LineBorder(new Color(128, 128, 192, 64), 1));
			
			this.add(descriptionTextArea);
			
			descriptionTextArea.setBackground(ColorUtilities.Transparent);
			descriptionTextArea.setForeground(FG);
			
			descriptionTextArea.setEditable(false);
			
			descriptionTextArea.setFocusable(false);  //keyboard opt-out
			JavaGUIUtilities.destroyOpacityToMouseEvents(descriptionTextArea);  //mouse opt-out
		}
		
		
		
		
		
		List<ColinearLayoutEntry> l = new ArrayList<>();
		l.add(clefix(5, null));
		
		float vpad = 2;
		
		if (nameLabel != null)
		{
			l.add(clefix(nameHeight, nameLabel));
			l.add(clefix(vpad, null));
		}
		
		l.add(clerem(descriptionTextArea));
		l.add(clefix(4, null));
		
		ColinearLayoutParent layoutForDataArea = clepC(YVertical, l);
		
		
		layoutData = clepV(XHorizontal, clefix(7, null), clerem(layoutForDataArea), clefix(7, null));
		
		
		
		
		
		doLayout();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	protected void paintComponent(Graphics g)
	{
		int w = this.getWidth();
		int h = this.getHeight();
		
		//Clear bg :>
		{
			g.setColor(this.getBackground());
			g.fillRect(0, 0, w, h);
		}
		
		
		
		
		BufferedImage image = this.icon;
		
		if (image != null)
		{
			//Draw image! :D
			{
				int iw = least(h, icon.getWidth()+(paddingAroundImageInner+1)*2)+paddingAroundImageOuter*2;
				
				int imageSideLength = iw - (paddingAroundImageInner+1+paddingAroundImageOuter)*2;
				
				int xo = paddingAroundImageOuter;
				int yo = (iw - greatest(imageSideLength+(paddingAroundImageInner+1)*2, 0)) / 2;
				
				if (imageSideLength > 0)
				{
					g.drawImage(image, xo + 1 + paddingAroundImageInner, yo + 1 + paddingAroundImageInner, null);
				}
				
				g.setColor(this.getForeground());
				
				int s = paddingAroundImageInner + imageSideLength + paddingAroundImageInner;
				s++;  //width/height must be the number of non-drawn pixels inside the rectangle + 1
				g.drawRect(xo, yo, s, s);
			}
		}
	}
	
	
	@Override
	public void doLayout()
	{
		int w = this.getWidth();
		int h = this.getHeight();
		
		int imageRegionWidth = hasIcon() ? (least(h, icon.getWidth()+(paddingAroundImageInner+1)*2)+paddingAroundImageOuter*2) : 0;
		
		new ColinearAwtLayoutManager(layoutData).layout(imageRegionWidth, 0, w - imageRegionWidth, h);
	}
	
	
	
	
	
	public boolean hasIcon()
	{
		return icon != null;
	}
	
	
	public @Nullable BufferedImage getIcon()
	{
		return icon;
	}
	
	public @Nullable String getEntryName()
	{
		return nameLabel == null ? null : nameLabel.getText();
	}
	
	public @Nullable String getEntryDescription()
	{
		return descriptionTextArea == null ? null : descriptionTextArea.getText();
	}
	
	
	
	
	
	
	protected boolean highlighted;
	
	public void setHighlighted(boolean v)
	{
		this.highlighted = v;
		this.setBackground(v ? highlightedBGColor : unhighlightedBGColor);
		repaint();
	}
	
	@Override
	public boolean isHighlighted()
	{
		return highlighted;
	}
}
