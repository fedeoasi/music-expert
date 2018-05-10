package com.github.fedeoasi.music;

import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import com.github.fedeoasi.gui.ChordProgressionPanel;
import com.github.fedeoasi.gui.MusicExpert;

public class LoadDialog extends JDialog implements ActionListener {
	private JComboBox canzone = new JComboBox();
	private JButton ok = new JButton("Ok");
    private ChordProgressionPanel g = null;
	private File f = null;
	private String[] files;
	private String[] canzoni;
	
	public LoadDialog(MusicExpert me, ChordProgressionPanel g){
		super();
		this.g = g;
		setTitle("Carica brano");
		setLayout(new FlowLayout());
		
		f = new File("Canzoni/");
		files = f.list();
		canzoni = new String[files.length];
		for(int i=0; i<files.length; i++){
			StringTokenizer st = new StringTokenizer(files[i],".");
			String tmp = st.nextToken();
			canzoni[i] = tmp;
			canzone.addItem(tmp);
		}
		
		add(canzone);
		add(ok);
		ok.addActionListener(this);
		Point p = me.getLocation();
		setSize(200,100);
		setLocation(p.x+me.getWidth()/2-100,p.y+me.getHeight()/2-50);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		g.clear();
		g.caricaBrano(files[canzone.getSelectedIndex()]);
		setVisible(false);
	}
}
