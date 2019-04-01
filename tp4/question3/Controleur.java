package question3;

import question3.tp3.PileI;
import question3.tp3.PilePleineException;
import question3.tp3.PileVideException;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Décrivez votre classe Controleur ici.
 * 
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Controleur extends JPanel {

    private JButton push, add, sub, mul, div, clear;
    private PileModele<Integer> pile;
    private JTextField donnee;

    public Controleur(PileModele<Integer> pile) {
        super();
        this.pile = pile;
        this.donnee = new JTextField(8);

        this.push = new JButton("push");
        this.add = new JButton("+");
        this.sub = new JButton("-");
        this.mul = new JButton("*");
        this.div = new JButton("/");
        this.clear = new JButton("[]");

        setLayout(new GridLayout(2, 1));
        add(donnee);
        JPanel boutons = new JPanel();
        boutons.setLayout(new FlowLayout()); 
        ButtonListener actionListener = new ButtonListener();

        boutons.add(push);  push.addActionListener(actionListener);
        boutons.add(add);   add.addActionListener(actionListener);
        boutons.add(sub);   sub.addActionListener(actionListener);
        boutons.add(mul);   mul.addActionListener(actionListener);
        boutons.add(div);   div.addActionListener(actionListener);
        boutons.add(clear); clear.addActionListener(actionListener);
        add(boutons);
        boutons.setBackground(Color.red);
        actualiserInterface();
    }

    public void actualiserInterface() {
        // à compléter
        if(pile.estPleine()) push.setEnabled(false);
        else push.setEnabled(true);
        if(pile.taille() <= 1){
            add.setEnabled(false);
            sub.setEnabled(false);
            mul.setEnabled(false);
            div.setEnabled(false);
        } else {
            add.setEnabled(true);
            sub.setEnabled(true);
            mul.setEnabled(true);
            div.setEnabled(true);
        }
    }

    private Integer operande() throws NumberFormatException {
        return Integer.parseInt(donnee.getText());
    }

    // à compléter
    // en cas d'exception comme division par zéro, 
    // mauvais format de nombre suite à l'appel de la méthode operande
    // la pile reste en l'état (intacte)
     public class ButtonListener implements ActionListener{
    public void actionPerformed(ActionEvent event){
        String commande = event.getActionCommand();
        if(commande.equals("push")){
            try{
                pile.empiler(operande());
            } catch(NumberFormatException e){}
            catch(PilePleineException e) {e.printStackTrace();}
        } else if(commande.equals("[]")){
            while(!pile.estVide()){
                try{
                    pile.depiler();
                } catch(PileVideException ex){ex.printStackTrace();}
            }
        } else if(commande.equals("+")||commande.equals("-")||commande.equals("*")||commande.equals("/")){
            int _1stoperand = 0;
            int _2ndoperand = 0;
            boolean dividedbyzero = false;
            try{
                _1stoperand = pile.depiler();
                _2ndoperand = pile.depiler();
            } catch(PileVideException pve){pve.printStackTrace();}
                
            int res = 0;
            
            if(commande.equals("+")) res = _2ndoperand + _1stoperand;
            else if(commande.equals("-")) res = _2ndoperand - _1stoperand;
            else if(commande.equals("*")) res = _2ndoperand * _1stoperand;
            else if(commande.equals("/")) {
                if(_1stoperand == 0) dividedbyzero = true;
                else res = _2ndoperand / _1stoperand;
            }
            try{
                if(dividedbyzero){
                    pile.empiler(_2ndoperand);
                    pile.empiler(_1stoperand);
                }
                else pile.empiler(res);
            } catch(PilePleineException ex){ex.printStackTrace();}
        }
       
        actualiserInterface();
    }
}

}
