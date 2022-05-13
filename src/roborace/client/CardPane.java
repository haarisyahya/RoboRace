package roborace.client;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import roborace.common.GameDialogs;

public class CardPane extends JPanel implements MouseListener {
	
    private final Image noCard;
    private final Image[] selectImages;
    private CardList cards;
    private CardList selectedCards;
    private boolean selecting;
    private final SelectDoneButton okButton;
    private final JFrame parent;

    @SuppressWarnings("LeakingThisInConstructor")
    public CardPane(SelectDoneButton okButton, JFrame parent) {
        this.okButton = okButton;
        this.parent = parent;
        okButton.setOwner(this);
        super.setPreferredSize(new Dimension(644,120));
        ImageAndAnimationFactory aminFac = ImageAndAnimationFactory.getInstance();
        noCard = aminFac.getNoCardImage();
        selectImages = aminFac.getSelectImages();
        cards = new CardList();
        selectedCards = new CardList();
        selecting = false;
        super.addMouseListener(this);
    }

    public CardList selectCards(CardList list) {
        cards = list;
        selecting = true;
        okButton.setEnabled(true);
        repaint();
        synchronized (this) {
            while (selecting) {
                try {
                    wait();
                } catch (InterruptedException e) {}
            }
        }
        return cards;
    }

    public void stopSelection() {
        if (selectedCards.size() == 5) {
            cards = selectedCards;
            selectedCards = new CardList();
            selecting = false;
            okButton.setEnabled(false);
            repaint();
            synchronized(this) {
                notify();
            }
        } else {
            GameDialogs.showMessageDialog("Selection error", "You need to select 5 cards.",parent);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        for(int i=0; i<7; i++) {
            if (i>=cards.size()) 
                g.drawImage(noCard,i*92,0,null);
            else {
                Card c = cards.get(i);
                g.drawImage(c.getImage(),i*92,0,null);
                int index = selectedCards.indexOf(c);
                if (index != -1) {
                    g.drawImage(selectImages[index],i*92+30,50,null);
                }
            }
        }		
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (selecting && e.getButton() == MouseEvent.BUTTON1) {
            int number = e.getX() / 92;
            if (0 <= number && number < cards.size()) {
                Card c = cards.get(number);
                if (selectedCards.contains(c)) {
                    selectedCards.remove(c);
                } else {
                    if (selectedCards.size() < 5) {
                        selectedCards.add(c);
                    }
                }
                repaint();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }	
}