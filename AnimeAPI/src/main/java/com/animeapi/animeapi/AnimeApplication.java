package com.animeapi.animeapi;

import com.animeapi.animeapi.payload.AnimePayload;
import com.animeapi.animeapi.payload.GetAnimePayload;
import com.animeapi.animeapi.ressources.PaginationInfo;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
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
        TabPane tabPane = new TabPane();

        //Ajoute un scroll (Pas vraiment utile)
        ScrollPane scrollPane = new ScrollPane(pagination);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Initialiser nos différents tabs
        Tab allAnimeTab = new Tab("Liste des animes", scrollPane);
        Tab searchAnimeTab = new Tab("Recherche", new Label("La recherche d'un anime spécifique."));
        Tab animeCharacterTab = new Tab("Les Persos", new Label("Les persos d'un anime."));

        // On ajoute nos tabs dans la tabulation
        tabPane.getTabs().addAll(allAnimeTab, searchAnimeTab, animeCharacterTab);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // On crée et affiche la scène
        Scene scene = new Scene(tabPane, 1160, 600);
        stage.setScene(scene);
        stage.show();
    }

    // Méthode pour créer notre pagination
    public Pagination createPagination(GetAnimePayload payload) {
        // initialiser une nouvelle fois le API
        AnimeAPI api = new AnimeAPI();
        // Prend le nombre total de pages depuis la réponse
        int totalPages = payload.PaginationInfo.TotalPages;

        // On initialise la pagination
        Pagination pagination = new Pagination(totalPages, 0);
        pagination.setMaxPageIndicatorCount(10);

        // On dit ce qui doit être fait quand on change de page
        pagination.setPageFactory(pageIndex -> {
            // Conteneur qui affiche un spinner en attendant les données
            StackPane pageContainer = new StackPane();
            ProgressIndicator spinner = new ProgressIndicator();
            pageContainer.getChildren().add(spinner);

            // Tâche asynchrone pour charger les données d'une page
            Task<GridPane> loadPageTask = new Task<>() {
                @Override
                protected GridPane call() throws Exception {
                    List<AnimePayload> animes = api.GetAnimes(pageIndex + 1).Animes;

                    GridPane gridPane = new GridPane();
                    gridPane.setHgap(10);
                    gridPane.setVgap(10);

                    for (int i = 0; i < animes.size(); i++) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/animeapi/animeapi/anime-card.fxml"));
                        Parent card = loader.load();
                        AnimeCardController controller = loader.getController();
                        controller.setData(animes.get(i).titlename, animes.get(i).image.Pané.LargeImageURL);

                        int col = i % 5;
                        int row = i / 5;
                        gridPane.add(card, col, row);
                        GridPane.setHgrow(card, Priority.ALWAYS);
                        GridPane.setVgrow(card, Priority.ALWAYS);
                    }

                    return gridPane;
                }

                @Override
                protected void succeeded() {
                    pageContainer.getChildren().setAll(getValue());
                }

                @Override
                protected void failed() {
                    pageContainer.getChildren().setAll(new Label("Erreur lors du chargement de la page."));
                }
            };

            new Thread(loadPageTask).start();

            return pageContainer;
        });

        // On retourne la pagination
        return pagination;
    }

    public static void main(String[] args) {
        launch();
    }
}
