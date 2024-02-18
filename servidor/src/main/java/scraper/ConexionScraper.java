package scraper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import recursoscomunes.Paginas;

public class ConexionScraper {
	
	private static String usuario;

	private static void urlNuevoGET(String recursoSolicitado) throws IOException, URISyntaxException {
		URI uri = new URI(recursoSolicitado);// Uniform Resource Identifier (URI)
		URL url = uri.toURL(); // Uniform Resource Locator (URL)
		
		procesarSolicitud(url);
	}

	private static void procesarSolicitud(URL url) throws IOException {

		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		StringBuffer body = leerBodyScraper(urlConnection);
		String formAction = sacarAction(body.toString());
		String variableGET = sacarVariableGET(body.toString());
		ArrayList<String> listaOpciones = extraerListaOpciones(body.toString());
		ArrayList<String> partidasGanadas = new ArrayList<>();
		ArrayList<String> partidasPerdidas = new ArrayList<>();
		for(String opcion: listaOpciones) {
			String respuesta = conexionVerPartida(formAction + "?" + variableGET + "="+opcion);
			String partidaGanada = esGanador(respuesta,usuario);
			if(partidaGanada!=null) {
				partidasGanadas.add("---Partida nº"+opcion);
			}
			
			if(esPerdedor(respuesta,usuario)==1) {
				partidasPerdidas.add("---Partida nº"+opcion);
			}
		}
		
		if(partidasGanadas.size()==0 && partidasPerdidas.size()==0) {
			System.out.println("Este usuario no tiene información sobre las partidas");
		}else {
			System.out.println("Partidas ganadas de "+usuario);
			for(String partida: partidasGanadas) {
				System.out.println(partida);
			}
			System.out.println("Partidas perdidas de "+usuario);
			for(String partida: partidasPerdidas) {
				System.out.println(partida);
			}
			System.out.println("Partidas ganadas: " + partidasGanadas.size() + "------Partidas perdidas: "+ partidasPerdidas.size());
		}
	}
	
	private static String sacarAction(String bodyHTML) {
		String regex = "<form[^>]*action=\"([^\"]+)\"[^>]*>";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(bodyHTML);
		if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
	}
	
	private static String sacarVariableGET(String bodyHTML) {
        String regex = "<select[^>]*name=\"([^\"]+)\"[^>]*>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(bodyHTML);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
	}
	
	private static ArrayList<String> extraerListaOpciones(String bodyHTML){
		ArrayList<String> opciones = new ArrayList<>();
		Document documento = Jsoup.parse(bodyHTML);
		Elements selects = documento.select("select");
		for(Element select: selects) {
			Elements options = select.select("option");
			for(Element opcion: options) {				
				String valorOpcion = opcion.attr("value");
				opciones.add(valorOpcion);
				
			}
		}
		
		return opciones;
	}
	
	private static String conexionVerPartida(String peticion) {
		StringBuilder sb = new StringBuilder();
		String body = "";
		try {
			URI uri = new URI(Paginas.raiz+peticion);
			URL url = uri.toURL();
			HttpURLConnection conexionUrl = (HttpURLConnection) url.openConnection();
			BufferedReader lector = new BufferedReader(new InputStreamReader(conexionUrl.getInputStream(),"UTF-8"));
			String linea;
			while (lector.ready()) {
				linea = lector.readLine();
		    	sb.append(linea).append("\n");
			}
			body = sb.toString();
		}catch(IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		return body;
	}

    private static String esGanador(String bodyHTML, String usuario) {
    	String partidaGanada = null;
        Document document = Jsoup.parse(bodyHTML);
        Element h2Element = document.select("header h2").first();
        String textoH2 = h2Element.text();
        String nombreUsuarioHTML = textoH2.split("=")[1];
        if(nombreUsuarioHTML.equals(usuario)) {
        	partidaGanada = "si";
        }
        
    	return partidaGanada;
    }
    
    private static int esPerdedor(String bodyHTML, String usuario) {
    	//Si es 0 no juega en esa partida, si es 1 es perdedor, si es 2 es ganador
        Document document = Jsoup.parse(bodyHTML);
        Elements h2Elements = document.select("div.board-container h2");
        for(Element h2: h2Elements) {
        	String h2Texto = h2.text();
        	if(h2Texto.equals("Tablero "+usuario)) {
                Element h2Ganador = document.select("header h2").first();
                String nombreGanador = h2Ganador.text().split("=")[1];
                if(nombreGanador.equals(usuario)==false) {
                	return 1;
                }else {
                	return 2;
                }
        	}
        }
    	return 0;
    }

	
	private static StringBuffer leerBodyScraper(HttpURLConnection urlConnection) throws IOException {
		String inputLine;
		StringBuffer sb = new StringBuffer(); 
		try (BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"))){
			while (in.ready()) {
			    inputLine = in.readLine();
		    	sb.append(inputLine).append("\n");
			}
			return sb;
		}catch (IOException e) {
	        // Manejar la excepción, por ejemplo, imprimir el mensaje de error
	        e.printStackTrace();
	    }
		return null;
	}
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		Scanner sc = new Scanner(System.in);
		String recursoSolicitado = Paginas.scraper;
		System.out.println("¿Sobre que usuario quieres la información?");
		usuario = sc.nextLine();
		System.out.println("Info sobre "+usuario);
		urlNuevoGET(recursoSolicitado);
		sc.close();
	}

}
