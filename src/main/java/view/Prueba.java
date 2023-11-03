package view;

import java.util.ArrayList;

import manejador.ManejadorWeb;
import model.Time;

public class Prueba {

	public static void main(String[] args) {
		
		ManejadorWeb manejador = new ManejadorWeb("http://api.openweathermap.org/data/2.5/forecast?q=Toledo,es&units=metric&mode=xml&APPID=601c9db344b44f9774ef76a4c07979b1&lang=sp");
		ArrayList<Time> times = manejador.parsear();
		
		for (Time time:times) {
			System.out.println(time);
		}

	}

}
