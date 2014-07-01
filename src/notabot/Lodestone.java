package notabot;

import java.awt.event.KeyEvent;

public enum Lodestone {
	DRAYNOR(KeyEvent.VK_D),
	LUMBRIDGE(KeyEvent.VK_L),
	ALKHARID(KeyEvent.VK_A),
	VARROCK(KeyEvent.VK_V),
	BURTHORPE(KeyEvent.VK_B),
	LUNAR_ISLE(KeyEvent.VK_ALT,KeyEvent.VK_L),
	BANDIT_CAMP(KeyEvent.VK_ALT,KeyEvent.VK_B),
	TAVERLY(KeyEvent.VK_T),
	EDGEVILLE(KeyEvent.VK_E),
	FALADOR(KeyEvent.VK_F),
	PORT_SARIM(KeyEvent.VK_P),
	ARDOUGNE(KeyEvent.VK_ALT,KeyEvent.VK_A),
	CATHERBY(KeyEvent.VK_C),
	YANILLE(KeyEvent.VK_Y),
	SEERS_VILLAGE(KeyEvent.VK_S),
	EAGLES_PEAK(KeyEvent.VK_ALT,KeyEvent.VK_E),
	TIRANNWN(KeyEvent.VK_ALT,KeyEvent.VK_T),
	OOGLOOG(KeyEvent.VK_O),
	KARAMJA(KeyEvent.VK_K),
	CANIFIS(KeyEvent.VK_ALT,KeyEvent.VK_C),
	WILDERNESS_VOLCANO(KeyEvent.VK_W),
	FREMENNIK(KeyEvent.VK_ALT,KeyEvent.VK_F),
	ASHDALE(KeyEvent.VK_SHIFT,KeyEvent.VK_A);
	
	public int keyint;
	public boolean hasSecondKey;
	public int keyint2;
	
	private Lodestone(int key)
	{
		keyint = key;
		hasSecondKey = false;
		keyint2 = 0;
	}
	private Lodestone(int key1, int key2)
	{
		keyint = key1;
		keyint2 = key2;
		hasSecondKey = true;
	}
	
}
