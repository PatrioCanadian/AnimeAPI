package com.animeapi.animeapi;
import com.animeapi.animeapi.payload.AnimePayload;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.List;


public class AnimeCardController {
    // Chacun de ces éléments existe dans le fichier de design (anime-card.fxml) je fais juste les reprendre ici pour pouvoir les modifier.
    @FXML
    public ImageView imageView;
    @FXML
    public Label titleLabel;
    @FXML
    public Text japaneseTitleLabel;
    @FXML
    public Label episodeLabel;
    @FXML
    public Label rankLabel;
    @FXML
    public Label seasonLabel;
    @FXML
    public Label scoreLabel;
    @FXML
    public Label genre1;
    @FXML
    public Label genre2;
    @FXML
    public Label genre3;
    @FXML
    public HBox cardContainer;



    public AnimeCardController() {
    }

    // Pour l'effet de hover
    private void handleMouseEnter(MouseEvent event){
        // On créer un ScaleTransition (pour avoir une animation) pour cardContainer
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), cardContainer);
        // On augmente le x et le y de 2.5% (1 = 100%. 0.5 = 50% etc...)
        scaleTransition.setByX(0.025);
        scaleTransition.setByY(0.025);
        // Que la carte soit au dessus des autres
        cardContainer.toFront();
        // On lance l'animation
        scaleTransition.play();
    }

    // Quand on est plus sur la carte
    private void handleMouseLeave(MouseEvent event){
        // On refait un scale transition
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), cardContainer);
        // On le remet à sa taille normal avec setToX et setToY (1 = taille de base, 1.1 = 10% plus grand etc... )
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        // On le remets comme les autres
        cardContainer.toBack();
        // On lance l'animation
        scaleTransition.play();
    }

    // Afficher les données d'un anime dans le design de carte
    public void setData(AnimePayload anime) {
        titleLabel.setText(anime.titleName);
        japaneseTitleLabel.setText(anime.titleJapanese);
        // Certain épisodes ont 0 on va juste dire qu'il sont pas encore terminé
        if (anime.NumberEpisode > 0) {
            episodeLabel.setText(anime.NumberEpisode + " episodes");
        }else{
            episodeLabel.setText("On going...");
        }
        // En java ta pas de .toString() c'est un maniere de convertir in int en string le ""+
        rankLabel.setText(""+anime.Rank);
        // certain on pas de saison, quand ils en ont on les affiche avec la date, sinon on enleve l'élément saison
        if(anime.Season != null){
            String Season = anime.Season.substring(0, 1).toUpperCase() + anime.Season.substring(1);
            seasonLabel.setText(Season + " "+anime.date);
        }else{
            Pane parent = (Pane) seasonLabel.getParent();
            parent.getChildren().remove(seasonLabel);
        }
        scoreLabel.setText(""+anime.score);
        // On met tous les éléments de genre dans une liste
        List<Label> genreLabels = List.of(genre1, genre2, genre3);

        // En gros certain animes ont pas de genre. Du coup dépendamment du nombre de genre qu'il y a, on va devoir cacher les éléments design des genre en trop.
        for (int i = 0; i < genreLabels.size(); i++) {
            // Si la taille des genre est plus petit que i actuel (imaginons c'est i=1 et la taille c'est 3)
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
        imageView.setImage(new javafx.scene.image.Image(anime.image.Pané.ImageURL));
        // Quand on une souris entre ou sort de la carte, lui dire quel méthode appeler
        cardContainer.setOnMouseEntered(this::handleMouseEnter);
        cardContainer.setOnMouseExited(this::handleMouseLeave);
    }

}
