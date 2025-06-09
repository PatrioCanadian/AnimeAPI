package com.animeapi.animeapi;

import com.animeapi.animeapi.payload.AnimeCharacterEntry;
import com.animeapi.animeapi.payload.AnimePayload;
import com.animeapi.animeapi.payload.VoiceActor;
import com.fasterxml.jackson.databind.ext.SqlBlobSerializer;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Scanner;

public class Anime extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        AnimeAPI API_Anime = new AnimeAPI();
        try {
            List<AnimePayload> ListAnime = API_Anime.GetAnimes(1).Animes;
            AnimePayload Mabite = ListAnime.get(1);

            if (Mabite.Type.equals("Movie")) {
                API_Anime.GetAnimeMovie(Mabite);
            } else {
                API_Anime.GetEpisodeByAnime(Mabite);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        Scanner ConsoleReader = new Scanner(System.in);
//        System.out.println("1. Liste de tous les animes");
//        System.out.println("2. Obtenir anime par nom");
//        System.out.println("3. Obtenir personnages de le numéro de l'anime indiqué");
//        int Choix = Integer.parseInt(ConsoleReader.nextLine());
//        switch (Choix) {
//
//            case 1:
//                try {
//                    List<AnimePayload> ListAnime = API_Anime.GetAnimes();
//                    for (AnimePayload anime : ListAnime) {
//                        System.out.print("nom: " + anime.titlename);
//                        System.out.print(" score: " + anime.score);
//                        System.out.print(" id: " + anime.id);
//                        System.out.print(" year: " + anime.date);
//                        System.out.println(" image: " + anime.image.Pané.ImageURL);
//                    }
//                } catch (Exception Error) {
//                    System.out.println(Error.getMessage());
//                }
//                break;
//            case 2:
//                System.out.print("Entrer le nom de l'anime: ");
//                String AnimeName = ConsoleReader.nextLine();
//                try {
//                    List<AnimePayload> ListAnime = API_Anime.GetAnimeByName(AnimeName);
//                    for (AnimePayload anime : ListAnime) {
//                        System.out.println(anime.titlename);
//                    }
//                } catch (Exception Error) {
//                    System.out.println(Error.getMessage());
//                }
//                break;
//            case 3:
//                System.out.print("Entrer l'id de l'anime: ");
//                try (Scanner scanner = new Scanner(System.in)) {
//                    int animeId = scanner.nextInt();
//
//                    List<AnimeCharacterEntry> characters = API_Anime.GetCharactersByAnime(animeId);
//                    for (AnimeCharacterEntry entry : characters) {
//                        System.out.println("Nom: " + entry.Character.CharacterName);
//                        System.out.println("Rôle: " + entry.Role);
//                        System.out.println("Image: " + entry.Character.image.Pané.ImageURL);
//                        for (VoiceActor voiceActor : entry.voiceActors )
//                        {
//                            System.out.println("    Doubleur: " + voiceActor.Person.Name + " , " + voiceActor.Language);
//                            System.out.println("    Image du doubleur: " + voiceActor.Person.image.Pané.ImageURL);
//
//
//                        }
//                        System.out.println("-------------------------");
//                    }
//                } catch (Exception e) {
//                    System.out.println("Erreur: " + e.getMessage());
//                }
//                break;
//
//        }
//

    }
}
