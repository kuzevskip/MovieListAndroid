package movielist.example.com.movielist.model;


import android.util.Log;

import movielist.example.com.movielist.MainActivity;

public class MovieController {

    public static final String TAG = MainActivity.class.getSimpleName();
    static {
        try {
            System.loadLibrary("movies-lib");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            Log.d(TAG, "Movies library failed to load.\\n\" + " + e);
        }
    }

    public static native Movie[] getMovies();
    public static native MovieDetail getMovieDetails(String name);
    public static native Actor[] getMovieActors(String name);
}
