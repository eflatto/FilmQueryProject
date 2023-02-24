package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
	private static final String user = "student";
	private static final String pass = "student";

	public DatabaseAccessorObject() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}

	@Override
	public Film findFilmById(int filmId) throws SQLException {
		Film film = null;

		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT id, title ,description ,release_year , language_id , rental_duration, rental_rate,length ,";
				sql+= " replacement_cost, rating,special_features  FROM film WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet filmResult = stmt.executeQuery();
		if (filmResult.next()) {
			film = new Film(); // Create the object
			// Here is our mapping of query columns to our object fields:
			film.setId(filmResult.getInt("id"));
			film.setTitle(filmResult.getString("title"));
			film.setDescription(filmResult.getString("description"));
			film.setReleaseYear(filmResult.getInt("release_year"));
			film.setLanguageId(filmResult.getInt("language_id"));
			film.setRentalDuration(filmResult.getInt("rental_duration"));
			film.setRentalRate(filmResult.getDouble("rental_rate"));
			film.setLength(filmResult.getInt("length"));
			film.setReplacementCost(filmResult.getDouble("replacement_cost"));
			film.setRating(filmResult.getString("rating"));
			film.setSpecialFeature(filmResult.getString("special_features"));
			

		}
		return film;
	}
	@Override
	public Actor findActorById(int actorId) throws SQLException {
		Actor actor = null;

		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, actorId);
		ResultSet actorResult = stmt.executeQuery();
		if (actorResult.next()) {
			actor = new Actor(); // Create the object
			// Here is our mapping of query columns to our object fields:
			actor.setId(actorResult.getInt("id"));
			actor.setFirstName(actorResult.getString("first_name"));
			actor.setLastName(actorResult.getString("last_name"));
		} 
		if(actor.equals(null)) {
			return null;
		}
		// ...
		return actor;
	}


	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		// TODO Auto-generated method stub
		return null;
	}		public List<Film> findFilmsByActorId(int actorId) {
		  List<Film> films = new ArrayList<>();
		  try {
		    Connection conn = DriverManager.getConnection(URL, user, pass);
		    String sql = "SELECT id, title, description, release_year, language_id, rental_duration, ";
		                sql += " rental_rate, length, replacement_cost, rating, special_features "
		               +  " FROM film JOIN film_actor ON film.id = film_actor.film_id "
		               + " WHERE actor_id = ?";
		    PreparedStatement stmt = conn.prepareStatement(sql);
		    stmt.setInt(1, actorId);
		    ResultSet rs = stmt.executeQuery();
		    while (rs.next()) {
		      Integer filmId = rs.getInt("id");
		      String title = rs.getString("title");
		      String desc = rs.getString("description");
		      Integer releaseYear = rs.getInt("release_year");
		      Integer langId = rs.getInt("language_id");
		      Integer rentDur = rs.getInt("rental_duration");
		      Double rate = rs.getDouble("rental_rate");
		      Integer length = rs.getInt("length");
		      Double repCost = rs.getDouble("replacement_cost");
		      String rating = rs.getString("rating");
		      String features = rs.getString("special_features");
		      Film film = new Film(filmId, title, desc, releaseYear,langId , rentDur, rate, length, repCost, rating, features);
		      films.add(film);
		    }
		    rs.close();
		    stmt.close();
		    conn.close();
		  } catch (SQLException e) {
		    e.printStackTrace();
		  }
		  return films;
		}

	
//	@Override
//	public List<Film> findFilmsByActorId(int actorId) {
//		  List<Film> films = new ArrayList<>();
//		  try {
//		    Connection conn = DriverManager.getConnection(URL, user, pass);
//		    String sql = "SELECT id, title, description, release_year, language_id, rental_duration, ";
//		                sql += " rental_rate, length, replacement_cost, rating, special_features "
//		               +  " FROM film JOIN film_actor ON film.id = film_actor.film_id "
//		               + " WHERE actor_id = ?";
//		    PreparedStatement stmt = conn.prepareStatement(sql);
//		    stmt.setInt(1, actorId);
//		    ResultSet rs = stmt.executeQuery();
//		    while (rs.next()) {
//		      int filmId = rs.getInt(1);
//		      String title = rs.getString(2);
//		      String desc = rs.getString(3);
//		      short releaseYear = rs.getShort(4);
//		      int langId = rs.getInt(5);
//		      int rentDur = rs.getInt(6);
//		      double rate = rs.getDouble(7);
//		      int length = rs.getInt(8);
//		      double repCost = rs.getDouble(9);
//		      String rating = rs.getString(10);
//		      String features = rs.getString(11);
//		      Film film = new Film(filmId, title, desc, releaseYear, langId,
//		                           rentDur, rate, length, repCost, rating, features);
//		      films.add(film);
//		    }
//		    rs.close();
//		    stmt.close();
//		    conn.close();
//		  } catch (SQLException e) {
//		    e.printStackTrace();
//		  }
//		  return films;
//		}


//
//		@Override
//		public List<Actor> findActorsByFilmId(int filmId) {
//			// TODO Auto-generated method stub
//			return null;
//		}

//	@Override
//	public List<Actor> findActorsByFilmId(int filmId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}