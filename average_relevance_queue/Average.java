
public class Average {
	public double averageQueue(double[] q, int element){	//�tlag sz�mol� f�ggv�ny, param�tere a t�mb aminek az �tlag�t kell sz�molni �s a t�mb elemsz�ma
		double value=0;										//value:= az �tlag �rt�ke, el�sz�r az �sszeg is ide ker�l, null�val inicializ�lom
		for(int i=0; i<element; i++){
			
			value=value+q[i];
		}
		//System.out.println("vegso ertek: "+value +"/"+element);
		value=value/element;
		return value;										//Visszat�r�s: az �tlag �rt�k�vel
	}

}
