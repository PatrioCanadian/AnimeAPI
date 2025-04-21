package com.animeapi.animeapi;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class AnimeCardController {
    @FXML
    public ImageView imageView;
    @FXML
    public Label label;

    public AnimeCardController() {
    }

    public void setData(String title, String imageUrl) {
        label.setText(title);
        imageView.setImage(new javafx.scene.image.Image(imageUrl));
    }

}
