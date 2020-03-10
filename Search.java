import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Search() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		search(keyword, response);
	}

	void search(String keyword, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String title = "Weapon Table Result";
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
				"transitional//en\">\n"; //
		out.println(docType + //
				"<html>\n" + //
				"<head><title>" + title + "</title></head>\n" + //
				"<body bgcolor=\"#f0f0f0\">\n" + //
				"<h1 align=\"center\">" + title + "</h1>\n");

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			DBConnection.getDBConnection();
			connection = DBConnection.connection;

			if (keyword.isEmpty()) {
				String selectSQL = "SELECT * FROM WeaponTable";
				preparedStatement = connection.prepareStatement(selectSQL);
			} else {
				String selectSQL = "SELECT * FROM WeaponTable WHERE TYPE LIKE ?";
				String theUserName = keyword + "%";
				preparedStatement = connection.prepareStatement(selectSQL);
				preparedStatement.setString(1, theUserName);
			}
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String weaponType = rs.getString("type").trim();
				String weaponName = rs.getString("name").trim();
				String rawDamage = rs.getString("raw").trim();
				String specialDamageType = rs.getString("spectype").trim();
				String specialDamageValue = rs.getString("specdmg").trim();
				String affinity = rs.getString("aff").trim();

				if (keyword.isEmpty() || weaponType.contains(keyword)) {
					out.println("Weapon Type: " + weaponType + "<br>");
					out.println("Weapon Name: " + weaponName + "<br>");
					out.println("Raw Damage: " + rawDamage + "<br>");
					out.println("Special Damage Type: " + specialDamageType + "<br>");
					out.println("Special Damage Value: " + specialDamageValue + "<br>");
					out.println("Affinity: " + affinity + "%<br><br>");
				}
			}
			out.println("<a href=/webprojectCSCI4830-techExcercise-Sortino/search.html>Search Data</a> <br>");
			out.println("</body></html>");
			rs.close();
			preparedStatement.close();
			connection.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException se2) {
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
