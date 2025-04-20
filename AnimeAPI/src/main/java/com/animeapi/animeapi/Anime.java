package com.animeapi.animeapi;

import com.animeapi.animeapi.payload.AnimePayload;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;

public class Anime extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        AnimeAPI API_Anime = new AnimeAPI();
        try{
            List<AnimePayload> AnimeList = API_Anime.GetAnimes();
            for(AnimePayload anime : AnimeList){
                System.out.println(anime.titlename);
            }
        }
        catch (Exception Error){
            System.out.println(Error.getMessage());
        }

    }
}
