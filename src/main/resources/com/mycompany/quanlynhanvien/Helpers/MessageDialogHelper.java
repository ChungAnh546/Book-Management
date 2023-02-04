/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlynhanvien.Helpers;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class MessageDialogHelper {
    public static void showMessageDialog(Component parent, String contest, String title)
    {
            JOptionPane.showMessageDialog(parent, contest, title,JOptionPane.INFORMATION_MESSAGE);
    }
    public static void showErrorDialog(Component parent, String contest, String title)
    {
            JOptionPane.showMessageDialog(parent, contest, title,JOptionPane.ERROR_MESSAGE);
    }
    public static int showConfirmDialog(Component parent, String contest, String title)
    {
           int choose = JOptionPane.showConfirmDialog(parent, contest, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
           return choose;
    }
}
