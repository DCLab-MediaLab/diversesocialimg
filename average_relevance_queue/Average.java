
public class Average {
	public double averageQueue(double[] q, int element){	//Átlag számoló függvény, paramétere a tömb aminek az átlagát kell számolni és a tömb elemszáma
		double value=0;										//value:= az átlag értéke, elõször az összeg is ide kerül, nullával inicializálom
		for(int i=0; i<element; i++){
			
			value=value+q[i];
		}
		//System.out.println("vegso ertek: "+value +"/"+element);
		value=value/element;
		return value;										//Visszatérés: az átlag értékével
	}

}
