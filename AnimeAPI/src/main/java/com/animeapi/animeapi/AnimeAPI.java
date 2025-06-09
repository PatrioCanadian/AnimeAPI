package com.animeapi.animeapi;

import com.animeapi.animeapi.payload.AnimeCharacterEntry;
import com.animeapi.animeapi.payload.AnimePayload;
import com.animeapi.animeapi.payload.GetAnimePayload;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class AnimeAPI {
    private String API_Url = "https://api.jikan.moe/v4/";
    private HttpClient ClientHttp;
    private HttpRequest.Builder RequestBuilder;
    private ObjectMapper Converter = new ObjectMapper();

    public AnimeAPI(){
        ClientHttp = HttpClient.newHttpClient();
        RequestBuilder = HttpRequest.newBuilder();
    }
    private HttpResponse MakeRequest(String ressource) throws IOException, InterruptedException {
        HttpRequest Request = RequestBuilder.uri(URI.create(API_Url + ressource)).GET().build();
        HttpResponse<String> Response = ClientHttp.send(Request,HttpResponse.BodyHandlers.ofString());
        return Response;
    }

    public GetAnimePayload GetAnimes(int PageNumber) throws IOException, InterruptedException {
        HttpResponse<String> Response = MakeRequest("anime?limit=6&page="+PageNumber);
        JsonNode JsonResponse = Converter.readTree(Response.body());
        GetAnimePayload AnimePayload = Converter.convertValue(JsonResponse, new TypeReference<GetAnimePayload>() {});
        return AnimePayload;
    }

    public List<AnimePayload> GetAnimeByName(String name) throws IOException, InterruptedException {
      HttpResponse<String> Response = MakeRequest("anime?q=" + name);
      JsonNode JsonResponse = Converter.readTree((Response.body()));
      JsonNode JsonAnimes = JsonResponse.get("data");
      List<AnimePayload> AnimeList = Converter.convertValue(JsonAnimes, new TypeReference<List<AnimePayload>>() {});
      return AnimeList;
    }

    public List<AnimeCharacterEntry> GetCharactersByAnime(int id) throws IOException, InterruptedException {
        HttpResponse<String> response = MakeRequest("anime/" + id + "/characters");
        JsonNode jsonResponse = Converter.readTree(response.body());
        JsonNode jsonData = jsonResponse.get("data");
        return Converter.convertValue(jsonData, new TypeReference<List<AnimeCharacterEntry>>() {});
    }

    public void GetEpisodeByAnime(AnimePayload InfosAnime) throws URISyntaxException, IOException {
        // one-piece
        String formattedName
                = InfosAnime.titleName.replace(" ", "-");
        //v6.voiranime.com/anime/{nom}/{nom}-{episode}-vostfr/
        int firstEpisode = 1;
        String numEpisode = String.valueOf(InfosAnime.NumberEpisode);
        int zerosCount = numEpisode.length() > 1 ? numEpisode.length() : numEpisode.length() + 1;
        String formattedEpisodeURL = formattedName
                + "-" + String.format("%0"+ zerosCount + "d", firstEpisode);
        String URLLink = "https://v6.voiranime.com/anime/" + formattedName
                + "/" + formattedEpisodeURL + "-" + "vostfr";
        Desktop desktop = Desktop.getDesktop();
        URI url = new URI(URLLink);
        desktop.browse(url);
    }

    public void GetAnimeMovie(AnimePayload InfosAnime) throws URISyntaxException, IOException {
        // one-piece
        String formattedName
                = InfosAnime.titleName.replaceAll("[ :]+", "-");
        //v6.voiranime.com/anime/{nom}/{nom}-{episode}-vostfr/
        int firstEpisode = 1;
        String numEpisode = String.valueOf(InfosAnime.NumberEpisode);
        int zerosCount = numEpisode.length() > 1 ? numEpisode.length() : numEpisode.length() + 1;
        String formattedMovieURL = "film-vostfr-" + formattedName;
        String URLLink = "https://v6.voiranime.com/anime/" + formattedName
                + "/" + formattedMovieURL;
        Desktop desktop = Desktop.getDesktop();
        URI url = new URI(URLLink);
        desktop.browse(url);
    }
}



