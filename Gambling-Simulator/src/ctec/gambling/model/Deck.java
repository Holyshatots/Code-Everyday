package ctec.gambling.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Deck

{
	private List<Card> deck = new LinkedList<Card>();

	public Deck()
	{
		for (Card.Suit suit : Card.Suit.values())
		{
			for (Card.Value value : Card.Value.values())
			{
				deck.add(new Card(suit, value));
			}
		}
		shuffle();
	}

	public void shuffle()
	{
		Collections.shuffle(deck);
	}

	public int cardsLeft()
	{

		return deck.size();
	}

	public Card dealCard()
	{
	
		if (deck.size() == 52)
		{
			shuffle();
		}
		Card temp;
		temp = deck.get(0);
		remove(0);
		return temp;
	}

	public Card getCard(int i)
	{
		return deck.get(i);
	}

	public Card remove(int i)
	{
		Card c = deck.get(i);
		deck.remove(i);
		return c;
	}
}
