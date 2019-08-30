package com.example.root.moviestwo.viewmodels.search;

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

public class SearchTvViewModel extends ViewModel {

    private static final String BASE_URL = BuildConfig.BASE_URL;
    private static final String SEARCH_TV_URL = BuildConfig.SEARCH_TV_URL;
    private static final String GENRE_URL = BuildConfig.GENRE_TV_URL;
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;
    private static final String QUERY = "&include_adult=false&query=";
    private static final String TAG = " ";

    private HashMap<Integer, String> mGenreMap = new HashMap<Integer, String>();
    private MutableLiveData<ArrayList<Movie>> mListTvs = new MutableLiveData<>();

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
                        int id = mGenres.getInt("id");
                        String name = mGenres.getString("name");
                        mGenreMap.put(id, name);
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

    public void setMovie(final String searchTv) {
        setGenre();
        AsyncHttpClient mClient = new AsyncHttpClient();
        final ArrayList<Movie> mListItemsTv = new ArrayList<>();
        String mUrl = BASE_URL + SEARCH_TV_URL + API_KEY + "&language=en-US" + QUERY + searchTv;
        mClient.get(mUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String mResult = new String(responseBody);
                    JSONObject mResponseObject = new JSONObject(mResult);
                    if (mResponseObject.getInt("total_results") > 0) {
                        JSONArray mListTv = mResponseObject.getJSONArray("results");
                        for (int i = 0; i < mListTv.length(); i++) {
                            JSONObject mTvShow = mListTv.getJSONObject(i);
                            String titleMovie = mTvShow.getString("name");
                            String posterMovie = mTvShow.getString("poster_path");
                            String backgroundMovie = mTvShow.getString("backdrop_path");
                            String descMovie = mTvShow.getString("overview");
                            String languageMovie = mTvShow.getString("original_language");
                            String releaseMovie = mTvShow.getString("first_air_date");
                            String popularMovie = mTvShow.getString("popularity");
                            String ratingMovie = mTvShow.getString("vote_average");
                            String categoryMovie = JSONObject.numberToString(2);

                            Movie tvShows = new Movie(
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
                            JSONArray genreId = mTvShow.getJSONArray("genre_ids");
                            for (int j = 0; j < genreId.length(); j++) {
                                int id = genreId.getInt(j);
                                String genreMovie = mGenreMap.get(id);
                                tvShows.addGenre(genreMovie);
                            }
                            mListItemsTv.add(tvShows);
                        }
                        mListTvs.postValue(mListItemsTv);
                    } else {
                        Log.d(TAG, "Data Not Found");
                    }

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
        return mListTvs;
    }

}
