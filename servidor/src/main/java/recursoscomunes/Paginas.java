package recursoscomunes;

import java.util.ArrayList;
import java.util.Arrays;

import info.Barco;
import info.Boton;

public class Paginas {
	public static String scraper = "http://localhost:5000/seleccionget";
	public static String quijote = "http://localhost:5000/quijote";
	public static String raiz = "http://localhost:5000/";
	
	public static String rellenarSeleccionIdPartida(ArrayList<Integer> listaId, String metodo,ArrayList<String> listaDatos) {
		String otroMetodo;
		if(metodo.equals("get")) {
			otroMetodo = "post";
		}else{
			otroMetodo = "get";
		}
		StringBuilder htmlCode = new StringBuilder();
	    htmlCode.append("<!DOCTYPE html>\n" +
	            "<html lang=\"es\">\n" +
	            "\n" +
	            "<head>\n" +
	            "    <meta charset=\"UTF-8\">\n" +
	            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
	            "    <title>Mi Página con Formulario</title>\n" +
	            "    <style>\n" +
	            "        body {\n" +
	            "            font-family: Arial, sans-serif;\n" +
	            "            margin: 0;\n" +
	            "            padding: 0;\n" +
	            "            background-color: #f4f4f4;\n" +
	            "            display: flex;\n" +
	            "            flex-direction: column;\n" +
	            "            align-items: center;\n" +
	            "        }\n" +
	            "\n" +
	            "        nav {\n" +
	            "            background-color: #333;\n" +
	            "            color: #fff;\n" +
	            "            padding: 10px;\n" +
	            "            text-align: center;\n" +
	            "            width: 100%;\n" +
	            "        }\n" +
	            "\n" +
	            "        nav a {\n" +
	            "            color: #fff;\n" +
	            "            text-decoration: none;\n" +
	            "            padding: 10px;\n" +
	            "            margin: 0 10px;\n" +
	            "            border-radius: 5px;\n" +
	            "            transition: background-color 0.3s ease;\n" +
	            "        }\n" +
	            "\n" +
	            "        nav a:hover {\n" +
	            "            background-color: #555;\n" +
	            "        }\n" +
	            "\n" +
	            "        main {\n" +
	            "            padding: 20px;\n" +
	            "            display: flex;\n" +
	            "            justify-content: space-evenly;\n" +
	            "            width: 80%;\n" +
	            "            margin: 0 auto;\n" +
	            "        }\n" +
	            "\n" +
	            "        .form-container {\n" +
	            "            max-width: 400px;\n" +
	            "            margin-bottom: 20px;\n" +
	            "        }\n" +
	            "\n" +
	            "        form {\n" +
	            "            background-color: #fff;\n" +
	            "            padding: 20px;\n" +
	            "            border-radius: 5px;\n" +
	            "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
	            "        }\n" +
	            "\n" +
	            "        label {\n" +
	            "            display: block;\n" +
	            "            margin-bottom: 10px;\n" +
	            "        }\n" +
	            "\n" +
	            "        select {\n" +
	            "            width: 100%;\n" +
	            "            padding: 8px;\n" +
	            "            margin-bottom: 15px;\n" +
	            "            box-sizing: border-box;\n" +
	            "        }\n" +
	            "\n" +
	            "        button {\n" +
	            "            background-color: #333;\n" +
	            "            color: #fff;\n" +
	            "            padding: 10px;\n" +
	            "            border: none;\n" +
	            "            border-radius: 5px;\n" +
	            "            cursor: pointer;\n" +
	            "        }\n" +
	            "\n" +
	            "        button:hover {\n" +
	            "            background-color: #555;\n" +
	            "        }\n" +
	            "\n" +
	            "        .info-table {\n" +
	            "            max-width: 400px;\n" +
	            "            background-color: #fff;\n" +
	            "            padding: 20px;\n" +
	            "            border-radius: 5px;\n" +
	            "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
	            "            margin-bottom: 20px;\n" +
	            "        }\n" +
	            "\n" +
	            "        .info-table table {\n" +
	            "            width: 100%;\n" +
	            "            border-collapse: collapse;\n" +
	            "            margin-top: 10px;\n" +
	            "        }\n" +
	            "\n" +
	            "        .info-table th, .info-table td {\n" +
	            "            border: 1px solid #ddd;\n" +
	            "            padding: 8px;\n" +
	            "            text-align: left;\n" +
	            "        }\n" +
	            "\n" +
	            "        .info-table th {\n" +
	            "            background-color: #333;\n" +
	            "            color: white;\n" +
	            "            width: auto;\n" +
	            "        }\n" +
	            "    </style>\n" +
	            "</head>\n" +
	            "\n" +
	            "<body>\n" +
	            "\n" +
	            "    <nav>\n" +
	            "        <a href=\"index.html\">Volver al menu</a>\n" +
	            "        <a href=\"seleccion"+otroMetodo+"\">Seleccion "+otroMetodo.toUpperCase()+"</a>" +
	            "    </nav>\n" +
	            "\n" +
	            "    <main>\n" +
	            "        <!-- Formulario en un div separado -->\n" +
	            "        <div class=\"form-container\">\n" +
	            "            <h1>Seleccionar ID: "+metodo.toUpperCase()+"</h1>\n" +
	            "            <form action=\"form\" method=\"" + metodo + "\">\n" +
	            "                <label for=\"id\">Selecciona un ID:</label>\n" +
	            "                <select id=\"id\" name=\"id\" required>\n");
	    for (Integer id : listaId) {
	        htmlCode.append("<option value=\"").append(id).append("\">ID").append(id).append("</option>\n");
	    }
	    htmlCode.append("                </select>\n" +
	            "                <button type=\"submit\">Enviar</button>\n" +
	            "            </form>\n" +
	            "        </div>\n" +
	            "\n" +
	            "        <!-- Nuevo div para la tabla -->\n" +
	            "        <div class=\"info-table\">\n" +
	            "            <h2>Información de Partidas</h2>\n" +
	            "            <table>\n" +
	            "                <tr>\n" +
	            "                    <th>ID</th>\n" +
	            "                    <th>Jugador 1</th>\n" +
	            "                    <th>Jugador 2</th>\n" +
	            "                    <th>Ganador</th>\n" +
	            "                    <th>Fecha Creación</th>\n" +
	            "                </tr>\n");

	    for (String datos : listaDatos) {
	        String[] partes = datos.split("@");
	        htmlCode.append("                <tr>\n");
	        for (String parte : partes) {
	            htmlCode.append("                    <td>").append(parte).append("</td>\n");
	        }
	        htmlCode.append("                </tr>\n");
	    }

	    htmlCode.append("            </table>\n" +
	            "        </div>\n" +
	            "    </main>\n" +
	            "\n" +
	            "</body>\n" +
	            "\n" +
	            "</html>\n");
	    return htmlCode.toString();
	}
	
