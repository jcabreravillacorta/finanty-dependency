package pe.finanty.servDepenFinanty;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Utils {

	String TMP_DIR = OSValidator.isWindows() ? "c:\\tmp\\" : "/tmp/";

	static List getListNotNull(List list) {
		if (list == null) {
			return new ArrayList();
		}
		return list;
	}


	static BigDecimal recortarDecimalSinRedondeoMulti(BigDecimal a, BigDecimal b, Integer cantDecimales){

		double numero2 = a.doubleValue() * b.doubleValue();
		String format = "#.";
		for (int i = 1 ; i <= cantDecimales; i++){
			format = format.concat("0");
		}

		DecimalFormat formato1 = new DecimalFormat(format);

		return new BigDecimal(formato1.format(numero2));
	}

	static BigDecimal recortarDecimalSinRedondeoDiv(BigDecimal a, BigDecimal b, Integer cantDecimales){

		double numero2 = a.doubleValue() / b.doubleValue();
		String format = "#.";
		for (int i = 1 ; i <= cantDecimales; i++){
			format = format.concat("0");
		}

		DecimalFormat formato1 = new DecimalFormat(format);

		return new BigDecimal(formato1.format(numero2));
	}

	static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}

	static Map convertListToMap(String attr, List items) {
		Map map = new LinkedHashMap();
		items.forEach((item) -> {
			Object key = ObjectUtil.getParentTree(item, attr);
			if (!(key == null)) {
				map.put(key, item);
			}
		});
		return map;
	}


	public static Map convertListToMap(String attrKey, String attrValue, List items) {
		Map map = new LinkedHashMap();
		items.forEach((item) -> {
			Object key = ObjectUtil.getParentTree(item, attrKey);
			if (!(key == null)) {
				Object val = ObjectUtil.getParentTree(item, attrValue);
				map.put(key, val);
			}
		});
		return map;
	}


	public static Map convertListToMapList(String attr, List items) {
		Map map = new LinkedHashMap();
		items.forEach((item) -> {
			Object key = ObjectUtil.getParentTree(item, attr);
			if (!(key == null)) {
				List lista = (List) map.get(key);
				if (lista == null) {
					lista = new ArrayList();
					map.put(key, lista);
				}
				lista.add(item);
			}
		});
		return map;
	}

	public static Map convertListToMapList(String attrKey, String attrValue, List items) {
		Map map = new LinkedHashMap();
		items.forEach((item) -> {
			Object key = ObjectUtil.getParentTree(item, attrKey);
			if (!(key == null)) {
				Object val = ObjectUtil.getParentTree(item, attrValue);
				List lista = (List) map.get(key);
				if (lista == null) {
					lista = new ArrayList();
					map.put(key, lista);
				}
				lista.add(val);
			}
		});
		return map;
	}

	public static ListsInspector analizeLists(List listDB, List listForm, String attr) {
		if (listDB == null) {
			listDB = new ArrayList();
		}
		if (listForm == null) {
			listForm = new ArrayList();
		}

		Map mapListDB = convertListToMap(attr, listDB);
		Map mapListForm = convertListToMap(attr, listForm);

		List newList = new ArrayList();
		List deadList = new ArrayList();
		List oldListDB = new ArrayList();
		List oldListForm = new ArrayList();

		for (Object itemDB : listDB) {
			Object idDB = ObjectUtil.getParentTree(itemDB, attr);
			Object itemForm = mapListForm.get(idDB);
			if (itemForm == null) {
				deadList.add(itemDB);
				continue;
			}
			oldListDB.add(itemDB);
			oldListForm.add(itemForm);
		}

		for (Object itemForm : listForm) {
			Object idForm = ObjectUtil.getParentTree(itemForm, attr);
			Object itemDB = mapListDB.get(idForm);
			if (itemDB == null) {
				newList.add(itemForm);
			}
		}

		ListsInspector inspector = new ListsInspector();
		inspector.setDeadList(deadList);
		inspector.setListDB(listDB);
		inspector.setListForm(listForm);
		inspector.setNewList(newList);
		inspector.setOldListDB(oldListDB);
		inspector.setOldListForm(oldListForm);

		return inspector;
	}

	static boolean esNumerico(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (NumberFormatException e) {

			return false;
		}
	}

	 static HttpComponentsClientHttpRequestFactory getRequestFactory(RestTemplate restTemplate) {
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		try {
			KeyStore clientStore = KeyStore.getInstance("PKCS12");

			InputStream inputStream  =  new ClassPathResource("ssl/star_finanty_boot.p12").getInputStream();

			System.out.println("inputStream --- >" + inputStream);

			clientStore.load(inputStream,
					"Finanty2023".toCharArray());

			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(clientStore, "Finanty2023".toCharArray());
			KeyManager[] keyManagers = kmf.getKeyManagers();

			KeyStore trustStore = KeyStore.getInstance("JKS");

			InputStream inputStream2  =new ClassPathResource("ssl/finanty-2023.jks").getInputStream();

			System.out.println("inputStream2 --- >" + inputStream2);
			trustStore.load(
					inputStream2,
					"Fin@nty2023".toCharArray());

			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(trustStore);
			TrustManager[] tms = tmf.getTrustManagers();

			SSLContext sslContext1 = SSLContext.getInstance("TLS");
			sslContext1.init(keyManagers, tms, new SecureRandom());

			SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext1);

			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

			requestFactory.setHttpClient(httpClient);

			restTemplate.setRequestFactory(requestFactory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestFactory;
	}

	static String obtenerExcepcion(Exception e){
		String[] ss = ExceptionUtils.getRootCauseStackTrace(e);
		String error = StringUtils.join(ss, ", ");
		if (error != null && !error.isEmpty()) {
			if (error.length() > 500) {
				error = error.substring(0, 500);
			}
		}

		return error;
	}

	static List<List> paginarLista(List deudaDTOList, int limite){
		int limit = limite;

		int b = (deudaDTOList.size() / limit) + 1;

		int total = deudaDTOList.size();
		int inicio = (total - limit) < 0 ? 0 : (total - limit);

		List<List> params = new ArrayList<>();
		for (int i = 0; i < b; i++) {

			List<Object> param = deudaDTOList.subList(inicio, total); // 3004 - 5004
			params.add(param);
			total = inicio;                             // 0 - 1004
			inicio = (total - limit) < 0 ? 0 : (total - limit);

		}

		return params;
	}
}
