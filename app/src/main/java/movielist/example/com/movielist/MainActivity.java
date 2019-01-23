package movielist.example.com.movielist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import movielist.example.com.movielist.model.MovieController;
import movielist.example.com.movielist.model.MovieDetail;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView moviesRecyclerView;
    private RecyclerView.Adapter moviesAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moviesRecyclerView = findViewById(R.id.movieList);

        layoutManager = new LinearLayoutManager(this);
        moviesRecyclerView.setLayoutManager(layoutManager);

        moviesAdapter = new MoviesAdapter();
        moviesRecyclerView.setAdapter(moviesAdapter);
    }

    public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
        class MovieViewHolder extends RecyclerView.ViewHolder {
            TextView nameTextView;
            TextView descriptionTextView;
            TextView ratingTextView;

            MovieViewHolder(View v) {
                super(v);
                nameTextView = v.findViewById(R.id.movieName);
                descriptionTextView = v.findViewById(R.id.description);
                ratingTextView = v.findViewById(R.id.rating);
            }
        }

        @Override
        public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
            return new MovieViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MovieViewHolder holder, int position) {
            String name = MovieController.getMovies()[position].getName();
            holder.nameTextView.setText(name);
            MovieDetail details = MovieController.getMovieDetails(name);
            holder.descriptionTextView.setText(details.getDescription());
            holder.ratingTextView.setText(String.valueOf(Math.round(details.getScore() * 100.0) / 100.0));

            holder.itemView.setOnClickListener(view -> {
                Intent movieIntent = new Intent(MainActivity.this, MovieActivity.class);
                movieIntent.putExtra(Constants.MOVIE_NAME, name);
                MainActivity.this.startActivity(movieIntent);
            });
        }

        @Override
        public int getItemCount() {
            return MovieController.getMovies().length;
        }
    }
}
