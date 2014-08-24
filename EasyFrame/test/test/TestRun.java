/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import sergiocosentino.easyframe.constructor.EasyFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 *
 * @author Sergio
 */
public class TestRun {
	
	public TestRun() {
		EasyFrame ef = EasyFrame.getInstanceOf();
		ef.addComponent(1, 1, new JLabel("0,0"), "00");
		ef.addComponent(1, 3, new JLabel("0,2"), "13");
		ef.addComponent(2, 2, new JLabel("1,1"), "22");
		ef.pack();
		ef.removeComponent("22");
		ef.pack();
		JTextArea area = new JTextArea();
		area.setColumns(8);
		area.setRows(13);
		area.setLineWrap(true);
		ef.addScrolledComponent(3, 1, area, "area");
		JLabel label = new JLabel("aaaaaaaaaaaa");
		ef.addLimitedComponent(3, 2, label, "label", 30, 20);
		ef.addTextArea(4, 1, "area2", 15, 5);
		ef.addButtonActionListener("area", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int a = 1;
			}
		});
		ef.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		ef.addNextComponent(new JLabel("next"), "next", 1);
		ef.pack();
		System.out.println(Arrays.toString(ef.getReferenceList()));
	}
	
	public static void main(String[] args) {
		TestRun testRun = new TestRun();
	}
}
