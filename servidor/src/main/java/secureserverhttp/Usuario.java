package secureserverhttp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class Usuario {
	private String nombre;
	private String contra;
	private static String nameDatabase = "hundirflota";
	private static String user = "root";
	private static String pass = "MANAGER"; 
	
	public Usuario(String nombre, String contra) {
		super();
		this.nombre = nombre;
		this.contra = contra;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getContra() {
		return contra;
	}
	public void setContra(String contra) {
		this.contra = contra;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contra, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(contra, other.contra) && Objects.equals(nombre, other.nombre);
	}
	
}
