package com.animeapi.animeapi;

import com.animeapi.animeapi.payload.AnimePayload;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.List;


public class AnimeCardController {
    // Chacun de ces éléments existe dans le fichier de design (anime-card.fxml) je fais juste les reprendre ici pour pouvoir les modifier.
    @FXML
    public ImageView ImageCard;
    @FXML
    public Label AnimeTitle;
    @FXML
    public Text AnimeJapaneseTitle;
    @FXML
    public Label NumberOfEpisodes;
    @FXML
    public Label AnimeRank;
    @FXML
    public Label AnimeSeason;
    @FXML
    public Label AnimeScore;
    @FXML
    public Label FirstGenre;
    @FXML
    public Label SecondGenre;
    @FXML
    public Label ThirdGenre;
    @FXML
    public HBox AnimeInfoCard;
    @FXML
    public SVGPath PlayButton;

    // Pour Garder une trace de l'anime qui correspond à la carte
    private AnimePayload CurrentAnime;

    public AnimeCardController() {
    }

    // Pour l'effet de hover
    public void handleMouseEnter(MouseEvent event) {
        // On créer un ScaleTransition (pour avoir une animation) pour cardContainer
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), AnimeInfoCard);

        // On crée un FadeTransition (pour avoir une animation de changement d'opacité) sur l'imageView
        FadeTransition imageFade = new FadeTransition(Duration.millis(200), ImageCard);

        // On augmente le x et le y de 2.5% (1 = 100%. 0.5 = 50% etc...)
        scaleTransition.setByX(0.025);
        scaleTransition.setByY(0.025);

        // Le changement d'opacité
        imageFade.setFromValue(1.0);
        imageFade.setToValue(0.7);

        // Que la carte soit au dessus des autres
        AnimeInfoCard.toFront();

        // On lance les animations
        scaleTransition.play();
        imageFade.play();

        // On rend le button play invisible
        PlayButton.setVisible(true);
    }

    // Quand on est plus sur la carte
    public void handleMouseLeave(MouseEvent event) {
        // On créer un ScaleTransition (pour avoir une animation) pour cardContainer
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), AnimeInfoCard);

        // On crée un FadeTransition (pour avoir une animation de changement d'opacité) sur l'imageView
        FadeTransition imageFade = new FadeTransition(Duration.millis(200), ImageCard);

        // On augmente le x et le y de 2.5% (1 = 100%. 0.5 = 50% etc...)
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);

        // Le changement d'opacité
        imageFade.setFromValue(0.7);
        imageFade.setToValue(1.0);

        // Que la carte soit au dessus des autres
        AnimeInfoCard.toBack();

        // On lance les animations
        scaleTransition.play();
        imageFade.play();

        // On rend le button play invisible
        PlayButton.setVisible(false);
    }

    // Afficher les données d'un anime dans le design de carte
    public void setData(AnimePayload anime) {

        AnimeTitle.setText(anime.titleName);

        AnimeJapaneseTitle.setText(anime.titleJapanese);

        // Certains épisodes ont 0 on va juste dire qu'ils sont pas encore terminé
        if (anime.NumberEpisode > 0) {
            NumberOfEpisodes.setText(anime.NumberEpisode + " episodes");
        } else {
            NumberOfEpisodes.setText("On going...");
        }
        // En java ta pas de .toString() c'est un manière de convertir in int en string le ""+
        AnimeRank.setText("" + anime.Rank);

        // Certain ont pas de saison, quand ils en ont on les affiche avec la date, sinon on enlève l'élément saison.
        if (anime.Season != null) {
            String Season = anime.Season.substring(0, 1).toUpperCase() + anime.Season.substring(1);
            AnimeSeason.setText(Season + " " + anime.date);
        } else {
            Pane parent = (Pane) AnimeSeason.getParent();
            parent.getChildren().remove(AnimeSeason);
        }

        AnimeScore.setText("" + anime.score);

        // On met tous les éléments de genre dans une liste
        List<Label> genreLabels = List.of(FirstGenre, SecondGenre, ThirdGenre);

        // En gros certain animes ont pas de genre. Du coup dépendamment du nombre de genre qu'il y a, on va devoir cacher les éléments de genre en trop.
        for (int i = 0; i < genreLabels.size(); i++) {
            // Si la taille des genres est plus petit que i actuel (imaginons c'est i=1 et la taille c'est 3)
            if (i < anime.Genres.size()) {
                // on prend l'élment design a l'index 1 donc Genre1 et on met son texte le nom du premier genre venant d'anime
                genreLabels.get(i).setText(anime.Genres.get(i).Name);
                genreLabels.get(i).setVisible(true);
            } else {
                // Sinon imaginons i=2 mais la taille des genres c'est 3, on cache l'élément design genre 3
                genreLabels.get(i).setText("");
                genreLabels.get(i).setVisible(false);
            }
        }

        // Afficher l'image de l'anime
        ImageCard.setImage(new javafx.scene.image.Image(anime.image.Pané.ImageURL));

        // On garde une trace de l'anime dans une variable
        CurrentAnime = anime;
    }

    // Méthode qui sera appeler quand le button play est cliqué.
    public void WatchAnime() {
        AnimeAPI API_Anime = new AnimeAPI();

        try {
            if (CurrentAnime.Type.equals("Movie")) {
                API_Anime.GetAnimeMovie(CurrentAnime);
            } else {
                API_Anime.GetEpisodeByAnime(CurrentAnime);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