	public static String rellenarSeleccionIdPartidaSegura(ArrayList<Integer> listaId, String metodo,ArrayList<String> listaDatos) {
		String otroMetodo;
		if(metodo.equals("get")) {
			otroMetodo = "post";
		}else{
			otroMetodo = "get";
		}
		StringBuilder htmlCode = new StringBuilder();
	    htmlCode.append("<!DOCTYPE html>\n" +
	            "<html lang=\"es\">\n" +
	            "\n" +
	            "<head>\n" +
	            "    <meta charset=\"UTF-8\">\n" +
	            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
	            "    <title>Mi Página con Formulario</title>\n" +
	            "    <style>\n" +
	            "        body {\n" +
	            "            font-family: Arial, sans-serif;\n" +
	            "            margin: 0;\n" +
	            "            padding: 0;\n" +
	            "            background-color: #f4f4f4;\n" +
	            "            display: flex;\n" +
	            "            flex-direction: column;\n" +
	            "            align-items: center;\n" +
	            "        }\n" +
	            "\n" +
	            "        nav {\n" +
	            "            background-color: #333;\n" +
	            "            color: #fff;\n" +
	            "            padding: 10px;\n" +
	            "            text-align: center;\n" +
	            "            width: 100%;\n" +
	            "        }\n" +
	            "\n" +
	            "        nav a {\n" +
	            "            color: #fff;\n" +
	            "            text-decoration: none;\n" +
	            "            padding: 10px;\n" +
	            "            margin: 0 10px;\n" +
	            "            border-radius: 5px;\n" +
	            "            transition: background-color 0.3s ease;\n" +
	            "        }\n" +
	            "\n" +
	            "        nav a:hover {\n" +
	            "            background-color: #555;\n" +
	            "        }\n" +
	            "\n" +
	            "        main {\n" +
	            "            padding: 20px;\n" +
	            "            display: flex;\n" +
	            "            justify-content: space-evenly;\n" +
	            "            width: 80%;\n" +
	            "            margin: 0 auto;\n" +
	            "        }\n" +
	            "\n" +
	            "        .form-container {\n" +
	            "            max-width: 400px;\n" +
	            "            margin-bottom: 20px;\n" +
	            "        }\n" +
	            "\n" +
	            "        form {\n" +
	            "            background-color: #fff;\n" +
	            "            padding: 20px;\n" +
	            "            border-radius: 5px;\n" +
	            "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
	            "        }\n" +
	            "\n" +
	            "        label {\n" +
	            "            display: block;\n" +
	            "            margin-bottom: 10px;\n" +
	            "        }\n" +
	            "\n" +
	            "        select {\n" +
	            "            width: 100%;\n" +
	            "            padding: 8px;\n" +
	            "            margin-bottom: 15px;\n" +
	            "            box-sizing: border-box;\n" +
	            "        }\n" +
	            "\n" +
	            "        button {\n" +
	            "            background-color: #333;\n" +
	            "            color: #fff;\n" +
	            "            padding: 10px;\n" +
	            "            border: none;\n" +
	            "            border-radius: 5px;\n" +
	            "            cursor: pointer;\n" +
	            "        }\n" +
	            "\n" +
	            "        button:hover {\n" +
	            "            background-color: #555;\n" +
	            "        }\n" +
	            "\n" +
	            "        .info-table {\n" +
	            "            max-width: 400px;\n" +
	            "            background-color: #fff;\n" +
	            "            padding: 20px;\n" +
	            "            border-radius: 5px;\n" +
	            "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
	            "            margin-bottom: 20px;\n" +
	            "        }\n" +
	            "\n" +
	            "        .info-table table {\n" +
	            "            width: 100%;\n" +
	            "            border-collapse: collapse;\n" +
	            "            margin-top: 10px;\n" +
	            "        }\n" +
	            "\n" +
	            "        .info-table th, .info-table td {\n" +
	            "            border: 1px solid #ddd;\n" +
	            "            padding: 8px;\n" +
	            "            text-align: left;\n" +
	            "        }\n" +
	            "\n" +
	            "        .info-table th {\n" +
	            "            background-color: #333;\n" +
	            "            color: white;\n" +
	            "            width: auto;\n" +
	            "        }\n" +
	            "    </style>\n" +
	            "</head>\n" +
	            "\n" +
	            "<body>\n" +
	            "\n" +
	            "    <nav>\n" +
	            "        <a href=\"indexseguro.html\">Volver al menu</a>\n" +
	            "    </nav>\n" +
	            "\n" +
	            "    <main>\n" +
	            "        <!-- Formulario en un div separado -->\n" +
	            "        <div class=\"form-container\">\n" +
	            "            <h1>Seleccionar ID: "+metodo.toUpperCase()+"</h1>\n" +
	            "            <form action=\"form\" method=\"" + metodo + "\">\n" +
	            "                <label for=\"id\">Selecciona un ID:</label>\n" +
	            "                <select id=\"id\" name=\"id\" required>\n");
	    for (Integer id : listaId) {
	        htmlCode.append("<option value=\"").append(id).append("\">ID").append(id).append("</option>\n");
	    }
	    htmlCode.append("                </select>\n" +
	            "                <button type=\"submit\">Enviar</button>\n" +
	            "            </form>\n" +
	            "        </div>\n" +
	            "\n" +
	            "        <!-- Nuevo div para la tabla -->\n" +
	            "        <div class=\"info-table\">\n" +
	            "            <h2>Información de Partidas</h2>\n" +
	            "            <table>\n" +
	            "                <tr>\n" +
	            "                    <th>ID</th>\n" +
	            "                    <th>Jugador 1</th>\n" +
	            "                    <th>Jugador 2</th>\n" +
	            "                    <th>Ganador</th>\n" +
	            "                    <th>Fecha Creación</th>\n" +
	            "                </tr>\n");

	    for (String datos : listaDatos) {
	        String[] partes = datos.split("@");
	        htmlCode.append("                <tr>\n");
	        for (String parte : partes) {
	            htmlCode.append("                    <td>").append(parte).append("</td>\n");
	        }
	        htmlCode.append("                </tr>\n");
	    }

	    htmlCode.append("            </table>\n" +
	            "        </div>\n" +
	            "    </main>\n" +
	            "\n" +
	            "</body>\n" +
	            "\n" +
	            "</html>");
	    return htmlCode.toString();
	}
	
