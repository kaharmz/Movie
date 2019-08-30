package com.example.root.moviestwo.viewmodels;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.root.moviestwo.BuildConfig;
import com.example.root.moviestwo.entity.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class TvShowViewModel extends ViewModel {

    private static final String BASE_URL = BuildConfig.BASE_URL;
    private static final String TV_URL = BuildConfig.TV_URL;
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private static final String GENRE_URL = BuildConfig.GENRE_TV_URL;
    private MutableLiveData<ArrayList<Movie>> mListTvShow = new MutableLiveData<>();
    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, String> genreMap = new HashMap<Integer, String>();

    private void setGenre() {
        AsyncHttpClient mClient = new AsyncHttpClient();
        String mUrl = BASE_URL + GENRE_URL + API_KEY + "&language=en-US";
        mClient.get(mUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String mResult = new String(responseBody);
                    JSONObject mResponseObject = new JSONObject(mResult);
                    JSONArray mListGenre = mResponseObject.getJSONArray("genres");
                    for (int i = 0; i < mListGenre.length(); i++) {
                        JSONObject mGenres = mListGenre.getJSONObject(i);
                        int mId = mGenres.getInt("id");
                        String mName = mGenres.getString("name");
                        genreMap.put(mId, mName);
                    }
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void setTvShow() {
        setGenre();
        AsyncHttpClient mClient = new AsyncHttpClient();
        final ArrayList<Movie> mListItemsTvShow = new ArrayList<>();
        String url = BASE_URL + TV_URL + API_KEY + "&language=en-US";
        mClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String mResult = new String(responseBody);
                    JSONObject mResponseObject = new JSONObject(mResult);
                    JSONArray mListTvShows = mResponseObject.getJSONArray("results");
                    for (int i = 0; i < mListTvShows.length(); i++) {
                        JSONObject mTvShows = mListTvShows.getJSONObject(i);
                        String titleMovie = mTvShows.getString("name");
                        String posterMovie = mTvShows.getString("poster_path");
                        String backgroundMovie = mTvShows.getString("backdrop_path");
                        String descMovie = mTvShows.getString("overview");
                        String languageMovie = mTvShows.getString("original_language");
                        String releaseMovie = mTvShows.getString("first_air_date");
                        String popularMovie = mTvShows.getString("popularity");
                        String ratingMovie = mTvShows.getString("vote_average");
                        String categoryMovie = JSONObject.numberToString(2);
                        Movie mTvShow = new Movie(
                                titleMovie,
                                posterMovie,
                                backgroundMovie,
                                descMovie,
                                releaseMovie,
                                languageMovie,
                                popularMovie,
                                ratingMovie,
                                categoryMovie
                        );
                        JSONArray genreId = mTvShows.getJSONArray("genre_ids");
                        for (int j = 0; j < genreId.length(); j++) {
                            int id = genreId.getInt(j);
                            String genreMovie = genreMap.get(id);
                            mTvShow.addGenre(genreMovie);
                        }
                        mListItemsTvShow.add(mTvShow);
                    }
                    mListTvShow.postValue(mListItemsTvShow);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<Movie>> getTvShow() {
        return mListTvShow;
    }

}
