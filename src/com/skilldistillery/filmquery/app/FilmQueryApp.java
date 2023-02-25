package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
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
		//app.test();
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
                    System.out.println("Found film: " + filmById.getTitle()+"----- Year:"+filmById.getReleaseYear()+ "----Description: "+
				filmById.getDescription());
                } else {
                    System.out.println("Film with id " + id + " not found.");
                }
                break;
			}
			
		}
	}

}
