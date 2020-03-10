
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Insert")
public class Insert extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Insert() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String weaponType = request.getParameter("weaponType");
		String weaponName = request.getParameter("weaponName");
		String rawDamage = request.getParameter("rawDamage");
		String specialDamageType = request.getParameter("specialDamageType");
		String specialDamageValue = request.getParameter("specialDamageValue");
		String affinity = request.getParameter("affinity");

		Connection connection = null;
		String insertSql = " INSERT INTO WeaponTable (id, TYPE, NAME, RAW, SPECTYPE, SPECDMG, AFF) values (default, ?, ?, ?, ?, ?, ?)";

		try {
			DBConnection.getDBConnection();
			connection = DBConnection.connection;
			PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
			preparedStmt.setString(1, weaponType);
			preparedStmt.setString(2, weaponName);
			preparedStmt.setString(3, rawDamage);
			preparedStmt.setString(4, specialDamageType);
			preparedStmt.setString(5, specialDamageValue);
			preparedStmt.setString(6, affinity);
			preparedStmt.execute();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Set response content type
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String title = "Weapon Inserted!";
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
		out.println(docType + //
				"<html>\n" + //
				"<head><title>" + title + "</title></head>\n" + //
				"<body bgcolor=\"#f0f0f0\">\n" + //
				"<h2 align=\"center\">" + title + "</h2>\n" + //
				"<ul>\n" + //

				"  <li><b>Weapon Type</b>: " + weaponType + "\n" + //
				"  <li><b>Weapon Name</b>: " + weaponName + "\n" + //
				"  <li><b>Raw Damage</b>: " + rawDamage + "\n" + //
				"  <li><b>Special Damage Type</b>: " + specialDamageType + "\n" + //
				"  <li><b>Special Damage Value</b>: " + specialDamageValue + "\n" + //
				"  <li><b>Affinity</b>: " + affinity + "%\n" + //


				"</ul>\n");

		out.println("<a href=/webprojectCSCI4830-techExcercise-Sortino/search.html>Search Data</a> <br>");
		out.println("</body></html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
