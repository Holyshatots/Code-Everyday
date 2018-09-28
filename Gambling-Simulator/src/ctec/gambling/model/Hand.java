package ctec.gambling.model;

import java.util.Vector;

public class Hand
{
	private Vector<Card> hand;

	public Hand()
	{
		hand = new Vector<Card>();
	}

	public void addCard(Card c)
	{
		if (c != null)
			hand.addElement(c);
	}

	public void showHand()
	{
		for (int i = 0; i < hand.size(); i++)
		{
			System.out.println(hand.elementAt(i));
		}
	}

	public int getHandSize()
	{
		return hand.size();
	}

	public Card getCard(int position)
	{
		if (position >= 0 && position < hand.size())
			return (Card) hand.elementAt(position);
		else
			return null;
	}

	public int getBlackJackValue()
	{
		int val = 0;
		boolean ace = false;
		for (int i = 0; i < hand.size(); i++)
		{
			Card card;
			card = getCard(i);
			val += card.getValueCode();
			if (card.getValueCode() == 1)
			{
				ace = true;
			}
		}
		if (ace == true && val + 10 <= 21)
		{
			val += 10;
		}
		return val;
	}
}