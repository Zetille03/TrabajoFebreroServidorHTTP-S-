package secureserverhttp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import info.Barco;
import recursoscomunes.Paginas;

public class ConexionClienteSegura extends Thread {
	private Socket socketCliente;
	private String nameDatabase = "hundirflota";
	private String user = "root";
	private String pass = "MANAGER"; 
	private static ArrayList<Integer> listaNegra = new ArrayList<Integer>();
	
	static {
        // Agregar algunos IDs al ArrayList
		listaNegra.add(1);
		listaNegra.add(2);
    }
	public ConexionClienteSegura(Socket socketCliente) {
		this.socketCliente = socketCliente;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try(BufferedReader lector = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
                BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream()))) {
			while (true) {
                // Lee la solicitud del cliente
                String primeraLinea = lector.readLine();
                while (primeraLinea == null) {
                    primeraLinea = lector.readLine();
                }
                System.out.println("Primera línea de la solicitud recibida: " + primeraLinea);

                // Procesa el resto del cuerpo de la solicitud, si lo hay
               

                // Extrae la ruta solicitada
                String ruta = obtenerRuta(primeraLinea);
                System.out.println("Ruta solicitada: " + ruta);

                if (primeraLinea.startsWith("GET")) {
                    System.out.println("Obtenido con GET");
                    procesarCuerpoSolicitud(lector);
                    enviarArchivo(escritor, ruta);
                } else if (primeraLinea.startsWith("POST")) {
                    System.out.println("Obtenido con POST");
                    procesarSolicitudPost(lector, escritor, ruta);
                }
            }
		}catch(IOException e) {
			
		}
	}
	
	private void procesarSolicitudPost(BufferedReader lector, BufferedWriter escritor, String ruta) throws IOException {
        // Procesa el cuerpo de la solicitud POST
        StringBuilder cuerpo = new StringBuilder();
        int contentLength = 0;

        // Lee la cabecera para obtener la longitud del contenido
        String linea;
        while ((linea = lector.readLine()) != null && !linea.isEmpty()) {
//            System.out.println("Cabecera de la solicitud POST: " + linea);

            if (linea.startsWith("Content-Length:")) {
                contentLength = Integer.parseInt(linea.substring("Content-Length:".length()).trim());
            }
        }
        // Lee el cuerpo de la solicitud en bloques
        char[] buffer = new char[contentLength];
        int bytesRead = lector.read(buffer, 0,contentLength);
        cuerpo.append(buffer, 0, bytesRead);

        // Aquí puedes procesar el cuerpo de la solicitud POST como sea necesario
        System.out.println("Cuerpo de la solicitud POST: " + cuerpo.toString());

        // Envía la respuesta HTML generada a partir del cuerpo de la solicitud POST
        
        StringBuilder respuesta = new StringBuilder();
        if(ruta.equals("form")) {
        	respuesta.append(rellenarPartidaSegura(cuerpo.toString()));
        }if(ruta.equals("registro")) {
        	ArrayList<String> datosRegistro = separarDatosRegistroPOST(cuerpo.toString()); 
    		int idUsuario = comprobarDatosRegistro(datosRegistro,respuesta);
    		if(idUsuario!=0) {
    			if(listaNegra.contains(idUsuario)) {
    				try (BufferedReader lectorArchivo = new BufferedReader(new FileReader("errorPermisos.html"))) {
    		            while ((linea = lectorArchivo.readLine()) != null) {
    		                respuesta.append(linea).append("\n");
    		            }
    		        } catch (FileNotFoundException e) {
    		        	e.printStackTrace();
    		        }
    			}else {
    				ArrayList<Integer> arrayIdPartidas = devolverIdsPartidasUsuarioConcreto(idUsuario);
            		respuesta.append(Paginas.rellenarSeleccionIdPartidaSegura(arrayIdPartidas, "post", registroPartidasBBDDUsuarioConcreto(idUsuario)));
    			}
    		}else {
    			cargarArchivo("errorRegistro.html", respuesta);
    		}
        }
        else {
        	respuesta.append(generarRespuestaPost(cuerpo.toString()));
        }
        escritor.write("HTTPS/1.1 200 OK\r\n");
        escritor.write("Content-Type: text/html\r\n");
        escritor.write("\r\n");
        escritor.write(respuesta.toString());
        escritor.flush();
    }

    private String generarRespuestaPost(String cuerpo) {
    	System.out.println("Genera el cuerpo");
        return "<html><body><h1>"+cuerpo+"</h1></body></html>";
    }

    private String obtenerRuta(String solicitud) {
        // Extrae la ruta solicitada desde la solicitud HTTP GET
        String[] partes = solicitud.split(" ");
        if(partes.length>1) {
        	if(partes[1].length()>1) {
        		partes[1] = partes[1].substring(1);
        	}
        	return partes[1];
        }else {
        	return "/";
        }
    }
    
    private boolean isPHP(String ruta) {
    	return ruta.toLowerCase().endsWith(".php");
    }
    
    private void procesarCuerpoSolicitud(BufferedReader lector) throws IOException {
        String linea;
        while ((linea = lector.readLine()) != null && !linea.isEmpty()) {
//            System.out.println("Cuerpo de la solicitud: " + linea);
        }
    }

    private void enviarArchivo(BufferedWriter escritor, String ruta) throws IOException {
        // Construye la ruta completa al archivo solicitado
        StringBuilder contenido = new StringBuilder();
        if(ruta.equals("/")) {
        	ruta="indexseguro.html";
        }
        System.out.println(ruta);
        try (BufferedReader lectorArchivo = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = lectorArchivo.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        } catch (FileNotFoundException e) {
                contenido.append("Error: Archivo no encontrado - ").append(ruta);
                escritor.write("HTTPS/1.1 404 Not Found\r\n");
                escritor.write("Content-Type: text/plain\r\n");
                escritor.write("\r\n");
                escritor.write("Archivo no encontrado: " + ruta);
                escritor.flush();
                return;
        }

        escritor.write("HTTPS/1.1 200 OK\r\n");
        escritor.write("Content-Type: text/html\r\n");
        escritor.write("\r\n");
    	escritor.write(contenido.toString());
        escritor.flush();
    }
    
    public static void cargarArchivo(String ruta, StringBuilder contenido) {
    	try (BufferedReader lectorArchivo = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = lectorArchivo.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
    public static ArrayList<String> separarDatosRegistroPOST(String datosRegistro){
        String[] partes = datosRegistro.split("&");
        ArrayList<String> datos = new ArrayList<String>();
        
        for(String par: partes) {
        	
        	String[] pares = par.split("=");
        	System.out.println(pares[0] + ""+ pares[1]);
        	datos.add(pares[1]);
        }
        return datos;
    }
    
    
    public int comprobarDatosRegistro(ArrayList<String> datos, StringBuilder contenido) {
        Connection conexion = null;
        try {
            Usuario u = new Usuario(datos.get(0), datos.get(1));
            String hashCode = String.valueOf(u.hashCode());
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nameDatabase, user, pass);

            String consultaSQL = "SELECT * FROM hundirflota.usuarios WHERE nombre_usuario = ? AND contra_usuario = ?";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(consultaSQL)) {
                preparedStatement.setString(1, u.getNombre());
                preparedStatement.setString(2, hashCode);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return resultSet.getInt("id_usuario");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
        
    public ArrayList<Integer> devolverIdsPartidasUsuarioConcreto(int idUsuario) {
    	Connection conexion = null;
    	ArrayList<Integer> ids = new ArrayList<Integer>();
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nameDatabase, user, pass);
			Statement statement = conexion.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT id_partida FROM partidas WHERE terminada=true AND (id_jugador1 = "+idUsuario+" OR id_jugador2 = "+idUsuario+");");
			while (resultSet.next()) {
				ids.add(resultSet.getInt("id_partida"));
			}
			return ids;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
    
    public ArrayList<String> registrosUsuariosBBDD(){
    	Connection conexion = null;
    	ArrayList<String> arrayUsuarios = new ArrayList<String>();
    	try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nameDatabase, user, pass);
			Statement statement = conexion.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT id_usuario,nombre_usuario FROM usuarios;");
			while (resultSet.next()) {
				String linea = resultSet.getInt("id_usuario") + "@" + resultSet.getString("nombre_usuario");
				arrayUsuarios.add(linea);
			}
			return arrayUsuarios; 
    	}catch (Exception e) {
    		
			e.printStackTrace();
		}
    	return null;
    }
    
    /*
     * Devuelve 1: String disparos,2: String colocacion1,3: String colocacion2,
     * 4: String nombre1, 5: String nombre 2, 6: String id1, 7: String id2,
     * 8: String idPartida, 9: String nombreGanador 
     */
    public String seleccionarPartidaTerminada(int eleccion) {
		String datosPartida = "";
		int id_jugador1 = 0;
		int id_jugador2=0;
		int id_ganador = 0;
		String ganador = "";
		Connection conexion = null;
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nameDatabase, user, pass);
			Statement statement = conexion.createStatement();
			ResultSet resultSet =  statement.executeQuery("SELECT id_jugador1,id_jugador2,id_ganador,disparos FROM partidas WHERE id_partida = "+eleccion+";");
			if(resultSet.next()) {
				id_jugador1 = resultSet.getInt("id_jugador1");
				id_jugador2 = resultSet.getInt("id_jugador2");
				id_ganador = resultSet.getInt("id_ganador");
				datosPartida+=resultSet.getString("disparos");
			}
			resultSet = statement.executeQuery("SELECT colocacion_barcos FROM barcos WHERE id_partida = "+eleccion+" AND id_jugador = "+id_jugador1+";");
			if(resultSet.next()) {
				datosPartida+="@"+resultSet.getString("colocacion_barcos");
			}
			resultSet = statement.executeQuery("SELECT colocacion_barcos FROM barcos WHERE id_partida = "+eleccion+" AND id_jugador = "+id_jugador2+";");
			if(resultSet.next()) {
				datosPartida+="@"+resultSet.getString("colocacion_barcos");
			}
			resultSet = statement.executeQuery("SELECT nombre_usuario FROM usuarios WHERE id_usuario = "+id_jugador1+";");
			if(resultSet.next()) {
				datosPartida+="@"+resultSet.getString("nombre_usuario");
			}
			resultSet = statement.executeQuery("SELECT nombre_usuario FROM usuarios WHERE id_usuario = "+id_jugador2+";");
			if(resultSet.next()) {
				datosPartida+="@"+resultSet.getString("nombre_usuario");
			}
			resultSet = statement.executeQuery("SELECT nombre_usuario FROM usuarios WHERE id_usuario = "+id_ganador+";");
			if(resultSet.next()) {
				 ganador = resultSet.getString("nombre_usuario");
			}
			datosPartida+="@"+id_jugador1+"@"+id_jugador2+"@"+eleccion+"@"+ganador;
			return datosPartida;
		} catch (Exception e) {
			if (e instanceof SQLException) {
				System.out.println("error seleccion de partida terminada");
				e.printStackTrace();
			}
		}
		return datosPartida;
	}
    
    public String rellenarPartidaSegura(String cuerpo) {
    	int indexEquals = cuerpo.indexOf("=");
    	int idValue = Integer.parseInt(cuerpo.substring(indexEquals + 1));
    	String[] mensajeArray = seleccionarPartidaTerminada(idValue).split("@");
    	ArrayList<Barco> arrayBarcos1 = Barco.pasarStringABarcos(mensajeArray[1]);
    	ArrayList<Barco> arrayBarcos2 = Barco.pasarStringABarcos(mensajeArray[2]);
    	ArrayList<Integer> disparos1 = sacarDisparos(mensajeArray[0], mensajeArray[6]);
    	ArrayList<Integer> disparos2 = sacarDisparos(mensajeArray[0], mensajeArray[5]);
    	String html = Paginas.rellenarPartidaSegura(mensajeArray[3],mensajeArray[4],Integer.parseInt(mensajeArray[5]),
    			Integer.parseInt(mensajeArray[6]),Integer.parseInt(mensajeArray[7]),mensajeArray[8],
    			arrayBarcos1,arrayBarcos2,disparos1,disparos2);
    	return html;
    }
    
	
    /**
     * Método que saca los disparos que recibe un tablero
     * 
     * @param disparos
     * @param idDisparador el que le dispara
     * @return
     */
    public ArrayList<Integer> sacarDisparos(String disparos, String idDisparador) {
    	ArrayList<Integer> arrayDisparos = new ArrayList<Integer>();
    	String[] disparosStringArray = disparos.split(";");
    	for(String s: disparosStringArray) {
    		String[] split = s.split(",");
    		if(split[0].equals(idDisparador)) {
    			arrayDisparos.add(Integer.parseInt(split[1]));
    		}
    	}
    	return arrayDisparos;
    }
    
    public ArrayList<String> registroPartidasBBDDUsuarioConcreto(int idUsuario) {
        ArrayList<String> strings = new ArrayList<>();

        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nameDatabase, user, pass);
             Statement statement = conexion.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id_partida,id_jugador1,id_jugador2,id_ganador,fecha_creacion FROM partidas WHERE terminada = true "
             		+ "AND (id_jugador1 = "+idUsuario+" OR id_jugador2 = "+idUsuario+");")) {

            while (resultSet.next()) {
                String datos = "";
                int idPartida = resultSet.getInt("id_partida");
                int idJugador1 = resultSet.getInt("id_jugador1");
                int idJugador2 = resultSet.getInt("id_jugador2");
                int ganador = resultSet.getInt("id_ganador");
                String fecha = resultSet.getDate("fecha_creacion").toString();
                datos += idPartida;

                try (Statement innerStatement = conexion.createStatement();
                     ResultSet resultSet1 = innerStatement.executeQuery("SELECT nombre_usuario FROM usuarios WHERE id_usuario = " + idJugador1 + ";")) {
                    if (resultSet1.next()) {
                        datos += "@" + resultSet1.getString("nombre_usuario");
                    }
                }

                try (Statement innerStatement = conexion.createStatement();
                     ResultSet resultSet2 = innerStatement.executeQuery("SELECT nombre_usuario FROM usuarios WHERE id_usuario = " + idJugador2 + ";")) {
                    if (resultSet2.next()) {
                        datos += "@" + resultSet2.getString("nombre_usuario");
                    }
                }

                try (Statement innerStatement = conexion.createStatement();
                     ResultSet resultSet3 = innerStatement.executeQuery("SELECT nombre_usuario FROM usuarios WHERE id_usuario = " + ganador + ";")) {
                    if (resultSet3.next()) {
                        datos += "@" + resultSet3.getString("nombre_usuario");
                    }
                }

                datos += "@" + fecha;
                strings.add(datos);
            }
        } catch (Exception e) {
            if (e instanceof SQLException) {
                System.out.println("Error en la selección de partidas terminada");
                e.printStackTrace();
            }
        }

        return strings;
    }
    
}