	public static String rellenarSeleccionIdJugador(ArrayList<Integer> listaId,ArrayList<String> listaDatos) {
		StringBuilder htmlCode = new StringBuilder();
	    htmlCode.append("<!DOCTYPE html>\n" +
	            "<html lang=\"es\">\n" +
	            "\n" +
	            "<head>\n" +
	            "    <meta charset=\"UTF-8\">\n" +
	            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
	            "    <title>Mi Página con Formulario</title>\n" +
	            "    <style>\n" +
	            "        body {\n" +
	            "            font-family: Arial, sans-serif;\n" +
	            "            margin: 0;\n" +
	            "            padding: 0;\n" +
	            "            background-color: #f4f4f4;\n" +
	            "            display: flex;\n" +
	            "            flex-direction: column;\n" +
	            "            align-items: center;\n" +
	            "        }\n" +
	            "\n" +
	            "        nav {\n" +
	            "            background-color: #333;\n" +
	            "            color: #fff;\n" +
	            "            padding: 10px;\n" +
	            "            text-align: center;\n" +
	            "            width: 100%;\n" +
	            "        }\n" +
	            "\n" +
	            "        nav a {\n" +
	            "            color: #fff;\n" +
	            "            text-decoration: none;\n" +
	            "            padding: 10px;\n" +
	            "            margin: 0 10px;\n" +
	            "            border-radius: 5px;\n" +
	            "            transition: background-color 0.3s ease;\n" +
	            "        }\n" +
	            "\n" +
	            "        nav a:hover {\n" +
	            "            background-color: #555;\n" +
	            "        }\n" +
	            "\n" +
	            "        main {\n" +
	            "            padding: 20px;\n" +
	            "            display: flex;\n" +
	            "            justify-content: space-evenly;\n" +
	            "            width: 80%;\n" +
	            "            margin: 0 auto;\n" +
	            "        }\n" +
	            "\n" +
	            "        .form-container {\n" +
	            "            max-width: 400px;\n" +
	            "            margin-bottom: 20px;\n" +
	            "        }\n" +
	            "\n" +
	            "        form {\n" +
	            "            background-color: #fff;\n" +
	            "            padding: 20px;\n" +
	            "            border-radius: 5px;\n" +
	            "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
	            "        }\n" +
	            "\n" +
	            "        label {\n" +
	            "            display: block;\n" +
	            "            margin-bottom: 10px;\n" +
	            "        }\n" +
	            "\n" +
	            "        select {\n" +
	            "            width: 100%;\n" +
	            "            padding: 8px;\n" +
	            "            margin-bottom: 15px;\n" +
	            "            box-sizing: border-box;\n" +
	            "        }\n" +
	            "\n" +
	            "        button {\n" +
	            "            background-color: #333;\n" +
	            "            color: #fff;\n" +
	            "            padding: 10px;\n" +
	            "            border: none;\n" +
	            "            border-radius: 5px;\n" +
	            "            cursor: pointer;\n" +
	            "        }\n" +
	            "\n" +
	            "        button:hover {\n" +
	            "            background-color: #555;\n" +
	            "        }\n" +
	            "\n" +
	            "        .info-table {\n" +
	            "            max-width: 400px;\n" +
	            "            background-color: #fff;\n" +
	            "            padding: 20px;\n" +
	            "            border-radius: 5px;\n" +
	            "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
	            "            margin-bottom: 20px;\n" +
	            "        }\n" +
	            "\n" +
	            "        .info-table table {\n" +
	            "            width: 100%;\n" +
	            "            border-collapse: collapse;\n" +
	            "            margin-top: 10px;\n" +
	            "        }\n" +
	            "\n" +
	            "        .info-table th, .info-table td {\n" +
	            "            border: 1px solid #ddd;\n" +
	            "            padding: 8px;\n" +
	            "            text-align: left;\n" +
	            "        }\n" +
	            "\n" +
	            "        .info-table th {\n" +
	            "            background-color: #333;\n" +
	            "            color: white;\n" +
	            "            width: auto;\n" +
	            "        }\n" +
	            "    </style>\n" +
	            "</head>\n" +
	            "\n" +
	            "<body>\n" +
	            "\n" +
	            "    <nav>\n" +
	            "        <a href=\"index.html\">Volver al menu</a>\n" +
	            "    </nav>\n" +
	            "\n" +
	            "    <main>\n" +
	            "        <!-- Formulario en un div separado -->\n" +
	            "        <div class=\"form-container\">\n" +
	            "            <h1>Seleccionar ID Usuario: </h1>\n" +
	            "            <form action=\"infojugador\" method=\"get\"\n" +
	            "                <label for=\"id\">Selecciona un ID:</label>\n" +
	            "                <select id=\"id\" name=\"id\" required>\n");
	    for (Integer id : listaId) {
	        htmlCode.append("<option value=\"").append(id).append("\">ID").append(id).append("</option>\n");
	    }
	    htmlCode.append("                </select>\n" +
	            "                <button type=\"submit\">Enviar</button>\n" +
	            "            </form>\n" +
	            "        </div>\n" +
	            "\n" +
	            "        <!-- Nuevo div para la tabla -->\n" +
	            "        <div class=\"info-table\">\n" +
	            "            <h2>Información de Partidas</h2>\n" +
	            "            <table>\n" +
	            "                <tr>\n" +
	            "                    <th>ID</th>\n" +
	            "                    <th>Nombre Usuario</th>\n" +
	            "                </tr>\n");

	    for (String datos : listaDatos) {
	        String[] partes = datos.split("@");
	        htmlCode.append("                <tr>\n");
	        for (String parte : partes) {
	            htmlCode.append("                    <td>").append(parte).append("</td>\n");
	        }
	        htmlCode.append("                </tr>\n");
	    }

	    htmlCode.append("            </table>\n" +
	            "        </div>\n" +
	            "    </main>\n" +
	            "\n" +
	            "</body>\n" +
	            "\n" +
	            "</html>");
	    return htmlCode.toString();
	}
	
