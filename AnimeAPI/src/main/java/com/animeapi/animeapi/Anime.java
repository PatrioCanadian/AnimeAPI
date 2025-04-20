package com.animeapi.animeapi;

import com.animeapi.animeapi.payload.AnimePayload;
import com.fasterxml.jackson.databind.ext.SqlBlobSerializer;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;
import java.util.Scanner;

public class Anime extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        AnimeAPI API_Anime = new AnimeAPI();

        Scanner ConsoleReader = new Scanner(System.in);
        System.out.println("1. Liste de tous les animes");
        System.out.println("2. Obtenir anime par nom");
        int Choix = Integer.parseInt(ConsoleReader.nextLine());
        switch (Choix) {

            case 1:
                try {
                    List<AnimePayload> ListAnime = API_Anime.GetAnimes();
                    for (AnimePayload anime : ListAnime) {
                        System.out.println(anime.titlename);
                    }
                } catch (Exception Error) {
                    System.out.println(Error.getMessage());
                }
                break;
            case 2:
                System.out.print("Entrer le nom de l'anime: ");
                String AnimeName = ConsoleReader.nextLine();
                try {
                    List<AnimePayload> ListAnime = API_Anime.GetAnimeByName(AnimeName);
                    for (AnimePayload anime : ListAnime) {
                        System.out.println(anime.titlename);
                    }
                } catch (Exception Error) {
                    System.out.println(Error.getMessage());
                }
                break;


        }
        

    }
}
