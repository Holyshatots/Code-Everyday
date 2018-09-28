package ctec.gambling.model;

public class Card
{
	public enum Value
	{
		TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(1);

		public final int value;

		Value(int c)
		{
			value = c;
		}
	}

	public enum Suit
	{
		CLUBS('C'), SPADES('S'), DIAMONDS('D'), HEARTS('H');

		public final char symbol;

		Suit(char c)
		{
			symbol = c;
		}
	}

	private Suit suit;
	private Value value;

	public Card(Suit suit, Value value)
	{
		this.suit = suit;
		this.value = value;
	}

	public Suit getSuit()
	{
		return suit;
	}

	public Value getValue()
	{
		return value;
	}

	public char getSuitCode()
	{
		return suit.symbol;
	}

	public int getValueCode()
	{
		return value.value;
	}

	public String toString()
	{
		return value + " of " + suit;
	}
}