	public static String rellenarPartidaSegura(String jugador1, String jugador2, int id1, int id2, int idPartida,
			String ganador, ArrayList<Barco> arrayBarcos1, ArrayList<Barco> arrayBarcos2, ArrayList<Integer> disparos1,
			ArrayList<Integer> disparos2) {
		String htmlCode = "<!DOCTYPE html>\n" + "<html lang=\"es\">\n" + "\n" + "<head>\n"
				+ "    <meta charset=\"UTF-8\">\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
				+ "    <title>Tableros de Colores</title>\n" + "    <style>\n" + "        body {\n"
				+ "            font-family: Arial, sans-serif;\n" + "            margin: 0;\n"
				+ "            padding: 0;\n" + "            background-color: #f4f4f4;\n"
				+ "            display: flex;\n" + "            flex-direction: column;\n"
				+ "            align-items: center;\n" + "        }\n" + "\n" + "        nav {\n"
				+ "            background-color: #333;\n" + "            color: #fff;\n"
				+ "            padding: 10px;\n" + "            text-align: center;\n" + "            width: 100%;\n"
				+ "        }\n" + "\n" + "        nav a {\n" + "            color: #fff;\n"
				+ "            text-decoration: none;\n" + "            padding: 10px;\n"
				+ "            margin: 0 10px;\n" + "            border-radius: 5px;\n"
				+ "            transition: background-color 0.3s ease;\n" + "        }\n" + "\n"
				+ "        nav a:hover {\n" + "            background-color: #555;\n" + "        }\n" + "\n"
				+ "        header {\n" +
//                "            background-color: #e6f7ff;\n" +
				"            padding: 20px;\n" + "            text-align: center;\n" + "        }\n" + "\n"
				+ "        .boards-container {\n" + "            display: flex;\n"
				+ "            justify-content: space-around;\n" + "            width: 100%;\n"
				+ "            margin-top: 20px;\n" + "        }\n" + "\n" + "        .board {\n"
				+ "            display: grid;\n" + "            grid-template-columns: repeat(10, 30px);\n"
				+ "            grid-template-rows: repeat(10, 30px);\n" + "            gap: 2px;\n" + "        }\n"
				+ "\n" + "        .cell {\n" + "            width: 30px;\n" + "            height: 30px;\n"
				+ "            border: 1px solid #ccc;\n" + "        }\n" + "\n"
				+ "        /* Definición de colores para cada celda */\n" + "        .colortocado {\n"
				+ "            background-color: #ffA500;\n" + "        }\n" + "\n" + "        .colorfallido {\n"
				+ "            background-color: #000000;\n" + "        }\n" + "\n" + "        .coloragua {\n"
				+ "            background-color: #0000ff;\n" + "        }\n" + "\n" + "        .colorbarco {\n"
				+ "            background-color: #00ff00;\n" + "        }\n"
				+ "        /* ... Puedes agregar más colores según sea necesario */\n" + "    </style>\n" + "</head>\n"
				+ "\n" + "<body>\n" + "    <nav>\n" + "        <a href=\"indexseguro.html\">Inicio</a> </nav>\n" + 
				"\n" + "    <header>\n"
				+ "        <h1>Partida ID=" + idPartida + "		Ganador=" + ganador + "</h1>\n" + "    </header>\n"
				+ "\n" + "    <div class=\"boards-container\">\n" + "        <div class=\"board-container\">\n"
				+ "            <h2>Tablero " + jugador1 + "</h2>\n"
				+ "            <div id=\"board1\" class=\"board\">\n" + "                <!-- Primer tablero -->\n";

		for (int row = 1; row <= 10; row++) {
			for (int col = 1; col <= 10; col++) {
				int posicion = (row - 1) * 10 + (col - 1);
				String color = comprobarPosicion(posicion, disparos1, arrayBarcos1);
//                htmlCode += "                <div class=\"cell color" + ((row - 1) * 10 + col) % 4 + "\"></div>\n";
				htmlCode += "                <div class=\"cell color" + color + "\"></div>\n";
			}
		}

		htmlCode += "            </div>\n" + "        </div>\n" + "\n" + "        <div class=\"board-container\">\n"
				+ "            <h2>Tablero " + jugador2 + "</h2>\n"
				+ "            <div id=\"board2\" class=\"board\">\n" + "                <!-- Segundo tablero -->\n";

		for (int row = 1; row <= 10; row++) {
			for (int col = 1; col <= 10; col++) {
				int posicion = (row - 1) * 10 + (col - 1);
				String color = comprobarPosicion(posicion, disparos2, arrayBarcos2);
//                htmlCode += "                <div class=\"cell color" + ((row - 1) * 10 + col) % 4 + "\"></div>\n";
				htmlCode += "                <div class=\"cell color" + color + "\"></div>\n";
			}
		}

		htmlCode += "            </div>\n" + "        </div>\n" + "    </div>\n" + "\n" + "</body>\n" + "\n"
				+ "</html>";

		return htmlCode;
	}

