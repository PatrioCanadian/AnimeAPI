package com.animeapi.animeapi;

import com.animeapi.animeapi.payload.AnimePayload;
import com.animeapi.animeapi.payload.GetAnimePayload;
import com.animeapi.animeapi.ressources.PaginationInfo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AnimeApplication extends Application {
    // Application Principale
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        //Recevoir les données une première fois
        AnimeAPI api = new AnimeAPI();
        GetAnimePayload initialResponse = api.GetAnimes(1);

        //Crée la Pagination
        Pagination pagination = createPagination(initialResponse);

        // Crée une tabulation vide
        TabPane Tabulation = new TabPane();

        //Ajoute un scroll (Pas vraiment utile)
        ScrollPane scrollPane = new ScrollPane(pagination);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Initialiser nos différents tabs
        Tab AllAnimeTab = new Tab("Liste des animes");
        Tab SearchAnimeTab = new Tab("Recherche");
        Tab AnimeCharacterTab = new Tab("Les Persos");

        // On définit quelle tab affiche quoi
        AllAnimeTab.setContent(scrollPane);
        SearchAnimeTab.setContent(new Label("La recherche d'un anime spécifique."));
        AnimeCharacterTab.setContent(new Label("Les persos d'un anime."));

        // On ajoute nos tabs dabs la tabulations
        Tabulation.getTabs().addAll(AllAnimeTab, SearchAnimeTab, AnimeCharacterTab);
        Tabulation.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // On crée et affiche la scène
        Scene scene = new Scene(Tabulation, 1160, 600);
        stage.setScene(scene);
        stage.show();

    }

    // Méthode pour créer notre pagination
    public Pagination createPagination(GetAnimePayload Payload){
        // initialiser une nouvelle fois le API
        AnimeAPI api = new AnimeAPI();
        // Prend le nombre total de page depuis la réponse
        int totalPages = Payload.PaginationInfo.TotalPages;
        // On initialise la pagination
        Pagination pagination = new Pagination(totalPages, 0);
        pagination.setMaxPageIndicatorCount(10);
        // On dit ce qui doit être fait quand on change de page
        pagination.setPageFactory(pageIndex -> {
            try {
                // On va get les animes selon la page choisi
                List<AnimePayload> animes = api.GetAnimes(pageIndex + 1).Animes;
                // On les mets dans une grille
                GridPane gridPane = new GridPane();
                gridPane.setHgap(10);
                gridPane.setVgap(10);

                for (int i = 0; i < animes.size(); i++) {
                    // On initialise notre card
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/animeapi/animeapi/anime-card.fxml"));
                    Parent card = loader.load();
                    // On prend le controller associé
                    AnimeCardController controller = loader.getController();
                    // On l'utilise pur afficher les données
                    controller.setData(animes.get(i).titlename, animes.get(i).image.Pané.LargeImageURL);
                    // Some bullshit (Dire où ajouter la card dans la grille)
                    int col = i % 5;
                    int row = i / 5;
                    gridPane.add(card, col, row);
                    // Qu'il prenne l'espace disponible
                    GridPane.setHgrow(card, Priority.ALWAYS);
                    GridPane.setVgrow(card, Priority.ALWAYS);
                }
                // On retourne notre nouvelle grille avec les nouvelles données
                return gridPane;
            } catch (Exception e) {
                e.printStackTrace();
                // S'il y a eu une erreur on retourne une grille vide
                return new GridPane();
            }
        });

        // On retourne la pagination
        return pagination;
    }

    public static void main(String[] args) {
        launch();
    }
}