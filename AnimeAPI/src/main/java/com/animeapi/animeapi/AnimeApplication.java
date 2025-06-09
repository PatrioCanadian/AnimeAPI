package com.animeapi.animeapi;

import com.animeapi.animeapi.payload.AnimePayload;
import com.animeapi.animeapi.payload.GetAnimePayload;
import com.animeapi.animeapi.ressources.PaginationInfo;
import javafx.application.Application;
import javafx.application.Platform;
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

        //Crée la Pagination, la méthode est en bas
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

        // on ajoute le fichier de css
        scene.getStylesheets().add(getClass().getResource("/com/animeapi/animeapi/dark-theme.css").toExternalForm());
        stage.show();
    }

    // Méthode pour créer notre pagination
    public Pagination createPagination(GetAnimePayload payload) {
        // initialiser une nouvelle fois le API
        AnimeAPI api = new AnimeAPI();
        // Prend le nombre total de pages depuis la réponse de l'api
        int totalPages = payload.PaginationInfo.TotalPages;

        // On initialise la pagination
        Pagination pagination = new Pagination(totalPages, 0);
        // La liste avec les numéros en bas la pour savoir combien en afficher
        pagination.setMaxPageIndicatorCount(10);

        // On dit ce qui doit être fait quand on change de page
        pagination.setPageFactory(pageIndex -> {
            // Au début on affiche le spinner
            StackPane container = new StackPane();
            ProgressIndicator spinner = new ProgressIndicator();
            container.getChildren().add(spinner);

            // Pour le loading
            new Thread(() -> {
                try {
                    List<AnimePayload> animes = api.GetAnimes(pageIndex + 1).Animes;

                    GridPane gridPane = new GridPane();
                    gridPane.setHgap(10);
                    gridPane.setVgap(10);

                    for (int i = 0; i < animes.size(); i++) {
                        // Ce fichier c'est pour le design d'une carte. J'utilise un autre logiciel (SceneBuilder) pour les créer.
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/animeapi/animeapi/anime-card.fxml"));
                        Parent card = loader.load();
                        // Pour chacun de ces fichiers designs il peut y avoir un controller. Ça permet de gérer chaque élement dans le design.
                        AnimeCardController controller = loader.getController();
                        controller.setData(animes.get(i));
                        // Pour savoir ou placer l'élément dans la grille. le 3 c'est pour dire que c'est du 3/3
                        int col = i % 3;
                        int row = i / 3;
                        gridPane.add(card, col, row);
                    }

                    // Mets les vrai données à la place du spinner
                    Platform.runLater(() -> container.getChildren().setAll(gridPane));
                } catch (Exception e) {
                    e.printStackTrace();
                    Platform.runLater(() -> container.getChildren().setAll(new Label("Erreur lors du chargement.")));
                }
            }).start();

            return container;
        });

        // On retourne la pagination
        return pagination;
    }

    public static void main(String[] args) {
        launch();
    }
}