	public static String rellenarPartida(String jugador1, String jugador2, int id1, int id2, int idPartida,
			String ganador, ArrayList<Barco> arrayBarcos1, ArrayList<Barco> arrayBarcos2, ArrayList<Integer> disparos1,
			ArrayList<Integer> disparos2) {
		String htmlCode = "<!DOCTYPE html>\n" + "<html lang=\"es\">\n" + "\n" + "<head>\n"
				+ "    <meta charset=\"UTF-8\">\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
				+ "    <title>Tableros de Colores</title>\n" + "    <style>\n" + "        body {\n"
				+ "            font-family: Arial, sans-serif;\n" + "            margin: 0;\n"
				+ "            padding: 0;\n" + "            background-color: #f4f4f4;\n"
				+ "            display: flex;\n" + "            flex-direction: column;\n"
				+ "            align-items: center;\n" + "        }\n" + "\n" + "        nav {\n"
				+ "            background-color: #333;\n" + "            color: #fff;\n"
				+ "            padding: 10px;\n" + "            text-align: center;\n" + "            width: 100%;\n"
				+ "        }\n" + "\n" + "        nav a {\n" + "            color: #fff;\n"
				+ "            text-decoration: none;\n" + "            padding: 10px;\n"
				+ "            margin: 0 10px;\n" + "            border-radius: 5px;\n"
				+ "            transition: background-color 0.3s ease;\n" + "        }\n" + "\n"
				+ "        nav a:hover {\n" + "            background-color: #555;\n" + "        }\n" + "\n"
				+ "        header {\n" + "            padding: 20px;\n" + "            text-align: center;\n"
				+ "        }\n" + "\n" + "        .boards-container {\n" + "            display: flex;\n"
				+ "            justify-content: space-around;\n" + "            width: 100%;\n"
				+ "            margin-top: 20px;\n" + "        }\n" + "\n" + "        .board-container {\n"
				+ "            text-align: center;\n" + "        }\n" + "\n" + "        .board {\n"
				+ "            display: grid;\n" + "            grid-template-columns: repeat(10, 30px);\n"
				+ "            grid-template-rows: repeat(10, 30px);\n" + "            gap: 2px;\n" + "        }\n"
				+ "\n" + "        .cell {\n" + "            width: 30px;\n" + "            height: 30px;\n"
				+ "            border: 1px solid #ccc;\n" + "        }\n" + "\n"
				+ "        /* Definición de colores para cada celda */\n" + "        .colortocado {\n"
				+ "            background-color: #ffA500;\n" + "        }\n" + "\n" + "        .colorfallido {\n"
				+ "            background-color: #000000;\n" + "        }\n" + "\n" + "        .coloragua {\n"
				+ "            background-color: #0000ff;\n" + "        }\n" + "\n" + "        .colorbarco {\n"
				+ "            background-color: #00ff00;\n" + "        }\n"
				+ "        /* ... Puedes agregar más colores según sea necesario */\n" + "\n"
				+ "        /* Representación de colores */\n" + "        .color-representation {\n"
				+ "            display: flex;\n" + "            justify-content: space-around;\n"
				+ "            margin-top: 20px;\n" + "        }\n" + "\n" + "        .color-sample {\n"
				+ "            width: 50px;\n" + "            height: 30px;\n" + "            text-align: center;\n"
				+ "            line-height: 30px;\n" + "            border: 1px solid #ccc;\n"
				+ "            border-radius: 5px;\n" + "            margin: 0 5px;\n" + "        }\n"
				+ "        /* ... Puedes agregar más estilos según sea necesario */\n" + "    </style>\n" + "</head>\n"
				+ "\n" + "<body>\n" + "    <nav>\n" + "        <a href=\"index.html\">Inicio</a>\n"
				+ "    </nav>\n" + "\n" + "    <header>\n"
				+ "        <h1>Partida ID=" + idPartida + "</h1>\n<h2>Ganador=" + ganador + "</h2>\n" + "    </header>\n"
				+ "\n" + "    <div class=\"boards-container\">\n" + "        <div class=\"board-container\">\n"
				+ "            <h2>Tablero " + jugador1 + "</h2>\n"
				+ "            <div id=\"board1\" class=\"board\">\n" + "                <!-- Primer tablero -->\n";

		for (int row = 1; row <= 10; row++) {
			for (int col = 1; col <= 10; col++) {
				int posicion = (row - 1) * 10 + (col - 1);
				String color = comprobarPosicion(posicion, disparos1, arrayBarcos1);
				htmlCode += "                <div class=\"cell color" + color + "\"></div>\n";
			}
		}

		htmlCode += "            </div>\n" + "        </div>\n" + "\n" + "        <div class=\"board-container\">\n"
				+ "            <h2>Tablero " + jugador2 + "</h2>\n"
				+ "            <div id=\"board2\" class=\"board\">\n" + "                <!-- Segundo tablero -->\n";

		for (int row = 1; row <= 10; row++) {
			for (int col = 1; col <= 10; col++) {
				int posicion = (row - 1) * 10 + (col - 1);
				String color = comprobarPosicion(posicion, disparos2, arrayBarcos2);
				htmlCode += "                <div class=\"cell color" + color + "\"></div>\n";
			}
		}

		htmlCode += "            </div>\n" + "        </div>\n" + "    </div>\n" + "\n"
				+ "    <!-- Representación de colores -->\n" + "    <div class=\"color-representation\">\n"
				+ "        <div class=\"color-sample\" style=\"background-color: #99ccff; color: #fff;\">Agua</div>\n"
				+ "        <div class=\"color-sample\" style=\"background-color: #66cc66; color: #fff;\">Barco</div>\n"
				+ "        <div class=\"color-sample\" style=\"background-color: #ffcc99; color: #fff;\">Tocado</div>\n"
				+ "        <div class=\"color-sample\" style=\"background-color: #000000; color: #fff;\">Fallido</div>\n"
				+ "        <!-- Añade más colores según sea necesario -->\n" + "    </div>\n" + "\n" + "</body>\n"
				+ "\n" + "</html>\n";

		return htmlCode;
	}

