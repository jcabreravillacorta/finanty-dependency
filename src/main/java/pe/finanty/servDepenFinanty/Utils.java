package pe.finanty.servDepenFinanty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	public static Boolean validarTelefonoCelular(String telefono) {
		Boolean r = false;

		if (telefono.length() == 9) {

			Pattern pattern = Pattern.compile("^[9]\\d{8}$");

			String tel = telefono;

			Matcher mather = pattern.matcher(tel);

			if (mather.find() == true) {
				r = true;
			} else {
				r = false;
			}

		} else {
			r = false;
		}
		return r;
	}

	public static Boolean validarTelefonoFijo(String telefono) {
		Boolean r = false;
		Pattern pattern = null;

		if (telefono.length() == 7 || telefono.length() == 6) {

			if (telefono.length() == 7) {
				pattern = Pattern.compile("[^091]\\d{6}$");
			} else if (telefono.length() == 6) {
				pattern = Pattern.compile("[^091]\\d{5}$");
			}

			String tel = telefono;

			Matcher mather = pattern.matcher(tel);

			if (mather.find() == true) {
				r = true;
			} else {
				r = false;
			}

		} else {
			r = false;
		}

		return r;
	}

	public static Boolean validarEmail(String emailParam) {
		Boolean r = false;
		Pattern pattern = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))");

		String email = emailParam;

		Matcher mather = pattern.matcher(email);

		if (mather.find() == true) {
			r = true;
		} else {
			r = false;
		}

		return r;
	}
}
