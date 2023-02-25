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

	public static void main(String[] args)  {
		FilmQueryApp app = new FilmQueryApp();
		// app.test();
		try {
			app.launch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
					int choice2;
					System.out.println("Enter your choice:");
					System.out.println("1. Return to main menu");
					System.out.println("2. View all film details");
					choice2=input.nextInt();
					switch (choice2) {
					case 1:
						System.out.println("returning to main menu");
						break;
					case 2:
						System.out.println(db.findFilmById(id));
						break;
					default:
						System.out.println("Invalid choice. Please try again.");
						break;
					}
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
					long startTime = System.currentTimeMillis();
					for(Film film : filmByKeyWord) {
						count++;
						System.out.println(film.getTitle() + "----- Year:" + film.getReleaseYear()+"----Rating "+film.getRating()
						+ "----Description: " + film.getDescription()+"------Language: "+film.getLanguage()+"---Actors: " +film.getActorList()+"----Category "+film.getCategory());
					}
					long endTime = System.currentTimeMillis();

					float duration = (endTime - startTime)/1000F;
					System.out.println(count+" row(s) in set ("+(duration)+" seconds)");
					
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