	public static String comprobarPosicion(int posicion, ArrayList<Integer> disparos, ArrayList<Barco> arrayBarcos) {
		String color;
		boolean tocado = false;
		boolean disparo = false;
		boolean barco = false;
		if (disparos.contains(posicion)) {
			disparo = true;
			tocado = comprobarPosicionConBarco(posicion, arrayBarcos);
		}
		if (disparo && tocado) {
			color = "tocado";
		} else if (disparo) {
			color = "fallido";
		} else if ((barco = comprobarPosicionConBarco(posicion, arrayBarcos)) == true) {
			color = "barco";
		} else {
			color = "agua";
		}
		return color;
	}

	public static boolean comprobarPosicionConBarco(int posicion, ArrayList<Barco> arrayBarcos) {
		for (Barco barco : arrayBarcos) {
			for (Boton b : barco.getBotonesBarco()) {
				if (b.getPosicionTablero() == posicion) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static String rellenarPaginaInfoJugador(ArrayList<String> contenido) {
		String htmlCode = "<!DOCTYPE html>\n" +
		        "<html lang=\"en\">\n" +
		        "<head>\n" +
		        "    <meta charset=\"UTF-8\">\n" +
		        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
		        "    <title>Info Jugador</title>\n" +
		        "    <style>\n" +
		        "        body {\n" +
		        "            font-family: Arial, sans-serif;\n" +
		        "            margin: 0;\n" +
		        "            padding: 0;\n" +
		        "            background-color: #f4f4f4;\n" +
		        "            display: flex;\n" +
		        "            flex-direction: column;\n" +
		        "            align-items: center;\n" +
		        "        }\n" +
		        "\n" +
		        "        p {\n" +
		        "            background-color: #fff;\n" +
		        "            padding: 10px;\n" +
		        "            border-radius: 5px;\n" +
		        "            margin: 10px;\n" +
		        "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
		        "        }\n" +
		        "\n" +
		        "        nav {\n" +
		        "            background-color: #333;\n" +
		        "            color: #fff;\n" +
		        "            padding: 10px;\n" +
		        "            text-align: center;\n" +
		        "            width: 100%;\n" +
		        "        }\n" +
		        "\n" +
		        "        nav a {\n" +
		        "            color: #fff;\n" +
		        "            text-decoration: none;\n" +
		        "            padding: 10px;\n" +
		        "            margin: 0 10px;\n" +
		        "            border-radius: 5px;\n" +
		        "            transition: background-color 0.3s ease;\n" +
		        "        }\n" +
		        "\n" +
		        "        nav a:hover {\n" +
		        "            background-color: #555;\n" +
		        "        }\n" +
		        "    </style>" +
		        "</head>\n" +
		        "<body>\n" +
		        "    <nav>\n" +
		        "        <a href=\"index.html\">Inicio</a>\n" +
		        "        <a href=\"seleccionjugador\">Volver Seleccion</a>\n" +
		        "    </nav>\n" +
		        "    <main>\n";
		for (String s : contenido) {
		    htmlCode += "<p>" + s + "</p>\n";
		}
		htmlCode += "    </main>\n" +
		        "</body>\n" +
		        "</html>\n";
		return htmlCode;
	}
}
