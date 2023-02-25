package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) throws SQLException {
		FilmQueryApp app = new FilmQueryApp();
		// app.test();
		app.launch();
	}

	private void test() throws SQLException {
		Film film = db.findFilmById(1);
		Actor actor = db.findActorById(1);
		List<Actor> list = db.findActorsByFilmId(1);
		System.out.println(film);
		System.out.println(actor);
		System.out.println(list);

	}

	private void launch() throws SQLException {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) throws SQLException {
		while (true) {
			System.out.println("Enter your choice:");
			System.out.println("1. Look up a film by its id");
			System.out.println("2. Look up a film by a search keyword");
			System.out.println("3. Exit the application");

			int choice = input.nextInt();
			switch (choice) {
			case 1:
				System.out.print("Enter film id: ");
				int id = input.nextInt();
				Film filmById = db.findFilmById(id);
				if (filmById != null) {
					System.out.println("Found film: " + filmById.getTitle() + "----- Year:" + filmById.getReleaseYear()+"----Rating "+filmById.getRating()
							+ "----Description: " + filmById.getDescription()+"------Language: "+filmById.getLanguage()+"---Actors: " +filmById.getActorList());
				} else {
					System.out.println("Film with id " + id + " not found.");
				}
				break;

			case 2:
				System.out.print("Enter search keyword: ");
				int count = 0;
				String keyword = input.next();
				List<Film> filmByKeyWord = db.findFilmsByKeyword(keyword);
				if (filmByKeyWord.isEmpty()) {		
					System.out.println("Film with keyword " + keyword + " not found.");
				} else {
					for(Film film : filmByKeyWord) {
						System.out.println(++count+" "+film.getTitle() + "----- Year:" + film.getReleaseYear()+"----Rating "+film.getRating()
						+ "----Description: " + film.getDescription()+"------Language: "+film.getLanguage()+"---Actors: " +film.getActorList());
					}
					System.out.println(count +" row(s) in set ");
				}
				
				

				break;

			case 3:
				System.out.println("Exiting application.");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
				break;
			}
		}
	}

}
