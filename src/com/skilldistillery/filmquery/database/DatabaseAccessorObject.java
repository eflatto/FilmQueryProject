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
		List<Actor> actors = new ArrayList<>();
		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT film.id, title ,description ,category.name ,release_year , language_id,language.name , rental_duration, rental_rate,length ,";
		sql += " replacement_cost, rating,special_features  FROM film JOIN language ON language.id=language_id JOIN film_category ON film.id = film_id JOIN category ON category.id = category_id WHERE film.id = ?";
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
			film.setLanguage(filmResult.getString("language.name"));
			film.setCategory(filmResult.getString("category.name"));
			actors = findActorsByFilmId(film.getId());
			film.setActorList(actors);

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
		
		return actor;
	}
	@Override
	public List<Actor> findActorsByFilmId(int filmIde) {
		// TODO Auto-generated method stub
		List<Actor> actorList = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT first_name ,last_name, actor.id,film_id  FROM actor  JOIN film_actor ON actor.id = actor_id "
					+ "JOIN film ON film_id = film.id WHERE film_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmIde);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Integer id = rs.getInt("id");
				String fname = rs.getString("first_name");
				String lname = rs.getString("last_name");

				Actor actor = new Actor(id, fname, lname);
				actorList.add(actor);

			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actorList;

	}

	public List<Film> findFilmsByActorId(int actorId) {
		List<Film> films = new ArrayList<>();
		List<Actor> actors = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT first_name ,last_name, actor.id,film_id  FROM actor JOIN film_actor ON actor.id = actor_id "
					+ "+JOIN film ON film_id = film.id WHERE actor.id = ?;";
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

				Film film = new Film(filmId, title, desc, releaseYear, langId, rentDur, rate, length, repCost, rating,
						features);
				films.add(film);
				actors = findActorsByFilmId(film.getId());
				film.setActorList(actors);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}

	public List<Film> findFilmsByKeyword(String word) {
		List<Film> films = new ArrayList<>();
		List<Actor> actors = new ArrayList<>();

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT film.id,category.name, title ,description ,release_year , language_id , rental_duration, rental_rate,length ,";
			sql += " replacement_cost, rating,special_features, language.name  FROM film JOIN language ON language.id=language_id JOIN film_category ON film.id = film_id JOIN category ON category.id = category_id WHERE film.title LIKE ? OR film.description LIKE ?";
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, "%" + word + "%");
			stmt.setString(2, "%" + word + "%");

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
				String language = rs.getString("language.name");
				String category = rs.getString("category.name");
				Film film = new Film(filmId, title, desc, releaseYear, langId, rentDur, rate, length, repCost, rating,
						features, language);
				film.setCategory(category);
				films.add(film);
				actors = findActorsByFilmId(film.getId());
				film.setActorList(actors);
				
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}

}
