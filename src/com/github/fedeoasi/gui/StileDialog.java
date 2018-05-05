package com.github.fedeoasi.gui;

import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

public class StileDialog extends JDialog implements ActionListener{
	MusicExpert me;
	JComboBox stile = new JComboBox();
	JButton ok = new JButton("Ok");
	
	public StileDialog(MusicExpert me){
		super();
		this.me = me;
		setTitle("Cambia stile");
		setLayout(new FlowLayout());
		for(int i=0; i<7; i++)
		stile.addItem(i+1);
		add(stile);
		add(ok);
		ok.addActionListener(this);
		Point p = me.getLocation();
		setSize(200,100);
		setLocation(p.x+me.getWidth()/2-100,p.y+me.getHeight()/2-50);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==ok){
			me.cambiaStile(stile.getSelectedIndex()+1);
			setVisible(false);
		}
		
	}
	
	

}
