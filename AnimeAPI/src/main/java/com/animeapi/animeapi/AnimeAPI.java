package com.animeapi.animeapi;

import com.animeapi.animeapi.payload.AnimeCharacterEntry;
import com.animeapi.animeapi.payload.AnimePayload;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
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

    public List<AnimePayload> GetAnimes() throws IOException, InterruptedException {
        HttpResponse<String> Response = MakeRequest("anime");
        JsonNode JsonResponse = Converter.readTree(Response.body());
        JsonNode JsonAnimes = JsonResponse.get("data");
        List<AnimePayload> AnimeList = Converter.convertValue(JsonAnimes, new TypeReference<List<AnimePayload>>() {});
        return AnimeList;
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
}



